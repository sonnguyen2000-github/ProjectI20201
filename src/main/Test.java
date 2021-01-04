package main;

import com.github.javafaker.Faker;

import java.sql.*;

public class Test{
    public static void main(String[] args){
        try{
            PostgresqlConn postgresql = new PostgresqlConn();
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT mahdld, \"from\", \"to\"\n" + "FROM public.\"HDLD\";");
            ResultSet rs1 = stmt.executeQuery(
                    "SELECT \"ID\", trinhdo, chucvu, mahdld, hoten\n" + "FROM public.\"EmployeeInformation\";");
            for(int i = 0; i < 50; i++){
                Faker faker = new Faker();
                String maHdld_ = faker.code().isbn10();
                String maNv_ = faker.code().gtin8();
                String fullName_ = faker.name().fullName();
                Date from_ = Date.valueOf("2015-01-01");
                Date to_ = Date.valueOf("2025-01-01");
                String chucvu_ = faker.job().position();
                String trinhdo_ = "Engineer";

                rs.moveToInsertRow();
                rs.updateString(1, maHdld_);
                rs.updateDate(2, from_);
                rs.updateDate(3, to_);
                rs.insertRow();

                rs1.moveToInsertRow();
                rs1.updateString(1, maNv_);
                rs1.updateString(2, trinhdo_);
                rs1.updateString(3, chucvu_);
                rs1.updateString(4, maHdld_);
                rs1.updateString(5, fullName_);
                rs1.insertRow();
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
