package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongRatings;

import java.util.List;

public interface ISongRatingsRepository {
    List<SongRatings> viewAllSongRatingsForUser(int idUser);
    List<SongRatings> viewAllSongRatingsForSong(int idSong);
    int addSongRating(SongRatings songRatings);
}
