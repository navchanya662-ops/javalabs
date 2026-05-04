package com.store;

import java.util.ArrayList;
import java.util.Scanner;

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
        ArrayList<Clothes> clothes = fileManager.loadFromFile(FILE_NAME);

        while (true) {
            printMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1 -> searchObject(scanner, clothes);
                case 2 -> createObject(scanner, clothes);
                case 3 -> printClothes(clothes);
                case 4 -> {
                    fileManager.saveToFile(clothes, FILE_NAME);
                    System.out.println("Дані збережено у файл " + FILE_NAME + ".");
                    System.out.println("Роботу програми завершено.");
                    return;
                }
                default -> System.out.println("Оберіть пункт меню від 1 до 4.");
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
        System.out.println("4. Завершити роботу програми");
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
     * Обробляє підменю пошуку об'єкта.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void searchObject(Scanner scanner, ArrayList<Clothes> clothes) {
        printSearchMenu();
        int choice = readSearchChoice(scanner);

        switch (choice) {
            case 0 -> System.out.println("Повернення до головного меню.");
            case 1 -> searchByName(scanner, clothes);
            case 2 -> searchBySize(scanner, clothes);
            case 3 -> System.out.println("Пошук за кольором буде реалізовано наступним комітом.");
            case 4 -> System.out.println("Пошук за типом об'єкта буде реалізовано наступним комітом.");
            default -> System.out.println("Оберіть пункт підменю від 0 до 4.");
        }
    }

    /**
     * Шукає елементи одягу за назвою.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void searchByName(Scanner scanner, ArrayList<Clothes> clothes) {
        System.out.print("Введіть назву для пошуку: ");
        String name = readNonBlankLine(scanner).toLowerCase();
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getName().toLowerCase().contains(name)) {
                results.add(item);
            }
        }

        printSearchResults(results);
    }

    /**
     * Шукає елементи одягу за розміром.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void searchBySize(Scanner scanner, ArrayList<Clothes> clothes) {
        System.out.print("Введіть розмір для пошуку (" + getAvailableSizes() + "): ");
        ClothesSize size = readClothesSize(scanner);
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getSize() == size) {
                results.add(item);
            }
        }

        printSearchResults(results);
    }

    /**
     * Обробляє підменю створення нового об'єкта.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void createObject(Scanner scanner, ArrayList<Clothes> clothes) {
        printCreateObjectMenu();
        int choice = readCreateObjectChoice(scanner);

        switch (choice) {
            case 0 -> System.out.println("Повернення до головного меню.");
            case 1 -> createClothes(scanner, clothes);
            case 2 -> createPants(scanner, clothes);
            case 3 -> createShirts(scanner, clothes);
            case 4 -> createJackets(scanner, clothes);
            case 5 -> createDresses(scanner, clothes);
            default -> System.out.println("Оберіть пункт підменю від 0 до 5.");
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Clothes і додає його до списку.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void createClothes(Scanner scanner, ArrayList<Clothes> clothes) {
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

            clothes.add(new Clothes(name, size, color, material, price));
            System.out.println("Об'єкт успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення об'єкта: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Pants і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void createPants(Scanner scanner, ArrayList<Clothes> clothes) {
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

            clothes.add(new Pants(name, size, color, material, price, hasPockets));
            System.out.println("Штани успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення штанів: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Shirts і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void createShirts(Scanner scanner, ArrayList<Clothes> clothes) {
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

            clothes.add(new Shirts(name, size, color, material, price, sleeveType));
            System.out.println("Сорочку успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення сорочки: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Jackets і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void createJackets(Scanner scanner, ArrayList<Clothes> clothes) {
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

            clothes.add(new Jackets(name, size, color, material, price, hasHood, insulationType));
            System.out.println("Куртку успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення куртки: " + exception.getMessage());
        }
    }

    /**
     * Зчитує дані з клавіатури, створює об'єкт Dresses і додає його до списку базового типу.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void createDresses(Scanner scanner, ArrayList<Clothes> clothes) {
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

            clothes.add(new Dresses(name, size, color, material, price, lengthType, isFormal));
            System.out.println("Сукню успішно створено.");
        } catch (IllegalArgumentException exception) {
            System.out.println("Помилка створення сукні: " + exception.getMessage());
        }
    }

    /**
     * Виводить усі створені елементи одягу.
     *
     * @param clothes список елементів одягу
     */
    private static void printClothes(ArrayList<Clothes> clothes) {
        if (clothes.isEmpty()) {
            System.out.println("Список елементів одягу порожній.");
            return;
        }

        System.out.println("\nСтворені елементи одягу:");
        int number = 1;
        for (Clothes item : clothes) {
            System.out.println(number + ". " + item);
            number++;
        }
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
     * Створює копію існуючого елемента одягу та додає її до списку.
     *
     * @param scanner об'єкт для зчитування введення
     * @param clothes список елементів одягу
     */
    private static void copyClothes(Scanner scanner, ArrayList<Clothes> clothes) {
        if (clothes.isEmpty()) {
            System.out.println("Немає об'єктів для копіювання.");
            return;
        }

        printClothes(clothes);
        System.out.print("Введіть номер об'єкта для копіювання: ");
        int index = readObjectIndex(scanner, clothes.size());
        Clothes copy = new Clothes(clothes.get(index));
        clothes.add(copy);
        System.out.println("Копію об'єкта успішно створено.");
    }

    /**
     * Зчитує номер пункту меню та перевіряє, що він знаходиться в межах від 1 до 4.
     *
     * @param scanner об'єкт для зчитування введення
     * @return коректний номер пункту меню
     */
    private static int readMenuChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 1 && value <= 4) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер пункту меню від 1 до 4: ");
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
                if (value >= 0 && value <= 4) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер пункту підменю від 0 до 4: ");
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
