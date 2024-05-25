//package org.example.kafkalabs.streams;
//
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.*;
//import org.example.kafkalabs.model.CatData;
//import org.example.kafkalabs.utill.KafkaConnectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Random;
//
//import static org.example.kafkalabs.config.kafka.KafkaTopicConfig.*;
//
//@Component
//public class KafkaStreamsPipelineLab3 {
//
//    private static final Serde<String> STRING_SERDE = Serdes.String();
//    private final KafkaConnectMapper kafkaConnectMapper;
//
//    private static final String KEY = "KEY";
//
//    public KafkaStreamsPipelineLab3(KafkaConnectMapper kafkaConnectMapper) {
//        this.kafkaConnectMapper = kafkaConnectMapper;
//    }
//
//    @Autowired
//    void lab3Pipeline(StreamsBuilder streamsBuilder) {
//        KStream<String, String> messageStream = streamsBuilder
//                .stream(INPUT_TOPIC, Consumed.with(STRING_SERDE, STRING_SERDE));
//
//        //1. Відфільтрувати записи, де коти ловлять більше 3х мишей за місяць.
//        messageStream.mapValues(value -> kafkaConnectMapper.getObjectFromStringMessage(value, CatData.class))
//                .filter((key, value) -> value.getPreyP_Month() > 3)
//                .mapValues(kafkaConnectMapper::mapObjectToStringMessage)
//                .to(MORE_THAN_3_OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));
//
//        // 2. Розділити записи на три гілки: вік 4 , від 4 до 10, більше 10 Записати результати у різні тем
//        messageStream.mapValues(value -> kafkaConnectMapper.getObjectFromStringMessage(value, CatData.class))
//                .split()
//                .branch((key, value) -> value.getAgeYears() < 4, Branched.withConsumer(
//                        (ks) -> ks.selectKey((key, value) -> KEY)
//                                .mapValues(kafkaConnectMapper::mapObjectToStringMessage)
//                        .to(LESS_THAN_4_OUTPUT_TOPIC)))
//                .branch((key, value) -> value.getAgeYears() >= 4 && value.getAgeYears() <= 10, Branched.withConsumer(
//                        (ks) -> ks.selectKey((key, value) -> KEY)
//                                .mapValues(kafkaConnectMapper::mapObjectToStringMessage)
//                        .to(MORE_EQUAL_THAN_4_LESS_EQUAL_THAN_10_OUTPUT_TOPIC)))
//                .branch((key, value) -> value.getAgeYears() > 10, Branched.withConsumer(
//                        (ks) -> ks.selectKey((key, value) -> KEY)
//                                .mapValues(kafkaConnectMapper::mapObjectToStringMessage)
//                        .to(MORE_THAN_10_OUTPUT_TOPIC)))
//                .defaultBranch();
//    }
//}
