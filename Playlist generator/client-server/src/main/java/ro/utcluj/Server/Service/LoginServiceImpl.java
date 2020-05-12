package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.Server.Repository.ILoginRepository;

public class LoginServiceImpl implements ILoginService {
    private ILoginRepository loginRepository;

    public LoginServiceImpl(ILoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    @Override
    public User userLogin(String username, String password){
        return loginRepository.getUser(username, password);
    }
}
