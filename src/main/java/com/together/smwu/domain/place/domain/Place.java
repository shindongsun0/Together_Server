package com.together.smwu.domain.place.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name = "PLACE")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    String name;

    @Column(name = "category")
    Integer category;

    @Column(name = "map_x")
    Long mapX;

    @Column(name = "map_y")
    Long mapY;

    String content;

    String location;

    @Column(name = "main_image_url")
    String mainImageUrl;

    @CreationTimestamp
    @Column(name = "post_time", nullable = false)
    Timestamp postTime;
    
    @Builder
    public Place(String name, Integer category, Long mapX, Long mapY,
                 String content, String location, String mainImageUrl) {
        this.name = name;
        this.category = category;
        this.mapX = mapX;
        this.mapY = mapY;
        this.content = content;
        this.location = location;
        this.mainImageUrl = mainImageUrl;
    }

    public void update(Place place) {
        this.name = place.name;
        this.category = place.category;
        this.mapX = place.mapX;
        this.mapY = place.mapY;
        this.content = place.content;
        this.location = place.location;
        this.mainImageUrl = place.mainImageUrl;
    }

    public void updateMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCategory() {
        return category;
    }

    public Long getMapX() {
        return mapX;
    }

    public Long getMapY() {
        return mapY;
    }

    public String getContent() {
        return content;
    }

    public String getLocation() {
        return location;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public Timestamp getPostTime() {
        return postTime;
    }
}
