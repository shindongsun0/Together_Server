package com.together.smwu.domain.crawl.dto;

public class NaverCrawlingResult {
    private String keyWord;
    private String placeName;
    private String placeLocation;
    private String author;

    public NaverCrawlingResult(String keyWord, String placeName,
                               String placeLocation, String author) {
        this.keyWord = keyWord;
        this.placeName = placeName;
        this.placeLocation = placeLocation;
        this.author = author;
    }

    public NaverCrawlingResult() {
    }

    public static NaverCrawlingResultBuilder builder() {
        return new NaverCrawlingResultBuilder();
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public String getPlaceName() {
        return this.placeName;
    }

    public String getPlaceLocation() {
        return this.placeLocation;
    }

    public String getAuthor() {
        return this.author;
    }

    public static class NaverCrawlingResultBuilder {
        private String keyWord;
        private String placeName;
        private String placeLocation;
        private String author;

        NaverCrawlingResultBuilder() {
        }

        public NaverCrawlingResultBuilder keyWord(String keyWord) {
            this.keyWord = keyWord;
            return this;
        }

        public NaverCrawlingResultBuilder placeName(String placeName) {
            this.placeName = placeName;
            return this;
        }

        public NaverCrawlingResultBuilder placeLocation(String placeLocation) {
            this.placeLocation = placeLocation;
            return this;
        }

        public NaverCrawlingResultBuilder author(String author) {
            this.author = author;
            return this;
        }

        public NaverCrawlingResult build() {
            return new NaverCrawlingResult(keyWord, placeName, placeLocation, author);
        }

        public String toString() {
            return "NaverCrawlingResult.NaverCrawlingResultBuilder(keyWord=" + this.keyWord + ", placeName=" + this.placeName + ", placeLocation=" + this.placeLocation + ", author=" + this.author + ")";
        }
    }
}
