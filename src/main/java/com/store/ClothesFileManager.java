package com.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Відповідає за зчитування та збереження елементів одягу у JSON-файлі.
 */
public class ClothesFileManager {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Створює менеджер для роботи з JSON-файлами одягу.
     */
    public ClothesFileManager() {
    }

    /**
     * Завантажує елементи одягу з JSON-файлу.
     *
     * @param fileName назва файлу для зчитування
     * @return список елементів одягу
     */
    public ArrayList<Clothes> loadFromFile(String fileName) {
        ArrayList<Clothes> clothes = new ArrayList<>();

        try (FileReader reader = new FileReader(fileName)) {
            Type listType = new TypeToken<ArrayList<ClothesRecord>>() {
            }.getType();
            ArrayList<ClothesRecord> records = gson.fromJson(reader, listType);

            if (records == null) {
                return clothes;
            }

            for (int i = 0; i < records.size(); i++) {
                try {
                    clothes.add(createClothes(records.get(i)));
                } catch (IllegalArgumentException exception) {
                    System.out.println("Некоректний запис " + (i + 1) + ": " + exception.getMessage());
                }
            }
        } catch (IOException exception) {
            System.out.println("Помилка читання файлу: " + exception.getMessage());
        } catch (JsonSyntaxException exception) {
            System.out.println("Помилка обробки JSON: " + exception.getMessage());
        }

        return clothes;
    }

    /**
     * Зберігає елементи одягу у JSON-файл.
     *
     * @param clothes список елементів одягу
     * @param fileName назва файлу для запису
     */
    public void saveToFile(ArrayList<Clothes> clothes, String fileName) {
        ArrayList<ClothesRecord> records = new ArrayList<>();
        for (Clothes item : clothes) {
            records.add(createRecord(item));
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(records, writer);
        } catch (IOException exception) {
            System.out.println("Помилка запису файлу: " + exception.getMessage());
        }
    }

    /**
     * Створює об'єкт ієрархії Clothes з JSON-запису.
     *
     * @param record JSON-запис
     * @return створений елемент одягу
     */
    private Clothes createClothes(ClothesRecord record) {
        if (record == null) {
            throw new IllegalArgumentException("запис не може бути null");
        }

        String type = requireText(record.type, "type").toUpperCase();
        ClothesSize size = parseSize(record.size);

        return switch (type) {
            case "CLOTHES" -> new Clothes(
                    requireText(record.name, "name"),
                    size,
                    requireText(record.color, "color"),
                    requireText(record.material, "material"),
                    record.price
            );
            case "PANTS" -> new Pants(
                    requireText(record.name, "name"),
                    size,
                    requireText(record.color, "color"),
                    requireText(record.material, "material"),
                    record.price,
                    record.hasPockets
            );
            case "SHIRTS" -> new Shirts(
                    requireText(record.name, "name"),
                    size,
                    requireText(record.color, "color"),
                    requireText(record.material, "material"),
                    record.price,
                    requireText(record.sleeveType, "sleeveType")
            );
            case "JACKETS" -> new Jackets(
                    requireText(record.name, "name"),
                    size,
                    requireText(record.color, "color"),
                    requireText(record.material, "material"),
                    record.price,
                    record.hasHood,
                    requireText(record.insulationType, "insulationType")
            );
            case "DRESSES" -> new Dresses(
                    requireText(record.name, "name"),
                    size,
                    requireText(record.color, "color"),
                    requireText(record.material, "material"),
                    record.price,
                    requireText(record.lengthType, "lengthType"),
                    record.formal
            );
            default -> throw new IllegalArgumentException("невідомий тип об'єкта: " + type);
        };
    }

    /**
     * Створює JSON-запис з об'єкта ієрархії Clothes.
     *
     * @param item елемент одягу
     * @return JSON-запис
     */
    private ClothesRecord createRecord(Clothes item) {
        ClothesRecord record = new ClothesRecord();
        record.name = item.getName();
        record.size = item.getSize().name();
        record.color = item.getColor();
        record.material = item.getMaterial();
        record.price = item.getPrice();

        if (item instanceof Pants pants) {
            record.type = "PANTS";
            record.hasPockets = pants.hasPockets();
        } else if (item instanceof Shirts shirts) {
            record.type = "SHIRTS";
            record.sleeveType = shirts.getSleeveType();
        } else if (item instanceof Jackets jackets) {
            record.type = "JACKETS";
            record.hasHood = jackets.hasHood();
            record.insulationType = jackets.getInsulationType();
        } else if (item instanceof Dresses dresses) {
            record.type = "DRESSES";
            record.lengthType = dresses.getLengthType();
            record.formal = dresses.isFormal();
        } else {
            record.type = "CLOTHES";
        }

        return record;
    }

    /**
     * Перевіряє, що текстове поле не порожнє.
     *
     * @param value значення поля
     * @param fieldName назва поля
     * @return непорожнє значення
     */
    private String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("поле '" + fieldName + "' не може бути порожнім");
        }
        return value.trim();
    }

    /**
     * Перетворює текстове значення на ClothesSize.
     *
     * @param value текстове значення розміру
     * @return розмір одягу
     */
    private ClothesSize parseSize(String value) {
        try {
            return ClothesSize.valueOf(requireText(value, "size").toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("невідомий розмір: " + value);
        }
    }

    /**
     * DTO для JSON-збереження об'єктів ієрархії Clothes.
     */
    private static class ClothesRecord {
        private String type;
        private String name;
        private String size;
        private String color;
        private String material;
        private double price;
        private boolean hasPockets;
        private String sleeveType;
        private boolean hasHood;
        private String insulationType;
        private String lengthType;
        private boolean formal;
    }
}
