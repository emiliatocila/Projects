package main.java.bll;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import main.java.model.Activity;
import main.java.model.User;
import main.java.repository.dao.UserDAO;

public class UserBLL {

    public UserBLL(){
    }

    public ArrayList<User> findUserByID(String idS){
        if (Pattern.matches("[0-9]+", idS)) {
            int id = Integer.parseInt(idS);
            ArrayList<User> u = new ArrayList<User>();
            UserDAO userDAO = new UserDAO();
            if (userDAO.findById("id", id) == null)
                return null;
            else
                u.add(userDAO.findById("id", id));
            return u;
        } else {
            return null;
        }
    }

    public ArrayList<User> findEmployeeByID(String idS){
        if (Pattern.matches("[0-9]+", idS)) {
            int id = Integer.parseInt(idS);
            ArrayList<User> u = new ArrayList<User>();
            UserDAO userDAO = new UserDAO();
            if (userDAO.findEmployeeById("id", id) == null)
                return null;
            else
                u.add(userDAO.findEmployeeById("id", id));
            return u;
        } else {
            return null;
        }
    }

    public ArrayList<User> viewAll(){
        UserDAO userDAO = new UserDAO();
        ArrayList<User> users = userDAO.viewAll();
        if (users == null)
            return null;
        return users;
    }

    public ArrayList<User> viewAllEmployees(){
        UserDAO userDAO = new UserDAO();
        ArrayList<User> users = userDAO.viewAllEmployees();
        if (users == null)
            return null;
        return users;
    }

    public String insert(String firstName, String lastName, String username, String password, int isAdmin){
        UserDAO userDAO = new UserDAO();
        if (Pattern.matches("[a-zA-Z]+", firstName)) {
            if (Pattern.matches("[a-zA-Z]+", lastName)) {
                if (username.length() > 1) {
                    if (password.length() > 2) {
                        int minPossibleNewId = 0;
                        int i = 0;
                        int ok = 0;
                        for (User user : userDAO.viewAll()) {
                            i++;
                            if (i != user.getId() && ok == 0) {
                                minPossibleNewId = i;
                                ok = 1;
                            }
                        }
                        if (minPossibleNewId == 0)
                            minPossibleNewId = ++i;
                        User u = new User(firstName, lastName, username, password, minPossibleNewId, isAdmin);
                        if (userDAO.insert(u) == -1)
                            return "Cannot create user! Username is taken!";
                        else
                            return "User created successfully!";
                    } else return "Password must be at least 3 characters long!";
                } else return "Username must be at least 2 characters long!";
            } else return "Last name invalid!";
        } else return "First name invalid!";
    }

    public String delete(int id) {
        UserDAO userDAO = new UserDAO();
        if (userDAO.delete(id) == -1)
            return "Cannot delete user!";
        else
            return "User deleted successfully!";
    }

    public String update(int id, String firstName, String lastName, String username) {
        if (Pattern.matches("[a-zA-Z]+", firstName)) {
            if (Pattern.matches("[a-zA-Z]+", lastName)) {
                if (username.length() > 1) {
                    UserDAO userDAO = new UserDAO();
                    User u = new User(id, firstName, lastName, username);
                    if (userDAO.update(u, id) == -1)
                        return "Cannot update user! Username is taken!";
                    else
                        return "User updated successfully!";
                } else return "Username must be at least 2 characters long!";
            } else return "Last name invalid!";
        } else return "First name invalid!";
    }

    public String generateReport(String idEmployee, String selectedDateFromStr, String selectedDateToStr){
        ActivityBLL activityBLL = new ActivityBLL();
        User u = findEmployeeByID(idEmployee).get(0);
        if (activityBLL.viewAllActivitiesBetweenDates(selectedDateFromStr, selectedDateToStr, Integer.parseInt(idEmployee)) != null) {
            ArrayList<Activity> activities = activityBLL.viewAllActivitiesBetweenDates(selectedDateFromStr, selectedDateToStr, Integer.parseInt(idEmployee));
            List<String> lines = new ArrayList<String>();
            lines.add("EMPLOYEE REPORT");
            lines.add("ID: " + u.getId() + ", " + u.getFirstName() + " " + u.getLastName());
            boolean sameDay = selectedDateFromStr.substring(0, 10).equals(selectedDateToStr.substring(0, 10));
            if (sameDay){
                lines.add(selectedDateFromStr.substring(0, 10));
            } else {
                lines.add(selectedDateFromStr + " TO " + selectedDateToStr);
            }
            for (Activity activity : activities) {
                lines.add("Activity ID: " + activity.getId() + ", Type: " + activity.getActivity() + ", Involving: " + activity.getInvolved() + ", On date: " + activity.getDate().substring(0, 10));
            }
            Path file = null;
            if (sameDay) {
                file = Paths.get("Reports/Report_EmployeeID" + idEmployee + "_Date" + selectedDateFromStr.substring(0, 10) + ".txt");
            } else {
                file = Paths.get("Reports/Report_EmployeeID" + idEmployee + "_Dates" + selectedDateFromStr.substring(0, 10) + "_" + selectedDateToStr.substring(0, 10) + ".txt");
            }
            try {
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return "Report generated successfully!";
        } else return "There are no activities performed by this employee in the selected interval!!";
    }

}
