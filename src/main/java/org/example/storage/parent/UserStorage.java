package org.example.storage.parent;

import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User register(User user);
    User update(User user);
    List<User> getUsers();
    User findById(Long userId);
    void addFriend(Long userId, Long friendId);
    void deleteFriend(Long userId, Long friendId);
    List<User> getFriends(Long userId);
    List<User> getCommonFriends(Long userId, Long otherId);
}
