package ro.utcluj.Model.service;

import ro.utcluj.Model.model.User;

public interface ILoginService {
    User userLogin(String username, String password);
}
