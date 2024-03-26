package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.inject.Inject;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import com.example.model.Car;
import com.example.repository.CarRepository.CarRepository;

@QuarkusTest
public class CarServiceTest {

    @Inject
    CarRepository carRepository;

    private Car car;
    ObjectId id = new ObjectId();

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();

        car = new Car();
        car.setId(id);
        car.setModel("RedBull 20");
        car.setManufactureYear("2024");
        car.setGearBox("Manual");
        car.setRentPrice(153);
        car.setSeats(1);
        car.setPickUpLocation("Milton Keynes, GB");
        carRepository.persist(car);
    }

    @Test
    void testGetById() {
        Car foundCar = carRepository.findById(car.getId());
        assertEquals(id, foundCar.getId());
        assertEquals("RedBull 20", foundCar.getModel());
        assertEquals(153, foundCar.getRentPrice());
        assertEquals("2024", foundCar.getManufactureYear());
        assertEquals("Manual", foundCar.getGearBox());
        assertEquals(1, foundCar.getSeats());
        assertEquals("Milton Keynes, GB", foundCar.getPickUpLocation());
    }

    @Test
    void testUpdate() {
        car.setModel("UpdatedModel");
        carRepository.update(car);
        Car updatedCar = carRepository.findById(car.getId());
        assertEquals("UpdatedModel", updatedCar.getModel());
    }

    @Test
    void testDeleteById() {
        assertTrue(carRepository.deleteById(car.getId()));
        assertNull(carRepository.findById(car.getId()));
    }

}