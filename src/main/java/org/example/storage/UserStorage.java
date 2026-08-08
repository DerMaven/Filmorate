package org.example.storage;

import org.example.exception.RegistrationException;
import org.example.exception.UpdateException;
import org.example.model.User;

import java.util.List;

public interface UserStorage {

    public User register(User user);
    public User update(User user);
    public List<User> getUsers();
    public User findById(Long userId);
}
