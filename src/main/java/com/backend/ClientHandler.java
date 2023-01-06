package com.backend;

import com.common.DataPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;

import com.common.Grupa;
import com.common.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.xml");
                SessionFactory factory = configuration.buildSessionFactory();
                Session session = factory.openSession();
                Transaction t = session.beginTransaction();
                // write on output stream based on the
                // answer from the client
                switch (dataPackage.getCommand()) {
                    case EXIT_PROGRAM:
                        System.out.println("Client " + this.s + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.s.close();
                        System.out.println("Connection closed");
                        isRunning = false;
                        break;

                    case ADD_STUDENT:
                        //Todo: add student to database;
                        Student s1 = (Student) dataPackage.getData();
                        session.save(s1);
                        t.commit();
                        break;
                    case DELETE_STUDENT:
                        //Todo: delete student from database;
                        Student s2 = (Student) dataPackage.getData();
                        session.delete(s2);
                        break;
                    case ADD_GROUP:
                        //Todo: add group to database;
                        Grupa g1 = (Grupa) dataPackage.getData();
                        session.save(g1);
                        break;
                    case DELETE_GROUP:
                        //Todo: delete group from database;
                        Grupa g2 = (Grupa) dataPackage.getData();
                        session.delete(g2);
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
                this.dos.writeUTF("successfully saved");
                factory.close();
                session.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();
        } catch (IOException e) {
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
