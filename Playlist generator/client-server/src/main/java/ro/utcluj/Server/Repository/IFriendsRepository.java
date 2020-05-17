package ro.utcluj.Server.Repository;

import ro.utcluj.ClientAndServer.Model.Friends;
import java.util.List;

public interface IFriendsRepository {
    List<Friends> viewAllFriendsRel(int idMe);
    List<Friends> viewAllFriendRelRequests(int idMe);
    List<Friends> viewAllFriendsRelConfirmedOrNot(int idMe);
    List<Friends> viewAllPendingFriendRequests(int idMe);
    int addFriend(Friends friend);
    int confirmFriendRequest(int id);
    int deleteFriend(int id);
}
