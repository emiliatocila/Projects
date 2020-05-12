package ro.utcluj.ClientAndServer.Model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table( name = "SongSugg")
public class SongSugg{
    private int id;
    private int idUserFrom;
    private int idUserTo;
    private int idSong;
    private int confirmed;

    public SongSugg() {
        // this form used by Hibernate
    }

    public SongSugg(int idUserFrom, int idUserTo, int idSong, int confirmed) {
        this.idUserFrom = idUserFrom;
        this.idUserTo = idUserTo;
        this.idSong = idSong;
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

    public int getIdUserFrom() {
        return idUserFrom;
    }

    public void setIdUserFrom(int idUserFrom) {
        this.idUserFrom = idUserFrom;
    }

    public int getIdUserTo() {
        return idUserTo;
    }

    public void setIdUserTo(int idUserTo) {
        this.idUserTo = idUserTo;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
