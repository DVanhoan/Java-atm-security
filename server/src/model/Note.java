package model;


import java.util.ArrayList;

public class Note<T> {
    T data;
    String message;
    ArrayList<T> list;

    boolean check;

    public Note() {
    }

    public Note(T data, String message, boolean check) {
        this.data = data;
        this.message = message;
        this.check = check;
    }

    public Note(T data, ArrayList<T> list, boolean check) {
        this.data = data;
        this.list = list;
        this.check = check;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }


    public boolean isError() { return !this.check; }

    @Override
    public String toString() {
        return "Note{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", list=" + list +
                ", check=" + check +
                '}';
    }
}
