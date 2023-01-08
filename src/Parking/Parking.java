package Parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Car.Car;
import Journal.*;


public class Parking {
    private List<Car> parking = new ArrayList<>();
    private Journal journal = new Journal();

    //------------------Конструкторы-----------------------------------------
    public Parking() {
    }

    public Parking(Parking parking) {
        this.parking = parking.getParking();
        this.journal = parking.getJournal();
    }

    //---------------------Гетеры и сетеры------------------------------------
    public List<Car> getParking() {
        return new ArrayList<>(parking);
    }

    public void setParking(List<Car> parking) {
        this.parking = parking;
    }

    public Journal getJournal() {
        return journal;
    }
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    //---------------------Регистрация въезда и выезда------------------------------
    public void carRegistrationCheckIn(Car car, LocalDateTime time){
        parking.add(car);
        journal.createNewEntry(time, car.getId());
    }
    public void carRegistrationCheckOut(Car car, LocalDateTime time){
        journal.completeEntry(time, car.getId());
    }
    //-----------------Начисление оплаты------------------------------------------------------
    public void ticketing(LocalDateTime time){
        for(Car car : parking){
            Entry entry = journal.getLastElementOfCarCheckList(car.getId());
            if(timeCheckForRent(time, entry)){
                entry.setRentSum(entry.getRentSum() + 0.1);
            }
        }
    }
    //-----------------Проверка времени для начисления оплаты----------------------------------
    private boolean timeCheckInCity(LocalDateTime time){
        return time.getHour() < 20 && time.getHour() > 9;
    }
    private boolean timeCheckInParking(LocalDateTime time, Entry entry){
        return time.isAfter(entry.getCheckIn().plusMinutes(30));
    }
    private boolean timeCheckForRent(LocalDateTime time, Entry entry){
        return timeCheckInCity(time) && timeCheckInParking(time, entry);
    }
    //-----------------Удаление элементов-------------------------------------------------
    public void removeCarInParking(List<Car> cars){
         parking.removeAll(cars);
    }
}
