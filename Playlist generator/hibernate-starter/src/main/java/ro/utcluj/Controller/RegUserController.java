package ro.utcluj.Controller;

import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.model.PlaylistSongs;
import ro.utcluj.Model.model.Song;
import ro.utcluj.Model.repository.*;
import ro.utcluj.Model.service.*;
import ro.utcluj.View.IRegularView;
import javax.swing.*;
import java.util.List;

public class RegUserController {
    private final IRegularView regView;
    private final ISongService songService;
    private final IPlaylistService playlistService;
    private final IPlaylistSongsService playlistSongsService;
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Song> playlistSongs;

    public RegUserController(IRegularView regView) {
        this.regView = regView;
        ISongRepository songRepository = new SongRepositoryImpl();
        IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
        IPlaylistSongsRepository playlistSongsRepository = new PlaylistSongsRepositoryImpl();
        songService = new SongServiceImpl(songRepository);
        playlistService = new PlaylistServiceImpl(playlistRepository);
        playlistSongsService = new PlaylistSongsServiceImpl(playlistSongsRepository);
        songs = songService.viewAllSongs();
        playlists = playlistService.viewAllPlaylistsForUser(regView.getUserId());
        regView.setPlaylists(playlists);
        regView.setSongs(songs);
        regView.init();
    }

    public RegUserController(IRegularView regView, ISongService songService, IPlaylistService playlistService, IPlaylistSongsService playlistSongsService) {
        this.regView = regView;
        this.songService = songService;
        this.playlistService = playlistService;
        this.playlistSongsService = playlistSongsService;
    }

    public void showAllSongs() {
        songs = songService.viewAllSongs();
        regView.setSongs(songs);
        regView.clearMainPanel();
        regView.init();
    }

    public void searchBySongs() {
        int[] searchByOptionPane = regView.showSearchByOptionPane();
        int option = searchByOptionPane[0];
        if (option == JOptionPane.OK_OPTION) {
            int criteriaIndex = searchByOptionPane[1];
            String criteria = regView.getCriteria();
            switch (criteriaIndex) {
                case 0:
                    songs = songService.viewAllSongsByTitle(criteria);
                    break;
                case 1:
                    songs = songService.viewAllSongsByArtist(criteria);
                    break;
                case 2:
                    songs = songService.viewAllSongsByAlbum(criteria);
                    break;
                case 3:
                    songs = songService.viewAllSongsByGenre(criteria);
                    break;
                case 4:
                    songs = songService.viewAllSongsByTopViews();
                    break;
            }
            if (songs.isEmpty()) {
                regView.showMessage("There are no songs found!");
            }
            else {
                regView.setSongs(songs);
            }
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void createNewPlaylist() {
        int option = regView.createNewPlaylistOptionPane();
        String message = null;
        if (option == JOptionPane.OK_OPTION){
            int idUser = regView.getUserId();
            String playlistName = regView.getPlaylistName();
            Playlist playlist = new Playlist(idUser, playlistName);
            List<Integer> idSongsForNewPlaylist = regView.getIdSongsForNewPlaylist();
            if (idSongsForNewPlaylist != null) {
                message = playlistService.createPlaylist(playlist);
                int idPlaylist = playlistService.viewPlaylistForUserWithName(idUser, playlistName).getId();
                for (int idSong : idSongsForNewPlaylist) {
                    PlaylistSongs playlistSong = new PlaylistSongs(idPlaylist, idSong);
                    playlistSongsService.addSong(playlistSong);
                }
                playlists = playlistService.viewAllPlaylistsForUser(regView.getUserId());
                regView.setPlaylists(playlists);
                regView.clearMainPanel();
                regView.init();
            }
            else message = "Cannot create playlist! No songs selected!";
            regView.showMessage(message);
        }
    }

    public void addSongsToAnExistingPlaylist() {
        int idUser = regView.getUserId();
        List<Playlist> playlists = playlistService.viewAllPlaylistsForUser(idUser);
        Playlist selectedPlaylist = null;
        if (playlists.isEmpty()) {
            regView.showMessage("There are no playlists created for this user!");
        }
        else {
            List<Object> addSongsOptionPane = regView.addNewSongsToExistingPlaylistOptionPane(playlists);
            List<Integer> idSongsForNewPlaylist = regView.getIdSongsForNewPlaylist();
            if (idSongsForNewPlaylist == null) {
                regView.showMessage("No songs selected!");
            }
            else {
                int option = (int)addSongsOptionPane.get(0);
                if (option == JOptionPane.OK_OPTION){
                    String selectedPlaylistName = addSongsOptionPane.get(1).toString();
                    selectedPlaylist = playlistService.viewPlaylistForUserWithName(idUser, selectedPlaylistName);
                    int duplicateSongs = 0;
                    for (int idSong : idSongsForNewPlaylist) {
                        PlaylistSongs playlistSong = new PlaylistSongs(selectedPlaylist.getId(), idSong);
                        String message = playlistSongsService.addSong(playlistSong);
                        if (message.equals("Duplicate song not added!"))
                            duplicateSongs++;
                    }
                    playlistSongs = playlistSongsService.viewAllSongsFromPlaylist(selectedPlaylist.getId());
                    regView.setPlaylistSongs(playlistSongs);
                    regView.clearMainPanel();
                    regView.init();
                    if (duplicateSongs == 0)
                        regView.showMessage("Song(s) added successfully!");
                    else if (duplicateSongs == idSongsForNewPlaylist.size())
                        regView.showMessage("Duplicate song(s) not added!");
                    else if (duplicateSongs > 0 && duplicateSongs < idSongsForNewPlaylist.size())
                        regView.showMessage("Song(s) added successfully! Duplicate song(s) not added!");
                    regView.resetIdSongsForNewPlaylist();
                }
            }
        }
    }

    public void viewPlaylist() {
        regView.setPlaylistsOrPlaylistsSongs(1);
        int okPlaylist = regView.selectPlaylistToView();
        if (okPlaylist == -1)
            regView.showMessage("Select a playlist to view");
        else {
            int idOfPlaylist = regView.getIdOfPlaylistToView();
            playlistSongs = playlistSongsService.viewAllSongsFromPlaylist(idOfPlaylist);
            regView.setPlaylistSongs(playlistSongs);
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void viewAllPlaylists() {
        regView.setPlaylistsOrPlaylistsSongs(0);
        playlists = playlistService.viewAllPlaylistsForUser(regView.getUserId());
        regView.setPlaylists(playlists);
        regView.clearMainPanel();
        regView.init();
    }

    public void removeSongFromPlaylist() {
        int ok = regView.deleteSong();
        if (ok == -1)
            regView.showMessage("Select a song to be deleted!");
        else {
            int idPlaylist = regView.getIdPlaylistToDelete();
            int idSong = regView.getIdSongToDelete();
            String message = playlistSongsService.removeSong(idPlaylist, idSong);
            regView.showMessage(message);
            playlistSongs = playlistSongsService.viewAllSongsFromPlaylist(regView.getIdOfPlaylistToView());
            regView.setPlaylistSongs(playlistSongs);
            regView.clearMainPanel();
            regView.init();
        }
    }

    public void playSong() {
        int ok = regView.playSong();
        if (ok == -1)
            regView.showMessage("Select a song to play!");
        else {
            int id = regView.getIdToPlay();
            String message = songService.playSong(id);
            regView.showMessage(message);
            playlistSongs = playlistSongsService.viewAllSongsFromPlaylist(regView.getIdOfPlaylistToView());
            regView.setPlaylistSongs(playlistSongs);
            songs = songService.viewAllSongs();
            regView.setSongs(songs);
            regView.clearMainPanel();
            regView.init();
        }
    }
}
