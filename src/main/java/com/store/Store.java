package com.store;

import java.util.ArrayList;

/**
 * Клас-контейнер, який зберігає товари магазину та їх кількість.
 */
public class Store {
    private ArrayList<Clothes> clothes;
    private ArrayList<Integer> quantities;

    /**
     * Створює порожній магазин.
     */
    public Store() {
        clothes = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    /**
     * Додає товар у магазин або збільшує кількість існуючого товару.
     *
     * @param cl товар для додавання
     * @param quantity кількість товару
     */
    public void addNewClothes(Clothes cl, int quantity) {
        if (cl == null) {
            throw new IllegalArgumentException("товар не може бути null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("кількість повинна бути більшою за 0");
        }

        for (int i = 0; i < clothes.size(); i++) {
            if (clothes.get(i).equals(cl)) {
                quantities.set(i, quantities.get(i) + quantity);
                return;
            }
        }

        clothes.add(cl);
        quantities.add(quantity);
    }

    /**
     * Повертає список товарів магазину.
     *
     * @return список товарів
     */
    public ArrayList<Clothes> getClothes() {
        return clothes;
    }

    /**
     * Повертає кількість товару за індексом.
     *
     * @param index індекс товару
     * @return кількість товару
     */
    public int getQuantity(int index) {
        return quantities.get(index);
    }

    /**
     * Повертає кількість унікальних товарів у магазині.
     *
     * @return кількість унікальних товарів
     */
    public int getTotalUniqueItems() {
        return clothes.size();
    }

    /**
     * Перевіряє, чи магазин порожній.
     *
     * @return true, якщо магазин не має товарів
     */
    public boolean isEmpty() {
        return clothes.isEmpty();
    }

    /**
     * Повертає товари, назва яких містить заданий текст.
     *
     * @param name текст для пошуку в назві
     * @return список знайдених товарів
     */
    public ArrayList<Clothes> findByName(String name) {
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
     * Повертає товари із заданим розміром.
     *
     * @param size розмір для пошуку
     * @return список знайдених товарів
     */
    public ArrayList<Clothes> findBySize(ClothesSize size) {
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (item.getSize() == size) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Повертає товари, колір яких містить заданий текст.
     *
     * @param color текст для пошуку в кольорі
     * @return список знайдених товарів
     */
    public ArrayList<Clothes> findByColor(String color) {
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
     * Повертає товари заданого типу.
     *
     * @param choice номер типу в меню
     * @return список знайдених товарів
     */
    public ArrayList<Clothes> findByType(int choice) {
        ArrayList<Clothes> results = new ArrayList<>();

        for (Clothes item : clothes) {
            if (matchesType(item, choice)) {
                results.add(item);
            }
        }

        return results;
    }

    /**
     * Перевіряє, чи відповідає товар обраному типу.
     *
     * @param item товар
     * @param choice номер типу в меню
     * @return true, якщо товар відповідає обраному типу
     */
    private boolean matchesType(Clothes item, int choice) {
        return switch (choice) {
            case 1 -> item.getClass() == BasicClothes.class;
            case 2 -> item instanceof Pants;
            case 3 -> item instanceof Shirts;
            case 4 -> item instanceof Jackets;
            case 5 -> item instanceof Dresses;
            default -> false;
        };
    }
}
