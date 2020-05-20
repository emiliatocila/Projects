package ro.utcluj.Client.View;

import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.User;
import java.util.List;

public interface IAdminView {

    void init();
    void setUsers(List<User> users);
    void setSongs(List<Song> songs);
    void clearMainPanel();
    String getUsername();
    String getPassword();
    int generateReport();
    String getTitleSong();
    String getArtist();
    String getAlbum();
    String getGenre();
    int getIdToDelete();
    int getIdToUpdate();
    int getIdToGenerate();
    String getTypeOfReport();
    String getUsernameToGenerate();
    String getUsernameToUpdate();
    String getTitleToUpdate();
    String getArtistToUpdate();
    String getAlbumToUpdate();
    String getGenreToUpdate();
    int getViewCountToUpdate();
    double getRatingToUpdate();
    int showInsertUserOptionPane();
    int showInsertSongOptionPane();
    int deleteUser();
    int deleteSong();
    int updateUser();
    int updateSong();
    void setVisibleAdminView(boolean value);
    void showMessage(String message);
}

