package Parking;

import Journal.Entry;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum Rates {
    MORNING (LocalTime.of(5, 0, 0,0), LocalTime.of(9,59,59,9999)){
        public void setEntryRent(Entry entry){
            entry.setRentSum(entry.getRentSum() + 0.1);
        };
    },
    DAY(LocalTime.of(10,0,0, 0), LocalTime.of(17,59,59,9999)){
        public void setEntryRent(Entry entry){
            entry.setRentSum(entry.getRentSum() + 0.2);
        }
    },
    EVENING(LocalTime.of(18,0,0, 0), LocalTime.of(22,59,59,9999)){
        public void setEntryRent(Entry entry){
            entry.setRentSum(entry.getRentSum() + 0.3);
        }
    },
    NIGHT(LocalTime.of(23,0,0, 0), LocalTime.of(4,59,59,9999)){
        public void setEntryRent(Entry entry){
            entry.setRentSum(entry.getRentSum() + 0.05);
        }
    };

    private final LocalTime startTime;
    private final LocalTime endTime;

    Rates(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static void accrueRentByTimePeriod(LocalDateTime time, Entry entry){
        LocalTime timeToCheck = time.toLocalTime();
        for (Rates rate: Rates.values()) {
            if(intervalCheck(timeToCheck, rate) && Parking.isStayMoreThenThirtyMinutes(time, entry)){
                rate.setEntryRent(entry);
            }
        }
    }
    private static boolean intervalCheck(LocalTime time, Rates rate){
        return time.isAfter(rate.startTime) && time.isBefore(rate.endTime);
    }
    public abstract void setEntryRent(Entry entry);
}
