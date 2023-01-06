package Journal;


import java.time.LocalDateTime;

public class Entry {
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
}
