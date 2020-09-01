package com.together.smwu.domain.placeImage.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.placeImage.domain.PlaceImage;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.domain.placeImage.domain.QPlaceImage.placeImage;

@RequiredArgsConstructor
public class PlaceImageRepositoryImpl implements PlaceImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<PlaceImage> findAllByPlace(Place place) {
        return queryFactory
                .select(placeImage)
                .from(placeImage)
                .where(placeImage.place.eq(place))
                .fetch();
    }
}
