package com.together.smwu.domain.crawl.dao;

import com.together.smwu.domain.crawl.domain.KeyWordPlace;

import java.util.List;

public interface KeyWordPlaceRepositoryCustom {
    List<KeyWordPlace> getLatestAuthorFromKeyWord();
}
