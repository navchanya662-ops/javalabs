package com.store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Відповідає за зчитування та збереження елементів одягу у текстовому файлі.
 */
public class ClothesFileManager {
    /**
     * Завантажує елементи одягу з файлу.
     *
     * @param fileName назва файлу для зчитування
     * @return список елементів одягу
     */
    public ArrayList<Clothes> loadFromFile(String fileName) {
        ArrayList<Clothes> clothes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        clothes.add(parseLine(line));
                    } catch (IllegalArgumentException exception) {
                        System.out.println("Некоректний рядок " + lineNumber + ": " + exception.getMessage());
                    }
                }
                lineNumber++;
            }
        } catch (IOException exception) {
            System.out.println("Помилка читання файлу: " + exception.getMessage());
        }

        return clothes;
    }

    /**
     * Зберігає елементи одягу у файл.
     *
     * @param clothes список елементів одягу
     * @param fileName назва файлу для запису
     */
    public void saveToFile(ArrayList<Clothes> clothes, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Clothes item : clothes) {
                writer.println(formatClothes(item));
            }
        } catch (IOException exception) {
            System.out.println("Помилка запису файлу: " + exception.getMessage());
        }
    }

    /**
     * Створює об'єкт одягу з одного рядка файлу.
     *
     * @param line рядок файлу
     * @return створений елемент одягу
     * @throws IllegalArgumentException якщо рядок має некоректний формат
     */
    private Clothes parseLine(String line) {
        String[] parts = line.split(";", -1);
        String type = getPart(parts, 0, "тип об'єкта").toUpperCase();

        return switch (type) {
            case "CLOTHES" -> parseClothes(parts);
            case "PANTS" -> parsePants(parts);
            case "SHIRTS" -> parseShirts(parts);
            case "JACKETS" -> parseJackets(parts);
            case "DRESSES" -> parseDresses(parts);
            default -> throw new IllegalArgumentException("невідомий тип об'єкта: " + type);
        };
    }

    /**
     * Створює базовий об'єкт Clothes з частин рядка.
     *
     * @param parts частини рядка
     * @return створений об'єкт Clothes
     */
    private Clothes parseClothes(String[] parts) {
        validatePartCount(parts, 6, "CLOTHES");
        return new Clothes(
                getPart(parts, 1, "назва"),
                parseSize(getPart(parts, 2, "розмір")),
                getPart(parts, 3, "колір"),
                getPart(parts, 4, "матеріал"),
                parsePrice(getPart(parts, 5, "ціна"))
        );
    }

    /**
     * Створює об'єкт Pants з частин рядка.
     *
     * @param parts частини рядка
     * @return створений об'єкт Pants
     */
    private Pants parsePants(String[] parts) {
        validatePartCount(parts, 7, "PANTS");
        return new Pants(
                getPart(parts, 1, "назва"),
                parseSize(getPart(parts, 2, "розмір")),
                getPart(parts, 3, "колір"),
                getPart(parts, 4, "матеріал"),
                parsePrice(getPart(parts, 5, "ціна")),
                parseBoolean(getPart(parts, 6, "наявність кишень"))
        );
    }

    /**
     * Створює об'єкт Shirts з частин рядка.
     *
     * @param parts частини рядка
     * @return створений об'єкт Shirts
     */
    private Shirts parseShirts(String[] parts) {
        validatePartCount(parts, 7, "SHIRTS");
        return new Shirts(
                getPart(parts, 1, "назва"),
                parseSize(getPart(parts, 2, "розмір")),
                getPart(parts, 3, "колір"),
                getPart(parts, 4, "матеріал"),
                parsePrice(getPart(parts, 5, "ціна")),
                getPart(parts, 6, "тип рукава")
        );
    }

    /**
     * Створює об'єкт Jackets з частин рядка.
     *
     * @param parts частини рядка
     * @return створений об'єкт Jackets
     */
    private Jackets parseJackets(String[] parts) {
        validatePartCount(parts, 8, "JACKETS");
        return new Jackets(
                getPart(parts, 1, "назва"),
                parseSize(getPart(parts, 2, "розмір")),
                getPart(parts, 3, "колір"),
                getPart(parts, 4, "матеріал"),
                parsePrice(getPart(parts, 5, "ціна")),
                parseBoolean(getPart(parts, 6, "наявність капюшона")),
                getPart(parts, 7, "тип утеплення")
        );
    }

    /**
     * Створює об'єкт Dresses з частин рядка.
     *
     * @param parts частини рядка
     * @return створений об'єкт Dresses
     */
    private Dresses parseDresses(String[] parts) {
        validatePartCount(parts, 8, "DRESSES");
        return new Dresses(
                getPart(parts, 1, "назва"),
                parseSize(getPart(parts, 2, "розмір")),
                getPart(parts, 3, "колір"),
                getPart(parts, 4, "матеріал"),
                parsePrice(getPart(parts, 5, "ціна")),
                getPart(parts, 6, "тип довжини"),
                parseBoolean(getPart(parts, 7, "ознака офіційності"))
        );
    }

    /**
     * Перевіряє кількість частин у рядку файлу.
     *
     * @param parts частини рядка
     * @param expectedCount очікувана кількість частин
     * @param type тип об'єкта
     */
    private void validatePartCount(String[] parts, int expectedCount, String type) {
        if (parts.length != expectedCount) {
            throw new IllegalArgumentException(type + " повинен мати " + expectedCount + " полів");
        }
    }

    /**
     * Повертає непорожню частину рядка.
     *
     * @param parts частини рядка
     * @param index індекс частини
     * @param fieldName назва поля
     * @return значення поля
     */
    private String getPart(String[] parts, int index, String fieldName) {
        if (index >= parts.length || parts[index].trim().isEmpty()) {
            throw new IllegalArgumentException("поле '" + fieldName + "' не може бути порожнім");
        }
        return parts[index].trim();
    }

    /**
     * Перетворює текстове значення на ClothesSize.
     *
     * @param value текстове значення розміру
     * @return розмір одягу
     */
    private ClothesSize parseSize(String value) {
        try {
            return ClothesSize.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("невідомий розмір: " + value);
        }
    }

    /**
     * Перетворює текстове значення на ціну.
     *
     * @param value текстове значення ціни
     * @return ціна
     */
    private double parsePrice(String value) {
        try {
            return Double.parseDouble(value.replace(',', '.'));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("некоректна ціна: " + value);
        }
    }

    /**
     * Перетворює текстове значення на boolean.
     *
     * @param value текстове значення boolean
     * @return boolean-значення
     */
    private boolean parseBoolean(String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("так")) {
            return true;
        }
        if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("ні") || value.equalsIgnoreCase("нi")) {
            return false;
        }
        throw new IllegalArgumentException("очікується true/false або так/ні, отримано: " + value);
    }

    /**
     * Перетворює об'єкт одягу на рядок для збереження у файл.
     *
     * @param item елемент одягу
     * @return рядок для файлу
     */
    private String formatClothes(Clothes item) {
        if (item instanceof Pants pants) {
            return String.join(";",
                    "PANTS",
                    pants.getName(),
                    pants.getSize().name(),
                    pants.getColor(),
                    pants.getMaterial(),
                    Double.toString(pants.getPrice()),
                    Boolean.toString(pants.hasPockets())
            );
        }
        if (item instanceof Shirts shirts) {
            return String.join(";",
                    "SHIRTS",
                    shirts.getName(),
                    shirts.getSize().name(),
                    shirts.getColor(),
                    shirts.getMaterial(),
                    Double.toString(shirts.getPrice()),
                    shirts.getSleeveType()
            );
        }
        if (item instanceof Jackets jackets) {
            return String.join(";",
                    "JACKETS",
                    jackets.getName(),
                    jackets.getSize().name(),
                    jackets.getColor(),
                    jackets.getMaterial(),
                    Double.toString(jackets.getPrice()),
                    Boolean.toString(jackets.hasHood()),
                    jackets.getInsulationType()
            );
        }
        if (item instanceof Dresses dresses) {
            return String.join(";",
                    "DRESSES",
                    dresses.getName(),
                    dresses.getSize().name(),
                    dresses.getColor(),
                    dresses.getMaterial(),
                    Double.toString(dresses.getPrice()),
                    dresses.getLengthType(),
                    Boolean.toString(dresses.isFormal())
            );
        }
        return String.join(";",
                "CLOTHES",
                item.getName(),
                item.getSize().name(),
                item.getColor(),
                item.getMaterial(),
                Double.toString(item.getPrice())
        );
    }
}
