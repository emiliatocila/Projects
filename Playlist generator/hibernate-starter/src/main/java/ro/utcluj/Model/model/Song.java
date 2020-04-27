package ro.utcluj.Model.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table( name = "Song")
public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int viewCount;

    public Song() {
        // this form used by Hibernate
    }

    public Song(String title, String artist, String album, String genre, int viewCount) {
        // for application use, to create new events
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.viewCount = viewCount;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
}
