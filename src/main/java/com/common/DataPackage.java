package com.common;

import java.util.HashMap;

/**
 * Class that represents a package of data that is sent between the server and the client.
 */
public class DataPackage implements java.io.Serializable {
    /**
     * command to be executed
     */
    private Command command;
    /**
     * map storing Object identifier and the Object
     */
    private HashMap<String, Object> map;

    /**
     * Constructor that creates a data package.
     *
     * @param command command to be executed
     * @param map     map storing Object identifier and the Object
     */
    public DataPackage(Command command, HashMap<String, Object> map) {
        this.command = command;
        this.map = map;
    }

    /**
     * Method that returns the command.
     *
     * @return command to be executed
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Method that returns the map.
     *
     * @return map storing Object identifier and the Object
     */
    public HashMap<String, Object> getMap() {
        return map;
    }

    /**
     * Enum that contains the commands that can be sent between the server and the client.
     */
    public enum Command {
        ADD_STUDENT,
        DELETE_STUDENT,
        ADD_GROUP,
        DELETE_GROUP,
        ADD_STUDENT_TO_GROUP,
        REMOVE_STUDENT_FROM_GROUP,
        ADD_DEADLINE,
        CHECK_ABSENCE,
        GET_ABSENCE_LIST,
        EXIT_PROGRAM,
        SUCCESSFUL,
        UNSUCCESSFUL,
        GET_GROUP_LIST,
    }
}