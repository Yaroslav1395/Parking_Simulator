import City.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import  Car.Car;
import State.OnParking;
import UserInteface.UserInterface;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        userInterface.startInterface();
    }

}