package ro.utcluj.Model.repository;

import ro.utcluj.Model.model.User;

public interface ILoginRepository {

    User getUser(String username, String password);
}
