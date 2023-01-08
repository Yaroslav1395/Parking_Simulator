package City;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Car.Car;
import Parking.Parking;


public class City {
    private final List<Car> carInCity = new ArrayList<>();
    private final Parking parking = new Parking();
    private LocalDateTime time;
    private final Random random = new Random();

    //---------------Конструкторы-------------------------------------------
    public City() {
        time = LocalDateTime.now();
        fillTheCityWithCars();
        monthSimulation();
    }

    //---------------Гетеры и сетеры---------------------------------------
    public List<Car> getCarInCity() {
        return new ArrayList<>(carInCity);
    }

    public Parking getParking() {
        return new Parking(parking);
    }

    //---------------Заполнение города машинами-----------------------------
    private void fillTheCityWithCars(){
        int count = 200;
        while (!(count == 0)){
            carInCity.add(new Car());
            count--;
        }
    }
    //---------------Симуляция месяца-----------------------------------------
    private void monthSimulation(){
        LocalDateTime timeForSimulation = time.plusMonths(1);
        while (time.isBefore(timeForSimulation)){
            makeMove();
        }
    }
    //---------------Симуляция хода------------------------------------------
    private void makeMove(){
        time = time.plusMinutes(5);
        parking.ticketing(time);
        randomStateChange();
    }
    //--------------Изменение состояния машин--------------------------------
    private void randomStateChange(){
        stateChangeInCity();
        stateChangeInParking();
    }
    private void stateChangeInCity(){
        List<Car> removeCar = new ArrayList<>();
        for (Car car : carInCity) {
            if (random.nextInt(100) + 1 <= 3
                    && parking.getParking().size() < 20) {
                car.getState().changeState();
                removeCar.add(car);
                parking.carRegistrationCheckIn(car, time);
            }
        }
        carInCity.removeAll(removeCar);
    }
    private void stateChangeInParking(){
        List<Car> removeCar = new ArrayList<>();
        for (Car car : parking.getParking()) {
            if(random.nextInt(100) + 1 <= 3){
                car.getState().changeState();
                parking.carRegistrationCheckOut(car, time);
                carInCity.add(car);
                removeCar.add(car);
            }
        }
        parking.removeCarInParking(removeCar);
    }

}
