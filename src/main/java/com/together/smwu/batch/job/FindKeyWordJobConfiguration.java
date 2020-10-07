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
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class FindKeyWordJobConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(FindKeyWordJobConfiguration.class);
    private static final String JOB_NAME = "crawlingJob";
    private static final String STEP_NAME = "crawlingStep";
    private static final int CHUNK_SIZE = 2;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final NaverCrawlProcessor naverBatchProcessor;
    private final NaverBatchWriter naverBatchWriter;
    private final CreatedDateJobParameter jobParameter;

    @Autowired
    public FindKeyWordJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                       EntityManagerFactory entityManagerFactory, NaverCrawlProcessor naverBatchProcessor, NaverBatchWriter naverBatchWriter, CreatedDateJobParameter jobParameter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.naverBatchProcessor = naverBatchProcessor;
        this.naverBatchWriter = naverBatchWriter;
        this.jobParameter = jobParameter;
    }

    @Bean(JOB_NAME + "jobParameter")
    @JobScope
    public CreatedDateJobParameter jobParameter(@Value("#{jobParameters[createdDate]}") String createdDateStr) {
        return new CreatedDateJobParameter(createdDateStr);
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
//                .next(findLocationOfKeyWordPlace)
                .build();
    }

    private Step CrawlingStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .<KeyWord, List<NaverCrawlingResult>>chunk(CHUNK_SIZE)
                .reader(keyWordReader())
                .processor(naverBatchProcessor)
                .writer(naverBatchWriter)
                .build();
    }

    @Bean(name = JOB_NAME + "_reader")
    @StepScope
    public JpaPagingItemReader<KeyWord> keyWordReader() {
        Map<String, Object> params = new HashMap<>();
        params.put("createDateTime", jobParameter.getCreatedDate());
        logger.info(">>>>>>>>>>> createdDateTime={}", jobParameter.getCreatedDate());

        return new JpaPagingItemReaderBuilder<KeyWord>()
                .name(JOB_NAME + "_reader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT k FROM KeyWord k WHERE k.createdDate = :order by keyword_id")
                .parameterValues(params)
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
