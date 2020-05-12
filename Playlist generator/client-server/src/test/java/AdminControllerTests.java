import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import ro.utcluj.Client.Controller.AdminController;
import ro.utcluj.ClientAndServer.Model.Playlist;
import ro.utcluj.Server.Service.IPlaylistService;
import ro.utcluj.Server.Service.IPlaylistSongsService;
import ro.utcluj.Server.Service.ISongService;
import ro.utcluj.Server.Service.IUserService;
import ro.utcluj.Client.View.IAdminView;
import javax.swing.*;
import java.util.ArrayList;

public class AdminControllerTests {
    /*@Test
    public void givenValidCredentials_insertRegularUser_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.showInsertUserOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getUsername()).thenReturn("user");
        when(adminView.getPassword()).thenReturn("user");
        when(userService.insertRegUser(eq("user"), eq("user"), eq(0))).thenReturn("User created successfully!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.insertRegUser();

        verify(adminView).showMessage("User created successfully!");
    }

    @Test
    public void givenInvalidCredentials_insertRegularUser_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.showInsertUserOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getUsername()).thenReturn("u");
        when(adminView.getPassword()).thenReturn("user");
        when(userService.insertRegUser(eq("u"), eq("user"), eq(0))).thenReturn("Username must be at least 2 characters long!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.insertRegUser();

        verify(adminView).showMessage("Username must be at least 2 characters long!");
    }

    @Test
    public void givenValidCredentials_insertSong_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.showInsertSongOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getTitleSong()).thenReturn("title");
        when(adminView.getArtist()).thenReturn("artist");
        when(adminView.getAlbum()).thenReturn("album");
        when(adminView.getGenre()).thenReturn("genre");
        when(songService.insertSong(eq("title"), eq("artist"), eq("album"), eq("genre"), eq(0))).thenReturn("Song created successfully!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.insertSong();

        verify(adminView).showMessage("Song created successfully!");

    }

    @Test
    public void givenInvalidCredentials_insertSong_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.showInsertSongOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getTitleSong()).thenReturn("title");
        when(adminView.getArtist()).thenReturn("artist");
        when(adminView.getAlbum()).thenReturn("album");
        when(adminView.getGenre()).thenReturn("genre");
        when(songService.insertSong(eq("title"), eq("artist"), eq("album"), eq("genre"), eq(0))).thenReturn("View count must be a positive number!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.insertSong();

        verify(adminView).showMessage("View count must be a positive number!");

    }

    @Test
    public void valid_deleteRegularUser_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.deleteUser()).thenReturn(0);
        when(adminView.getIdToDelete()).thenReturn(0);
        when(userService.deleteRegUser(eq(0))).thenReturn("User deleted successfully!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.deleteRegUser();

        verify(adminView).showMessage("User deleted successfully!");

    }

    @Test
    public void invalid_deleteRegularUser_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.deleteUser()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.deleteRegUser();

        verify(adminView).showMessage("Select a user to be deleted!");
    }

    @Test
    public void valid_deleteSong_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.deleteSong()).thenReturn(0);
        when(adminView.getIdToDelete()).thenReturn(0);
        when(songService.deleteSong(eq(0))).thenReturn("Song deleted successfully!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.deleteSong();

        verify(adminView).showMessage("Song deleted successfully!");

    }

    @Test
    public void invalid_deleteSong_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.deleteSong()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.deleteSong();

        verify(adminView).showMessage("Select a song to be deleted!");

    }

    @Test
    public void valid_updateRegularUser_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.updateUser()).thenReturn(0);
        when(adminView.getIdToUpdate()).thenReturn(0);
        when(adminView.getUsernameToUpdate()).thenReturn("newUsername");
        when(userService.updateRegUser(eq(0), eq("newUsername"))).thenReturn("User updated successfully!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.updateRegUser();

        verify(adminView).showMessage("User updated successfully!");

    }

    @Test
    public void invalid_updateRegularUser_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.updateUser()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.updateRegUser();

        verify(adminView).showMessage("Select a user to be updated!");

    }

    @Test
    public void valid_updateSong_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.updateSong()).thenReturn(0);
        when(adminView.getIdToUpdate()).thenReturn(0);
        when(adminView.getTitleToUpdate()).thenReturn("newTitle");
        when(adminView.getArtistToUpdate()).thenReturn("newArtist");
        when(adminView.getAlbumToUpdate()).thenReturn("newAlbum");
        when(adminView.getGenreToUpdate()).thenReturn("newGenre");
        when(adminView.getViewCountToUpdate()).thenReturn(0);
        when(songService.updateSong(eq(0), eq("newTitle"), eq("newArtist"), eq("newAlbum"), eq("newGenre"), eq(0))).thenReturn("Song updated successfully!");

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.updateSong();

        verify(adminView).showMessage("Song updated successfully!");

    }

    @Test
    public void invalid_updateSong_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.updateSong()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.updateSong();

        verify(adminView).showMessage("Select a song to be updated!");

    }


    @Test
    public void invalidNoUserSelected_generateReport_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.generateReport()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.generateReportRegUser();

        verify(adminView).showMessage("Select a user to generate a report for!");

    }

    @Test
    public void invalidNoPlaylists_generateReport_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IUserService userService = mock(IUserService.class);
        ISongService songService = mock(ISongService.class);
        IPlaylistService playlistService = mock(IPlaylistService.class);
        IPlaylistSongsService playlistSongsService = mock(IPlaylistSongsService.class);
        when(adminView.generateReport()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getIdToGenerate()).thenReturn(0);
        when(adminView.getUsernameToGenerate()).thenReturn("user");
        when(playlistService.viewAllPlaylistsForUser(eq(0))).thenReturn(new ArrayList<Playlist>());

        AdminController controller = new AdminController(adminView, userService, songService, playlistService, playlistSongsService);

        controller.generateReportRegUser();

        verify(adminView).showMessage("The selected user does not have any playlists created!");

    }*/

}
