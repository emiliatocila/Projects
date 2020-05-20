package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public interface ISongRepository {
    List<Song> viewAllSongs();
    Song getSongById(int id);
    List<Song> viewAllSongsByTitle(String title);
    List<Song> viewAllSongsByArtist(String artist);
    List<Song> viewAllSongsByAlbum(String album);
    List<Song> viewAllSongsByGenre(String genre);
    List<Song> viewAllSongsByTopViews();
    List<Song> viewAllSongsByRating();
    int insertSong(Song song);
    int deleteSong(int id);
    int updateSong(int id, String newTitle, String newArtist, String newAlbum, String newGenre, int newViewCount, double newRating);
    int playSong(int id);
}
