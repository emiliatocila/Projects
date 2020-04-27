package ro.utcluj.View;

import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.model.Song;

import java.util.List;

public interface IRegularView {
    void init();
    void setSongs(List<Song> songs);
    void setPlaylists(List<Playlist> playlists);
    void setPlaylistSongs(List<Song> songs);
    void clearMainPanel();
    int getUserId();
    int[] showSearchByOptionPane();
    int createNewPlaylistOptionPane();
    List<Object> addNewSongsToExistingPlaylistOptionPane(List<Playlist> playlists);
    int deleteSong();
    String getCriteria();
    String getPlaylistName();
    List<Integer> getIdSongsForNewPlaylist();
    int playSong();
    void resetIdSongsForNewPlaylist();
    void setPlaylistsOrPlaylistsSongs(int val);
    int selectPlaylistToView();
    int getIdOfPlaylistToView();
    int getIdSongToDelete();
    int getIdPlaylistToDelete();
    int getIdToPlay();
    void showMessage(String message);
}
