package com.together.smwu.batch.config;

import com.google.common.collect.Queues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Queue;

@Component
public class DataShareBean<T> {
    private static Logger logger = LoggerFactory.getLogger(DataShareBean.class);
    private Queue<T> shareDataQueue;

    public DataShareBean() {
        this.shareDataQueue = Queues.newConcurrentLinkedQueue();
    }

    public void putData(T data) {
        if (shareDataQueue == null) {
            logger.error("Queue is not initialized");
            return;
        }
        shareDataQueue.addAll(Collections.singleton(data));
    }

    public T getData() {
        if (shareDataQueue == null) {
            return null;
        }
        return shareDataQueue.poll();
    }

    public int getSize() {
        if (this.shareDataQueue == null) {
            logger.error("Map is not initialize");
            return 0;
        }
        return shareDataQueue.size();
    }
}
