package ru.nsu.martynov;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private final Socket socket;
    private final int clientID;
    private final ObjectOutputStream out;
    private ObjectInputStream in;

    private ServerEngine serverEngine;

    public ClientConnection(Socket socket, int clientID, ServerEngine serverEngine) {
        this.socket = socket;
        this.clientID = clientID;
        this.serverEngine = serverEngine;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании потока вывода", e);
        }
        System.out.println("snake1" + serverEngine.getGameEngine().getGameMap().getSnakeById(clientID));
        try {
            System.out.println("apple");
            this.in = new ObjectInputStream(socket.getInputStream());
            System.out.println("pine");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании потока ввода", e);
        }
        System.out.println("snake2");
        System.out.println("snake2" + serverEngine.getGameEngine().getGameMap().getSnakeById(clientID));
        listenToClient();
    }

    private void listenToClient() {
        if (in == null) {
            System.out.println("in null");
            return;
        }
        new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    Object obj = in.readObject();
                    if (obj instanceof Direction) {
                        Direction direction = (Direction) obj;
                        System.out.println(direction);
                        sendMessage(direction.toString());
                        Snake snake = serverEngine.getGameEngine().getGameMap().getSnakeById(clientID);
                        System.out.println(snake);
                        snake.setDirection(direction);
                        System.out.println(snake);
                    }
                    // если позже появятся другие типы — обработаем их тут
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ошибка чтения от клиента: " + e.getMessage());
//                serverEngine.disconnectClient(this);
            }
        }).start();

        System.out.println(15);
    }

    public void onDirectionReceived(ClientConnection client, Direction direction) {
        int clientId = client.getClientID(); // или другой способ идентификации

        Snake snake = serverEngine.getGameEngine().getGameMap().getSnakeById(clientId);
        if (snake != null) {
            snake.setDirection(direction);
        }
    }

    public void sendGameMap(GameMap gameMap, int id, boolean gameOver, boolean gameWon) {
        try {
            GameStateDTO gameStateDTO = new GameStateDTO(gameMap, id, gameOver, gameWon);
            out.reset(); // очистка кеша объектов
            out.writeObject(gameStateDTO);
            out.flush();
        } catch (IOException e) {
            System.err.println("Ошибка при отправке GameMap клиенту " + clientID);
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println("Ошибка при отправке сообщения клиенту " + clientID);
            e.printStackTrace();
        }
    }

    public int getClientID() {
        return clientID;
    }

    public void close() {
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии потока вывода клиента " + clientID);
            e.printStackTrace();
        }

        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии сокета клиента " + clientID);
            e.printStackTrace();
        }
    }
}
