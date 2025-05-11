package ru.nsu.martynov;

import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class ServerEngine implements GameUpdateListener {

    private final Settings settings;
    private final Map<Integer, ClientConnection> clients = new ConcurrentHashMap<>();
    private GameEngine gameEngine = null;

    public ServerEngine(Settings settings) {
        this.settings = settings;
    }

    public synchronized void addClient(Socket socket) {
        if (gameEngine == null) {
            startGameEngine();
        }

        int clientID = gameEngine.getNextClientID();

        ClientConnection connection = new ClientConnection(socket, clientID, this);
        clients.put(clientID, connection);

        if (gameEngine.getGameMap().spawnSnake(clientID)) {
            connection.sendMessage("Вы подключились к игре!");
        } else {
            connection.sendMessage("gameEngine.getGameMap().spawnSnake(clientID) == false :(");
        }
    }

    public synchronized void removeClient(ClientConnection client) {

        int clientID = client.getClientID();

        clients.remove(clientID);
        if (gameEngine == null || gameEngine.getGameMap() == null) {
            return;
        }

        gameEngine.getGameMap().removeSnake(
                gameEngine.getGameMap().getSnakeById(clientID)
        );

        if (!gameEngine.getNoPlayerYet()) {
            if (clients.isEmpty() || gameEngine.getGameMap().getSnakes().isEmpty()) {
                stopGameEngine(false);
            }
        }
    }

    private void startGameEngine() {
        if (gameEngine != null) {
            stopGameEngine(false);
        }

        gameEngine = new GameEngine(settings);
        gameEngine.setUpdateListener(this); // <-- наша связь
        gameEngine.start();
    }

    private void stopGameEngine(boolean inside) {
        System.out.println("stopGameEngine");
        if (gameEngine == null) {
            return;
        }
        gameEngine.stop(inside);
        gameEngine = null;
    }

    // Метод от GameUpdateListener: вызывается каждый тик
    @Override
    public void onGameMapUpdated(GameMap gameMap) {
        if (gameMap.getSnakes().isEmpty()) {
            if (!gameEngine.getNoPlayerYet()) {
                stopGameEngine(true);
            }

            for (ClientConnection client : clients.values()) {
                removeClient(client);
            }

            clients.clear();
        }

        for (ClientConnection client : clients.values()) {
            int id = client.getClientID();
            client.sendGameMap(gameMap, id, false, false);
        }
    }

    @Override
    public void onSnakeWin(Snake snake, GameMap gameMap) {
        int clientID = snake.getID();
        int score = snake.getBody().size();
        ClientConnection client = clients.get(clientID);

        if (client == null) {
            return;
        }

        client.sendMessage("Вы выиграли, ваша длина: " + score + "!");
        client.sendGameMap(gameMap, clientID, true, true);

        ClientConnection clientConnection = clients.get(clientID);
        removeClient(clientConnection);
    }

    @Override
    public void onSnakeDeath(Snake snake, GameMap gameMap) {
        int clientID = snake.getID();
        int score = snake.getBody().size();
        ClientConnection client = clients.get(clientID);

        if (client == null) {
            return;
        }

        client.sendMessage("Вы проиграли, ваша длина: " + score + "!");
        client.sendGameMap(gameMap, clientID, true, false);

        ClientConnection clientConnection = clients.get(clientID);
        removeClient(clientConnection);
    }

    private void broadcastMessage(String message) {
        for (ClientConnection client : clients.values()) {
            client.sendMessage(message);
        }
    }

    public void shutdown() {
        stopGameEngine(false);

        for (ClientConnection client : clients.values()) {
            client.sendMessage("Сервер отключается. До свидания!");
            client.close(); // метод close() должен закрыть ObjectOutputStream и сокет
        }

        clients.clear();
        System.out.println("Все клиенты отключены. Сервер завершил работу.");
    }

    public GameEngine getGameEngine() {
        System.out.println(gameEngine.toString());
        return gameEngine;
    }
}
