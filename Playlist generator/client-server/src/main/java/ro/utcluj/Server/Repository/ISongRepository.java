package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public interface ISongRepository {
    List<Song> viewAllSongs();
    List<Song> viewAllSongsByTitle(String title);
    List<Song> viewAllSongsByArtist(String artist);
    List<Song> viewAllSongsByAlbum(String album);
    List<Song> viewAllSongsByGenre(String genre);
    List<Song> viewAllSongsByTopViews();
    int insertSong(Song song);
    int deleteSong(int id);
    int updateSong(int id, String newTitle, String newArtist, String newAlbum, String newGenre, int newViewCount);
    int playSong(int id);
}
