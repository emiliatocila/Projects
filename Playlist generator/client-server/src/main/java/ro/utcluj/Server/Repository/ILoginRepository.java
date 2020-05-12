package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.User;

public interface ILoginRepository {

    User getUser(String username, String password);
}
