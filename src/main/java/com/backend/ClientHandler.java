package com.backend;

import com.common.DataPackage;
import com.common.Grupa;
import com.common.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.HashMap;

public class ClientHandler extends Thread {
    //private String name;
    private Socket s;
    private ObjectInputStream dis;
    private ObjectOutputStream dos;
    private boolean isRunning = true;
    @PersistenceContext
    private EntityManager em;

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
                HashMap<String, Object> data = dataPackage.getData();
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
                        em.getTransaction().begin();
                        Student student = new Student();
                        student.setId((int) data.get("ID_Studenta"));
                        student.setImie((String) data.get("Imie"));
                        student.setNazwisko((String) data.get("Nazwisko"));
                        student.setGrupa((int) data.get("ID_Grupy"));
                        em.persist(student);
                        em.getTransaction().commit();
                        break;
                    case DELETE_STUDENT:
                        //Todo: delete student from database;
                        em.getTransaction().begin();
                        em.remove(em.find(Student.class, data.get("ID_Studenta")));
                        em.getTransaction().commit();
                        break;
                    case ADD_GROUP:
                        //Todo: add group to database;
                        em.getTransaction().begin();
                        Grupa grupa = new Grupa();
                        grupa.setId((int) data.get("ID_Grupy"));
                        grupa.setNazwa((String) data.get("Nazwa"));
                        grupa.setTermin(null);
                        em.persist(grupa);
                        em.getTransaction().commit();
                        break;
                    case DELETE_GROUP:
                        //Todo: delete group from database;
                        em.getTransaction().begin();
                        em.remove(em.find(Grupa.class, data.get("ID_Grupy")));
                        em.getTransaction().commit();
                        break;
                    case ADD_STUDENT_TO_GROUP:
                        //Todo: add student to group in database;
                        em.getTransaction().begin();
                        em.find(Student.class, data.get("ID_Studenta")).setGrupa((int) data.get("ID_Grupy"));
                        em.getTransaction().commit();
                        break;
                    case REMOVE_STUDENT_FROM_GROUP:
                        //Todo: remove student from group in database;
                        em.getTransaction().begin();
                        em.find(Student.class, data.get("ID_Studenta")).setGrupa(null);
                        em.getTransaction().commit();
                        break;
                    case ADD_DEADLINE:
                        //Todo: add deadline for a group;
                        em.getTransaction().begin();
                        em.find(Grupa.class, data.get("ID_Grupy")).setTermin((Date) data.get("Termin"));
                        em.getTransaction().commit();
                        break;
                    case CHECK_ABSENCE:
                        //Todo: check if student is absent;
                        em.getTransaction().begin();
                        Student student1 = em.find(Student.class, data.get("ID_Studenta"));
                        student1.setObecnosc((String) data.get("Obecnosc"));
                        em.getTransaction().commit();
                        break;
                    case GET_ABSENCE_LIST:
                        //Todo: get list of students who are absent;
                        CriteriaBuilder cb = em.getCriteriaBuilder();
                        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
                        Root<Student> rootEntry = cq.from(Student.class);
                        CriteriaQuery<Student> all = cq.select(rootEntry);
                        TypedQuery<Student> allQuery = em.createQuery(all);
                        HashMap<String, Object> students = new HashMap<>();
                        for (Student s : allQuery.getResultList()) {
                            students.put(String.valueOf(s.getId()), s);
                        }
                        DataPackage dataPackage1 = new DataPackage(null, students);
                        dos.writeObject(dataPackage1);
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
