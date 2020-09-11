package com.together.smwu.domain.user.dao;

import com.together.smwu.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialId(String id);

    Optional<User> findBySocialIdAndProvider(String uid, String provider);
}
