package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongSugg;
import java.util.List;
import java.util.Observable;

public abstract class ISongSuggService extends Observable {
    public abstract List<SongSugg> viewAllSongSuggSent(int idMe);
    public abstract List<SongSugg> viewAllSongSuggReceived(int idMe);
    public abstract Song getSuggSong(SongSugg songSugg);
    public abstract String getUsernameWhoSuggested(SongSugg songSugg);
    public abstract String addSongSugg(int idMe, String usernameTo, int idSong);
    public abstract String confirmSongSugg(int id);
    public abstract String deleteSongSugg(int id);
}
