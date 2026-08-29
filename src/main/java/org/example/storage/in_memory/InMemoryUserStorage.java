package org.example.storage.in_memory;

import lombok.RequiredArgsConstructor;
import org.example.exception.RegistrationException;
import org.example.exception.SameIDException;
import org.example.exception.UpdateException;
import org.example.exception.UserAlreadyExistsException;
import org.example.model.User;
import org.example.storage.parent.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("memoryUserStorage")
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private Integer idCounter = 0;
    Map<Integer, User> userRepository = new HashMap<>();

    @Override
    public User register(User user) {
        if (userRepository.containsValue(user)) {
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
    public void addFriend(Integer userId, Integer friendId) {
        User friend = findById(friendId).get();
        User user = findById(userId).get();

        if (Objects.equals(userId, friendId)) throw new SameIDException("ID пользователей одинаковы");
        if (user.getFriends().contains(friend.getId())) throw new UserAlreadyExistsException("Друг с ID: " + friendId + " уже существует в списке друзей");

        friend.getFriends().add(user.getId());
        user.getFriends().add(friend.getId());
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        User friend = findById(friendId).get();
        User user = findById(userId).get();

        if (Objects.equals(userId, friendId)) throw new SameIDException("ID пользователей одинаковы");
        if (user.getFriends().contains(friend.getId())) throw new UserAlreadyExistsException("Друг с ID: " + friendId + " уже существует в списке друзей");

        friend.getFriends().remove(user.getId());
        user.getFriends().remove(friend.getId());
    }

    @Override
    public List<User> getFriends(Integer userId) {
        User user = findById(userId).get();
        return user.getFriends()
                .stream()
                .map(this::findById)
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        User otherUser = findById(otherId).get();
        User user = findById(userId).get();


        return user.getFriends().stream()
                .filter(id -> otherUser.getFriends().contains(id))
                .map(this::findById)
                .flatMap(Optional::stream)
                .toList();
    }

    private Integer generateId() {
        return ++idCounter;
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return Optional.of(userRepository.get(userId));
    }
}
