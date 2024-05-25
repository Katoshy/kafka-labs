//package org.example.kafkalabs.streams;
//
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.*;
//import org.example.kafkalabs.model.CatData;
//import org.example.kafkalabs.utill.KafkaConnectMapper;
//import org.example.kafkalabs.utill.StringUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//
//import static org.example.kafkalabs.config.kafka.KafkaTopicConfig.*;
//
//@Component
//public class KafkaStreamsPipelineLab4 {
//
//    private static final Serde<String> STRING_SERDE = Serdes.String();
//    private final KafkaConnectMapper kafkaConnectMapper;
//
//    private static final String KEY = "KEY";
//
//    public KafkaStreamsPipelineLab4(KafkaConnectMapper kafkaConnectMapper) {
//        this.kafkaConnectMapper = kafkaConnectMapper;
//    }
//
//    @Autowired
//    public void lab4PipelineCowsPrice(StreamsBuilder streamsBuilder) {
//        KStream<String, String> messageStream = streamsBuilder
//                .stream(INPUT_TOPIC, Consumed.with(STRING_SERDE, STRING_SERDE));
//
//        // 1. Порахувати кількість котів, що ловлять більше 3 мишей на місяць.
//        KTable<String, Long> huntsMoreThan3 = messageStream.selectKey((key, value) -> KEY)
//                .mapValues(value -> kafkaConnectMapper.getObjectFromStringMessage(value, CatData.class))
//                .filter((key, value) -> value.getPreyP_Month() > 3)
//                .mapValues(kafkaConnectMapper::mapObjectToStringMessage)
//                .groupBy((key, value) -> key)
//                .count();
//
//        huntsMoreThan3.toStream()
//                .mapValues(Object::toString)
//                .to(AMOUNT_WHERE_CAT_HUNTS_OVER_3);
//
//        // 2. Порахувати кількість стерилізованих котів молодших 4 років.
//        KTable<String, Long> amountOfMilkProducedDuringYearsAvgPriceMilkLower013 =
//                messageStream.selectKey((key, value) -> KEY)
//                        .mapValues(value -> kafkaConnectMapper.getObjectFromStringMessage(value, CatData.class))
//                        .filter((key, value) -> value.getAgeYears() < 4 &&
//                                ("Neutered".equals(value.getAnimalReproductiveCondition()) ||
//                                        "Spayed".equals(value.getAnimalReproductiveCondition())))
//                        .mapValues(kafkaConnectMapper::mapObjectToStringMessage)
//                        .groupBy((key, value) -> key)
//                        .count();
//
//        amountOfMilkProducedDuringYearsAvgPriceMilkLower013.toStream()
//                .mapValues(Object::toString)
//                .to(STERILIZED_CATS_YOUNGER_THAN_4_YEARS);
//
////
//        // 3. Зчитати у Kafka Stream потоки з п.2 лабораторної роботи №3 (результат розгалудження).
//        // Об’єднати ці потоки за допомогою операцій join.
//
//        KStream<String, String> lessThan4 = streamsBuilder
//                .stream(LESS_THAN_4_OUTPUT_TOPIC, Consumed.with(STRING_SERDE, STRING_SERDE));
//
//        KStream<String, String> moreEqualThan4LessEqualThan10 = streamsBuilder
//                .stream(MORE_EQUAL_THAN_4_LESS_EQUAL_THAN_10_OUTPUT_TOPIC, Consumed.with(STRING_SERDE, STRING_SERDE));
//
//        KStream<String, String> moreThan10 = streamsBuilder
//                .stream(MORE_THAN_10_OUTPUT_TOPIC, Consumed.with(STRING_SERDE, STRING_SERDE));
//
//
//        KStream<String, String> joinedStream = lessThan4
//                .join(
//                        moreEqualThan4LessEqualThan10,
//                        StringUtil::getRandom,
//                        JoinWindows.of(Duration.ofMinutes(5))
//                )
//                .join(
//                        moreThan10,
//                        StringUtil::getRandom,
//                        JoinWindows.of(Duration.ofMinutes(5))
//                );
//
//        joinedStream.to(JOINED_4_TO_10_YEARS_CATS);
//    }
//}