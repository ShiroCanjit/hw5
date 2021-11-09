package hw5.entity;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;


public final class Waybill {
    private int id;
    private int number;
    private Timestamp date;
    private int sender;

    public Waybill(int id, int number, Timestamp date, int sender) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.sender = sender;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {this.date = date; }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }


    @Override
    public @NotNull String toString() {
        return "Organization{" +
                "id=" + id +
                ", number=" + number +
                ", date=" + date +
                ", sender=" + sender +
                '}';
    }
}
