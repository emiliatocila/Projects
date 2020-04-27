package ro.utcluj.Model.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table( name = "PlaylistSongs")
public class PlaylistSongs {
    private int id;
    private int idPlaylist;
    private int idSong;

    public PlaylistSongs() {
        // this form used by Hibernate
    }

    public PlaylistSongs(int idPlaylist, int idSong) {
        // for application use, to create new events
        this.idPlaylist = idPlaylist;
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

    public int getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(int idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }
}
