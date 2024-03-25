package com.example.repository.CarRepository;

import com.example.model.Car;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarRepository implements PanacheMongoRepository<Car>{
}
