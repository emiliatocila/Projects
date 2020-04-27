package ro.utcluj.Model.repository;

import ro.utcluj.Model.model.User;
import java.util.List;

public interface IUserRepository {
    List<User> viewAllRegUsers();
    int insertRegUser(User user);
    int deleteRegUser(int id);
    int updateRegUser(int id, String newUsername);
}
