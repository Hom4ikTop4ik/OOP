package ru.nsu.martynov;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private final Socket socket;
    private final int clientID;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private volatile boolean isClosed = false;

    private final ServerEngine serverEngine;

    public boolean getIsClosed() {
        return isClosed;
    }

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

        try {
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании потока ввода", e);
        }

        listenToClient();
    }

    private void listenToClient() {
        new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    Object obj = in.readObject();
                    if (obj instanceof Direction) {
                        Direction direction = (Direction) obj;
                        onDirectionReceived(direction);
                        System.out.println(direction);
                    }
                    // если позже появятся другие типы — обработаем их тут
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ошибка чтения от клиента: " + e.getMessage());
                close();
            } finally {
                close();
            }
        }).start();
    }

    public void onDirectionReceived(Direction direction) {
        Snake snake = serverEngine.getGameEngine().getGameMap().getSnakeById(clientID);
        if (snake != null) {
            snake.setDirection(direction);
        }
    }

    public void sendGameMap(GameMap gameMap, boolean gameOver, boolean gameWon) {
        try {
            GameStateDTO gameStateDTO = new GameStateDTO(gameMap, this.getClientID(), gameOver, gameWon);
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
        this.isClosed = true;
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии потока вывода клиента " + clientID);
            e.printStackTrace();
        }

        try {
            if (in != null) {
                in.close();
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
