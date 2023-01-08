package UserInteface;

import Journal.Entry;
import Journal.Journal;
import Parking.Parking;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

public interface Countable {
    void dailyIncomeCalculation(LocalDate time, Map<String, LinkedList<Entry>> journal);
    void maxMiddleMinIncomeCalculation(Map<String, LinkedList<Entry>> journal);
    void longestParkedCars(Map<String, LinkedList<Entry>> journal);
    void carStayTotalThirtyMin(Map<String, LinkedList<Entry>> journal);
    void parkingOccupancyPercentage(Parking parking);
    void carInParkingGivenDay(Map<String, LinkedList<Entry>> journal);
    void byNumberGetDayInParking(Map<String, LinkedList<Entry>> journal);
}
