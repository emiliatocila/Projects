package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.Server.Repository.IUserRepository;
import java.util.List;

public class UserServiceImpl implements IUserService {
    private IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> viewAllRegUsers(){
        List<User> regularUsers = userRepository.viewAllRegUsers();
        return regularUsers;
    }

    @Override
    public String insertRegUser(String username, String password, int isAdmin) {
        if (username.length() > 1) {
            if (password.length() > 2) {
                if (userRepository.insertRegUser(new User(username, password, isAdmin)) == -1)
                    return "Cannot create user! Username is taken!";
                else return "User created successfully!";
            } else return "Password must be at least 3 characters long!";
        } else return "Username must be at least 2 characters long!";
    }

    @Override
    public String deleteRegUser(int id) {
        if (userRepository.deleteRegUser(id) == -1)
            return "Cannot delete user!";
        else
            return "User deleted successfully!";
    }

    @Override
    public String updateRegUser(int id, String newUsername) {
        if (newUsername.length() > 1) {
            if (userRepository.updateRegUser(id, newUsername) == -1)
                return "Cannot update user!";
            else return "User updated successfully!";
        } else return "Username must be at least 2 characters long!";
    }

    @Override
    public User getUserWithUsername(String username) {
        if(username.length() < 2)
            return null;
        else
            return userRepository.getUserWithUsername(username);
    }
}
