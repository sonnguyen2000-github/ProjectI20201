package main;

import java.sql.Date;

public class Person{
    private String ID;
    private String hoten;
    private String trinhdo;
    private String chucvu;
    private String mahdld;
    private Date from, to;

    public Person(String ID, String hoten, String trinhdo, String chucvu, String mahdld){
        this.ID = ID;
        this.hoten = hoten;
        this.trinhdo = trinhdo;
        this.chucvu = chucvu;
        this.mahdld = mahdld;
    }

    public Person(String ID, String hoten, String trinhdo, String chucvu, String mahdld, Date from, Date to){
        this.ID = ID;
        this.hoten = hoten;
        this.trinhdo = trinhdo;
        this.chucvu = chucvu;
        this.mahdld = mahdld;
        this.from = from;
        this.to = to;
    }

    public Date getFrom(){
        return from;
    }

    public Date getTo(){
        return to;
    }

    @Override
    public String toString(){
        return getID() + "\t" + String.format("%-25s", getHoten()) + "\t\t\t\t" + String.format("%-20s", getTrinhdo()) +
               "\t\t\t" + String.format("%-20s", getChucvu()) + "\t\t\t" + getMahdld();
    }

    public String getID(){
        return ID;
    }

    public String getHoten(){
        return hoten;
    }

    public String getTrinhdo(){
        return trinhdo;
    }

    public String getChucvu(){
        return chucvu;
    }

    public String getMahdld(){
        return mahdld;
    }
}
