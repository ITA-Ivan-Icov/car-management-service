package com.example.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MongoEntity(database = "car-service", collection = "cars")
@Builder
public class Car {

    @BsonId
    private ObjectId id;
    private String model;
    private String manufactureYear;
    private String gearBox;
    private Integer rentPrice;
    private Integer seats;
    private String pickUpLocation;

    public Car(String model, String manufactureYear, String gearBox, Integer rentPrice, Integer seats, String pickUpLocation) {
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.gearBox = gearBox;
        this.rentPrice = rentPrice;
        this.seats = seats;
        this.pickUpLocation = pickUpLocation;
    }
}
