package com.together.smwu.web.repository.user;

import com.together.smwu.web.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUid(String id);

    Optional<User> findByUidAndProvider(String uid, String provider);
}
