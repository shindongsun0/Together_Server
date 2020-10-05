package com.together.smwu.batch.writer;

import com.together.smwu.domain.crawl.dao.KeyWordPlaceRepository;
import com.together.smwu.domain.crawl.dto.KeyWordPlace;
import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class NaverBatchWriter implements ItemWriter<List<NaverCrawlingResult>> {
    private static final Logger logger = LoggerFactory.getLogger(NaverBatchWriter.class);
    private final KeyWordPlaceRepository keyWordPlaceRepository;

    @Transactional
    @Override
    public void write(List<? extends List<NaverCrawlingResult>> items) throws Exception {
        List<KeyWordPlace> keyWordPlaces = items.stream()
                .flatMap(List::stream)
                .map(KeyWordPlace::from)
                .collect(Collectors.toList());
        keyWordPlaceRepository.saveAll(keyWordPlaces);
    }
}
