package org.example.storage.parent;

import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User register(User user);
    User update(User user);
    List<User> getUsers();
    Optional<User> findById(Integer userId);
    void addFriend(Integer userId, Integer friendId);
    void deleteFriend(Integer userId, Integer friendId);
    List<User> getFriends(Integer userId);
    List<User> getCommonFriends(Integer userId, Integer otherId);
}
