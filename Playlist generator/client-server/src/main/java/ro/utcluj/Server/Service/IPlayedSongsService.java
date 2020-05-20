package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.PlayedSongs;
import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public interface IPlayedSongsService {
    List<PlayedSongs> viewAllPlayedSongsForUser(int idUser);
    Song getPlayedSong(PlayedSongs playedSong);
    List<Song> getPlayedSongs(int idUser);
    String addPlayedSong(int idUser, int idSong);
}
