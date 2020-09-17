package com.example.mysqlKafkaJava.controller;

import com.example.mysqlKafkaJava.model.Stud;
import com.example.mysqlKafkaJava.repository.StudRepo;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class StudController {

    @Autowired
    static
    StudRepo studRepo;

    public static Topology createTopology(){
        StreamsBuilder builder = new StreamsBuilder();
        // 1 - stream from Kafka

       // Stud student = new Stud();
        KStream<String, String> textLines = builder.stream("spring-input-topicc");
        KStream<String, String> wordCounts = textLines.filter((key, value) -> value.contains("Pass")== true);

        wordCounts.to("spring-output-topicc", Produced.with(Serdes.String(), Serdes.String()));

        wordCounts.foreach((key,value) -> new Stud(key,value));


        return builder.build();
    }


    public void home(Stud stud){

        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "record-application");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        //RecordApp recordApp = new RecordApp();
        KafkaStreams streams = new KafkaStreams(StudController.createTopology(), properties);
        streams.start();
        studRepo.save(stud);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        // Update:
        // print the topology every 10 seconds for learning purposes
        while(true){
            streams.localThreadsMetadata().forEach(data -> System.out.println(data));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }

    }

    //studRepo.save()
}
