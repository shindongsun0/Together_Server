package com.together.smwu.web.repository.place.image;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.together.smwu.web.repository.place.Place;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.together.smwu.web.repository.place.image.QPlaceImage.placeImage;

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
