package ro.utcluj.Model.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table( name = "Playlist")
public class Playlist {
    private int id;
    private int idUser;
    private String name;

    public Playlist() {
        // this form used by Hibernate
    }

    public Playlist(int idUser, String name) {
        // for application use, to create new events
        this.idUser = idUser;
        this.name = name;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
