package com.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.UUID;

/**
 * Драйвер програми для створення та перегляду елементів одягу через консольне меню.
 */
public class Main {
    private static final String FILE_NAME = "input.json";

    /**
     * Забороняє створення об'єктів службового класу Main.
     */
    private Main() {
    }

    /**
     * Запускає консольне меню програми.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClothesFileManager fileManager = new ClothesFileManager();
        Store store = fileManager.loadStoreFromFile(FILE_NAME);

        while (true) {
            printMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1 -> searchObject(scanner, store);
                case 2 -> createObject(scanner, store);
                case 3 -> printClothes(store);
                case 4 -> printSortedClothes(scanner, store);
                case 5 -> {
                    fileManager.saveStoreToFile(store, FILE_NAME);
                    System.out.println("Дані збережено у файл " + FILE_NAME + ".");
                    System.out.println("Роботу програми завершено.");
                    return;
                }
                default -> System.out.println("Оберіть пункт меню від 1 до 5.");
            }
        }
    }

    /**
     * Виводить головне меню програми.
     */
    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Пошук об'єкта");
        System.out.println("2. Створити новий об'єкт");
        System.out.println("3. Вивести інформацію про всі об'єкти");
        System.out.println("4. Вивести відсортовану інформацію про всі об'єкти");
        System.out.println("5. Завершити роботу програми");
        System.out.print("Оберіть пункт меню: ");
    }

    /**
     * Виводить підменю пошуку об'єктів.
     */
    private static void printSearchMenu() {
        System.out.println("\nОберіть критерій пошуку:");
        System.out.println("1. Пошук за назвою");
        System.out.println("2. Пошук за розміром");
        System.out.println("3. Пошук за кольором");
        System.out.println("4. Пошук за типом об'єкта");
        System.out.println("5. Пошук за UUID");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");
    }

    /**
     * Виводить підменю створення об'єктів.
     */
    private static void printCreateObjectMenu() {
        System.out.println("\nОберіть тип нового об'єкта:");
        System.out.println("1. Звичайний одяг");
        System.out.println("2. Штани");
        System.out.println("3. Сорочка");
        System.out.println("4. Куртка");
        System.out.println("5. Сукня");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");
    }

    /**
     * Виводить підменю вибору критерію сортування.
     */
    private static void printSortMenu() {
        System.out.println("\nОберіть критерій сортування:");
        System.out.println("1. Сортувати за назвою");
        System.out.println("2. Сортувати за ціною");
        System.out.println("3. Сортувати за розміром");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");
    }

    /**
     * Обробляє підменю пошуку об'єкта.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void searchObject(Scanner scanner, Store store) {
        printSearchMenu();
        int choice = readSearchChoice(scanner);

        switch (choice) {
            case 0 -> System.out.println("Повернення до головного меню.");
            case 1 -> searchByName(scanner, store);
            case 2 -> searchBySize(scanner, store);
            case 3 -> searchByColor(scanner, store);
            case 4 -> searchByType(scanner, store);
            case 5 -> searchByUuid(scanner, store);
            default -> System.out.println("Оберіть пункт підменю від 0 до 5.");
        }
    }

    /**
     * Шукає елементи одягу за назвою.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void searchByName(Scanner scanner, Store store) {
        System.out.print("Введіть назву для пошуку: ");
        String name = readNonBlankLine(scanner);
        printSearchResults(store.findByName(name));
    }

    /**
     * Шукає елементи одягу за розміром.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void searchBySize(Scanner scanner, Store store) {
        System.out.print("Введіть розмір для пошуку (" + getAvailableSizes() + "): ");
        ClothesSize size = readClothesSize(scanner);
        printSearchResults(store.findBySize(size));
    }

    /**
     * Шукає елементи одягу за кольором.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void searchByColor(Scanner scanner, Store store) {
        System.out.print("Введіть колір для пошуку: ");
        String color = readNonBlankLine(scanner);
        printSearchResults(store.findByColor(color));
    }

    /**
     * Шукає елементи одягу за типом об'єкта.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void searchByType(Scanner scanner, Store store) {
        printTypeSearchMenu();
        int choice = readTypeSearchChoice(scanner);
        if (choice == 0) {
            System.out.println("Повернення до підменю пошуку.");
            return;
        }

        printSearchResults(store.findByType(choice));
    }

    /**
     * Шукає елемент одягу за UUID.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void searchByUuid(Scanner scanner, Store store) {
        System.out.print("Введіть UUID для пошуку: ");
        String input = readNonBlankLine(scanner);

        try {
            UUID uuid = UUID.fromString(input);
            Clothes result = store.findByUuid(uuid);
            if (result == null) {
                System.out.println("Об'єкт із таким UUID не знайдено.");
                return;
            }
            System.out.println("\nЗнайдений об'єкт:");
            System.out.println(result);
        } catch (IllegalArgumentException exception) {
            System.out.println("Некоректний формат UUID.");
        }
    }

    /**
     * Повертає елементи одягу, назва яких містить заданий текст.
     *
     * @param clothes список елементів одягу
     * @param name текст для пошуку в назві
     * @return список знайдених елементів одягу
     */
    static ArrayList<Clothes> findByName(ArrayList<Clothes> clothes, String name) {
        String searchValue = name.toLowerCase();
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getName().toLowerCase().contains(searchValue)) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Повертає елементи одягу із заданим розміром.
     *
     * @param clothes список елементів одягу
     * @param size розмір для пошуку
     * @return список знайдених елементів одягу
     */
    static ArrayList<Clothes> findBySize(ArrayList<Clothes> clothes, ClothesSize size) {
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getSize() == size) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Повертає елементи одягу, колір яких містить заданий текст.
     *
     * @param clothes список елементів одягу
     * @param color текст для пошуку в кольорі
     * @return список знайдених елементів одягу
     */
    static ArrayList<Clothes> findByColor(ArrayList<Clothes> clothes, String color) {
        String searchValue = color.toLowerCase();
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getColor().toLowerCase().contains(searchValue)) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Повертає елементи одягу заданого типу.
     *
     * @param clothes список елементів одягу
     * @param choice номер типу в меню
     * @return список знайдених елементів одягу
     */
    static ArrayList<Clothes> findByType(ArrayList<Clothes> clothes, int choice) {
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (matchesType(item, choice)) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Виводить підменю вибору типу об'єкта для пошуку.
     */
    private static void printTypeSearchMenu() {
        System.out.println("\nОберіть тип об'єкта:");
        System.out.println("1. Звичайний одяг");
        System.out.println("2. Штани");
        System.out.println("3. Сорочка");
        System.out.println("4. Куртка");
        System.out.println("5. Сукня");
        System.out.println("0. Повернутися до підменю пошуку");
        System.out.print("Ваш вибір: ");
    }

    /**
     * Перевіряє, чи відповідає об'єкт обраному типу.
     *
     * @param item елемент одягу
     * @param choice номер типу в меню
     * @return true, якщо об'єкт відповідає обраному типу
     */
    static boolean matchesType(Clothes item, int choice) {
        return switch (choice) {
            case 1 -> item.getClass() == BasicClothes.class;
            case 2 -> item instanceof Pants;
            case 3 -> item instanceof Shirts;
            case 4 -> item instanceof Jackets;
            case 5 -> item instanceof Dresses;
            default -> false;
        };
    }

    /**
     * Обробляє підменю створення нового об'єкта.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void createObject(Scanner scanner, Store store) {
        printCreateObjectMenu();
        int choice = readCreateObjectChoice(scanner);

        switch (choice) {
            case 0 -> System.out.println("Повернення до головного меню.");
            case 1 -> createClothes(scanner, store);
            case 2 -> createPants(scanner, store);
            case 3 -> createShirts(scanner, store);
            case 4 -> createJackets(scanner, store);
            case 5 -> createDresses(scanner, store);
            default -> System.out.println("Оберіть пункт підменю від 0 до 5.");
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Clothes і додає його до списку.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void createClothes(Scanner scanner, Store store) {
        try {
            System.out.println("\nНовий елемент одягу");
            System.out.print("Назва: ");
            String name = readNonBlankLine(scanner);

            System.out.print("Розмір (" + getAvailableSizes() + "): ");
            ClothesSize size = readClothesSize(scanner);

            System.out.print("Колір: ");
            String color = readNonBlankLine(scanner);

            System.out.print("Матеріал: ");
            String material = readNonBlankLine(scanner);

            System.out.print("Ціна: ");
            double price = readNonNegativeDouble(scanner);

            System.out.print("Кількість: ");
            int quantity = readPositiveInt(scanner);

            store.addNewClothes(new BasicClothes(name, size, color, material, price), quantity);
            System.out.println("Об'єкт успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення об'єкта: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Pants і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void createPants(Scanner scanner, Store store) {
        try {
            System.out.println("\nНові штани");
            System.out.print("Назва: ");
            String name = readNonBlankLine(scanner);

            System.out.print("Розмір (" + getAvailableSizes() + "): ");
            ClothesSize size = readClothesSize(scanner);

            System.out.print("Колір: ");
            String color = readNonBlankLine(scanner);

            System.out.print("Матеріал: ");
            String material = readNonBlankLine(scanner);

            System.out.print("Ціна: ");
            double price = readNonNegativeDouble(scanner);

            System.out.print("Є кишені (так/ні): ");
            boolean hasPockets = readBooleanAnswer(scanner);

            System.out.print("Кількість: ");
            int quantity = readPositiveInt(scanner);

            store.addNewClothes(new Pants(name, size, color, material, price, hasPockets), quantity);
            System.out.println("Штани успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення штанів: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Shirts і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void createShirts(Scanner scanner, Store store) {
        try {
            System.out.println("\nНова сорочка");
            System.out.print("Назва: ");
            String name = readNonBlankLine(scanner);

            System.out.print("Розмір (" + getAvailableSizes() + "): ");
            ClothesSize size = readClothesSize(scanner);

            System.out.print("Колір: ");
            String color = readNonBlankLine(scanner);

            System.out.print("Матеріал: ");
            String material = readNonBlankLine(scanner);

            System.out.print("Ціна: ");
            double price = readNonNegativeDouble(scanner);

            System.out.print("Тип рукава: ");
            String sleeveType = readNonBlankLine(scanner);

            System.out.print("Кількість: ");
            int quantity = readPositiveInt(scanner);

            store.addNewClothes(new Shirts(name, size, color, material, price, sleeveType), quantity);
            System.out.println("Сорочку успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення сорочки: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Jackets і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void createJackets(Scanner scanner, Store store) {
        try {
            System.out.println("\nНова куртка");
            System.out.print("Назва: ");
            String name = readNonBlankLine(scanner);

            System.out.print("Розмір (" + getAvailableSizes() + "): ");
            ClothesSize size = readClothesSize(scanner);

            System.out.print("Колір: ");
            String color = readNonBlankLine(scanner);

            System.out.print("Матеріал: ");
            String material = readNonBlankLine(scanner);

            System.out.print("Ціна: ");
            double price = readNonNegativeDouble(scanner);

            System.out.print("Є капюшон (так/ні): ");
            boolean hasHood = readBooleanAnswer(scanner);

            System.out.print("Тип утеплення: ");
            String insulationType = readNonBlankLine(scanner);

            System.out.print("Кількість: ");
            int quantity = readPositiveInt(scanner);

            store.addNewClothes(new Jackets(name, size, color, material, price, hasHood, insulationType), quantity);
            System.out.println("Куртку успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення куртки: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Dresses і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void createDresses(Scanner scanner, Store store) {
        try {
            System.out.println("\nНова сукня");
            System.out.print("Назва: ");
            String name = readNonBlankLine(scanner);

            System.out.print("Розмір (" + getAvailableSizes() + "): ");
            ClothesSize size = readClothesSize(scanner);

            System.out.print("Колір: ");
            String color = readNonBlankLine(scanner);

            System.out.print("Матеріал: ");
            String material = readNonBlankLine(scanner);

            System.out.print("Ціна: ");
            double price = readNonNegativeDouble(scanner);

            System.out.print("Тип довжини: ");
            String lengthType = readNonBlankLine(scanner);

            System.out.print("Офіційна (так/ні): ");
            boolean isFormal = readBooleanAnswer(scanner);

            System.out.print("Кількість: ");
            int quantity = readPositiveInt(scanner);

            store.addNewClothes(new Dresses(name, size, color, material, price, lengthType, isFormal), quantity);
            System.out.println("Сукню успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення сукні: " + exception.getMessage());
        }
    }

    /**
     * Виводить усі створені елементи одягу.
     *
     * @param store магазин з товарами
     */
    private static void printClothes(Store store) {
        if (store.isEmpty()) {
            System.out.println("Список елементів одягу порожній.");
            return;
        }

        System.out.println("\nСтворені елементи одягу:");
        int number = 1;
        ArrayList<Clothes> clothes = store.getClothes();
        for (int i = 0; i < clothes.size(); i++) {
            System.out.println(number + ". " + clothes.get(i) + " | Кількість: " + store.getQuantity(i));
            number++;
        }
    }

    /**
     * Виводить усі елементи одягу, відсортовані за обраним критерієм.
     *
     * @param scanner об'єкт для зчитування введення
     * @param store магазин з товарами
     */
    private static void printSortedClothes(Scanner scanner, Store store) {
        if (store.isEmpty()) {
            System.out.println("Список елементів одягу порожній.");
            return;
        }

        printSortMenu();
        int choice = readSortChoice(scanner);
        if (choice == 0) {
            System.out.println("Повернення до головного меню.");
            return;
        }

        ArrayList<Clothes> sorted = new ArrayList<>(store.getClothes());
        Collections.sort(sorted, createSortComparator(choice));

        System.out.println("\nВідсортовані елементи одягу:");
        int number = 1;
        for (Clothes item : sorted) {
            System.out.println(number + ". " + item);
            number++;
        }
    }

    /**
     * Повертає компаратор відповідно до вибраного критерію сортування.
     *
     * @param choice номер критерію сортування
     * @return компаратор для сортування одягу
     */
    private static Comparator<Clothes> createSortComparator(int choice) {
        return switch (choice) {
            case 1 -> createNameComparator();
            case 2 -> createPriceComparator();
            case 3 -> createSizeComparator();
            default -> throw new IllegalArgumentException("Невідомий критерій сортування.");
        };
    }

    /**
     * Створює Comparator через lambda-вираз для сортування за назвою.
     *
     * @return компаратор за назвою
     */
    static Comparator<Clothes> createNameComparator() {
        return (first, second) -> first.getName().compareToIgnoreCase(second.getName());
    }

    /**
     * Створює Comparator через lambda-вираз для сортування за ціною.
     *
     * @return компаратор за ціною
     */
    static Comparator<Clothes> createPriceComparator() {
        return (first, second) -> Double.compare(first.getPrice(), second.getPrice());
    }

    /**
     * Створює Comparator через lambda-вираз для сортування за розміром.
     *
     * @return компаратор за розміром
     */
    static Comparator<Clothes> createSizeComparator() {
        return (first, second) -> first.getSize().compareTo(second.getSize());
    }

    /**
     * Виводить результати пошуку.
     *
     * @param results знайдені елементи одягу
     */
    private static void printSearchResults(ArrayList<Clothes> results) {
        if (results.isEmpty()) {
            System.out.println("За заданим критерієм нічого не знайдено.");
            return;
        }

        System.out.println("\nРезультати пошуку:");
        int number = 1;
        for (Clothes item : results) {
            System.out.println(number + ". " + item);
            number++;
        }
    }

    /**
     * Зчитує номер пункту меню та перевіряє, що він знаходиться в межах від 1 до 5.
     *
     * @param scanner об'єкт для зчитування введення
     * @return коректний номер пункту меню
     */
    private static int readMenuChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 1 && value <= 5) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер пункту меню від 1 до 5: ");
        }
    }

    /**
     * Зчитує номер пункту підменю пошуку.
     *
     * @param scanner об'єкт для зчитування введення
     * @return коректний номер пункту підменю пошуку
     */
    private static int readSearchChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0 && value <= 5) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер пункту підменю від 0 до 5: ");
        }
    }

    /**
     * Зчитує номер критерію сортування.
     *
     * @param scanner об'єкт для зчитування введення
     * @return коректний номер критерію сортування
     */
    private static int readSortChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0 && value <= 3) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер критерію від 0 до 3: ");
        }
    }

    /**
     * Зчитує номер типу об'єкта для пошуку.
     *
     * @param scanner об'єкт для зчитування введення
     * @return коректний номер типу об'єкта
     */
    private static int readTypeSearchChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0 && value <= 5) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер типу від 0 до 5: ");
        }
    }

    /**
     * Зчитує номер пункту підменю створення об'єктів.
     *
     * @param scanner об'єкт для зчитування введення
     * @return коректний номер пункту підменю
     */
    private static int readCreateObjectChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0 && value <= 5) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер пункту підменю від 0 до 5: ");
        }
    }

    /**
     * Зчитує індекс існуючого об'єкта зі списку.
     *
     * @param scanner об'єкт для зчитування введення
     * @param size кількість об'єктів у списку
     * @return індекс об'єкта у списку
     */
    private static int readObjectIndex(Scanner scanner, int size) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number >= 1 && number <= size) {
                    return number - 1;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер об'єкта від 1 до " + size + ": ");
        }
    }

    /**
     * Зчитує розмір одягу з переліку ClothesSize.
     *
     * @param scanner об'єкт для зчитування введення
     * @return коректний розмір одягу
     */
    private static ClothesSize readClothesSize(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return ClothesSize.valueOf(input);
            } catch (IllegalArgumentException ignored) {
                System.out.print("Введіть один із доступних розмірів (" + getAvailableSizes() + "): ");
            }
        }
    }

    /**
     * Зчитує відповідь користувача у форматі так/ні.
     *
     * @param scanner об'єкт для зчитування введення
     * @return true для відповіді "так", false для відповіді "ні"
     */
    private static boolean readBooleanAnswer(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("так")) {
                return true;
            }
            if (input.equals("ні") || input.equals("нi")) {
                return false;
            }
            System.out.print("Введіть 'так' або 'ні': ");
        }
    }

    /**
     * Повертає доступні розміри одягу у вигляді рядка.
     *
     * @return доступні розміри одягу
     */
    private static String getAvailableSizes() {
        StringBuilder sizes = new StringBuilder();
        ClothesSize[] values = ClothesSize.values();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sizes.append(", ");
            }
            sizes.append(values[i]);
        }
        return sizes.toString();
    }

    /**
     * Зчитує невід'ємне дробове число.
     *
     * @param scanner об'єкт для зчитування введення
     * @return невід'ємне число
     */
    private static double readNonNegativeDouble(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim().replace(',', '.');
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть невід'ємне число: ");
        }
    }

    /**
     * Зчитує додатне ціле число.
     *
     * @param scanner об'єкт для зчитування введення
     * @return додатне ціле число
     */
    private static int readPositiveInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть ціле число більше за 0: ");
        }
    }

    /**
     * Зчитує непорожній рядок.
     *
     * @param scanner об'єкт для зчитування введення
     * @return непорожній рядок без зайвих пробілів на початку та в кінці
     */
    private static String readNonBlankLine(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print("Значення не може бути порожнім. Спробуйте ще раз: ");
        }
    }
}
