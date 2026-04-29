package com.store;

import java.util.Objects;

public class Clothes {
    private String name;
    private String size;
    private String color;
    private String material;
    private double price;

    public Clothes(String name, String size, String color, String material, double price) {
        setName(name);
        setSize(size);
        setColor(color);
        setMaterial(material);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateText(name, "Назва");
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        validateText(size, "Розмір");
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        validateText(color, "Колір");
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        validateText(material, "Матеріал");
        this.material = material;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        validatePrice(price);
        this.price = price;
    }

    private static void validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не може бути порожнім");
        }
    }

    private static void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
    }

    @Override
    public String toString() {
        return "Одяг{"
                + "назва='" + name + '\''
                + ", розмір='" + size + '\''
                + ", колір='" + color + '\''
                + ", матеріал='" + material + '\''
                + ", ціна=" + price
                + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Clothes clothes)) {
            return false;
        }
        return Double.compare(price, clothes.price) == 0
                && Objects.equals(name, clothes.name)
                && Objects.equals(size, clothes.size)
                && Objects.equals(color, clothes.color)
                && Objects.equals(material, clothes.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, color, material, price);
    }
}
