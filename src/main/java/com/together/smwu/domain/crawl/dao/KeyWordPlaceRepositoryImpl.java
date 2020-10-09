package com.together.smwu.domain.crawl.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.crawl.domain.KeyWordPlace;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.domain.crawl.domain.QKeyWordPlace.keyWordPlace;

@RequiredArgsConstructor
public class KeyWordPlaceRepositoryImpl implements KeyWordPlaceRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<KeyWordPlace> getLatestAuthorFromKeyWord() {
        return queryFactory
                .select(Projections.constructor(KeyWordPlace.class,
                        keyWordPlace.id, keyWordPlace.keyWord, keyWordPlace.name, keyWordPlace.location,
                        keyWordPlace.author, keyWordPlace.createdDate.max()))
                .from(keyWordPlace)
                .groupBy(keyWordPlace.keyWord)
                .fetch();
    }
}
