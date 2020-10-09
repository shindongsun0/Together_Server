package com.together.smwu.batch.writer;

import com.together.smwu.batch.config.DataShareBean;
import com.together.smwu.domain.crawl.dao.KeyWordPlaceRepository;
import com.together.smwu.domain.crawl.dao.KeyWordRepository;
import com.together.smwu.domain.crawl.domain.KeyWord;
import com.together.smwu.domain.crawl.domain.KeyWordPlace;
import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import com.together.smwu.domain.crawl.exception.KeyWordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@StepScope
public class NaverBatchWriter implements ItemWriter<List<NaverCrawlingResult>> {
    private static final Logger logger = LoggerFactory.getLogger(NaverBatchWriter.class);
    private final KeyWordPlaceRepository keyWordPlaceRepository;
    private final KeyWordRepository keyWordRepository;
    private DataShareBean<List<KeyWordPlace>> dataShareBean;

    @Transactional
    @Override
    public void write(List<? extends List<NaverCrawlingResult>> items) throws Exception {
        List<KeyWordPlace> keyWordPlaces = items.stream()
                .flatMap(List::stream)
                .map(KeyWordPlace::from)
                .collect(Collectors.toList());
        keyWordPlaceRepository.saveAll(keyWordPlaces);
        setUpForTransmission(keyWordPlaces);
        updateKeyWordInfo();
    }

    private void setUpForTransmission(List<KeyWordPlace> keyWordPlaces) {
            dataShareBean.putData(keyWordPlaces);
    }

    private void updateKeyWordInfo() {
        List<KeyWordPlace> latestKeyWordPlaces = keyWordPlaceRepository.getLatestAuthorFromKeyWord();
        List<KeyWord> keyWords = latestKeyWordPlaces.stream()
                .map(this::updateKeyWordLatestAuthor)
                .collect(Collectors.toList());
        keyWordRepository.saveAll(keyWords);
    }


    private KeyWord updateKeyWordLatestAuthor(KeyWordPlace keyWordPlace) {
        KeyWord keyword = keyWordRepository.findByName(keyWordPlace.getKeyWord())
                .orElseThrow(KeyWordNotFoundException::new);
        keyword.setLatestAuthor(keyWordPlace.getAuthor());
        return keyword;
    }
}
