package ro.utcluj.View;

import ro.utcluj.Model.model.User;

interface ILoginDataProvider {

    String getUsername();
    String getPassword();

}

interface IViewProvider {

    void setVisibleLoginView(boolean boolValue);
    void showRegularView(User user);
    void showAdminView(User user);
    void showErrorMessage(String message);

}

public interface ILoginView extends ILoginDataProvider, IViewProvider{
}
