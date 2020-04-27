import org.junit.jupiter.api.Test;
import ro.utcluj.Controller.LoginController;
import ro.utcluj.Model.model.User;
import ro.utcluj.Model.service.ILoginService;
import ro.utcluj.View.ILoginView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTests {
    @Test
    public void givenAdminUsernameAndPassword_login_showAdminView() {
        ILoginView loginView = mock(ILoginView.class);
        ILoginService loginService = mock(ILoginService.class);
        when(loginView.getUsername()).thenReturn("emiliatocila");
        when(loginView.getPassword()).thenReturn("password978");
        User user = new User("emiliatocila", "password978", 1);
        when(loginService.userLogin(eq("emiliatocila"), eq("password978"))).thenReturn(user);

        LoginController controller = new LoginController(loginView, loginService);

        controller.login();

        verify(loginView).showAdminView(user);
    }

    @Test
    public void givenRegularUsernameAndPassword_login_showRegularView() {
        ILoginView loginView = mock(ILoginView.class);
        ILoginService loginService = mock(ILoginService.class);
        when(loginView.getUsername()).thenReturn("user1");
        when(loginView.getPassword()).thenReturn("user1");
        User user = new User("user1", "user1", 0);
        when(loginService.userLogin(eq("user1"), eq("user1"))).thenReturn(user);
        LoginController controller = new LoginController(loginView, loginService);

        controller.login();

        verify(loginView).showRegularView(user);
    }

    @Test
    public void givenInvalidUsernameAndPassword_login_showErrorMessage() {
        ILoginView loginView = mock(ILoginView.class);
        ILoginService loginService = mock(ILoginService.class);
        when(loginView.getUsername()).thenReturn("notanusername");
        when(loginView.getPassword()).thenReturn("nope");
        User user = new User("notausername", "nope", 0);
        when(loginService.userLogin(eq("notausername"), eq("nope"))).thenReturn(user);
        LoginController controller = new LoginController(loginView, loginService);

        controller.login();

        verify(loginView).showErrorMessage("Invalid username/password");
    }

    @Test
    public void givenInvalidUsernameAndPassword_login_showErrorMessage2() {
        TestLoginView loginView = new TestLoginView("notanusername", "nope");
        ILoginService loginService = mock(ILoginService.class);

        LoginController controller = new LoginController(loginView, loginService);

        controller.login();

        assertEquals(loginView.shownErrorMessage, "Invalid username/password");
    }

    class TestLoginView implements ILoginView
    {
        private final String username;
        private final String password;

        TestLoginView(String username, String password)
        {
            this.username = username;
            this.password = password;
        }

        @Override
        public void showRegularView(User user) {

        }

        @Override
        public void showAdminView(User user) {

        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public void setVisibleLoginView(boolean boolValue) {

        }

        public String shownErrorMessage;
        @Override
        public void showErrorMessage(String message) {
            shownErrorMessage = message;
        }
    }
}
