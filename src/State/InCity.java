package State;

import Car.Car;

public class InCity extends State{
    public InCity(Car car) {
        super(car);
    }

    @Override
    public void changeState() {
        super.getCar().setState(new OnParking(super.getCar()));
    }
}
