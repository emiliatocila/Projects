package ro.utcluj.ClientAndServer.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table( name = "PlayedSongs")
public class PlayedSongs {
    private int id;
    private int idUser;
    private int idSong;

    public PlayedSongs() {
        // this form used by Hibernate
    }

    public PlayedSongs(int idUser, int idSong) {
        // for application use, to create new events
        this.idUser = idUser;
        this.idSong = idSong;
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

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }
}
