package com.together.smwu.domain.crawl.dao;

import com.together.smwu.domain.crawl.domain.KeyWordPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyWordPlaceRepository extends JpaRepository<KeyWordPlace, Long>, KeyWordPlaceRepositoryCustom {
    Optional<KeyWordPlace> findByName(String name);
}
