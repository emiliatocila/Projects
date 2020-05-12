package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.User;

public interface ILoginService {
    User userLogin(String username, String password);
}
