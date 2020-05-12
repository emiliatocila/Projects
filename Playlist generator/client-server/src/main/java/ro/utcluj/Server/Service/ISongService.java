package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public interface ISongService {
    List<Song> viewAllSongs();
    List<Song> viewAllSongsByTitle(String title);
    List<Song> viewAllSongsByArtist(String artist);
    List<Song> viewAllSongsByAlbum(String album);
    List<Song> viewAllSongsByGenre(String genre);
    List<Song> viewAllSongsByTopViews();
    String insertSong(String title, String artist, String album, String genre, int viewCount);
    String deleteSong(int id);
    String updateSong(int id, String newTitle, String newArtist, String newAlbum, String newGenre, int newViewCount);
    String playSong(int id);
}
