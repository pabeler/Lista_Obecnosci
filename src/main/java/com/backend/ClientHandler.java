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

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class that handles the client's requests.
 */
public class ClientHandler extends Thread {
    private Socket s;
    private ObjectInputStream dis;
    private ObjectOutputStream dos;
    private boolean isRunning = true;
    private SessionFactory sessionFactory;

    public ClientHandler(Socket s, ObjectInputStream dis, ObjectOutputStream dos) {
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
        Session session = sessionFactory.openSession();
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
                            if (((String) data.get("Imie")).isEmpty() || ((String) data.get("Nazwisko")).isEmpty()) {
                                throw new Exception("Imie i nazwisko nie moga byc puste");
                            }
                            Student student = new Student();
                            student.setImie((String) data.get("Imie"));
                            student.setNazwisko((String) data.get("Nazwisko"));
                            student.setGrupa(null);
                            session.getTransaction().begin();
                            session.persist(student);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case DELETE_STUDENT -> {
                        try {
                            Student student = session.find(Student.class, data.get("ID_Studenta"));
                            if (student == null) {
                                throw new Exception("Nie ma takiego studenta");
                            }
                            session.getTransaction().begin();
                            session.remove(student);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case ADD_GROUP -> {
                        try {
                            if (((String) data.get("Nazwa")).isEmpty()) {
                                throw new Exception("Nazwa grupy nie moze byc pusta");
                            }
                            Grupa grupa = new Grupa();
                            grupa.setNazwa((String) data.get("Nazwa"));
                            grupa.setTermin(null);
                            session.getTransaction().begin();
                            session.persist(grupa);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case DELETE_GROUP -> {
                        try {
                            Grupa grupa = session.find(Grupa.class, data.get("ID_Grupy"));
                            if (grupa == null) {
                                throw new Exception("Nie ma takiej grupy");
                            }
                            session.getTransaction().begin();
                            session.remove(grupa);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case ADD_STUDENT_TO_GROUP -> {
                        try {
                            if (session.find(Grupa.class, data.get("ID_Grupy")) == null) {
                                throw new Exception("Nie ma takiej grupy");
                            }
                            Student student = session.find(Student.class, data.get("ID_Studenta"));
                            if (student == null) {
                                throw new Exception("Nie ma takiego studenta");
                            }
                            session.getTransaction().begin();
                            session.detach(student);
                            student.setGrupa((int) data.get("ID_Grupy"));
                            session.merge(student);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case REMOVE_STUDENT_FROM_GROUP -> {
                        try {
                            Student student = session.find(Student.class, data.get("ID_Studenta"));
                            if (student == null) {
                                throw new Exception("Nie ma takiego studenta");
                            }
                            session.getTransaction().begin();
                            session.detach(student);
                            student.setGrupa(null);
                            session.merge(student);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case ADD_DEADLINE -> {
                        try {
                            Pattern DATE_PATTERN = Pattern.compile(
                                    "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
                                            + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                                            + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                                            + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
                            Matcher matcher = DATE_PATTERN.matcher((String) data.get("Data"));
                            if (!matcher.matches()) {
                                throw new Exception("Niepoprawna data");
                            }
                            Grupa grupa = session.find(Grupa.class, data.get("ID_Grupy"));
                            if (grupa == null) {
                                throw new Exception("Nie ma takiej grupy");
                            }
                            grupa.setTermin(Date.valueOf((String) data.get("Data")));
                            session.getTransaction().begin();
                            session.detach(grupa);
                            session.merge(grupa);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case CHECK_ABSENCE -> {
                        try {
                            if (data.get("Obecnosc") == null) {
                                throw new Exception("Obecnosc nie moze byc pusta");
                            }
                            Student student = session.find(Student.class, data.get("ID_Studenta"));
                            if (student == null) {
                                throw new Exception("Nie ma takiego studenta");
                            }
                            session.getTransaction().begin();
                            session.detach(student);
                            student.setObecnosc((String) data.get("Obecnosc"));
                            session.merge(student);
                            session.getTransaction().commit();
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, null));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case GET_ABSENCE_LIST -> {
                        try {
                            CriteriaBuilder cb = session.getCriteriaBuilder();
                            CriteriaQuery<Student> cq = cb.createQuery(Student.class);
                            Root<Student> rootEntry = cq.from(Student.class);
                            CriteriaQuery<Student> all = cq.select(rootEntry);
                            TypedQuery<Student> allQuery = session.createQuery(all);
                            HashMap<String, Object> students = new HashMap<>();
                            for (Student s : allQuery.getResultList()) {
                                students.put(String.valueOf(s.getId()), s);
                            }
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, students));
                        } catch (Exception e) {
                            dos.writeObject(new DataPackage(DataPackage.Command.UNSUCCESSFUL, null));
                        }
                    }
                    case GET_GROUP_LIST -> {
                        try {
                            CriteriaBuilder cb = session.getCriteriaBuilder();
                            CriteriaQuery<Grupa> cq = cb.createQuery(Grupa.class);
                            Root<Grupa> rootEntry = cq.from(Grupa.class);
                            CriteriaQuery<Grupa> all = cq.select(rootEntry);
                            TypedQuery<Grupa> allQuery = session.createQuery(all);
                            HashMap<String, Object> groups = new HashMap<>();
                            for (Grupa g : allQuery.getResultList()) {
                                groups.put(String.valueOf(g.getId()), g);
                            }
                            dos.writeObject(new DataPackage(DataPackage.Command.SUCCESSFUL, groups));
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

    public Socket getSocket() {
        return s;
    }
}