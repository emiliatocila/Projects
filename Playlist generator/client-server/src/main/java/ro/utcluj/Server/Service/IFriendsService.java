package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.User;
import java.util.List;

public interface IFriendsService {
    List<User> viewAllFriends(int idMe);
    List<User> viewAllFriendRequests(int idMe);
    List<User> viewAllPendingFriendRequests(int idMe);
    String addFriend(String username, int idMe);
    String confirmFriendRequest(String username, int idMe);
    String deleteFriend(String username, int idMe, int fr);
}
