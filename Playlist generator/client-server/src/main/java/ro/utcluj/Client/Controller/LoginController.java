package ro.utcluj.Client.Controller;

import ro.utcluj.Client.Client;
import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.ClientAndServer.Communication.RequestHandler;
import ro.utcluj.Client.View.ILoginView;

public class LoginController {
    private final RequestHandler requestHandler;
    private final ILoginView loginView;

    public LoginController(ILoginView loginView) {
        this.loginView = loginView;
        requestHandler = new RequestHandler();
    }

    public void login() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        String encodedRequest = requestHandler.encodeRequest("LOGIN", "username=" + username + "#password=" + password + "#");
        String encodedResponse = Client.getConnection().sendRequestToServer(encodedRequest);
        User user = (User) requestHandler.decodeResponse(encodedResponse, User.class).get(0);

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
