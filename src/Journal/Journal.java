package Journal;

import java.time.LocalDateTime;
import java.util.*;

public class Journal {
    private Map<String, LinkedList<Entry>> parkingJournal = new HashMap<>();

    //----------------Конструкторы---------------------------------
    public Journal() {
    }
    public Journal(Map<String, LinkedList<Entry>> parkingJournal) {
        this.parkingJournal = parkingJournal;
    }


    //---------------Гетеры и сетеры-------------------------------

    public Map<String, LinkedList<Entry>> getParkingJournal() {
        return parkingJournal;
    }

    public void setParkingJournal(Map<String, LinkedList<Entry>> parkingJournal) {
        this.parkingJournal = parkingJournal;
    }

    //--------------Запись в журнал--------------------------------
    public void createNewEntry(LocalDateTime time, String carNumber){
        if(parkingJournal.containsKey(carNumber)){
            parkingJournal.get(carNumber).add(new Entry(time));
        }
        else {
            parkingJournal.put(carNumber, new LinkedList<>(List.of(new Entry(time))));
        }
    }
    public void completeEntry(LocalDateTime time, String carNumber){
        parkingJournal.get(carNumber).get(getIndexLastElementOfJournalList(carNumber)).setCheckOut(time);
    }

    public int getIndexLastElementOfJournalList(String carNumber){
        return  parkingJournal.get(carNumber).size() - 1;
    }
    public Entry getLastElementOfCarCheckList(String carNumber){
        return parkingJournal.get(carNumber).get(getIndexLastElementOfJournalList(carNumber));
    }

}
