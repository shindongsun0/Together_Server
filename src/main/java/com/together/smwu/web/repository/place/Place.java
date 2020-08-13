package com.together.smwu.web.repository.place;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "PLACE")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private long placeId;

    @Column(name = "name", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    String name;

    @Column(name = "category")
    int category;

    @Column(name = "map_x")
    long mapX;

    @Column(name = "map_y")
    long mapY;

    String content;

    String location;

    @Column(name = "main_image_url")
    String mainImageUrl;
}
