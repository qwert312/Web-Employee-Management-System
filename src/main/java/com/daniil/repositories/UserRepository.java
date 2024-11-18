package com.daniil.repositories;

import com.daniil.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u")
    Page<User> findAllUsersPageable(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = :username, u.password = :password WHERE u.id = :id")
    void updateUsernameAndPassword(String username, String password, long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = :username WHERE u.id = :id")
    void updateUsername(String username, long id);
}
