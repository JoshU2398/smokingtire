package learn.capstone.models;

import java.util.Objects;

public class Car {
    Integer carId;
    Integer horsepower;
    String drivetrain;
    String chassis;
    String transmission;
    String make;
    String model;
    Integer modelYear;

    public Car() {
    }

    public Car(Integer carId, Integer horsepower, String drivetrain, String chassis, String transmission, String make, String model, Integer modelYear) {
        this.carId = carId;
        this.horsepower = horsepower;
        this.drivetrain = drivetrain;
        this.chassis = chassis;
        this.transmission = transmission;
        this.make = make;
        this.model = model;
        this.modelYear = modelYear;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(String drivetrain) {
        this.drivetrain = drivetrain;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carId, car.carId) && Objects.equals(horsepower, car.horsepower) && Objects.equals(drivetrain, car.drivetrain) && Objects.equals(chassis, car.chassis) && Objects.equals(transmission, car.transmission) && Objects.equals(make, car.make) && Objects.equals(model, car.model) && Objects.equals(modelYear, car.modelYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, horsepower, drivetrain, chassis, transmission, make, model, modelYear);
    }
}
