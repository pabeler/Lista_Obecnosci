package com.backend;

import com.common.DataPackage;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientHandler extends Thread {
    //private String name;
    private Socket s;
    private ObjectInputStream dis;
    private ObjectOutputStream dos;
    private boolean isRunning = true;

    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos) {
        //this.name = name;
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    public void run() {
        String received;
        String toreturn;

        while (isRunning) {
            try {
                // Ask user what he wants
                DataPackage dataPackage = (DataPackage) dis.readObject();

                // creating Date object
                Date date = new Date();

                // write on output stream based on the
                // answer from the client
                switch (dataPackage.getCommand()) {
                    case EXIT_PROGRAM :
                        System.out.println("Client " + this.s + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.s.close();
                        System.out.println("Connection closed");
                        isRunning = false;
                        break;

                    case ADD_STUDENT:
                        //Todo: add student to database;
                        break;
                    case DELETE_STUDENT:
                        //Todo: delete student from database;
                        break;
                    case ADD_GROUP:
                        //Todo: add group to database;
                        break;
                    case DELETE_GROUP:
                        //Todo: delete group from database;
                        break;
                    case ADD_STUDENT_TO_GROUP:
                        //Todo: add student to group in database;
                        break;
                    case REMOVE_STUDENT_FROM_GROUP:
                        //Todo: remove student from group in database;
                        break;
                    case ADD_DEADLINE:
                        //Todo: add deadline for a group;
                        break;
                    case CHECK_ABSENCE:
                        //Todo: check if student is absent;
                        break;
                    case GET_ABSENCE_LIST:
                        //Todo: get list of students who are absent;
                        break;

                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        };

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }


//    public String getClientName() {
//        return name;
//    }
//
//    public void setClientName(String name) {
//        this.name = name;
//    }

    public Socket getSocket() {
        return s;
    }

    public void setSocket(Socket s) {
        this.s = s;
    }

    public ObjectInputStream getDis() {
        return dis;
    }

    public void setDis(ObjectInputStream dis) {
        this.dis = dis;
    }

    public ObjectOutputStream getDos() {
        return dos;
    }

    public void setDos(ObjectOutputStream dos) {
        this.dos = dos;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

//    public boolean sendToClient(String clientName, String message) {
//        for(Client c : clients) {
//            if(c.getClientName().equals(clientName)) {
//                c.sendMessage(message);
//                return true;
//            }
//        }
//        return false;
//    }

    private void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void sendToAllExceptSender(Client c, String received) {
//        for (Client client : clients) {
//            if (client != c) {
//                client.sendMessage(received);
//            }
//        }
//    }

//    public static void sendToAll(String msg){
//        for (Client client : clients){
//            client.sendMessage(msg);
//        }
//    }

}
