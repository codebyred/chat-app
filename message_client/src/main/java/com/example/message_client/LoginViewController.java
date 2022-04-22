package com.example.message_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {
    @FXML
    private Button login_btn;
    @FXML
    private TextField user_name;

    private Client client;
    private Parent root;

    public ArrayList<String> users= new ArrayList<>();;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        users.add("alom");

        login_btn.setOnAction((event)->{
            String userName = user_name.getText();
            //checks if users exits
            if(!users.contains(userName)){
                return;
            }
            try {
                //creating new scene
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client-view.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                root = fxmlLoader.load();
                //creating object of client-view controller class
                ClientViewController clientViewController=fxmlLoader.getController();
                clientViewController.setUsername(userName);

                //setting new scene
                Scene scene = new Scene(root, 478, 400);
                stage.setScene(scene);

            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }
}

