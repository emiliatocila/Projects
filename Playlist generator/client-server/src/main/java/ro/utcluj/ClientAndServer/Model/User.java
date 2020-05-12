package ro.utcluj.ClientAndServer.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table( name = "User")
public class User {
    private int id;
    private String username;
    private String password;
    private int isAdmin;

    public User() {
        // this form used by Hibernate
    }

    public User(String username, String password, int isAdmin) {
        // for application use, to create new events
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")



    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getIsAdmin() { return isAdmin; }

    public void setIsAdmin(int isAdmin) { this.isAdmin = isAdmin; }
}
