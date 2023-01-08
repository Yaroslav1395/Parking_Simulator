package Journal;


import java.time.LocalDateTime;
import java.util.Comparator;

public class Entry implements Comparator<Entry> {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double rentSum;
    //-----------------Конструкторы----------------------------------------

    public Entry(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    //-----------------Гетеры и сетеры--------------------------------------

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public double getRentSum() {
        return rentSum;
    }

    public void setRentSum(double rentSum) {
        this.rentSum = rentSum;
    }

    @Override
    public String toString() {
        return  "Время заезда: " + checkIn +
                " Время выезда: " + checkOut +
                " Сумма: " + rentSum +
                "\n";
    }

    @Override
    public int compare(Entry o1, Entry o2) {
        return o1.checkOut.compareTo(o2.getCheckOut());
    }
}
