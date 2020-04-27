package ro.utcluj.ClientServer;

import ro.utcluj.View.LoginView;

import java.io.*;
import java.net.Socket;
import java.time.Instant;

public class Client
{
    public static class Connection extends Thread
    {
        private final Socket socket;
        private final ObjectOutputStream output;
        private final ObjectInputStream input;

        public Connection(Socket socket) throws IOException
        {
            this.socket = socket;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            
        }

        @Override
        public void run()
        {
            try
            {
                while (socket.isConnected())
                {
                    // Seems that input.available() is not reliable?
                    boolean serverHasData = socket.getInputStream().available() > 0;
                    if (serverHasData) {
                        String msg = (String) input.readObject();
                        System.out.println(Instant.now() + " Got from server: " + msg);
                    }

                    try
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.println(Instant.now() + " Server disconnected");
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        public void sendMessageToServer(String message) throws IOException
        {
            output.writeObject(message);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Connection connection = new Connection(new Socket("localhost", 3000));
        connection.start();

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Main app loop.");
        while (true)
        {
            System.out.println(Instant.now() + " Type the message to send to the server and press enter:");

            String message = consoleInput.readLine();
            connection.sendMessageToServer(message);
        }
    }
}
