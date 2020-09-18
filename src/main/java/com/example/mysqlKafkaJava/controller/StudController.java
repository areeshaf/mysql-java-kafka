package com.example.mysqlKafkaJava.controller;

import com.example.mysqlKafkaJava.model.Stud;
//import com.example.mysqlKafkaJava.repository.StudRepo;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
public class StudController {





//    public static Topology createTopology(){
//        StreamsBuilder builder = new StreamsBuilder();
//        // 1 - stream from Kafka
//
//       // Stud student = new Stud();
//        KStream<String, String> textLines = builder.stream("springg-inputt-topicc");
//        KStream<String, String> wordCounts = textLines.filter((key, value) -> value.contains("Pass")== true);
//
//        wordCounts.to("springg-outputt-topicc", Produced.with(Serdes.String(), Serdes.String()));
//
//        wordCounts.foreach((key,value) -> new Stud(key,value));
//
//
//        return builder.build();
//    }



//    @PostConstruct
//    private void home(){
//
//
//
//    }




    Processor processor;
    @Autowired
    public StudController(Processor processor){
        this.processor=processor;
    }


    public void consume() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "record-application");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        //RecordApp recordApp = new RecordApp();

        Topology topology = new Topology().addSource("Input topic", "springg-inputt-topic")
                .addProcessor("PROCESS_ORG-SRCH", () -> processor, "Input topic");
        KafkaStreams streams = new KafkaStreams(topology, properties);
        streams.start();


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
