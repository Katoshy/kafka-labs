package org.example.kafkalabs.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String INPUT_TOPIC = "cats.public.cats";

    public static final String MORE_THAN_3_OUTPUT_TOPIC = "more-than-3";

    public static final String LESS_THAN_4_OUTPUT_TOPIC = "less-than-4";
    public static final String MORE_EQUAL_THAN_4_LESS_EQUAL_THAN_10_OUTPUT_TOPIC = "more-equal-than-4-less-equal-than-10";
    public static final String MORE_THAN_10_OUTPUT_TOPIC = "more-than-10";

    /*public static final String AMOUNT_WHERE_COW_PRICE_LOWER_1100 = "amount-where-cow-price-lower-1100";

    public static final String AMOUNT_MILK_PRODUCED_DURING_YEARS_AVG_MILK_PRICE_LOWER_013 = "amount-milk-produced-during-years-avg-milk-price-lower-013";

    public static final String JOINED_AVG_PRICE_MILK_TOPIC = "joined-avg-price-milk";

    public static final String WINDOWED_TOPIC = "windowed";

    public static final String PRODUCER_METRICS_TOPIC = "producer-metrics";*/


    @Bean
    @Qualifier("cats.public.cats")
    public NewTopic catsTopic() {
        return TopicBuilder.name(INPUT_TOPIC)
                .build();
    }

    @Bean
    @Qualifier("more-than-3-topic")
    public NewTopic lessThan1000Topic() {
        return TopicBuilder.name(MORE_THAN_3_OUTPUT_TOPIC)
                .build();
    }

    @Bean
    @Qualifier("more-than-4")
    public NewTopic lessThan4() {
        return TopicBuilder.name(LESS_THAN_4_OUTPUT_TOPIC)
                .build();
    }

    @Bean
    @Qualifier("more-equal-than-4-less-equal-than-10")
    public NewTopic moreEqualThan4LessEqual10() {
        return TopicBuilder.name(MORE_EQUAL_THAN_4_LESS_EQUAL_THAN_10_OUTPUT_TOPIC)
                .build();
    }

    @Bean
    @Qualifier("less-than-10")
    public NewTopic moreThan016() {
        return TopicBuilder.name(MORE_THAN_10_OUTPUT_TOPIC)
                .build();
    }

    /*@Bean
    @Qualifier("amount-where-cow-price-lower-1100")
    public NewTopic amountWhereCowPriceLower1100() {
        return TopicBuilder.name(AMOUNT_WHERE_COW_PRICE_LOWER_1100)
                .build();
    }

    @Bean
    @Qualifier("amount-milk-produced-during-years-avg-milk-price-lower-013")
    public NewTopic amountMilkProducedDuringYearsAvgMilkPriceLower013() {
        return TopicBuilder.name(AMOUNT_MILK_PRODUCED_DURING_YEARS_AVG_MILK_PRICE_LOWER_013)
                .build();
    }

    @Bean
    @Qualifier("joined-avg-price-milk")
    public NewTopic joinedAvgPriceMilkTopic() {
        return TopicBuilder.name(JOINED_AVG_PRICE_MILK_TOPIC)
                .build();
    }

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
    }*/
}
