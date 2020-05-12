package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongSugg;

import java.util.List;

public interface ISongSuggRepository {
    List<SongSugg> viewAllSongSuggSent(int idMe);
    List<SongSugg> viewAllSongSuggReceived(int idMe);
    Song getSuggSong(SongSugg songSugg);
    int addSongSugg(SongSugg songSugg);
    int confirmSongSugg(int id);
    int deleteSongSugg(int id);
}
