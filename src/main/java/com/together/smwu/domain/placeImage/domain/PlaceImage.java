package com.together.smwu.domain.placeImage.domain;

import com.together.smwu.domain.place.domain.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "PLACE_IMAGE")
public class PlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_image_id")
    private long placeImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="place_id", nullable = false)
    private Place place;

    @Column(name = "image_url")
    private String imageUrl;
}
