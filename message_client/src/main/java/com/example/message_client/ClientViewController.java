package com.example.message_client;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientViewController implements Initializable {
    @FXML
    private Button send_btn;
    @FXML
    private TextField text_message;
    @FXML
    private ScrollPane sp;
    @FXML
    private VBox vbox_messages;
    @FXML
    private Label user_name;

    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //connect to server
        createClient();

        //vbox increases vertically when hbox is added
        vbox_messages.heightProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue)->{
            //increases scrollpane height
            sp.setVvalue((double) newValue );
        });
        if(client!=null)
            client.receiveMessageFromServer(vbox_messages);

        // to send message to server and display the sent message in gui
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
                //appending hbox to vboxS
                vbox_messages.getChildren().add(hbox);

                //sending message to client
                if(client!=null)
                    client.sendMessageToServer(messageToSend);

                text_message.clear();
            }
        });

    }
    // add messages received from server to gui
    public static void addLabel(String messageFromServer,VBox vbox){

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);

        //creating new text field
        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);"+
                "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5,10,5,10));
        hbox.getChildren().add(textFlow);

        //updates gui on a separate thread
        Platform.runLater(()->{
            vbox.getChildren().add(hbox);
        });
    }
    public void createClient(){
        try{
            this.client = new Client(new Socket("localhost",3000));
            System.out.println("Connection established on port 3000");

        }catch (IOException e){
            System.out.println("Error connecting to the server");
            e.printStackTrace();
        }

    }
    public void setUsername(String username){
        this.user_name.setText(username);
    }
}