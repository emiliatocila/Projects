package ro.utcluj.Server.Service;

import ro.utcluj.ClientAndServer.Model.Friends;
import ro.utcluj.ClientAndServer.Model.User;
import ro.utcluj.Server.Repository.IFriendsRepository;
import ro.utcluj.Server.Repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;

public class FriendsServiceImpl implements IFriendsService {
    private IFriendsRepository friendsRepository;
    private IUserRepository userRepository;

    public FriendsServiceImpl(IFriendsRepository friendsRepository, IUserRepository userRepository){
        this.friendsRepository = friendsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> viewAllFriends(int idMe) {
        List<Friends> friendsRel = friendsRepository.viewAllFriendsRel(idMe);
        List<User> friends = new ArrayList<User>();
        if (!friendsRel.isEmpty()) {
            for (Friends rel : friendsRel) {
                int idOtherFriend = 0;
                if (idMe == rel.getIdFriend1())
                    idOtherFriend = rel.getIdFriend2();
                else if (idMe == rel.getIdFriend2())
                    idOtherFriend = rel.getIdFriend1();
                if (idOtherFriend != 0) {
                    User otherFriend = userRepository.getUserWithId(idOtherFriend);
                    if (otherFriend != null)
                        friends.add(otherFriend);
                }
            }
        }
        return friends;
    }

    @Override
    public List<User> viewAllFriendRequests(int idMe) {
        List<Friends> friendRequestsRel = friendsRepository.viewAllFriendRelRequests(idMe);
        List<User> friendRequests = new ArrayList<User>();
        if (!friendRequestsRel.isEmpty()) {
            for (Friends rel : friendRequestsRel) {
                int idOtherFriend = 0;
                if (idMe == rel.getIdFriend2())
                    idOtherFriend = rel.getIdFriend1();
                if (idOtherFriend != 0) {
                    User otherFriend = userRepository.getUserWithId(idOtherFriend);
                    if (otherFriend != null)
                        friendRequests.add(otherFriend);
                }
            }
        }
        return friendRequests;
    }

    @Override
    public List<User> viewAllPendingFriendRequests(int idMe) {
        List<Friends> pendingFriendRequests = friendsRepository.viewAllPendingFriendRequests(idMe);
        List<User> pendingFriends = new ArrayList<User>();
        if (!pendingFriendRequests.isEmpty()) {
            for (Friends pending : pendingFriendRequests) {
                int idOtherFriend = pending.getIdFriend2();
                    User otherFriend = userRepository.getUserWithId(idOtherFriend);
                    if (otherFriend != null)
                        pendingFriends.add(otherFriend);
            }
        }
        return pendingFriends;
    }

    @Override
    public String addFriend(String username, int idMe) {
        if (username.length() > 1) {
            User newFriend = userRepository.getUserWithUsername(username);
            if (newFriend != null) {
                List<User> friends = this.viewAllFriends(idMe);
                List<User> friendRequestsToMe = this.viewAllFriendRequests(idMe);
                List<User> friendRequestsToNewFriend = this.viewAllFriendRequests(newFriend.getId());
                int ok = 1;
                for (User u1 : friends)
                    if (u1.getId() == newFriend.getId())
                        ok = 0;
                    if (ok == 1) {
                        for (User u2 : friendRequestsToMe)
                            if (u2.getId() == newFriend.getId())
                                ok = 0;
                            if (ok == 1) {
                                for (User u2 : friendRequestsToNewFriend)
                                    if (u2.getId() == idMe)
                                        ok = 0;
                                    if (ok == 1) {
                                        if (friendsRepository.addFriend(new Friends(idMe, newFriend.getId(), 0)) == 1)
                                            return "Friend request sent!";
                                        else return "Cannot send friend request!";
                                    } else return "You have already sent a friend request to this user!";
                    } else return "This user already sent you a friend request!";
                } else return "You are already friends with this user!";
            } else return "There are no users with this username!";
        } else return "Username must be at least 2 characters long!";
    }

    @Override
    public String confirmFriendRequest(String username, int idMe) {
        User requestedFriendship = userRepository.getUserWithUsername(username);
        List<Friends> friendRelRequests = friendsRepository.viewAllFriendRelRequests(idMe);
        int id = 0;
        for (Friends friendship : friendRelRequests)
            if (friendship.getIdFriend1() == requestedFriendship.getId()) {
                id = friendship.getId();
                break;
            }
        if (friendsRepository.confirmFriendRequest(id) == -1)
            return "Friend request cannot be confirmed!";
        else
            return "Friend request confirmed!";
    }

    @Override
    public String deleteFriend(String username, int idMe, int fr) {
        User toDelete = userRepository.getUserWithUsername(username);
        List<Friends> friendRel = friendsRepository.viewAllFriendsRelConfirmedOrNot(idMe);
        int id = 0;
        for (Friends friendship : friendRel) {
            if (fr == 1) {
                if (friendship.getIdFriend1() == toDelete.getId()) {
                    id = friendship.getId();
                    break;
                }
            } else if (fr == 0) {
                if (friendship.getIdFriend1() == toDelete.getId()) {
                    id = friendship.getId();
                    break;
                } else if (friendship.getIdFriend2() == toDelete.getId()) {
                    id = friendship.getId();
                    break;
                }
            }
        }
        if (fr == 1) {
            if (friendsRepository.deleteFriend(id) == -1)

                return "Friend request cannot be denied!";
            else
                return "Friend request denied!";
        }
        else if (fr == 0) {
            if (friendsRepository.deleteFriend(id) == -1)

                return "Friend cannot be removed!";
            else
                return "Friend removed!";
        }
        else return "Invalid operation!";
    }
}
