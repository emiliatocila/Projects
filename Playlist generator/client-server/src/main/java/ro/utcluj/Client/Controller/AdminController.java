package ro.utcluj.Client.Controller;

import ro.utcluj.Client.Report.IReport;
import ro.utcluj.Client.Report.ReportFactory;
import ro.utcluj.Client.View.LoginView;
import ro.utcluj.ClientAndServer.Communication.IRequestHandler;
import ro.utcluj.ClientAndServer.Model.Playlist;
import ro.utcluj.ClientAndServer.Model.Song;
import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.ClientAndServer.Communication.RequestHandler;
import ro.utcluj.Client.View.IAdminView;
import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {
    private final IRequestHandler requestHandler;
    private final IAdminView adminView;
    private List<User> regUsers;
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Song> playlistSongs;

    public AdminController(IAdminView adminView) {
        this.adminView = adminView;
        requestHandler = new RequestHandler();
        this.initUsersAndSongs();
    }

    public AdminController(IAdminView adminView, IRequestHandler requestHandler) {
        this.adminView = adminView;
        this.requestHandler = requestHandler;
    }

    public void initUsersAndSongs() {
        this.initUsers();
        this.initSongs();
        adminView.init();
    }

    public void initUsers() {
        regUsers = requestHandler.getResult("SHOWALLUSERS", null, User.class);
        adminView.setUsers(regUsers);
    }

    public void initSongs() {
        songs = requestHandler.getResult("SHOWALLSONGS", null, Song.class);
        adminView.setSongs(songs);
    }

    public void showAllRegUsers(){
        this.initUsers();
        adminView.clearMainPanel();
        adminView.init();
    }

    public void showAllSongs() {
        this.initSongs();
        adminView.clearMainPanel();
        adminView.init();
    }

    public void logOut() {
        adminView.setVisibleAdminView(false);
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }

    public void insertRegUser(){
        int option = adminView.showInsertUserOptionPane();
        if (option == JOptionPane.OK_OPTION){
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            String params = "";
            params += "username=" + username + "#password=" + password + "#";

            String message = (requestHandler.getResult("INSERTUSER", params, String.class)).get(0);

            adminView.showMessage(message);
            adminView.clearMainPanel();
            this.initUsers();
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

            String params = "";
            params += "title=" + title + "#artist=" + artist + "#album=" + album + "#genre=" + genre + "#";

            String message = (requestHandler.getResult("INSERTSONG", params, String.class)).get(0);

            adminView.showMessage(message);
            adminView.clearMainPanel();
            this.initSongs();
            adminView.init();
        }
    }

    public void deleteRegUser(){
        int ok = adminView.deleteUser();
        if (ok == -1)
            adminView.showMessage("Select a user to be deleted!");
        else {
            int id = adminView.getIdToDelete();

            String params = "";
            params += "id=" + id + "#";

            String message = ( requestHandler.getResult("DELETEUSER", params, String.class)).get(0);

            adminView.showMessage(message);
            adminView.clearMainPanel();
            this.initUsers();
            adminView.init();
        }
    }

    public void deleteSong() {
        int ok = adminView.deleteSong();
        if (ok == -1)
            adminView.showMessage("Select a song to be deleted!");
        else {
            int id = adminView.getIdToDelete();

            String params = "";
            params += "id=" + id + "#";

            String message = (requestHandler.getResult("DELETESONG", params, String.class)).get(0);

            adminView.showMessage(message);
            adminView.clearMainPanel();
            this.initSongs();
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

            String params = "";
            params += "id=" + id + "#newUsername=" + newUsername + "#";

            String message = (requestHandler.getResult("UPDATEUSER", params, String.class)).get(0);

            adminView.showMessage(message);
            adminView.clearMainPanel();
            this.initUsers();
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

            String params = "";
            params += "id=" + id + "#newTitle=" + newTitle + "#newArtist=" + newArtist + "#newAlbum=" +
                    newAlbum + "#newGenre=" + newGenre + "#newViewCount=" + newViewCount + "#";

            String message = (requestHandler.getResult("UPDATESONG", params, String.class)).get(0);

            adminView.showMessage(message);
            adminView.clearMainPanel();
            this.initSongs();
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

            String params = "";
            params += "idUser=" + id + "#";

            playlists = requestHandler.getResult("SHOWALLPLAYLISTS", params, Playlist.class);

            if (playlists.isEmpty()) {
                adminView.showMessage("The selected user does not have any playlists created!");
            }
            else {
                Map<Playlist, List<Song>> playlistSongsMap = new HashMap<>();
                for (Playlist playlist : playlists) {

                    params = "";
                    params += "idPlaylist=" + playlist.getId() + "#";

                    playlistSongs = requestHandler.getResult("SHOWALLPLAYLISTSONGS", params, Song.class);

                    playlistSongsMap.put(playlist, playlistSongs);
                }
                IReport report = reportFactory.getReport(typeOfReport, playlistSongsMap, username);
                report.generateReport();
                adminView.showMessage("Report generated successfully!");
            }
        }
    }

}
