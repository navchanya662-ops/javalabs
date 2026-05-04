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
}
