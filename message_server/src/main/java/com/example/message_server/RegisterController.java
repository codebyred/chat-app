package com.example.message_server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button login_btn;
    @FXML
    private Button register_btn;
    @FXML
    private TextField user_name;
    @FXML
    private ImageView img;

    FileWriter fw;
    BufferedWriter bw;
    PrintWriter pw;

    public void initialize(URL location, ResourceBundle resources){


        register_btn.setOnAction((event)->{
            //open users list file
            try{
                fw = new FileWriter("userClients.txt",true);
                bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);
                String userName = user_name.getText();
                //append username to file
                pw.println(userName);
                pw.flush();
            }catch(IOException e){
                System.out.println("We are having some error son");
            }
            //close all connections
            try{
                pw.close();
                bw.close();
                fw.close();
            }catch (IOException e){

            }

        });
        img.addEventHandler(MouseEvent.MOUSE_CLICKED,(event)->{
            try {
                //creating new scene
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

                //setting new scene
                Scene scene = new Scene(fxmlLoader.load(), 478, 400);
                stage.setScene(scene);

            }catch(IOException e){
                e.printStackTrace();
            }
        });
    }
}
