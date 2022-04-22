package com.ex.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*When a client connects, the server spawns a thread to handle the client.
* every client will have a client handler*/
public class ClientHandler implements Runnable{
    //list of clientHandlers
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private String clientUserName;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    // Creating the client handler from the socket the server passes.
    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.clientUserName = bufferedReader.readLine();
            System.out.println(clientUserName);
            clientHandlers.add(this);
            broadcastMessage("SERVER:"+clientUserName+"has entered the chat");

        }catch(IOException e){
            System.out.println("Error Creating server");
            e.printStackTrace();
            closeAll(socket,bufferedReader,bufferedWriter);
    }
    }
    //run method will be executed in a separate thread created by serverApp
    @Override
    public void run() {
        String messageFromClient;
        // Continue to listen for messages while a connection with the client is still established.
        while (socket.isConnected()) {
            try {
                // Read what the client sent and then send it to every other client.
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                // Close everything.
                closeAll(socket, bufferedReader, bufferedWriter);
                break;
            }
        }

    }
    public void broadcastMessage(String message){
        for(ClientHandler clientHandler:clientHandlers){
            try {
                // to broadcast message to other users except the sender of the message
                if (!clientHandler.clientUserName.equals(clientUserName)){
                //writes message to every client sockets bufferedWriter
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    //clears buffer outputting everything inside it
                    clientHandler.bufferedWriter.flush();
              }
            }catch(IOException e) {
                // Gracefully close everything.
                closeAll(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("Server:"+clientUserName+"has left the group chat");
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
}
