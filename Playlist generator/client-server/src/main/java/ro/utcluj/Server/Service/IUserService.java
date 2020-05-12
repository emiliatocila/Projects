package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.User;
import java.util.List;

public interface IUserService {
    List<User> viewAllRegUsers();
    String insertRegUser(String username, String password, int isAdmin);
    String deleteRegUser(int id);
    String updateRegUser(int id, String newUsername);
    User getUserWithUsername(String username);
}
