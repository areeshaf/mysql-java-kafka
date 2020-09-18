package com.example.mysqlKafkaJava.controller;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor extends AbstractProcessor<String, String> {
    @Override
    public void process(String key, String record) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            //LOGGER.info(e);
        }
    }

}
