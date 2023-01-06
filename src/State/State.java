package State;
import Car.Car;
public abstract class State {
    private Car car;

    public State(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
    public abstract void changeState();

}
