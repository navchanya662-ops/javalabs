package com.store;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
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
    private ComboBox<String> typeComboBox;
    private TextField nameField;
    private ComboBox<ClothesSize> sizeComboBox;
    private TextField colorField;
    private TextField materialField;
    private TextField priceField;
    private TextField quantityField;
    private CheckBox hasPocketsCheckBox;
    private TextField sleeveTypeField;
    private CheckBox hasHoodCheckBox;
    private TextField insulationTypeField;
    private TextField lengthTypeField;
    private CheckBox formalCheckBox;
    private Label messageLabel;
    private ListView<String> clothesListView;
    private TextField selectedUuidField;
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
        title.getStyleClass().add("title");
        GridPane form = createObjectForm();
        VBox searchPanel = createUuidSearchPanel();
        clothesListView = new ListView<>();
        selectedUuidField = new TextField();
        selectedUuidField.setEditable(false);
        selectedUuidField.setPromptText("Оберіть об'єкт у списку");
        clothesListView.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> showSelectedUuid(newValue.intValue())
        );

        Button copyUuidButton = new Button("Скопіювати UUID");
        copyUuidButton.setOnAction(event -> copySelectedUuid());
        Label listTitle = new Label("Об'єкти");
        listTitle.getStyleClass().add("section-title");
        VBox listPanel = new VBox(8, listTitle, clothesListView, selectedUuidField, copyUuidButton);
        listPanel.getStyleClass().add("panel");

        BorderPane root = new BorderPane();
        root.setTop(title);
        root.setCenter(new VBox(16, form, searchPanel));
        root.setRight(listPanel);

        Scene scene = new Scene(root, 1050, 620);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
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
        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Звичайний одяг", "Штани", "Сорочка", "Куртка", "Сукня");
        typeComboBox.setValue("Звичайний одяг");
        typeComboBox.setOnAction(event -> updateAdditionalFieldsState());

        nameField = new TextField();
        sizeComboBox = new ComboBox<>();
        sizeComboBox.getItems().addAll(ClothesSize.values());
        sizeComboBox.setValue(ClothesSize.M);
        colorField = new TextField();
        materialField = new TextField();
        priceField = new TextField();
        quantityField = new TextField();
        hasPocketsCheckBox = new CheckBox("Є кишені");
        sleeveTypeField = new TextField();
        hasHoodCheckBox = new CheckBox("Є капюшон");
        insulationTypeField = new TextField();
        lengthTypeField = new TextField();
        formalCheckBox = new CheckBox("Офіційна");
        messageLabel = new Label();
        messageLabel.getStyleClass().add("message");

        Button addButton = new Button("Додати");
        addButton.setOnAction(event -> addClothes());
        Button updateButton = new Button("Оновити вибраний");
        updateButton.setOnAction(event -> updateSelectedClothes());
        Button deleteButton = new Button("Видалити вибраний");
        deleteButton.setOnAction(event -> deleteSelectedClothes());

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);
        form.getStyleClass().add("panel");
        form.add(new Label("Тип:"), 0, 0);
        form.add(typeComboBox, 1, 0);
        form.add(new Label("Назва:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Розмір:"), 0, 2);
        form.add(sizeComboBox, 1, 2);
        form.add(new Label("Колір:"), 0, 3);
        form.add(colorField, 1, 3);
        form.add(new Label("Матеріал:"), 0, 4);
        form.add(materialField, 1, 4);
        form.add(new Label("Ціна:"), 0, 5);
        form.add(priceField, 1, 5);
        form.add(new Label("Кількість:"), 0, 6);
        form.add(quantityField, 1, 6);
        form.add(hasPocketsCheckBox, 2, 1);
        form.add(new Label("Тип рукава:"), 2, 2);
        form.add(sleeveTypeField, 3, 2);
        form.add(hasHoodCheckBox, 2, 3);
        form.add(new Label("Тип утеплення:"), 2, 4);
        form.add(insulationTypeField, 3, 4);
        form.add(new Label("Тип довжини:"), 2, 5);
        form.add(lengthTypeField, 3, 5);
        form.add(formalCheckBox, 2, 6);
        form.add(new VBox(8, addButton, updateButton, deleteButton, messageLabel), 1, 7);

        updateAdditionalFieldsState();

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

        Label searchTitle = new Label("Пошук за UUID");
        searchTitle.getStyleClass().add("section-title");
        VBox panel = new VBox(8, searchTitle, searchForm, searchResultArea);
        panel.getStyleClass().add("panel");
        return panel;
    }

    /**
     * Додає елемент одягу вибраного типу в магазин на основі даних із форми.
     */
    private void addClothes() {
        try {
            String name = nameField.getText().trim();
            ClothesSize size = sizeComboBox.getValue();
            String color = colorField.getText().trim();
            String material = materialField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim().replace(',', '.'));
            int quantity = Integer.parseInt(quantityField.getText().trim());

            Clothes item = createClothesBySelectedType(name, size, color, material, price);
            store.addNewClothes(item, quantity);
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
     * Оновлює вибраний у списку елемент одягу на основі даних із форми.
     */
    private void updateSelectedClothes() {
        int index = clothesListView.getSelectionModel().getSelectedIndex();
        if (index < 0 || index >= store.getClothes().size()) {
            messageLabel.setText("Спочатку оберіть об'єкт у списку.");
            return;
        }

        try {
            Clothes existingObject = store.getClothes().get(index);
            String name = nameField.getText().trim();
            ClothesSize size = sizeComboBox.getValue();
            String color = colorField.getText().trim();
            String material = materialField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim().replace(',', '.'));

            Clothes updatedObject = createClothesBySelectedType(name, size, color, material, price);
            updatedObject.setUuid(existingObject.getUuid());
            if (store.update(existingObject, updatedObject)) {
                updateClothesList();
                clothesListView.getSelectionModel().select(index);
                searchResultArea.setText(updatedObject.toString());
                messageLabel.setText("Об'єкт успішно оновлено.");
            } else {
                messageLabel.setText("Об'єкт не знайдено.");
            }
        } catch (NumberFormatException exception) {
            messageLabel.setText("Ціна має бути числовою.");
        } catch (IllegalArgumentException exception) {
            messageLabel.setText("Помилка: " + exception.getMessage());
        }
    }

    /**
     * Видаляє вибраний у списку елемент одягу через Store.
     */
    private void deleteSelectedClothes() {
        int index = clothesListView.getSelectionModel().getSelectedIndex();
        if (index < 0 || index >= store.getClothes().size()) {
            messageLabel.setText("Спочатку оберіть об'єкт у списку.");
            return;
        }

        Clothes existingObject = store.getClothes().get(index);
        if (store.delete(existingObject)) {
            updateClothesList();
            clearForm();
            selectedUuidField.clear();
            uuidSearchField.clear();
            searchResultArea.clear();
            messageLabel.setText("Об'єкт успішно видалено.");
        } else {
            messageLabel.setText("Об'єкт не знайдено.");
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
        hasPocketsCheckBox.setSelected(false);
        sleeveTypeField.clear();
        hasHoodCheckBox.setSelected(false);
        insulationTypeField.clear();
        lengthTypeField.clear();
        formalCheckBox.setSelected(false);
    }

    /**
     * Заповнює форму даними вибраного елемента одягу.
     *
     * @param item елемент одягу
     * @param quantity кількість товару
     */
    private void fillForm(Clothes item, int quantity) {
        typeComboBox.setValue(getTypeLabel(item));
        nameField.setText(item.getName());
        sizeComboBox.setValue(item.getSize());
        colorField.setText(item.getColor());
        materialField.setText(item.getMaterial());
        priceField.setText(Double.toString(item.getPrice()));
        quantityField.setText(Integer.toString(quantity));
        hasPocketsCheckBox.setSelected(item instanceof Pants pants && pants.hasPockets());
        sleeveTypeField.setText(item instanceof Shirts shirts ? shirts.getSleeveType() : "");
        hasHoodCheckBox.setSelected(item instanceof Jackets jackets && jackets.hasHood());
        insulationTypeField.setText(item instanceof Jackets jackets ? jackets.getInsulationType() : "");
        lengthTypeField.setText(item instanceof Dresses dresses ? dresses.getLengthType() : "");
        formalCheckBox.setSelected(item instanceof Dresses dresses && dresses.isFormal());
        updateAdditionalFieldsState();
    }

    /**
     * Повертає назву типу для ComboBox на основі конкретного класу об'єкта.
     *
     * @param item елемент одягу
     * @return назва типу для GUI
     */
    private String getTypeLabel(Clothes item) {
        if (item instanceof Pants) {
            return "Штани";
        }
        if (item instanceof Shirts) {
            return "Сорочка";
        }
        if (item instanceof Jackets) {
            return "Куртка";
        }
        if (item instanceof Dresses) {
            return "Сукня";
        }
        return "Звичайний одяг";
    }

    /**
     * Створює об'єкт одягу відповідно до вибраного типу.
     *
     * @param name назва
     * @param size розмір
     * @param color колір
     * @param material матеріал
     * @param price ціна
     * @return створений об'єкт одягу
     */
    private Clothes createClothesBySelectedType(
            String name,
            ClothesSize size,
            String color,
            String material,
            double price
    ) {
        String type = typeComboBox.getValue();
        return switch (type) {
            case "Штани" -> new Pants(name, size, color, material, price, hasPocketsCheckBox.isSelected());
            case "Сорочка" -> new Shirts(name, size, color, material, price, sleeveTypeField.getText().trim());
            case "Куртка" -> new Jackets(
                    name,
                    size,
                    color,
                    material,
                    price,
                    hasHoodCheckBox.isSelected(),
                    insulationTypeField.getText().trim()
            );
            case "Сукня" -> new Dresses(
                    name,
                    size,
                    color,
                    material,
                    price,
                    lengthTypeField.getText().trim(),
                    formalCheckBox.isSelected()
            );
            default -> new BasicClothes(name, size, color, material, price);
        };
    }

    /**
     * Активує тільки ті додаткові поля, які потрібні для вибраного типу одягу.
     */
    private void updateAdditionalFieldsState() {
        String type = typeComboBox.getValue();
        hasPocketsCheckBox.setDisable(!"Штани".equals(type));
        sleeveTypeField.setDisable(!"Сорочка".equals(type));
        hasHoodCheckBox.setDisable(!"Куртка".equals(type));
        insulationTypeField.setDisable(!"Куртка".equals(type));
        lengthTypeField.setDisable(!"Сукня".equals(type));
        formalCheckBox.setDisable(!"Сукня".equals(type));
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
     * Показує UUID вибраного у списку об'єкта в окремому полі для копіювання.
     *
     * @param index індекс вибраного об'єкта
     */
    private void showSelectedUuid(int index) {
        if (index < 0 || index >= store.getClothes().size()) {
            selectedUuidField.clear();
            return;
        }
        Clothes item = store.getClothes().get(index);
        String uuid = store.getClothes().get(index).getUuid().toString();
        selectedUuidField.setText(uuid);
        uuidSearchField.setText(uuid);
        fillForm(item, store.getQuantity(index));
    }

    /**
     * Копіює UUID вибраного об'єкта в буфер обміну.
     */
    private void copySelectedUuid() {
        String uuid = selectedUuidField.getText().trim();
        if (uuid.isEmpty()) {
            messageLabel.setText("Спочатку оберіть об'єкт у списку.");
            return;
        }

        ClipboardContent content = new ClipboardContent();
        content.putString(uuid);
        Clipboard.getSystemClipboard().setContent(content);
        messageLabel.setText("UUID скопійовано.");
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
