package com.together.smwu.web.repository.place;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.web.repository.place.QPlace.place;

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
