package ro.utcluj.Model.service;

import ro.utcluj.Model.model.User;
import java.util.List;

public interface IUserService {
    List<User> viewAllRegUsers();
    String insertRegUser(String username, String password, int isAdmin);
    String deleteRegUser(int id);
    String updateRegUser(int id, String newUsername);
}
