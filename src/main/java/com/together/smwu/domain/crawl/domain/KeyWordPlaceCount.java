package com.together.smwu.domain.crawl.domain;

import com.together.smwu.global.config.entity.BaseEntity;
import lombok.Builder;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "keyword_place_count_id"))
@Table(name = "KEYWORD_PLACE_COUNT")
public class KeyWordPlaceCount extends BaseEntity {
    private String keyWord;
    private String name;
    private String location;
    private int count;

    @Builder
    public KeyWordPlaceCount(String keyWord, String name, String location, int count) {
        this.keyWord = keyWord;
        this.name = name;
        this.location = location;
        this.count = count;
    }

    public KeyWordPlaceCount() {
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

    public int getCount() {
        return this.count;
    }
}
