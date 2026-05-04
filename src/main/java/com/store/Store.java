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
}
