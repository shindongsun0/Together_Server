package com.together.smwu.batch.job;

import com.together.smwu.batch.BatchHelper;
import com.together.smwu.batch.processor.NaverCrawlProcessor;
import com.together.smwu.batch.writer.NaverBatchWriter;
import com.together.smwu.domain.crawl.domain.KeyWord;
import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
public class FindKeyWordJobConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(FindKeyWordJobConfiguration.class);
    private static final String JOB_NAME = "findKeyWordJob";
    private static final String STEP_NAME = "findKeyWordStep";
    private static final int CHUNK_SIZE = 2;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final NaverCrawlProcessor naverBatchProcessor;
    private final NaverBatchWriter naverBatchWriter;

    @Autowired
    public FindKeyWordJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                       EntityManagerFactory entityManagerFactory, NaverCrawlProcessor naverBatchProcessor, NaverBatchWriter naverBatchWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.naverBatchProcessor = naverBatchProcessor;
        this.naverBatchWriter = naverBatchWriter;
    }

//    @Bean
//    @JobScope
//    public QuerydslPagingItemReaderJobParameter jobParameter() {
//        return new QuerydslPagingItemReaderJobParameter();
//    }

    @Bean
    public CronTriggerFactoryBean findKeyWordJobTrigger() {
        return BatchHelper.cronTriggerFactoryBeanBuilder()
                .cronExpression("0 0/2 * * * ?")
//                .cronExpression("0 10 23 * *")
                .jobDetailFactoryBean(findKeyWordJobSchedule())
                .build();
    }

    @Bean
    public JobDetailFactoryBean findKeyWordJobSchedule() {
        return BatchHelper.jobDetailFactoryBeanBuilder()
                .job(crawlingJob())
                .build();
    }

    @Bean
    public Job crawlingJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(findKeyWordStep())
//                .next(findLocationOfKeyWordPlace)
                .build();
    }

    private Step findKeyWordStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .<KeyWord, List<NaverCrawlingResult>>chunk(CHUNK_SIZE)
                .reader(keyWordReader())
                .processor(naverBatchProcessor)
                .writer(naverBatchWriter)
                .build();
    }

    @Bean
    public JpaPagingItemReader<KeyWord> keyWordReader(){
        return new JpaPagingItemReaderBuilder<KeyWord>()
                .name("keyWordReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT k FROM KeyWord k order by keyword_id")
                .build();
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
