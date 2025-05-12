package ru.nsu.martynov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private static final int PORT = 12345;

    public static void main(String[] args) {
//        System.setOut(new java.io.PrintStream(System.out) {
//            @Override
//            public void println(String x) {
//                super.println(x);
//                if (x != null && x.trim().equals("0")) {
//                    System.err.println("⚠ println(\"0\") called");
//                    Thread.dumpStack();
//                }
//            }
//
//            @Override
//            public void println(int x) {
//                super.println(x);
//                if (x == 0) {
//                    System.err.println("⚠ println(0) called");
//                    Thread.dumpStack();
//                }
//            }
//        });


        Settings settings = new Settings();
        ServerEngine serverEngine = new ServerEngine(settings);

        Thread connectionThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Сервер запущен на порту " + PORT);

                // Обработчик завершения по сигналу (например, Ctrl+C)
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    System.out.println("\nЗавершение работы сервера...\n");
                    serverEngine.shutdown(); // Метод для отключения клиентов и остановки GameEngine
                }));

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Клиент подключён: " + clientSocket.getInetAddress());
                    serverEngine.addClient(clientSocket);
                }
            } catch (IOException e) {
                System.err.println("Ошибка в соединении: " + e.getMessage());
            }
        });

        connectionThread.start();

        // Основной поток можно оставить для логики завершения сервера, логирования и т.п.
    }
}
