package com.store;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість елементів одягу: ");
        int count = readPositiveInt(scanner);
        Clothes[] clothes = new Clothes[count];

        for (int i = 0; i < clothes.length; i++) {
            System.out.println("\nЕлемент одягу #" + (i + 1));
            System.out.print("Назва: ");
            String name = readNonBlankLine(scanner);

            System.out.print("Розмір: ");
            String size = readNonBlankLine(scanner);

            System.out.print("Колір: ");
            String color = readNonBlankLine(scanner);

            System.out.print("Ціна: ");
            double price = readNonNegativeDouble(scanner);

            clothes[i] = new Clothes(name, size, color, price);
        }

        System.out.println("\nСтворені елементи одягу:");
        for (Clothes item : clothes) {
            System.out.println(item);
        }
    }

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
            System.out.print("Введіть додатне ціле число: ");
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
