package org.example.kafkalabs.controller;

import org.example.kafkalabs.model.CatData;
import org.example.kafkalabs.service.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import static org.example.kafkalabs.config.kafka.KafkaTopicConfig.INPUT_TOPIC;

@RestController
@RequestMapping("cats")
public class CatsController {
    private final KafkaService kafkaService;

    private final JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(CatsController.class);

    public CatsController(KafkaService kafkaService, JdbcTemplate jdbcTemplate) {
        this.kafkaService = kafkaService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/sendRandom")
    @ResponseStatus(HttpStatus.OK)
    public void sendRandomCatToKafka() {
        LOGGER.info("Request to send random cat data to Kafka.");
        CatData catData = CatData.createRandomCat();
        kafkaService.sendToKafka(catData, INPUT_TOPIC);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void sendCatData(@RequestBody CatData cat) {
        LOGGER.info("Attempting to save cat data in database: {}", cat);
        try {
            kafkaService.sendToKafka(cat, INPUT_TOPIC);
            LOGGER.info("Successfully send cat data into the Kafka INPUT TOPIC.");
        } catch (Exception e) {
            LOGGER.error("Error occurred while sending cat data to Kafka. Details: {}", e.getMessage());
        }
    }
}