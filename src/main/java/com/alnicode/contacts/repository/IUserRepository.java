package com.alnicode.contacts.repository;

import com.alnicode.contacts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);

}
