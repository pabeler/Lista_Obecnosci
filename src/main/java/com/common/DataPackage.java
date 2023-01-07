package com.common;

import java.util.HashMap;

public class DataPackage implements java.io.Serializable {
    private Command command;
    private HashMap<String, Object> data;

    public DataPackage(Command command, HashMap<String, Object> data) {
        this.command = command;
        this.data = data;
    }

    public DataPackage(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public enum Command{
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
    }
}
