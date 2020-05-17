import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import ro.utcluj.Client.Controller.AdminController;
import ro.utcluj.ClientAndServer.Communication.IRequestHandler;
import ro.utcluj.Client.View.IAdminView;
import ro.utcluj.ClientAndServer.Model.Playlist;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdminControllerTests {
    @Test
    public void givenValidCredentials_insertRegularUser_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.showInsertUserOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getUsername()).thenReturn("user");
        when(adminView.getPassword()).thenReturn("user");

        List<String> messages = new ArrayList<>();
        String message = "User created successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("INSERTUSER"), eq("username=user#password=user#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.insertRegUser();

        verify(adminView).showMessage(message);
    }

    @Test
    public void givenInvalidCredentials_insertRegularUser_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.getUsername()).thenReturn("u");
        when(adminView.getPassword()).thenReturn("user");

        List<String> messages = new ArrayList<>();
        String message = "Username must be at least 2 characters long!";
        messages.add(message);

        when(requestHandler.getResult(eq("INSERTUSER"), eq("username=u#password=user#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.insertRegUser();

        verify(adminView).showMessage("Username must be at least 2 characters long!");
    }

    @Test
    public void givenValidCredentials_insertSong_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.showInsertSongOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getTitleSong()).thenReturn("title");
        when(adminView.getArtist()).thenReturn("artist");
        when(adminView.getAlbum()).thenReturn("album");
        when(adminView.getGenre()).thenReturn("genre");

        List<String> messages = new ArrayList<>();
        String message = "Song created successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("INSERTSONG"), eq("title=title#artist=artist#album=album#genre=genre#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.insertSong();

        verify(adminView).showMessage("Song created successfully!");

    }

    @Test
    public void givenInvalidCredentials_insertSong_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.showInsertSongOptionPane()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getTitleSong()).thenReturn("");
        when(adminView.getArtist()).thenReturn("artist");
        when(adminView.getAlbum()).thenReturn("album");
        when(adminView.getGenre()).thenReturn("genre");

        List<String> messages = new ArrayList<>();
        String message = "Title must be at least 1 character long!";
        messages.add(message);

        when(requestHandler.getResult(eq("INSERTSONG"), eq("title=#artist=artist#album=album#genre=genre#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.insertSong();

        verify(adminView).showMessage("Title must be at least 1 character long!");

    }

    @Test
    public void valid_deleteRegularUser_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.deleteUser()).thenReturn(0);
        when(adminView.getIdToDelete()).thenReturn(0);

        List<String> messages = new ArrayList<>();
        String message = "User deleted successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("DELETEUSER"), eq("id=0#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.deleteRegUser();

        verify(adminView).showMessage("User deleted successfully!");

    }

    @Test
    public void invalid_deleteRegularUser_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.deleteUser()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.deleteRegUser();

        verify(adminView).showMessage("Select a user to be deleted!");
    }

    @Test
    public void valid_deleteSong_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.deleteSong()).thenReturn(0);
        when(adminView.getIdToDelete()).thenReturn(0);

        List<String> messages = new ArrayList<>();
        String message = "Song deleted successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("DELETESONG"), eq("id=0#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.deleteSong();

        verify(adminView).showMessage("Song deleted successfully!");

    }

    @Test
    public void invalid_deleteSong_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.deleteSong()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.deleteSong();

        verify(adminView).showMessage("Select a song to be deleted!");

    }

    @Test
    public void valid_updateRegularUser_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.updateUser()).thenReturn(0);
        when(adminView.getIdToUpdate()).thenReturn(0);
        when(adminView.getUsernameToUpdate()).thenReturn("newUsername");

        List<String> messages = new ArrayList<>();
        String message = "User updated successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("UPDATEUSER"), eq("id=0#newUsername=newUsername#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.updateRegUser();

        verify(adminView).showMessage("User updated successfully!");

    }

    @Test
    public void invalid_updateRegularUser_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.updateUser()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.updateRegUser();

        verify(adminView).showMessage("Select a user to be updated!");

    }

    @Test
    public void valid_updateSong_showMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.updateSong()).thenReturn(0);
        when(adminView.getIdToUpdate()).thenReturn(0);
        when(adminView.getTitleToUpdate()).thenReturn("newTitle");
        when(adminView.getArtistToUpdate()).thenReturn("newArtist");
        when(adminView.getAlbumToUpdate()).thenReturn("newAlbum");
        when(adminView.getGenreToUpdate()).thenReturn("newGenre");
        when(adminView.getViewCountToUpdate()).thenReturn(0);

        List<String> messages = new ArrayList<>();
        String message = "Song updated successfully!";
        messages.add(message);

        when(requestHandler.getResult(eq("UPDATESONG"), eq("id=0#newTitle=newTitle#newArtist=newArtist#newAlbum=newAlbum#newGenre=newGenre#newViewCount=0#"), eq(String.class))).thenReturn(messages);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.updateSong();

        verify(adminView).showMessage("Song updated successfully!");

    }

    @Test
    public void invalid_updateSong_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.updateSong()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.updateSong();

        verify(adminView).showMessage("Select a song to be updated!");

    }


    @Test
    public void invalidNoUserSelected_generateReport_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.generateReport()).thenReturn(-1);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.generateReportRegUser();

        verify(adminView).showMessage("Select a user to generate a report for!");

    }

    @Test
    public void invalidNoPlaylists_generateReport_showErrorMessage(){
        IAdminView adminView = mock(IAdminView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(adminView.generateReport()).thenReturn(JOptionPane.OK_OPTION);
        when(adminView.getIdToGenerate()).thenReturn(0);
        when(adminView.getUsernameToGenerate()).thenReturn("user");

        List<Playlist> playlists = new ArrayList<>();

        when(requestHandler.getResult(eq("SHOWALLPLAYLISTS"), eq("idUser=0#"), eq(Playlist.class))).thenReturn(playlists);

        AdminController controller = new AdminController(adminView, requestHandler);

        controller.generateReportRegUser();

        verify(adminView).showMessage("The selected user does not have any playlists created!");

    }

}
