package com.example.message_server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button login_btn;
    @FXML
    private TextField user_name;
    @FXML
    private Button register_btn;

    private Parent root;

    ArrayList<String> users = new ArrayList<>();
    FileReader fr ;
    BufferedReader br;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        //get user names from text file and store it in arraylist
        try{
            fr = new FileReader("userClients.txt");
            br=new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            //read names from each line of the file and add it to the arraylist
            while((line=br.readLine())!=null)
            {
                users.add(line);
            }
        }catch(IOException e){

        }
        login_btn.setOnAction((event)->{
            String userName = user_name.getText();
            if(!users.contains(userName)){
                return;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(ServerApp.class.getResource("hello-view.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                root = fxmlLoader.load();
                //creating object of client-view controller class
                ServerViewController serverViewController=fxmlLoader.getController();
                serverViewController.setUsername(userName);

                //setting new scene
                Scene scene = new Scene(root, 478, 400);
                stage.setScene(scene);

            }catch(IOException e){
                e.printStackTrace();
            }
        });
        register_btn.setOnAction(event -> {
            try{
                //creating new scene
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                root = fxmlLoader.load();

                //setting new scene
                Scene scene = new Scene(root, 478, 400);
                stage.setScene(scene);
            }catch (IOException e){
                e.printStackTrace();
            }

        });
    }

}
