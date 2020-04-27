package ro.utcluj.Controller;

import ro.utcluj.Model.Report.IReport;
import ro.utcluj.Model.Report.ReportFactory;
import ro.utcluj.Model.model.Playlist;
import ro.utcluj.Model.model.Song;
import ro.utcluj.Model.model.User;
import ro.utcluj.Model.repository.*;
import ro.utcluj.Model.service.*;
import ro.utcluj.View.IAdminView;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {

    private final IAdminView adminView;
    private final IPlaylistService playlistService;
    private final IPlaylistSongsService playlistSongsService;
    private final IUserService userService;
    private final ISongService songService;
    private List<User> regUsers;
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Song> playlistSongs;

    public AdminController(IAdminView adminView) {
        this.adminView = adminView;
        IUserRepository userRepository = new UserRepositoryImpl();
        ISongRepository songRepository = new SongRepositoryImpl();
        IPlaylistRepository playlistRepository = new PlaylistRepositoryImpl();
        IPlaylistSongsRepository playlistSongsRepository = new PlaylistSongsRepositoryImpl();
        userService = new UserServiceImpl(userRepository);
        songService = new SongServiceImpl(songRepository);
        playlistService = new PlaylistServiceImpl(playlistRepository);
        playlistSongsService = new PlaylistSongsServiceImpl(playlistSongsRepository);
        regUsers = userService.viewAllRegUsers();
        songs = songService.viewAllSongs();
        adminView.setUsers(regUsers);
        adminView.setSongs(songs);
        adminView.init();
    }

    public AdminController(IAdminView adminView, IUserService userService, ISongService songService, IPlaylistService playlistService, IPlaylistSongsService playlistSongsService){
        this.adminView = adminView;
        this.userService = userService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.playlistSongsService = playlistSongsService;
    }

    public void showAllRegUsers(){
        regUsers = userService.viewAllRegUsers();
        adminView.setUsers(regUsers);
        adminView.clearMainPanel();
        adminView.init();
    }

    public void showAllSongs() {
        songs = songService.viewAllSongs();
        adminView.setSongs(songs);
        adminView.clearMainPanel();
        adminView.init();
    }

    public void insertRegUser(){
        int option = adminView.showInsertUserOptionPane();
        if (option == JOptionPane.OK_OPTION){
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            String message = userService.insertRegUser(username, password, 0);
            adminView.showMessage(message);
            adminView.clearMainPanel();
            regUsers = userService.viewAllRegUsers();
            adminView.setUsers(regUsers);
            adminView.init();
        }
    }

    public void insertSong() {
        int option = adminView.showInsertSongOptionPane();
        if (option == JOptionPane.OK_OPTION) {
            String title = adminView.getTitleSong();
            String artist = adminView.getArtist();
            String album = adminView.getAlbum();
            String genre = adminView.getGenre();
            String message = songService.insertSong(title, artist, album, genre, 0);
            adminView.showMessage(message);
            adminView.clearMainPanel();
            songs = songService.viewAllSongs();
            adminView.setSongs(songs);
            adminView.init();
        }
    }

    public void deleteRegUser(){
        int ok = adminView.deleteUser();
        if (ok == -1)
            adminView.showMessage("Select a user to be deleted!");
        else {
            int id = adminView.getIdToDelete();
            String message = userService.deleteRegUser(id);
            adminView.showMessage(message);
            adminView.clearMainPanel();
            regUsers = userService.viewAllRegUsers();
            adminView.setUsers(regUsers);
            adminView.init();
        }
    }

    public void deleteSong() {
        int ok = adminView.deleteSong();
        if (ok == -1)
            adminView.showMessage("Select a song to be deleted!");
        else {
            int id = adminView.getIdToDelete();
            String message = songService.deleteSong(id);
            adminView.showMessage(message);
            adminView.clearMainPanel();
            songs = songService.viewAllSongs();
            adminView.setSongs(songs);
            adminView.init();
        }
    }

    public void updateRegUser(){
        int ok = adminView.updateUser();
        if (ok == -1)
            adminView.showMessage("Select a user to be updated!");
        else {
            int id = adminView.getIdToUpdate();
            String newUsername = adminView.getUsernameToUpdate();
            String message = userService.updateRegUser(id, newUsername);
            adminView.showMessage(message);
            adminView.clearMainPanel();
            regUsers = userService.viewAllRegUsers();
            adminView.setUsers(regUsers);
            adminView.init();
        }
    }

    public void updateSong() {
        int ok = adminView.updateSong();
        if (ok == -1)
            adminView.showMessage("Select a song to be updated!");
        else {
            int id = adminView.getIdToUpdate();
            String newTitle = adminView.getTitleToUpdate();
            String newArtist = adminView.getArtistToUpdate();
            String newAlbum = adminView.getAlbumToUpdate();
            String newGenre = adminView.getGenreToUpdate();
            int newViewCount = adminView.getViewCountToUpdate();
            String message = songService.updateSong(id, newTitle, newArtist, newAlbum, newGenre, newViewCount);
            adminView.showMessage(message);
            adminView.clearMainPanel();
            songs = songService.viewAllSongs();
            adminView.setSongs(songs);
            adminView.init();
        }
    }

    public void generateReportRegUser(){
        ReportFactory reportFactory = new ReportFactory();
        int ok = adminView.generateReport();
        if (ok == -1) {
            adminView.showMessage("Select a user to generate a report for!");
        }
        else if (ok == JOptionPane.OK_OPTION){
            int id = adminView.getIdToGenerate();
            String typeOfReport = adminView.getTypeOfReport();
            String username = adminView.getUsernameToGenerate();
            playlists = playlistService.viewAllPlaylistsForUser(id);
            if (playlists.isEmpty()) {
                adminView.showMessage("The selected user does not have any playlists created!");
            }
            else {
                Map<Playlist, List<Song>> playlistSongsMap = new HashMap<>();
                for (Playlist playlist : playlists) {
                    playlistSongs = playlistSongsService.viewAllSongsFromPlaylist(playlist.getId());
                    playlistSongsMap.put(playlist, playlistSongs);
                }
                IReport report = reportFactory.getReport(typeOfReport, playlistSongsMap, username);
                report.generateReport();
                adminView.showMessage("Report generated successfully!");
            }
        }
    }

}
