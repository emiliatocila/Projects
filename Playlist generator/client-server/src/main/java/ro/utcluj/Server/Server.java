package ro.utcluj.Server;

import ro.utcluj.ClientAndServer.Communication.LiveNotificationHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<ServerConnectionToClient> clients = new ArrayList<ServerConnectionToClient>();

    public static void main(String[] args) throws IOException{

        try (ServerSocket socket = new ServerSocket(3000)) {
            while (true) {
                Socket clientSocket = socket.accept();
                LiveNotificationHandler liveNotificationHandler = new LiveNotificationHandler();
                ServerConnectionToClient serverConnectionToClient =  new ServerConnectionToClient(clientSocket, liveNotificationHandler);
                clients.add(serverConnectionToClient);
                liveNotificationHandler.addObserver(serverConnectionToClient);
                serverConnectionToClient.start();
            }
        }
    }

    public static void broadcastMessage(String message) {
        for (ServerConnectionToClient client : clients) {
            client.sendMessageToClients(message);
        }
    }
}

