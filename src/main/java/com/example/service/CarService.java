package com.example.service;

import com.example.repository.CarRepository.CarRepository;
import com.example.service.carservice.Car;
import com.example.service.carservice.CarServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class CarService extends CarServiceGrpc.CarServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);
    @Inject
    CarRepository carRepository;

    @Override
    public void createCar(Car.CreateCarRequest request, StreamObserver<Car.CarResponse> responseObserver) {
        logger.info("Received createCar request: {}", request);
        com.example.model.Car car = new com.example.model.Car(
                request.getModel(),
                request.getManufactureYear(),
                request.getGearBox(),
                request.getSeats(),
                request.getRentPrice(),
                request.getPickUpLocation()
        );

        ObjectId id = new ObjectId();
        car.setId(id);
        carRepository.persist(car);
        Car.CarResponse response = Car.CarResponse.newBuilder()
                .setId(car.getId().toHexString())
                .setModel(car.getModel())
                .setManufactureYear(car.getManufactureYear())
                .setGearBox(car.getGearBox())
                .setRentPrice(car.getRentPrice())
                .setPickUpLocation(car.getPickUpLocation())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        logger.info("Sending createCar response: {}", response);
    }


    @Override
    public void getCar(Car.GetCarRequest request, StreamObserver<Car.CarResponse> responseObserver) {
        logger.info("Received getCar request with ID: {}", request.getId());

        String carId = request.getId();
        ObjectId objectId = new ObjectId(carId);

        com.example.model.Car car = carRepository.findById(objectId);

        if (car != null) {
            Car.CarResponse response = Car.CarResponse.newBuilder()
                    .setId(car.getId().toHexString())
                    .setModel(car.getModel())
                    .setManufactureYear(car.getManufactureYear())
                    .setGearBox(car.getGearBox())
                    .setRentPrice(car.getRentPrice())
                    .setPickUpLocation(car.getPickUpLocation())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("Sending getCar response: {}", response);
        } else {
            logger.warn("Car with ID {} not found", carId);
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }


    @Override
    public void deleteCar(Car.DeleteCarRequest request, StreamObserver<Car.DeleteCarResponse> responseObserver) {
        logger.info("Received deleteCar request with ID: {}", request.getId());
        String carId = request.getId();
        ObjectId objectId = new ObjectId(carId);

        boolean success = carRepository.deleteById(objectId);

        if (success) {
            logger.info("Car deleted successfully");
        } else {
            logger.warn("Failed to delete car");
        }

        Car.DeleteCarResponse response = Car.DeleteCarResponse.newBuilder()
                .setSuccess(success)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateCar(Car.UpdateCarRequest request, StreamObserver<Car.CarResponse> responseObserver) {
        logger.info("Received updateCar request with ID: {}", request.getId());
        String carId = request.getId();
        ObjectId objectId = new ObjectId(carId);

        com.example.model.Car car = carRepository.findById(objectId);

        if (car != null) {
            if (request.hasModel()) {
                car.setModel(request.getModel());
            }
            if (request.hasManufactureYear()) {
                car.setManufactureYear(request.getManufactureYear());
            }
            if (request.hasGearBox()) {
                car.setGearBox(request.getGearBox());
            }
            if (request.hasRentPrice()) {
                car.setRentPrice(request.getRentPrice());
            }
            if (request.hasSeats()) {
                car.setSeats(request.getSeats());
            }
            if (request.hasPickUpLocation()) {
                car.setPickUpLocation(request.getPickUpLocation());
            }

            car.setId(objectId);
            carRepository.update(car);

            Car.CarResponse response = Car.CarResponse.newBuilder()
                    .setId(car.getId().toHexString())
                    .setModel(car.getModel())
                    .setManufactureYear(car.getManufactureYear())
                    .setGearBox(car.getGearBox())
                    .setRentPrice(car.getRentPrice())
                    .setPickUpLocation(car.getPickUpLocation())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("Sending updateCar response: {}", response);
        } else {
            logger.warn("Car with ID {} not found", carId);
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }




}