package com.example.message_server;

import javafx.application.Platform;

import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable{
    @FXML
    private Button send_btn;
    @FXML
    private TextField text_message;
    @FXML
    private ScrollPane sp;
    @FXML
    private VBox vbox_messages;

    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        new Thread(()->{
            server = createServer();
        }).start();

        if(server!=null)
            server.receiveMessageFromClient(vbox_messages);

        //
        vbox_messages.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue)->{
                sp.setVvalue((double) newValue );
        });
        //runs on separate thread

        //to display message on vbox from text field
        send_btn.setOnAction(event-> {
            //get text from textField
            String messageToSend = text_message.getText();
            //checks if text field is empty
            if (!messageToSend.isEmpty()) {

                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER_RIGHT);

                //creating new text field
                Text text = new Text(messageToSend);
                //styling text
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("fx-color: rgb(239,242,255);" +
                        "-fx-background-color: rgb(15,125,242);" +
                        "-fx-background-radius: 20px;");
                textFlow.setPadding(new Insets(5, 10, 5, 10));

                //appending text to hbox
                hbox.getChildren().add(textFlow);
                //appending hbox to vbox
                vbox_messages.getChildren().add(hbox);

                if(server!=null)
                    server.sendMessageToClient(messageToSend);


                //sending message to client
                text_message.clear();
            }
        });
    }
    public static Server createServer(){
        try {
            Server server = new Server(new ServerSocket(3000));
            System.out.println("Server started listening on port 3000");
            return server;
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("error creating server");
        }
        return null;
    }
    //method for displaying client message to gui
    public static void addLabel(String messageFromClient,VBox vbox){

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);

        //creating new text field
        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);"+
                "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5,10,5,10));
        hbox.getChildren().add(textFlow);

        Platform.runLater(()->{
            vbox.getChildren().add(hbox);
        });
    }

}