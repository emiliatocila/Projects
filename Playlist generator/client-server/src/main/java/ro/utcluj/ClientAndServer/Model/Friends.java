package ro.utcluj.ClientAndServer.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table( name = "Friends")
public class Friends {
    private int id;
    private int idFriend1;
    private int idFriend2;
    private int confirmed;

    public Friends() {
        // this form used by Hibernate
    }

    public Friends(int idFriend1, int idFriend2, int confirmed) {
        // for application use, to create new events
        this.idFriend1 = idFriend1;
        this.idFriend2 = idFriend2;
        this.confirmed = confirmed;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFriend1() {
        return idFriend1;
    }

    public void setIdFriend1(int idFriend1) {
        this.idFriend1 = idFriend1;
    }

    public int getIdFriend2() {
        return idFriend2;
    }

    public void setIdFriend2(int idFriend2) {
        this.idFriend2 = idFriend2;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
