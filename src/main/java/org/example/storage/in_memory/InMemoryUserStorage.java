package org.example.storage.in_memory;

import lombok.RequiredArgsConstructor;
import org.example.exception.*;
import org.example.model.User;
import org.example.storage.parent.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("memoryUserStorage")
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private Long idCounter = 0L;
    Map<Long, User> userRepository = new HashMap<>();

    @Override
    public User register(User user) {
        if (userRepository.containsKey(user.getId())) {
            throw new RegistrationException("Данный пользователь уже существует");
        }
        if (user.getName() == null) user.setName(user.getLogin());
        user.setId(generateId());
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!userRepository.containsKey(user.getId())) {
            throw new UpdateException("Данного пользователя не существует");
        }
        userRepository.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.values().stream().toList();
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
        User friend = findById(friendId);
        User user = findById(userId);

        if (Objects.equals(userId, friendId)) throw new SameIDException("ID пользователей одинаковы");
        if (user.getFriends().contains(friend)) throw new UserAlreadyExistsException("Друг с ID: " + friendId + " уже существует в списке друзей");

        friend.getFriends().add(user);
        user.getFriends().add(friend);
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        User friend = findById(friendId);
        User user = findById(userId);

        if (Objects.equals(userId, friendId)) throw new SameIDException("ID пользователей одинаковы");
        if (!user.getFriends().contains(friend)) throw new UserNotFoundException("Друг с ID: " + friendId + " отсутствует в списке друзей");

        friend.getFriends().remove(user);
        user.getFriends().remove(friend);
    }

    @Override
    public List<User> getFriends(Long userId) {
        User user = findById(userId);
        return user.getFriends()
                .stream().toList();
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long otherId) {
        User otherUser = findById(otherId);
        User user = findById(userId);


        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .toList();
    }

    private Long generateId() {
        return ++idCounter;
    }

    @Override
    public User findById(Long userId) {
        return userRepository.get(userId);
    }
}
