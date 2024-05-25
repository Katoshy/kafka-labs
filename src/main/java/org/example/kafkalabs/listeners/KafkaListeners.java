package org.example.kafkalabs.listeners;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.example.kafkalabs.model.CatData;
import org.example.kafkalabs.utill.KafkaConnectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.example.kafkalabs.config.kafka.KafkaTopicConfig.*;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

@Component
public class KafkaListeners {

    private final KafkaConnectMapper kafkaConnectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ConsumerFactory<String, String> consumerFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListeners.class);

    public KafkaListeners(KafkaTemplate<String, String> kafkaTemplate,
                          KafkaConnectMapper kafkaConnectMapper,
                          ConsumerFactory<String, String> consumerFactory) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConnectMapper = kafkaConnectMapper;
        this.consumerFactory = consumerFactory;
    }


    @KafkaListener(topics = "cats.public.cats", groupId = "None", containerFactory = "listenerFactory")
    void listenCatData(String message, Consumer<?, ?> consumer) {
        CatData cat = kafkaConnectMapper.getObjectFromStringMessage(message, CatData.class);
        logProcessMessage(cat, INPUT_TOPIC);
        sendConsumerMetrics(consumer);
    }

    private void sendConsumerMetrics(Consumer<?, ?> consumer) {
        if (consumer != null) {
            Map<MetricName, ? extends Metric> metrics = consumer.metrics();
            String metricsString = getMetricsString(metrics);
            kafkaTemplate.send(PRODUCER_METRICS_TOPIC, metricsString);
            LOGGER.warn("Sent consumer metrics to topic {}", PRODUCER_METRICS_TOPIC);
        } else {
            LOGGER.warn("Consumer is not available.");
        }
    }

    private String getMetricsString(Map<MetricName, ? extends Metric> metricNameMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<MetricName, ? extends Metric> entry : metricNameMap.entrySet()) {
            MetricName metricName = entry.getKey();
            Metric metric = entry.getValue();
            stringBuilder.append(metricName.name())
                    .append(": ")
                    .append(metric.metricValue())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
//    @KafkaListener(topics = "more-than-3", groupId = "None", containerFactory = "listenerFactory")
//    void listenMoreThan3(String message) {
//        CatData cat = kafkaConnectMapper.getObjectFromStringMessage(message, CatData.class);
//        logProcessMessage(cat, MORE_THAN_3_OUTPUT_TOPIC);
//    }
//
//    @KafkaListener(topics = "less-than-4", groupId = "None", containerFactory = "listenerFactory")
//    void listenLessThan4(String message) {
//        CatData cat = kafkaConnectMapper.getObjectFromStringMessage(message, CatData.class);
//        logProcessMessage(cat, LESS_THAN_4_OUTPUT_TOPIC);
//    }
//
//    @KafkaListener(topics = "more-equal-than-4-less-equal-than-10", groupId = "None", containerFactory = "listenerFactory")
//    void listenMoreEqualThan4LessEqualThan10(String message) {
//        CatData cat = kafkaConnectMapper.getObjectFromStringMessage(message, CatData.class);
//        logProcessMessage(cat, MORE_EQUAL_THAN_4_LESS_EQUAL_THAN_10_OUTPUT_TOPIC);
//    }
//
//    @KafkaListener(topics = "more-than-10", groupId = "None", containerFactory = "listenerFactory")
//    void listenMoreThan10(String message) {
//        CatData cat = kafkaConnectMapper.getObjectFromStringMessage(message, CatData.class);
//        logProcessMessage(cat, MORE_THAN_10_OUTPUT_TOPIC);
//    }

//    @KafkaListener(topics = "amount-cats-hunts-over-3", groupId = "None", containerFactory = "listenerFactory")
//    void listenAmountWhereCatHuntsOver3(String message) {
//        logProcessMessage(message, AMOUNT_WHERE_CAT_HUNTS_OVER_3);
//    }
//
//    @KafkaListener(topics = "amount-sterilized-cats-younger-than-4", groupId = "None", containerFactory = "listenerFactory")
//    void listenAmountMilkProducedDuringYearsAvgMilkPriceLower013(String message) {
//        logProcessMessage(message, STERILIZED_CATS_YOUNGER_THAN_4_YEARS);
//    }
//
//    @KafkaListener(topics = "joined-age-cats", groupId = "None", containerFactory = "listenerFactory")
//    void listenJoinedAvgPrice(String message) {
//        CatData cat = kafkaConnectMapper.getObjectFromStringMessage(message, CatData.class);
//        logProcessMessage(cat, JOINED_4_TO_10_YEARS_CATS);
//    }

    @KafkaListener(topics = "windowed", groupId = "None", containerFactory = "listenerFactory")
    void listenWindowedTopic(String message) {
        logProcessMessage(message, WINDOWED_TOPIC);
    }

    @KafkaListener(topics = "producer-metrics", groupId = "None", containerFactory = "listenerFactory")
    void listenProducerMetricsTopic(String message) {
        logProcessMessage(message, PRODUCER_METRICS_TOPIC);
    }

    private static <T> void logProcessMessage(T processedMessage, String topic) {
        LOGGER.warn("Topic - {} has new message.",topic);
        LOGGER.warn("Processed message object - {}", processedMessage);
    }
}
