package main.java.bll;

import main.java.model.User;
import main.java.repository.LoginRepository;

import java.util.ArrayList;

public class LoginBLL {

    public LoginBLL(){
    }

    public ArrayList<User> findUserByUsername(String username){
        ArrayList<User> u = new ArrayList<User>();
        LoginRepository loginRepository = new LoginRepository();
        if (loginRepository.findByUsername(username) == null)
            return null;
        else
            u.add(loginRepository.findByUsername(username));
        return u;
    }
}
