package ro.utcluj.Model.service;

import ro.utcluj.Model.model.User;
import ro.utcluj.Model.repository.ILoginRepository;

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
