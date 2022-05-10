package com.ex.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public ServerApp(ServerSocket serverSocket){

        this.serverSocket = serverSocket;

    }
    //
    public void startServer(){
        System.out.println("Server started");
        try{
            //accepts client connections
            while(!serverSocket.isClosed()){
                this.socket = serverSocket.accept();
                //creates a new clientHandler for each client
                new Thread(new ClientHandler(socket)).start();
            }
        }catch(IOException e){
            System.out.println(e);
            closeServerSocket();
        }
    }
    //close server socket
    public void closeServerSocket(){
        try{
            if(serverSocket != null)
                serverSocket.close();
        }catch(IOException e){

        }
    }
    public static void main(String[] args) throws IOException{

        // starts execution of server

        new ServerApp(new ServerSocket(3000)).startServer();


    }
}
