package com.store;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Відповідає за збереження товарів магазину в базу даних через JDBC.
 */
public class DatabaseManager {
    private static final String INSERT_SQL = """
            INSERT INTO clothes (
                type, name, size, color, material, price, quantity,
                has_pockets, sleeve_type, has_hood, insulation_type, length_type, formal
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private final String url;
    private final String user;
    private final String password;

    /**
     * Створює менеджер бази даних і читає параметри підключення з файлу.
     *
     * @param configPath шлях до файлу db.properties
     * @throws IOException якщо конфігураційний файл неможливо прочитати
     */
    public DatabaseManager(String configPath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(configPath)) {
            properties.load(input);
        }

        url = requireProperty(properties, "db.url");
        user = requireProperty(properties, "db.user");
        password = requireProperty(properties, "db.password");
    }

    /**
     * Зберігає товар у таблицю clothes.
     *
     * @param item товар для збереження
     * @param quantity кількість товару
     */
    public void insertClothes(Clothes item, int quantity) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            statement.setString(1, getType(item));
            statement.setString(2, item.getName());
            statement.setString(3, item.getSize().name());
            statement.setString(4, item.getColor());
            statement.setString(5, item.getMaterial());
            statement.setDouble(6, item.getPrice());
            statement.setInt(7, quantity);
            setSubclassFields(statement, item);
            statement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("Помилка роботи з базою даних: " + exception.getMessage());
        }
    }

    private String requireProperty(Properties properties, String name) {
        String value = properties.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("У конфігурації відсутнє значення " + name);
        }
        return value.trim();
    }

    private String getType(Clothes item) {
        if (item instanceof Pants) {
            return "PANTS";
        }
        if (item instanceof Shirts) {
            return "SHIRTS";
        }
        if (item instanceof Jackets) {
            return "JACKETS";
        }
        if (item instanceof Dresses) {
            return "DRESSES";
        }
        return "CLOTHES";
    }

    private void setSubclassFields(PreparedStatement statement, Clothes item) throws SQLException {
        setNullableBoolean(statement, 8, null);
        setNullableString(statement, 9, null);
        setNullableBoolean(statement, 10, null);
        setNullableString(statement, 11, null);
        setNullableString(statement, 12, null);
        setNullableBoolean(statement, 13, null);

        if (item instanceof Pants pants) {
            setNullableBoolean(statement, 8, pants.hasPockets());
        } else if (item instanceof Shirts shirts) {
            setNullableString(statement, 9, shirts.getSleeveType());
        } else if (item instanceof Jackets jackets) {
            setNullableBoolean(statement, 10, jackets.hasHood());
            setNullableString(statement, 11, jackets.getInsulationType());
        } else if (item instanceof Dresses dresses) {
            setNullableString(statement, 12, dresses.getLengthType());
            setNullableBoolean(statement, 13, dresses.isFormal());
        }
    }

    private void setNullableString(PreparedStatement statement, int index, String value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.VARCHAR);
            return;
        }
        statement.setString(index, value);
    }

    private void setNullableBoolean(PreparedStatement statement, int index, Boolean value) throws SQLException {
        if (value == null) {
            statement.setNull(index, Types.BOOLEAN);
            return;
        }
        statement.setBoolean(index, value);
    }
}
