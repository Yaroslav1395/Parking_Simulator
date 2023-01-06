package State;

import Car.Car;

public class InCity extends State{
    public InCity(Car car) {
        super(car);
    }

    @Override
    public void changeState() {
        System.out.println(super.getCar().getId() + " - заехал на парковку");
        super.getCar().setState(new OnParking(super.getCar()));
    }
}
