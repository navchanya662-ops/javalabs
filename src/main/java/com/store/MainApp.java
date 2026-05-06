package com.store;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

/**
 * Стартовий клас JavaFX-додатка для роботи з колекцією одягу.
 */
public class MainApp extends Application {
    private final Store store = new Store();
    private TextField nameField;
    private ComboBox<ClothesSize> sizeComboBox;
    private TextField colorField;
    private TextField materialField;
    private TextField priceField;
    private TextField quantityField;
    private Label messageLabel;
    private ListView<String> clothesListView;
    private TextField uuidSearchField;
    private TextArea searchResultArea;

    /**
     * Запускає JavaFX-додаток.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Створює головне вікно JavaFX-додатка.
     *
     * @param stage головне вікно додатка
     */
    @Override
    public void start(Stage stage) {
        Label title = new Label("Магазин одягу");
        GridPane form = createObjectForm();
        VBox searchPanel = createUuidSearchPanel();
        clothesListView = new ListView<>();
        BorderPane root = new BorderPane();
        root.setTop(title);
        root.setCenter(new VBox(16, form, searchPanel));
        root.setRight(clothesListView);

        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Магазин одягу");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Створює форму для додавання базового елемента одягу.
     *
     * @return форма додавання одягу
     */
    private GridPane createObjectForm() {
        nameField = new TextField();
        sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(ClothesSize.values());
        sizeComboBox.setValue(ClothesSize.M);
        colorField = new TextField();
        materialField = new TextField();
        priceField = new TextField();
        quantityField = new TextField();
        messageLabel = new Label();

        Button addButton = new Button("Додати");
        addButton.setOnAction(event -> addBasicClothes());

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.add(new Label("Назва:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Розмір:"), 0, 1);
        form.add(sizeComboBox, 1, 1);
        form.add(new Label("Колір:"), 0, 2);
        form.add(colorField, 1, 2);
        form.add(new Label("Матеріал:"), 0, 3);
        form.add(materialField, 1, 3);
        form.add(new Label("Ціна:"), 0, 4);
        form.add(priceField, 1, 4);
        form.add(new Label("Кількість:"), 0, 5);
        form.add(quantityField, 1, 5);
        form.add(new VBox(8, addButton, messageLabel), 1, 6);

        return form;
    }

    /**
     * Створює панель пошуку елемента одягу за UUID.
     *
     * @return панель пошуку за UUID
     */
    private VBox createUuidSearchPanel() {
        uuidSearchField = new TextField();
        searchResultArea = new TextArea();
        searchResultArea.setEditable(false);
        searchResultArea.setWrapText(true);

        Button searchButton = new Button("Знайти");
        searchButton.setOnAction(event -> searchByUuid());

        GridPane searchForm = new GridPane();
        searchForm.setHgap(8);
        searchForm.setVgap(8);
        searchForm.add(new Label("UUID:"), 0, 0);
        searchForm.add(uuidSearchField, 1, 0);
        searchForm.add(searchButton, 2, 0);

        return new VBox(8, new Label("Пошук за UUID"), searchForm, searchResultArea);
    }

    /**
     * Додає базовий елемент одягу в магазин на основі даних із форми.
     */
    private void addBasicClothes() {
        try {
            String name = nameField.getText().trim();
            ClothesSize size = sizeComboBox.getValue();
            String color = colorField.getText().trim();
            String material = materialField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim().replace(',', '.'));
            int quantity = Integer.parseInt(quantityField.getText().trim());

            store.addNewClothes(new BasicClothes(name, size, color, material, price), quantity);
            clearForm();
            updateClothesList();
            messageLabel.setText("Об'єкт успішно додано.");
        } catch (NumberFormatException exception) {
            messageLabel.setText("Ціна і кількість мають бути числовими.");
        } catch (IllegalArgumentException exception) {
            messageLabel.setText("Помилка: " + exception.getMessage());
        }
    }

    /**
     * Очищає поля форми після успішного додавання.
     */
    private void clearForm() {
        nameField.clear();
        sizeComboBox.setValue(ClothesSize.M);
        colorField.clear();
        materialField.clear();
        priceField.clear();
        quantityField.clear();
    }

    /**
     * Оновлює короткий список елементів одягу у форматі назва та UUID.
     */
    private void updateClothesList() {
        clothesListView.getItems().clear();
        for (Clothes item : store.getClothes()) {
            clothesListView.getItems().add(item.getName() + " | UUID: " + item.getUuid());
        }
    }

    /**
     * Шукає елемент одягу за UUID і показує повну інформацію про результат.
     */
    private void searchByUuid() {
        String input = uuidSearchField.getText().trim();
        if (input.isEmpty()) {
            searchResultArea.setText("Введіть UUID.");
            return;
        }

        try {
            UUID uuid = UUID.fromString(input);
            Clothes result = store.findByUuid(uuid);
            if (result == null) {
                searchResultArea.setText("Об'єкт не знайдено.");
                return;
            }
            searchResultArea.setText(result.toString());
        } catch (IllegalArgumentException exception) {
            searchResultArea.setText("Некоректний формат UUID.");
        }
    }
}
