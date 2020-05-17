package ro.utcluj.Client.View;

import ro.utcluj.ClientAndServer.Model.User;

interface ILoginDataProvider {

    String getUsername();
    String getPassword();

}

interface IViewProvider {

    void setVisibleLoginView(boolean boolValue);
    void showLoadingScreen();
    void showRegularView(User user);
    void showAdminView(User user);
    void showErrorMessage(String message);

}

public interface ILoginView extends ILoginDataProvider, IViewProvider{
}
