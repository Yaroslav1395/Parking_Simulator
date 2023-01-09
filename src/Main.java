import City.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

import  Car.Car;
import Parking.Parking;
import State.OnParking;
import UserInteface.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        userInterface.startInterface();
    }
}