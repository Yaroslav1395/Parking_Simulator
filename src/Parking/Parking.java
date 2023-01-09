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
                Rates.accrueRentByTimePeriod(time, entry);
        }
    }
    //-----------------Проверка времени для начисления оплаты----------------------------------

    protected static boolean isStayMoreThenThirtyMinutes(LocalDateTime time, Entry entry){
        return time.isAfter(entry.getCheckIn().plusMinutes(30));
    }

    //-----------------Удаление элементов-------------------------------------------------
    public void removeCarInParking(List<Car> cars){
         parking.removeAll(cars);
    }
}
