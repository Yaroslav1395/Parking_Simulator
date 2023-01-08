package State;

import Car.Car;

public class OnParking extends State{

    public OnParking(Car car) {
        super(car);
    }

    @Override
    public void changeState() {
        super.getCar().setState(new InCity(super.getCar()));
    }
}
