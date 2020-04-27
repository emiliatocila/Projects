package ro.utcluj.Controller;

import ro.utcluj.Model.model.User;
import ro.utcluj.Model.repository.ILoginRepository;
import ro.utcluj.Model.repository.LoginRepositoryImpl;
import ro.utcluj.Model.service.ILoginService;
import ro.utcluj.Model.service.LoginServiceImpl;
import ro.utcluj.View.ILoginView;

public class LoginController {

    private final ILoginView loginView;
    private final ILoginService loginService;

    public LoginController(ILoginView loginView) {
        this.loginView = loginView;
        ILoginRepository loginRepository = new LoginRepositoryImpl();
        loginService = new LoginServiceImpl(loginRepository);
    }

    public LoginController(ILoginView loginView, ILoginService loginService) {
        this.loginView = loginView;
        this.loginService = loginService;
    }

    public void login() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        User user = null;

        user = loginService.userLogin(username, password);
        if (user == null)
            loginView.showErrorMessage("Invalid username/password");
        else {
            if (user.getIsAdmin() == 0){
                loginView.showRegularView(user);
                loginView.setVisibleLoginView(false);
            }
            else {
                loginView.showAdminView(user);
                loginView.setVisibleLoginView(false);
            }
        }
    }

}
