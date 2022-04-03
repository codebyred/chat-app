package com.example.message_server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //fxml files
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),478,400);

        Image icon = new Image("https://cdn-icons-png.flaticon.com/512/1041/1041916.png");
        stage.getIcons().add(icon);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.setResizable(false);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}