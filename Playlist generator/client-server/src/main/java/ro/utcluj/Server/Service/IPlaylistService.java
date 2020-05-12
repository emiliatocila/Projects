package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Playlist;
import java.util.List;

public interface IPlaylistService {
    List<Playlist> viewAllPlaylistsForUser(int idUser);
    Playlist viewPlaylistForUserWithName(int idUser, String name);
    String createPlaylist(Playlist playlist);
    String deletePlaylist(int idPlaylist);
}
