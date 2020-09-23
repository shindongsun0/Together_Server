package com.together.smwu.domain.placeImage.domain;

import com.together.smwu.domain.place.domain.Place;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "PLACE_IMAGE")
public class PlaceImage {

    public PlaceImage() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public PlaceImage(final Place place, final String imageUrl) {
        this.place = place;
        this.imageUrl = imageUrl;
    }
}
