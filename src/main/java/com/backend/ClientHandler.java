package com.backend;

import com.common.DataPackage;
import com.common.Grupa;
import com.common.Student;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.HashMap;

public class ClientHandler extends Thread {
    //private String name;
    private Socket s;
    private ObjectInputStream dis;
    private ObjectOutputStream dos;
    private boolean isRunning = true;
    private SessionFactory sessionFactory;


    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos) {
        //this.name = name;
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    public void run() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
        Session em = sessionFactory.openSession();


        while (isRunning) {
            try {
                // Ask user what he wants
                DataPackage dataPackage = (DataPackage) dis.readObject();
                HashMap<String, Object> data = dataPackage.getData();
                // write on output stream based on the
                // answer from the client
                switch (dataPackage.getCommand()) {
                    case EXIT_PROGRAM -> {
                        System.out.println("Client " + this.s + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.s.close();
                        System.out.println("Connection closed");
                        isRunning = false;
                    }
                    case ADD_STUDENT -> {
                        try {
                            em.getTransaction().begin();
                            Student student = new Student();
                            student.setImie((String) data.get("Imie"));
                            student.setNazwisko((String) data.get("Nazwisko"));
                            student.setGrupa(null);
                            em.persist(student);
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (IOException e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case DELETE_STUDENT -> {
                        try {
                            em.getTransaction().begin();
                            em.remove(em.find(Student.class, data.get("ID_Studenta")));
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (IOException e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case ADD_GROUP -> {
                        try {
                            em.getTransaction().begin();
                            Grupa grupa = new Grupa();
                            grupa.setNazwa((String) data.get("Nazwa"));
                            grupa.setTermin(null);
                            em.persist(grupa);
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (IOException e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case DELETE_GROUP -> {
                        try {
                            em.getTransaction().begin();
                            em.remove(em.find(Grupa.class, data.get("ID_Grupy")));
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (IOException e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case ADD_STUDENT_TO_GROUP -> {
                        try {
                            Grupa grupa = em.find(Grupa.class, data.get("ID_Grupy"));
                            Student student1 = em.find(Student.class, data.get("ID_Studenta"));
                            em.detach(student1);
                            student1.setGrupa((int) data.get("ID_Grupy"));
                            em.getTransaction().begin();
                            em.merge(student1);
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case REMOVE_STUDENT_FROM_GROUP -> {
                        try {
                            Student student2 = em.find(Student.class, data.get("ID_Studenta"));
                            em.detach(student2);
                            student2.setGrupa(null);
                            em.getTransaction().begin();
                            em.merge(student2);
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case ADD_DEADLINE -> {
                        try {
                            Grupa grupa1 = em.find(Grupa.class, data.get("ID_Grupy"));
                            em.detach(grupa1);
                            grupa1.setTermin((Date) data.get("Termin"));
                            em.getTransaction().begin();
                            em.merge(grupa1);
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case CHECK_ABSENCE -> {
                        try {
                            Student student3 = em.find(Student.class, data.get("ID_Studenta"));
                            em.detach(student3);
                            student3.setObecnosc((String) data.get("Obecnosc"));
                            em.getTransaction().begin();
                            em.merge(student3);
                            em.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case GET_ABSENCE_LIST -> {
                        try {
                            CriteriaBuilder cb = em.getCriteriaBuilder();
                            CriteriaQuery<Student> cq = cb.createQuery(Student.class);
                            Root<Student> rootEntry = cq.from(Student.class);
                            CriteriaQuery<Student> all = cq.select(rootEntry);
                            TypedQuery<Student> allQuery = em.createQuery(all);
                            HashMap<String, Object> students = new HashMap<>();
                            for (Student s : allQuery.getResultList()) {
                                students.put(String.valueOf(s.getId()), s);
                            }
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, students));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    default -> dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
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
