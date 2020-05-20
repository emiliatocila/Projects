package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.PlayedSongs;
import ro.utcluj.ClientAndServer.Model.Song;
import java.util.List;

public interface IPlayedSongsRepository {
    List<PlayedSongs> viewAllPlayedSongsForUser(int idUser);
    Song getPlayedSong(PlayedSongs playedSong);
    int addPlayedSong(PlayedSongs playedSong);
}
