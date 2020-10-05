package com.together.smwu.domain.crawl.dto;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "KEYWORD_PLACE")
public class KeyWordPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_place_id")
    private Long id;

    private String keyWord;
    private String name;
    private String location;
    private String author;

    @Builder
    public KeyWordPlace(String keyWord, String name, String location, String author, int count) {
        this.keyWord = keyWord;
        this.name = name;
        this.location = location;
        this.author = author;
    }

    public KeyWordPlace() {
    }

    public static KeyWordPlace from(NaverCrawlingResult naverCrawlingResult) {
        return KeyWordPlace.builder()
                .keyWord(naverCrawlingResult.getKeyWord())
                .name(naverCrawlingResult.getPlaceName())
                .location(naverCrawlingResult.getPlaceLocation())
                .author(naverCrawlingResult.getAuthor())
                .build();
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }

    public String getAuthor(){
        return this.author;
    }
}
