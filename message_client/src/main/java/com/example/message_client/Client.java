package com.example.message_client;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Client(Socket socket){
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public void closeAll(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedReader.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void sendMessageToServer(String messageToServer){
        try{
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            closeAll(socket,bufferedReader,bufferedWriter);
        }

    }
    public void receiveMessageFromServer(VBox vbox){
        new Thread(()->{
            while(socket.isConnected()){
                try{
                    String messageFromServer = bufferedReader.readLine();
                    HelloController.addLabel(messageFromServer,vbox);
                }catch(IOException e){
                    e.printStackTrace();
                    System.out.println("Error receiving message from client");
                    closeAll(socket,bufferedReader,bufferedWriter);
                    break;
                }
            }
        }).start();
    }
}
