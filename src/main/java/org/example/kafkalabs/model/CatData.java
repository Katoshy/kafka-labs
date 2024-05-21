package org.example.kafkalabs.model;

import lombok.Data;

import java.util.Random;

@Data
public class CatData {
    private String tagId;
    private String animalId;
    private String animalTaxon;
    private String deployOnDate;
    private String deployOffDate;
    private boolean hunt;
    private Double preyP_Month;
    private String animalReproductiveCondition;
    private String animalSex;
    private Double hrsIndoors;
    private int n_Cats;
    private boolean foodDry;
    private boolean foodWet;
    private boolean foodOther;
    private String studySite;
    private int ageYears;

    public static CatData createRandomCat() {
        Random random = new Random();
        CatData catData = new CatData();

        // Randomly populate cat data
        catData.setTagId("Tag-" + random.nextInt(1000));
        catData.setAnimalId("Animal-" + random.nextInt(1000));
        catData.setAnimalTaxon("Felis catus");
        catData.setDeployOnDate("2017-06-" + (random.nextInt(28) + 1) + "T01:02:09Z");
        catData.setDeployOffDate("2017-07-" + (random.nextInt(28) + 1) + "T02:10:52Z");
        catData.setHunt(random.nextBoolean());
        catData.setPreyP_Month(random.nextDouble() * 15);
        catData.setAnimalReproductiveCondition(random.nextBoolean() ? "Neutered" : "Spayed");
        catData.setAnimalSex(random.nextBoolean() ? "m" : "f");
        catData.setHrsIndoors(random.nextDouble() * 24);
        catData.setN_Cats(random.nextInt(5) + 1);
        catData.setFoodDry(random.nextBoolean());
        catData.setFoodWet(random.nextBoolean());
        catData.setFoodOther(random.nextBoolean());
        catData.setStudySite("UK");
        catData.setAgeYears(random.nextInt(15) + 1);

        return catData;
    }
}