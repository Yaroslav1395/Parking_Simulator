package State;

import Car.Car;

public class OnParking extends State{

    public OnParking(Car car) {
        super(car);
    }

    @Override
    public void changeState() {
        System.out.println(super.getCar().getId() + " - выехал с парковки");
        super.getCar().setState(new InCity(super.getCar()));
    }
}
