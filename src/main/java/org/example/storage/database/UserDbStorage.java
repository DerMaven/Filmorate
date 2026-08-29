package org.example.storage.database;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.storage.parent.UserStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository("dbUserStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User register(User user) {
        String sql = "insert into users (id, email, login, name, birthday) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        return user;
    }

    @Override
    public User update(User user) {
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public List<User> getUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, UserDbStorage::userRowMapper);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        String sql = "select * from users where id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, UserDbStorage::userRowMapper, userId);
            return Optional.ofNullable(user);
        } catch (UserNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        String sql = "insert into users_friends values (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public void deleteFriend(Integer userId, Integer friendId) {
        String sql = "delete from users_friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> getFriends(Integer userId) {
        String sql = "select u.* from users u " +
                "join users_friends uf on u.id = uf.friend_id " +
                "where uf.user_id = ?";
        return jdbcTemplate.query(sql, UserDbStorage::userRowMapper, userId);
    }

    @Override
    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        String sql = "select u.* from users u " +
                "join users_friends uf1 on u.id = uf1.friend_id " +
                "join users_friends uf2 on u.id = uf2.friend_id " +
                "where uf1.user_id = ? and uf2.user_id = ?";
        return jdbcTemplate.query(sql, UserDbStorage::userRowMapper, userId, otherId);
    }


    private static User userRowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate()
        );
    }
}
