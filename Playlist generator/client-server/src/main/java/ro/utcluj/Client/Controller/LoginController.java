package ro.utcluj.Client.Controller;

import ro.utcluj.ClientAndServer.Communication.IRequestHandler;
import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.ClientAndServer.Communication.RequestHandler;
import ro.utcluj.Client.View.ILoginView;
import javax.swing.*;

public class LoginController {
    private final IRequestHandler requestHandler;
    private final ILoginView loginView;

    public LoginController(ILoginView loginView) {
        this.loginView = loginView;
        requestHandler = new RequestHandler();
    }

    public LoginController(ILoginView loginView, IRequestHandler requestHandler) {
        this.loginView = loginView;
        this.requestHandler = requestHandler;
    }

    public void login() {
        String params = "";
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        params += "username=" + username + "#password=" + password + "#";

        User user = (requestHandler.getResult("LOGIN", params, User.class)).get(0);

        if (user == null)
            loginView.showErrorMessage("Invalid username/password");
        else {

            if (user.getIsAdmin() == 0){
                User finalUser1 = user;
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        loginView.showLoadingScreen();
                        loginView.showRegularView(finalUser1);
                        return true;
                    }

                    @Override
                    protected void done() {
                        loginView.setVisibleLoginView(false);
                    }
                };
                worker.execute();
            }
            else if (user.getIsAdmin() == 1) {
                User finalUser = user;
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        loginView.showLoadingScreen();
                        loginView.showAdminView(finalUser);
                        return true;
                    }

                    @Override
                    protected void done() {
                        loginView.setVisibleLoginView(false);
                    }
                };
                worker.execute();
            }
        }
    }

    public void loginForUT() {
        String params = "";
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        params += "username=" + username + "#password=" + password + "#";

        User user = (requestHandler.getResult("LOGIN", params, User.class)).get(0);

        if (user == null)
            loginView.showErrorMessage("Invalid username/password");
        else {
            if (user.getIsAdmin() == 0){
                loginView.showRegularView(user);
                loginView.setVisibleLoginView(false);
            }
            else if (user.getIsAdmin() == 1) {
                loginView.showAdminView(user);
                loginView.setVisibleLoginView(false);
            }
        }
    }

}
