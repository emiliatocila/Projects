package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.Playlist;
import java.util.List;

public interface IPlaylistRepository {
    List<Playlist> viewAllPlaylistsForUser(int idUser);
    List<Playlist> getPlaylistForUserWithName(int idUser, String name);
    int createPlaylist(Playlist playlist);
    int deletePlaylist(int id);
}
