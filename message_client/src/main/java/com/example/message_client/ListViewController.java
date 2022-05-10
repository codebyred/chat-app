package com.example.message_client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListViewController implements Initializable {
    @FXML
    private ListView<String> lists;

    String[] options = {"Chat","Read blog","Play a game"};
    String selectedOption;

    @Override
    public void initialize(URL url, ResourceBundle rs){
        //adding options to listview
        lists.getItems().addAll(options);
        //adding listener to select items on the list
        lists.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                selectedOption = lists.getSelectionModel().getSelectedItem();
                if(selectedOption == "Chat"){
                    try{
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Parent root = fxmlLoader.load();

                        //setting new scene
                        Scene scene = new Scene(root, 478, 400);
                        stage.setScene(scene);
                    }catch(IOException e){

                    }
                }
            }
        });

    }
}
