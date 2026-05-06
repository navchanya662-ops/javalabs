package com.store;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Стартовий клас JavaFX-додатка для роботи з колекцією одягу.
 */
public class MainApp extends Application {
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
        BorderPane root = new BorderPane(title);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Магазин одягу");
        stage.setScene(scene);
        stage.show();
    }
}
