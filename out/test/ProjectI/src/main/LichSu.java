package main;

import java.sql.*;

public class LichSu{
    protected String tenda, vitri, cn, cm, congty;
    protected int from, to;

    public LichSu(){
    }

    public LichSu(String tenda, String vitri, String cn, String cm, int from, int to){
        this.tenda = tenda;
        this.vitri = vitri;
        this.cn = cn;
        this.cm = cm;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString(){
        return "Tên dự án: " + this.tenda + "\n" + "Công ty:" + this.congty + "\n" + "Vị trí: " + this.vitri + "\n" + "Chuyên môn: " + this.cm;
    }

    public String getTenda(){
        return tenda;
    }

    public void setTenda(String tenda){
        this.tenda = tenda;
    }

    public String getVitri(){
        return vitri;
    }

    public void setVitri(String vitri){
        this.vitri = vitri;
    }

    public String getCn(){
        return cn;
    }

    public void setCn(String cn){
        this.cn = cn;
    }

    public String getCm(){
        return cm;
    }

    public void setCm(String cm){
        this.cm = cm;
    }

    public int getFrom(){
        return from;
    }

    public void setFrom(int from){
        this.from = from;
    }

    public int getTo(){
        return to;
    }

    public void setTo(int to){
        this.to = to;
    }

    public String getCongty(){
        return congty;
    }

    public void setCongty(String congty) throws SQLException{
        this.congty = congty;

        PostgresqlConn postgresqlConn = new PostgresqlConn();
        Connection connection = postgresqlConn.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(
                "SELECT mact, ten, diachi, quocgia\n" + "FROM public.\"CongTy\"\n" + "WHERE mact = 'this';");
        if(rs.next()){
            this.congty = rs.getString(2);
        }

        connection.close();
    }
}
