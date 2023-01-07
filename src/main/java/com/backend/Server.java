package com.backend;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int PORT = 1234;
    private static final String HOST = "localhost";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lista_obecnosci_jpa?serverTimezone=UTC";
    private static ArrayList<ClientHandler> clients = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        // server is listening on port 5056

        ServerSocket ss = new ServerSocket(5056);
        //Thread clientMessageHandler = new serverHandlers.MessageHandler(); //ServerMessageHandler
        //clientMessageHandler.start();
        System.out.println("Socket server started at port: " + PORT);
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();


                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                ClientHandler c = new ClientHandler(s, dis, dos);
                clients.add(c);
                c.start();

            }
            catch (Exception e){
                for(ClientHandler c : clients) {
                    if(c.getSocket().equals(s)){
                        s.close();
                        clients.remove(c);
                        break;
                    }
                }
                e.printStackTrace();
            }
        }

    }
}

