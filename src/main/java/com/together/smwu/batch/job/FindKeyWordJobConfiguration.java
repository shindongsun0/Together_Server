package com.together.smwu.batch.job;

import com.together.smwu.batch.BatchHelper;
import com.together.smwu.batch.config.JpaItemListWriter;
import com.together.smwu.batch.processor.CountKeyWordPlaceProcessor;
import com.together.smwu.batch.processor.NaverCrawlProcessor;
import com.together.smwu.batch.reader.KeyWordPlaceReader;
import com.together.smwu.batch.writer.NaverBatchWriter;
import com.together.smwu.domain.crawl.domain.KeyWord;
import com.together.smwu.domain.crawl.domain.KeyWordPlace;
import com.together.smwu.domain.crawl.domain.KeyWordPlaceCount;
import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class FindKeyWordJobConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(FindKeyWordJobConfiguration.class);
    private static final String JOB_NAME = "findKeyWordJob";
    private static final String CRAWLING_STEP = "crawlingStep";
    private static final String COUNT_KEY_WORD_PLACE_STEP = "countKeyWordPlaceStep";
    private static final int CHUNK_SIZE = 2;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final NaverCrawlProcessor naverBatchProcessor;
    private final NaverBatchWriter naverBatchWriter;
    private final KeyWordPlaceReader keyWordPlaceReader;
    private final CountKeyWordPlaceProcessor countKeyWordPlaceProcessor;

    @Bean(JOB_NAME + "jobParameter")
    @JobScope
    public CreatedDateJobParameter jobParameter(@Value("#{jobParameters[createdDate]}") String createdDateStr) {
        return new CreatedDateJobParameter(createdDateStr);
    }

    @Bean
    public CronTriggerFactoryBean findKeyWordJobTrigger() {
        return BatchHelper.cronTriggerFactoryBeanBuilder()
                .cronExpression("0 0/2 * * * ?")
//                .cronExpression("0 10 23 * *")
                .jobDetailFactoryBean(crawlingJobSchedule())
                .build();
    }

    @Bean
    public JobDetailFactoryBean crawlingJobSchedule() {
        return BatchHelper.jobDetailFactoryBeanBuilder()
                .job(crawlingJob())
                .build();
    }

    @Bean
    public Job crawlingJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(CrawlingStep())
                .next(CountKeyWordPlaceStep())
                .build();
    }

    private Step CrawlingStep() { //TODO KeyWord의 latestAuthor?면 일단 pass하는 크롤링 logic 추가
        return stepBuilderFactory.get(CRAWLING_STEP)
                .<KeyWord, List<NaverCrawlingResult>>chunk(CHUNK_SIZE)
                .reader(keyWordReader())
                .processor(naverBatchProcessor)
                .writer(naverBatchWriter)
                .build();
    }

    private Step CountKeyWordPlaceStep() {
        return stepBuilderFactory.get(COUNT_KEY_WORD_PLACE_STEP)
                .<List<KeyWordPlace>, List<KeyWordPlaceCount>>chunk(CHUNK_SIZE)
                .reader(keyWordPlaceReader)
                .processor(countKeyWordPlaceProcessor)
                .writer(writerList())
                .build();
    }

    @Bean
    public JpaPagingItemReader<KeyWord> keyWordReader() {
        return new JpaPagingItemReaderBuilder<KeyWord>()
                .name(JOB_NAME + "_reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT k FROM KeyWord k")
                .saveState(false)
                .build();
    }

    private JpaItemListWriter<KeyWordPlaceCount> writerList() {
        JpaItemWriter<KeyWordPlaceCount> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);

        return new JpaItemListWriter<>(writer);
    }
//    @Bean
//    @StepScope
//    public QuerydslPagingItemReader<Place> findKeyWordReader() {
//        logger.info("[FindKeyWordJob]: findKeyWord Reader begin.");
//        return new QuerydslPagingItemReader<String>(emf, CHUNK_SIZE, queryFactory -> queryFactory
//                .selectFrom(place)
//                .where(place.postTime.eq(jobParameter.getTxDate())))
//                .name( "fi ndKeyWordReader")
//                .entityManagerFactory(emf)
//                .pageSize(CHUNK_SIZE)
//                .queryString("")
//                .build();
//    }
}
