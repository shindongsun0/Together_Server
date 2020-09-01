package com.together.smwu.domain.place.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.place.domain.Place;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.domain.place.domain.QPlace.place;

@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Place> findAll(){
        return queryFactory
                .select(place)
                .from(place)
                .orderBy(place.postTime.desc())
                .fetch();
    }

}
