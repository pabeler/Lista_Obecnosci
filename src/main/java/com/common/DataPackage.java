package com.common;

public class DataPackage {
    private Command command;
    private Object data; //Students are Objects, so we can use this field for both Student and List<Student>

    public DataPackage(Command command, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
        EXIT_PROGRAM
    }
}
