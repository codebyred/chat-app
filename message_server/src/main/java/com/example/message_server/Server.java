package com.example.message_server;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Server(ServerSocket serverSocket){
        try{
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            System.out.println("success");

        }catch(IOException e){
            System.out.println("Error Creating server");
            e.printStackTrace();
            closeAll(socket,bufferedReader,bufferedWriter);

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
    public void sendUserNameToClient(String userName){
        try{
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            closeAll(socket,bufferedReader,bufferedWriter);
        }
    }
    public void sendMessageToClient(String messageToClient){
        try{
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            closeAll(socket,bufferedReader,bufferedWriter);
        }

    }
    public void receiveMessageFromClient(VBox vbox){
        new Thread(()->{
            while(socket.isConnected()){
                try{
                    String messageFromClient = bufferedReader.readLine();
                    HelloController.addLabel(messageFromClient,vbox);
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
