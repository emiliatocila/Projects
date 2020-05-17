package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Playlist;
import java.util.List;

public interface IPlaylistService {
    List<Playlist> viewAllPlaylistsForUser(int idUser);
    Playlist viewPlaylistForUserWithName(int idUser, String name);
    String createPlaylist(int idUser, String name);
    String deletePlaylist(int idPlaylist);
}
