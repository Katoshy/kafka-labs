package org.example.kafkalabs.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String INPUT_TOPIC = "cats.public.cats";

//    public static final String MORE_THAN_3_OUTPUT_TOPIC = "more-than-3";
//
//    public static final String LESS_THAN_4_OUTPUT_TOPIC = "less-than-4";
//    public static final String MORE_EQUAL_THAN_4_LESS_EQUAL_THAN_10_OUTPUT_TOPIC = "more-equal-than-4-less-equal-than-10";
//    public static final String MORE_THAN_10_OUTPUT_TOPIC = "more-than-10";
//
//    public static final String AMOUNT_WHERE_CAT_HUNTS_OVER_3 = "amount-cats-hunts-over-3";
//    public static final String STERILIZED_CATS_YOUNGER_THAN_4_YEARS = "amount-sterilized-cats-younger-than-4";
//    public static final String JOINED_4_TO_10_YEARS_CATS = "joined-age-cats";

    public static final String WINDOWED_TOPIC = "windowed";
    public static final String PRODUCER_METRICS_TOPIC = "producer-metrics";


    @Bean
    @Qualifier("cats.public.cats")
    public NewTopic catsTopic() {
        return TopicBuilder.name(INPUT_TOPIC)
                .build();
    }

//    @Bean
//    @Qualifier("more-than-3-topic")
//    public NewTopic moreThan3Topic() {
//        return TopicBuilder.name(MORE_THAN_3_OUTPUT_TOPIC)
//                .build();
//    }
//
//    @Bean
//    @Qualifier("less-than-4")
//    public NewTopic lessThan4() {
//        return TopicBuilder.name(LESS_THAN_4_OUTPUT_TOPIC)
//                .build();
//    }
//
//    @Bean
//    @Qualifier("more-equal-than-4-less-equal-than-10")
//    public NewTopic moreEqualThan4LessEqual10() {
//        return TopicBuilder.name(MORE_EQUAL_THAN_4_LESS_EQUAL_THAN_10_OUTPUT_TOPIC)
//                .build();
//    }
//
//    @Bean
//    @Qualifier("more-than-10")
//    public NewTopic moreThan10() {
//        return TopicBuilder.name(MORE_THAN_10_OUTPUT_TOPIC)
//                .build();
//    }
//
//    @Bean
//    @Qualifier("amount-cats-hunts-over-3")
//    public NewTopic amountCatsHuntsOver3() {
//        return TopicBuilder.name(AMOUNT_WHERE_CAT_HUNTS_OVER_3)
//                .build();
//    }
//
//    @Bean
//    @Qualifier("amount-sterilized-cats-younger-than-4")
//    public NewTopic amountStirilizedCatsYoungerThen4() {
//        return TopicBuilder.name(STERILIZED_CATS_YOUNGER_THAN_4_YEARS)
//                .build();
//    }
//
//    @Bean
//    @Qualifier("joined-age-cats")
//    public NewTopic joinedAgeCatsTopic() {
//        return TopicBuilder.name(JOINED_4_TO_10_YEARS_CATS)
//                .build();
//    }

    @Bean
    @Qualifier("windowed")
    public NewTopic windowedTopic() {
        return TopicBuilder.name(WINDOWED_TOPIC)
                .build();
    }

    @Bean
    @Qualifier("producer-metrics")
    public NewTopic producerMetrics() {
        return TopicBuilder.name(PRODUCER_METRICS_TOPIC)
                .build();
    }
}
