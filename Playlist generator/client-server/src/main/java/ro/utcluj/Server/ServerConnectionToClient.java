package ro.utcluj.Server;

import ro.utcluj.ClientAndServer.Communication.LiveNotificationHandler;
import ro.utcluj.ClientAndServer.Communication.RequestHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Instant;
import java.util.Observable;
import java.util.Observer;

public class ServerConnectionToClient extends Thread implements Observer {
    private final Socket clientSocket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private RequestHandler requestHandler;
    private LiveNotificationHandler liveNotificationHandler;

    public ServerConnectionToClient(Socket clientSocket, LiveNotificationHandler liveNotificationHandler) throws IOException {
        this.clientSocket = clientSocket;
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());
        this.liveNotificationHandler = liveNotificationHandler;
        requestHandler = new RequestHandler(liveNotificationHandler);
    }

    @Override
    public void run() {
        try {
            ApplicationSession applicationSession = new ApplicationSession();
            while (clientSocket.isConnected()) {
                boolean clientHasData = clientSocket.getInputStream().available() > 0;
                if (clientHasData) {
                    this.handleRequestFromClient();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Instant.now() + " Client disconnected?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // cleanup
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRequestFromClient() {
        try {
            String msg = (String) input.readObject();
            System.out.println(Instant.now() + " Got from client: " + msg);
            String response = requestHandler.handleRequest(msg);
            System.out.println(Instant.now() + " Sending response: " + response);
            this.sendMessageToClients(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String responseMessage = ((String)arg);
        System.out.println(Instant.now() + " Sending response: " + responseMessage);
        Server.broadcastMessage(responseMessage);
    }

    public void sendMessageToClients(String message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
