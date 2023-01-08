package UserInteface;

import City.City;
import Journal.Entry;
import Parking.Parking;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserInterface implements Countable{
    private final City city = new City();
    private final Scanner scanner = new Scanner(System.in);
    private void userInterfacePrint(){
        System.out.println("Выберите номер действия");
        System.out.println("1 - Общая сумма заработка за один день");
        System.out.println("2 - Минимальная, средняя, максимальная сумма заработка за период");
        System.out.println("3 - Машины стоящие на парковке дольше всего");
        System.out.println("4 - Количество машин за день, стоящие меньше 30 минут");
        System.out.println("5 - Средний процент загруженности за день");
        System.out.println("6 - Список машин заехавших на парковку за определенный час/день");
        System.out.println("7 - Вывести статистику по номеру машины");
        System.out.println("0 - Выход");
    }
    //----------------Ввод данных пользователем---------------------------------
    public int userInputNumber(int limit, String message){
        int userNumber;
        System.out.print(message);
        try {
            userNumber = new Scanner(System.in).nextInt();
            if(userNumber > limit) throw new IllegalArgumentException();
            return userNumber;
        }
        catch (IllegalArgumentException e){
            System.out.println("\nЧисло выше допустимого. Попробуйте снова");
            userNumber = userInputNumber(limit, message);
        }
        catch (InputMismatchException e){
            System.out.println("\nВвели строку. Попробуйте снова");
            userNumber = userInputNumber(limit, message);
        }
        return userNumber;
    }
    public String userInputDate(){
        String userString;
        System.out.print("Введите дату эквивалентную примеру (2023-12-30): ");
        try {
            userString = new Scanner(System.in).nextLine();
            if(!(userString.length() == 10)) throw new IllegalArgumentException();
            String[] stringArray = userString.split("-");
            for (String str: stringArray) {
               Integer.parseInt(str);
            }
            return userString;
        }
        catch (IllegalArgumentException e){
            System.out.println("\nСтока не эквивалентна примеру. Попробуйте снова");
            userString = userInputDate();
        }
        return userString;
    }
    private String userInputCarNumber(){
        String userString;
        System.out.print("Введите номер машины согласно примеру (CC0326): ");
        try {
            userString = new Scanner(System.in).nextLine();
            if(!(userString.length() == 6)) throw new IllegalArgumentException();
            return userString;
        }
        catch (IllegalArgumentException e){
            System.out.println("\nСтока не эквивалентна примеру. Попробуйте снова");
            userString = userInputDate();
        }
        return userString;
    }
    //-------------Методы расчета статистики----------------------------------------------
    @Override
    public void dailyIncomeCalculation(LocalDate time, Map<String, LinkedList<Entry>> journal) {
        double sum = 0d;
        for(List<Entry> entryList : journal.values()){
            for (Entry entry: entryList) {
                LocalDate localDate = entry.getCheckIn().toLocalDate();
                if(localDate.isEqual(time) && entry.getRentSum() > 0){
                    sum += entry.getRentSum();
                }
            }
        }
        System.out.printf("За %s число сумма составила: %.2f\n", time, sum);
    }

    @Override
    public void maxMiddleMinIncomeCalculation(Map<String, LinkedList<Entry>> journal) {
        //список для всех записей за период
        List<Entry> listOfAllEntry = new ArrayList<>();
        //список для всех уникальных дат
        Set<LocalDate> dates = new HashSet<>();
        //ассоциативный массив для хранения даты и выручки за эту дату
        Map<LocalDate, Double> dateIncome = new HashMap<>();

        //вытаскиваю все записи из ассоциативного массива в listOfAllEntry и добавляю
        //уникальные даты в dates
        for (List<Entry> entryList : journal.values()) {
            for (Entry entry: entryList) {
                if(!(entry.getCheckOut() == null)){
                    listOfAllEntry.add(entry);
                    dates.add(entry.getCheckOut().toLocalDate());
                }
            }
        }

        //перебираю уникальные даты, чтобы сравнить их с датами из listOfAllEntry
        //для того, чтобы вычислить общий заработок за дань и добавить в ассоциативный массив(дата - прибыль)
        double rentSum = 0d;
        for (LocalDate date: dates) {
            for (Entry entry: listOfAllEntry) {
                if(date.isEqual(entry.getCheckOut().toLocalDate())){
                   rentSum += entry.getRentSum();
                }
            }
            dateIncome.put(date, rentSum);
            rentSum = 0d;
        }

        //создаю примитивные переменные для хранения значений и
        //LocalDate для хранения даты
        double minDayIncome = Double.MAX_VALUE;
        LocalDate minIncomeDate = null;
        double maxDayIncome = 0d;
        LocalDate maxIncomeDate = null;
        double middleIncome = 0d;

        //вычисляю min max middle выручку
        for (Double dayIncome: dateIncome.values()) {
            if(minDayIncome > dayIncome){
                minDayIncome = dayIncome;
            }
            else if(maxDayIncome < dayIncome){
                maxDayIncome = dayIncome;
            }
            middleIncome += dayIncome;
        }
        middleIncome /= dateIncome.size();

        //нахожу дату по значению чтобы после вывести в сообщении за какую дату была выручка
        for (LocalDate date: dates) {
            Double income = dateIncome.get(date);
            if(income == minDayIncome){
                minIncomeDate = date;
            }
        }
        for (LocalDate date: dates) {
            Double income = dateIncome.get(date);
            if(income == maxDayIncome){
                maxIncomeDate = date;
            }
        }
        //вывод
        System.out.printf("Минимальный заработок за период был %s и составил: %.2f\n",
                minIncomeDate, minDayIncome);
        System.out.printf("Максимальный заработок за период был %s и составил: %.2f$\n",
                maxIncomeDate, maxDayIncome);
        System.out.printf("Средний заработок за период составил: %.2f$\n", middleIncome);
    }

    @Override
    public void longestParkedCars(Map<String, LinkedList<Entry>> journal) {
        Map<String, Duration> periodCarStay = new HashMap<>();
        Set<String> carNumbers = journal.keySet();

        for (String carNumber : carNumbers) {
            Duration durationMax = Duration.ZERO;
            Duration durationPresent = Duration.ZERO;
            for (Entry entry: journal.get(carNumber)) {
                if(!(entry.getCheckOut() == null)){
                    durationPresent = Duration.between(entry.getCheckIn(), entry.getCheckOut());
                }
                if(durationMax.getSeconds() < durationPresent.getSeconds()){
                    durationMax = durationPresent;
                }
            }
            periodCarStay.put(carNumber, durationMax);
        }

        List<Duration> topDuration = new ArrayList<>(periodCarStay.values());
        topDuration.sort(Duration::compareTo);
        System.out.println("10 машин которые стояли дольше всех");
        for(int i = topDuration.size() - 1; i > topDuration.size() - 10; i--){
            for (String carNumber: carNumbers) {
                Duration duration = periodCarStay.get(carNumber);
                if(topDuration.get(i).getSeconds() == duration.getSeconds()){
                    System.out.println(carNumber + ": " + topDuration.get(i).getSeconds() / 60 + " мин");
                }
            }
        }
    }

    @Override
    public void carStayTotalThirtyMin(Map<String, LinkedList<Entry>> journal) {
        List<Entry> listOfAllEntry = new ArrayList<>();
        Set<LocalDate> dates = new HashSet<>();
        Map<LocalDate, Integer> carStayDay = new HashMap<>();
        int countForCars = 0;
        for (List<Entry> entryList: journal.values()) {
            listOfAllEntry.addAll(entryList);
        }

        for (Entry entry: listOfAllEntry) {
            if(!(entry.getCheckOut() == null)){
                dates.add(entry.getCheckOut().toLocalDate());
            }
        }

        for (LocalDate date : dates){
            for (Entry entry: listOfAllEntry) {
                if(!(entry.getCheckOut() == null)
                        && date.isEqual(entry.getCheckOut().toLocalDate())
                        && entry.getCheckOut().isBefore(entry.getCheckIn().plusMinutes(30))){
                    countForCars++;
                }
            }
            carStayDay.put(date, countForCars);
            countForCars = 0;
        }

        for (LocalDate date: dates) {
            System.out.printf("За %s стояло меньше 30 минут: %s машины\n", date, carStayDay.get(date));
        }
    }

    @Override
    public void parkingOccupancyPercentage(Parking parking) {
        double fullLoadInHour = 20 * 24;
        List<Entry> listOfAllEntry = new ArrayList<>();
        Set<LocalDate> dates = new HashSet<>();
        Map<LocalDate, Double> carStayDay = new HashMap<>();

        for (List<Entry> entryList: parking.getJournal().getParkingJournal().values()) {
            listOfAllEntry.addAll(entryList);
        }

        for (Entry entry: listOfAllEntry) {
            if(!(entry.getCheckOut() == null)){
                dates.add(entry.getCheckOut().toLocalDate());
            }
        }

        for (LocalDate date: dates) {
            double dayLoadInHure = 0d;
            double timeInMinute = 0d;
            for (Entry entry: listOfAllEntry) {
                if(!(entry.getCheckOut() == null)
                        && entry.getCheckOut().toLocalDate().isEqual(date)){
                    timeInMinute += (double) Duration.between(entry.getCheckIn(), entry.getCheckOut()).getSeconds() / 60;
                }
                dayLoadInHure = timeInMinute / 60;
            }
            carStayDay.put(date, dayLoadInHure);
        }

        for (LocalDate date : dates) {
            double percent = carStayDay.get(date) * 100 / fullLoadInHour;
            System.out.printf("%s числа была загруженность %.3f%%\n", date, percent);
        }
    }

    @Override
    public void carInParkingGivenDay(Map<String, LinkedList<Entry>> journal) {
        LocalDate userDate = castUserInputDate();
        Set<String > allKeys = journal.keySet();

        System.out.printf("За %s числа на парковку заезжали следующие машины: \n", userDate);

        for (String key: allKeys) {
            List<Entry> entryList = journal.get(key);
            for (Entry entry : entryList) {
                if(entry.getCheckIn().toLocalDate().isEqual(userDate)){
                    System.out.println(key);
                    break;
                }
            }
        }
    }

    @Override
    public void byNumberGetDayInParking(Map<String, LinkedList<Entry>> journal) {
        String userInputNumber = userInputCarNumber();
        try {
            List<Entry> entryList = journal.get(userInputNumber);
            System.out.println("Машина была на парковке в следующие дни: ");
            for (Entry entry: entryList) {
                System.out.println(entry);
            }
        }
        catch (NullPointerException e){
            System.out.println("Такого номера нет. Попробуйте снова");
            byNumberGetDayInParking(journal);
        }
    }

    //-------------Адаптация данных введенных пользователем-----------------------------------
    private LocalDate castUserInputDate(){
        String stringDate = userInputDate();
        try{
            return LocalDate.parse(stringDate);
        }
        catch (DateTimeParseException e){
            System.out.println("Неправильно ввели дату. Попробуйте снова");
            castUserInputDate();
        }
        throw new RuntimeException("Ошибка при парсинге даты");
    }

    //---------------Реализация интерфейса-------------------------
    public void startInterface(){
        userInterfacePrint();
        boolean isEnd = false;
        while (!isEnd){
            Map<String, LinkedList<Entry>> journal = city.getParking().getJournal().getParkingJournal();
            switch (userInputNumber(8, "Введите номер действия: ")){
                case 1 :
                    LocalDate localDate = castUserInputDate();
                    dailyIncomeCalculation(localDate, journal);
                    break;
                case 2 :
                    maxMiddleMinIncomeCalculation(journal);
                    break;
                case 3 :
                    longestParkedCars(journal);
                    break;
                case 4:
                    carStayTotalThirtyMin(journal);
                    break;
                case 5:
                    parkingOccupancyPercentage(city.getParking());
                    break;
                case 6:
                    carInParkingGivenDay(journal);
                    break;
                case 7:
                    byNumberGetDayInParking(journal);
                    break;
                case 0 :
                    isEnd = true;
                    break;
            }
        }

    }

}


