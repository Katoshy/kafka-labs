package org.example.kafkalabs.controller;

import org.example.kafkalabs.model.CatData;
import org.example.kafkalabs.service.KafkaService;
import org.example.kafkalabs.streams.KafkaStreamsPipelineLab3;
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
    public void saveCatData(@RequestBody CatData cat) {
        LOGGER.info("Attempting to save cat data in database: {}", cat);
        try {
            String sql = "INSERT INTO cats (tag_id, animal_id, animal_taxon, deploy_on_date, deploy_off_date, hunt, " +
                    "prey_p_month, animal_reproductive_condition, animal_sex, hrs_indoors, n_cats, " +
                    "food_dry, food_wet, food_other, study_site, age_years) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql,
                    cat.getTagId(),
                    cat.getAnimalId(),
                    cat.getAnimalTaxon(),
                    cat.getDeployOnDate(),
                    cat.getDeployOffDate(),
                    cat.isHunt(),
                    cat.getPreyP_Month(),
                    cat.getAnimalReproductiveCondition(),
                    cat.getAnimalSex(),
                    cat.getHrsIndoors(),
                    cat.getN_Cats(),
                    cat.isFoodDry(),
                    cat.isFoodWet(),
                    cat.isFoodOther(),
                    cat.getStudySite(),
                    cat.getAgeYears());

            LOGGER.info("Successfully inserted cat data into the database.");
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving cat data to database. Details: {}", e.getMessage());
        }
    }
}