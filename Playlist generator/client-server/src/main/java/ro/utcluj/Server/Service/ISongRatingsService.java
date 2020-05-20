package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongRatings;
import java.util.List;

public interface ISongRatingsService {
    List<SongRatings> viewAllSongRatingsForUser(int idUser);
    List<SongRatings> viewAllSongRatingsForSong(int idSong);
    String addSongRating(int idUser, int idSong, int stars);
}
