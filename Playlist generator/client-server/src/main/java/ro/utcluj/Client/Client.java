package ro.utcluj.Client;

import ro.utcluj.Client.Controller.RegUserController;
import ro.utcluj.Client.View.LoginView;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static ClientConnectionToServer connection;
    private static RegUserController regUserController;

    public static ClientConnectionToServer getConnection() {
        return connection;
    }
    public static void setRegUserController(RegUserController regUserControllerGet) { regUserController = regUserControllerGet; }
    public static RegUserController getRegUserController() { return regUserController; }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        connection = null;
        try {
            connection = new ClientConnectionToServer(new Socket("localhost", 3000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.start();
    }
}
