package ro.utcluj.ClientAndServer.Model;

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
    private double rating;

    public Song() {
        // this form used by Hibernate
    }

    public Song(String title, String artist, String album, String genre, int viewCount, double rating) {
        // for application use, to create new events
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.viewCount = viewCount;
        this.rating = rating;
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

    public double getRating() { return rating; }

    public void setRating(double rating) { this.rating = rating; }
}
