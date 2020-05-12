package ro.utcluj.Client.Controller;

import ro.utcluj.Client.Client;
import ro.utcluj.Client.ClientConnectionToServer;
import ro.utcluj.Client.Report.IReport;
import ro.utcluj.Client.Report.ReportFactory;
import ro.utcluj.Client.View.LoginView;
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
    private static ClientConnectionToServer connection;
    private final RequestHandler requestHandler;
    private final IAdminView adminView;
    private List<User> regUsers;
    private List<Song> songs;
    private List<Playlist> playlists;
    private List<Song> playlistSongs;

    public AdminController(IAdminView adminView) {
        this.adminView = adminView;
        connection = Client.getConnection();
        requestHandler = new RequestHandler();
        this.initUsersAndSongs();
    }

    public void initUsersAndSongs() {
        this.initUsers();
        this.initSongs();
        adminView.init();
    }

    public void initUsers() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLUSERS", null);
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        regUsers = (List<User>) requestHandler.decodeResponse(encodedResponse, User.class);
        adminView.setUsers(regUsers);
    }

    public void initSongs() {
        String encodedRequest = requestHandler.encodeRequest("SHOWALLSONGS", null);
        String encodedResponse = connection.sendRequestToServer(encodedRequest);
        songs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);
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

            String encodedRequest = requestHandler.encodeRequest("INSERTUSER", "username=" + username + "#password=" + password + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("INSERTSONG", "title=" + title + "#artist=" +
                                    artist + "#album=" + album + "#genre=" + genre + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("DELETEUSER", "id=" + id + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("DELETESONG", "id=" + id + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("UPDATEUSER", "id=" + id + "#newUsername=" + newUsername + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("UPDATESONG", "id=" + id + "#newTitle=" + newTitle + "#newArtist=" +
                                    newArtist + "#newAlbum=" + newAlbum + "#newGenre=" + newGenre + "#newViewCount=" + newViewCount + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            String message = ((List<String>) requestHandler.decodeResponse(encodedResponse, String.class)).get(0);

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

            String encodedRequest = requestHandler.encodeRequest("SHOWALLPLAYLISTS", "idUser=" + id + "#");
            String encodedResponse = connection.sendRequestToServer(encodedRequest);
            playlists = (List<Playlist>) requestHandler.decodeResponse(encodedResponse, Playlist.class);

            if (playlists.isEmpty()) {
                adminView.showMessage("The selected user does not have any playlists created!");
            }
            else {
                Map<Playlist, List<Song>> playlistSongsMap = new HashMap<>();
                for (Playlist playlist : playlists) {

                    encodedRequest = requestHandler.encodeRequest("SHOWALLPLAYLISTSONGS", "idPlaylist=" + playlist.getId() + "#");
                    encodedResponse = connection.sendRequestToServer(encodedRequest);
                    playlistSongs = (List<Song>) requestHandler.decodeResponse(encodedResponse, Song.class);

                    playlistSongsMap.put(playlist, playlistSongs);
                }
                IReport report = reportFactory.getReport(typeOfReport, playlistSongsMap, username);
                report.generateReport();
                adminView.showMessage("Report generated successfully!");
            }
        }
    }

}
