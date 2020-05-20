package ro.utcluj.ClientAndServer.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "SongRatings")
public class SongRatings {
    private int id;
    private int idUser;
    private int idSong;
    private int stars;

    public SongRatings() {
        // this form used by Hibernate
    }

    public SongRatings(int idUser, int idSong, int stars) {
        // for application use, to create new events
        this.idUser = idUser;
        this.idSong = idSong;
        this.stars = stars;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")

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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
