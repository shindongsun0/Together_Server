package com.together.smwu.domain.crawl.dto;

public class KeyWordDate {
    private String keyWord;
    private String author;

    public KeyWordDate(String keyWord, String author) {
        this.keyWord = keyWord;
        this.author = author;
    }

    public KeyWordDate() {
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public String getAuthor() {
        return this.author;
    }
}
