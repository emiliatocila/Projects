import org.junit.jupiter.api.Test;
import ro.utcluj.Client.Controller.LoginController;
import ro.utcluj.ClientAndServer.Communication.IRequestHandler;
import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.Client.View.ILoginView;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class LoginControllerTests {
    @Test
    public void givenAdminUsernameAndPassword_login_showAdminView() {
        ILoginView loginView = mock(ILoginView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(loginView.getUsername()).thenReturn("user1");
        when(loginView.getPassword()).thenReturn("user1");

        List<User> users = new ArrayList<>();
        User user = new User("user1", "user1", 1);
        users.add(user);

        when(requestHandler.getResult(eq("LOGIN"), eq("username=user1#password=user1#"), eq(User.class))).thenReturn(users);

        LoginController controller = new LoginController(loginView, requestHandler);

        controller.loginForUT();

        verify(loginView).showAdminView(user);
    }

    @Test
    public void givenRegularUsernameAndPassword_login_showRegularView() {
        ILoginView loginView = mock(ILoginView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        when(loginView.getUsername()).thenReturn("user1");
        when(loginView.getPassword()).thenReturn("user1");

        List<User> users = new ArrayList<>();
        User user = new User("user1", "user1", 0);
        users.add(user);

        when(requestHandler.getResult(eq("LOGIN"), eq("username=user1#password=user1#"), eq(User.class))).thenReturn(users);


        LoginController controller = new LoginController(loginView, requestHandler);

        controller.loginForUT();

        verify(loginView).showRegularView(user);
    }

    @Test
    public void givenInvalidUsernameAndPassword_login_showErrorMessage() {
        ILoginView loginView = mock(ILoginView.class);
        IRequestHandler requestHandler = mock(IRequestHandler.class);

        List<User> nullList = new ArrayList<>();
        nullList.add(null);

        when(loginView.getUsername()).thenReturn("notanusername");
        when(loginView.getPassword()).thenReturn("nope");
        when(requestHandler.getResult(eq("LOGIN"), eq("username=notanusername#password=nope#"), eq(User.class))).thenReturn(nullList);

        LoginController controller = new LoginController(loginView, requestHandler);

        controller.loginForUT();

        verify(loginView).showErrorMessage("Invalid username/password");
    }

}
