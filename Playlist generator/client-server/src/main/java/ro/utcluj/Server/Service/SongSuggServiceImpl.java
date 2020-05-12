package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.SongSugg;
import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.Server.Repository.ISongRepository;
import ro.utcluj.Server.Repository.ISongSuggRepository;
import ro.utcluj.Server.Repository.IUserRepository;
import java.util.List;

public class SongSuggServiceImpl extends ISongSuggService {
    private ISongSuggRepository songSuggRepository;
    private ISongRepository songRepository;
    private IUserRepository userRepository;

    public SongSuggServiceImpl(ISongSuggRepository songSuggRepository, ISongRepository songRepository, IUserRepository userRepository) {
        this.songSuggRepository = songSuggRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<SongSugg> viewAllSongSuggSent(int idMe) {
        return songSuggRepository.viewAllSongSuggSent(idMe);
    }

    @Override
    public List<SongSugg> viewAllSongSuggReceived(int idMe) {
        return songSuggRepository.viewAllSongSuggReceived(idMe);
    }

    @Override
    public Song getSuggSong(SongSugg songSugg) {
        return songSuggRepository.getSuggSong(songSugg);
    }

    @Override
    public String getUsernameWhoSuggested(SongSugg songSugg) {
        User whoSuggested = userRepository.getUserWithId(songSugg.getIdUserFrom());
        return whoSuggested.getUsername();
    }

    @Override
    public String addSongSugg(int idMe, String usernameTo, int idSong) {
        User userTo = userRepository.getUserWithUsername(usernameTo);
        int idUserTo = userTo.getId();
        SongSugg newSongSugg = new SongSugg(idMe, idUserTo, idSong, 0);
        List<SongSugg> songSuggsSent = songSuggRepository.viewAllSongSuggSent(idMe);
        for (SongSugg songSugg : songSuggsSent) {
            if (songSugg.getIdUserTo() == idUserTo) {
                if (songSugg.getIdSong() == idSong)
                    return "You have already suggested this song to this friend!";
            }
        }
        if (songSuggRepository.addSongSugg(newSongSugg) == -1)
            return "Cannot suggest song!";
        else {
            setChanged();
            notifyObservers(newSongSugg);
            return "Song suggestion sent!";
        }
    }

    @Override
    public String confirmSongSugg(int id) {
        if (songSuggRepository.confirmSongSugg(id) == -1)
            return "Cannot accept song suggestion!";
        else {
            this.deleteSongSugg(id);
            return "Song suggestion accepted!";
        }
    }

    @Override
    public String deleteSongSugg(int id) {
        if (songSuggRepository.deleteSongSugg(id) == -1)
            return "Cannot deny song suggestion!";
        else
            return "Song suggestion denied!";
    }
}
