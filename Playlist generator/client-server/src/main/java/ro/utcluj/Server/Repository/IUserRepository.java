package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.User;
import java.util.List;

public interface IUserRepository {
    List<User> viewAllRegUsers();
    int insertRegUser(User user);
    int deleteRegUser(int id);
    int updateRegUser(int id, String newUsername);
    User getUserWithUsername(String username);
    User getUserWithId(int id);
}
