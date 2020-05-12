package ro.utcluj.Client;

import ro.utcluj.ClientAndServer.Communication.LiveNotificationResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Instant;

public class ClientConnectionToServer extends Thread {
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final LiveNotificationResponse liveNotificationResponse;

    public ClientConnectionToServer(Socket socket) throws IOException {
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        liveNotificationResponse = new LiveNotificationResponse();
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                boolean serverHasData = socket.getInputStream().available() > 0;
                if (serverHasData)
                    this.checkLiveNotification();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Instant.now() + " Server disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //cleanup
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendRequestToServer(String message) {
        try {
            output.writeObject(message);
            while(true) {
                String response = (String) input.readObject();
                System.out.println(Instant.now() + "Server responded with: " + message);
                if (response != null) {
                    if (response.length() > 15) {
                        if (response.substring(0, 16).equals("LIVENOTIFICATION"))
                            System.out.println(Instant.now() + "Skip reading live notification message: " + response);
                        else
                            return response;
                    }
                    else
                        return response;
                }
                else
                    return response;
            }
        } catch (IOException e) {
            System.out.println(Instant.now() + "Cannot send message to server!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Instant.now() + "Cannot read message from server!");
        } return null;
    }

    public void checkLiveNotification() {
        try {
            String response = (String) input.readObject();
            liveNotificationResponse.addObserver(Client.getRegUserController());
            liveNotificationResponse.handle(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
