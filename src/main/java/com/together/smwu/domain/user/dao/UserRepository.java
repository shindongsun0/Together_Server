package com.together.smwu.domain.user.dao;

import com.together.smwu.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUid(String id);

    Optional<User> findByUidAndProvider(String uid, String provider);
}
