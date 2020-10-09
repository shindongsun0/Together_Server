package com.together.smwu.batch.reader;

import com.together.smwu.batch.config.DataShareBean;
import com.together.smwu.domain.crawl.domain.KeyWordPlace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemReader;

import javax.annotation.PostConstruct;
import java.util.List;

@StepScope
@Component
public class KeyWordPlaceReader implements ItemReader<List<KeyWordPlace>> {
    private static final Logger logger = LoggerFactory.getLogger(KeyWordPlaceReader.class);

    private DataShareBean<List<KeyWordPlace>> dataShareBean;
    private List<KeyWordPlace> keyWordPlaces;
    private boolean isRead;

    public KeyWordPlaceReader() {
    }

    public KeyWordPlaceReader(DataShareBean<List<KeyWordPlace>> dataShareBean, List<KeyWordPlace> keyWordPlaces) {
        this.dataShareBean = dataShareBean;
        this.keyWordPlaces = keyWordPlaces;
    }

    @PostConstruct
    public void init(){
        isRead = false;
    }
    @Override
    public List<KeyWordPlace> read() throws Exception {
        logger.info("[KeyWordPlaceReader] Start");
        if(!isRead){
            isRead = true;
            List<KeyWordPlace> keyWordPlaces = this.keyWordPlaces;
            logger.info("[KeyWordPlaceReader] KeyWordPlace 수: {}", keyWordPlaces.size());
            return keyWordPlaces;
        }
        return null;
    }

    @BeforeStep
    public void retrieveInterstepData(StepExecution stepExecution){
        this.keyWordPlaces = dataShareBean.getData();
    }
}
