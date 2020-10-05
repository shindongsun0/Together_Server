package com.together.smwu.domain.crawl.dao;

import com.together.smwu.domain.crawl.dto.KeyWordPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyWordPlaceRepository extends JpaRepository<KeyWordPlace, Long> {
    Optional<KeyWordPlace> findByName(String name);
}
