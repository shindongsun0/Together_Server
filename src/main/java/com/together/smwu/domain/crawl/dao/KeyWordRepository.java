package com.together.smwu.domain.crawl.dao;

import com.together.smwu.domain.crawl.domain.KeyWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyWordRepository extends JpaRepository<KeyWord, Long> {
    Optional<KeyWord> findByName(String name);
}
