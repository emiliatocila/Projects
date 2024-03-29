package ro.utcluj.Client.View;

import ro.utcluj.Client.Controller.LoginController;
import ro.utcluj.ClientAndServer.Model.User;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame implements ILoginView {

    private JPanel loginPanel;
    private JPanel loginInfoPanel;
    private final JTextField usernameTextField;
    private final JPasswordField passwordTextField;
    private final ImageIcon loadingIcon = new ImageIcon("ajax-loader.gif");

    public LoginView() {
        loginPanel = new JPanel();
        loginInfoPanel = new JPanel();
        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        usernamePanel.setLayout(new FlowLayout());
        passwordPanel.setLayout(new FlowLayout());
        loginInfoPanel.setLayout(new BoxLayout(loginInfoPanel, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        usernameTextField = new JTextField(15);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

        passwordTextField = new JPasswordField(15);
        passwordTextField.setEchoChar('*');
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);

        JButton loginButton = new JButton();
        loginButton.setText("Login");

        loginInfoPanel.add(usernamePanel);
        loginInfoPanel.add(passwordPanel);
        loginInfoPanel.add(loginButton);

        loginPanel.add(loginInfoPanel);

        this.setContentPane(loginPanel);
        this.pack();
        this.setMinimumSize(new Dimension(400, 200));
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        LoginController loginController = new LoginController(this);
        loginButton.addActionListener(e -> loginController.login());
    }

    @Override
    public void setVisibleLoginView(boolean boolValue){
        this.setVisible(boolValue);
    }

    @Override
    public void showLoadingScreen() {
        loginPanel.remove(loginInfoPanel);
        loginPanel.add(new JLabel("Logging in...", loadingIcon, JLabel.CENTER));
        loginPanel.revalidate();
        loginPanel.repaint();
    }

    @Override
    public void showRegularView(User user) {
        new RegularView(user).setVisible(true);
    }

    @Override
    public void showAdminView(User user) {
        new AdminView(user).setVisible(true);
    }

    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }

    @Override
    public String getPassword() {
        return passwordTextField.getText();
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}
