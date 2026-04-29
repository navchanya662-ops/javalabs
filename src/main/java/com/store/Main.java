package com.store;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Clothes> clothes = new ArrayList<>();

        while (true) {
            printMenu();
            int choice = readMenuChoice(scanner);

            switch (choice) {
                case 1 -> createClothes(scanner, clothes);
                case 2 -> printClothes(clothes);
                case 3 -> {
                    System.out.println("Роботу програми завершено.");
                    return;
                }
                default -> System.out.println("Оберіть пункт меню від 1 до 3.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Створити новий об'єкт");
        System.out.println("2. Вивести всі об'єкти");
        System.out.println("3. Завершити роботу");
        System.out.print("Оберіть пункт меню: ");
    }

    private static void createClothes(Scanner scanner, ArrayList<Clothes> clothes) {
        try {
            System.out.println("\nНовий елемент одягу");
            System.out.print("Назва: ");
            String name = readNonBlankLine(scanner);

            System.out.print("Розмір: ");
            String size = readNonBlankLine(scanner);

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

    private static void printClothes(ArrayList<Clothes> clothes) {
        if (clothes.isEmpty()) {
            System.out.println("Список елементів одягу порожній.");
            return;
        }

        System.out.println("\nСтворені елементи одягу:");
        for (int i = 0; i < clothes.size(); i++) {
            System.out.println((i + 1) + ". " + clothes.get(i));
        }
    }

    private static int readMenuChoice(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 1 && value <= 3) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Введіть номер пункту меню від 1 до 3: ");
        }
    }

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
