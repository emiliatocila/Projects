package ro.utcluj;

import ro.utcluj.View.LoginView;

public class Main{
    public static void main(String[] args) {
        ApplicationSession applicationSession = new ApplicationSession();
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }
}
