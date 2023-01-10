package com.frontend;

import com.common.DataPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class that represents the client.
 */
public class Client {
    private static final int PORT = 5056;
    private static final String HOST = "localhost";
    Socket socket = new Socket(HOST, PORT);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

    public Client() throws IOException {
    }

    /**
     * Method that sends a data package to the server.
     *
     * @param dataPackage data package to be sent
     * @throws IOException if an I/O error occurs
     */
    public void send(DataPackage dataPackage) throws IOException {
        objectOutputStream.writeObject(dataPackage);
    }

    /**
     * Method that receives a data package from the server.
     *
     * @return data package received from the server
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public DataPackage receive() throws IOException, ClassNotFoundException {
        return (DataPackage) objectInputStream.readObject();
    }

    /**
     * Method that closes the client.
     *
     * @throws IOException if an I/O error occurs
     */
    void close() throws IOException {
        socket.close();
        objectInputStream.close();
        objectOutputStream.close();
    }
}