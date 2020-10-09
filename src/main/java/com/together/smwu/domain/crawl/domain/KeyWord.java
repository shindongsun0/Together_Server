package com.together.smwu.domain.crawl.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@Table(name = "KEYWORD")
public class KeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "latest_author", nullable = false)
    private String latestAuthor;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLatestAuthor() {
        return this.latestAuthor;
    }

    public void setLatestAuthor(String latestAuthor){
        this.latestAuthor = latestAuthor;
    }
}
