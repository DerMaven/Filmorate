package org.example.storage.jpa_repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u.* from users u join users_friends uf1 on u.id = uf1.friend_id join users_friends uf2 on u.id = uf2.friend_id where uf1.user_id = :userId and :uf2.user_id = :otherId")
    List<User> getCommonFriends(Long id, Long otherId);
}
