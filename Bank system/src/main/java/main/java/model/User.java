package main.java.model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int isAdmin;

    public User(){
    }

    public User(String firstName, String lastName, String username, String password, int id, int isAdmin) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public User(int id, String firstName, String lastName, String username) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int admin) { this.isAdmin = admin; }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
