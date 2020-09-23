package com.together.smwu.domain.placeImage.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceImageRepositoryImpl implements PlaceImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    public List<PlaceImage> findAllByPlace(Place place) {
//        return queryFactory
//                .select(placeImage)
//                .from(placeImage)
//                .where(placeImage.place.eq(place))
//                .fetch();
//    }
}
