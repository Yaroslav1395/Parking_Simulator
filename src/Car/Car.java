package Car;

import State.*;

import java.util.Objects;
import java.util.Random;

public class Car {
    private String id;
    private State state;
    //----------------Конструкторы----------------------------------------------------
    public Car() {
        id = createCarId();
        state = new InCity(this);
    }

    //----------------Гетеры и сетеры----------------------------------------------------
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    //----------------Создание номера машины----------------------------------------------------
    private String createCarId(){
        Random random = new Random();
        StringBuilder id;
        id = new StringBuilder(Letters.values()[random.nextInt(Letters.values().length)].name());
        id.append(Letters.values()[random.nextInt(Letters.values().length)].name());
        for(int i = 0; i < 4; i++){
            id.append(random.nextInt(9));
        }
        return id.toString();
    }

    //----------------Печать данных машины------------------------------------------------------

    @Override
    public String toString() {
        return "Номер машины: " + id;
    }

    //----------------Для сравнения--------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

enum Letters{
    A, B, C, D, E, F
}
