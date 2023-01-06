package com.example.demo1;

import com.common.DataPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final int PORT = 5056;
    private static final String HOST = "localhost";
    Socket socket = new Socket(HOST, PORT);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

    public Client() throws IOException {
    }


    public void send(DataPackage dataPackage) throws IOException {
        objectOutputStream.writeObject(dataPackage);
    }

    public void receive() throws IOException, ClassNotFoundException {
        System.out.println((String) objectInputStream.readObject());
    }
    void close() throws IOException {
        socket.close();
        objectInputStream.close();
        objectOutputStream.close();
    }


}

