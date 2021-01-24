package main;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Test{
    public static void main(String[] args){

        /* try{
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
        }*/
        /*try{
            PostgresqlConn postgresqlConn = new PostgresqlConn();
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(
                    "SELECT \"ID\", trinhdo, chucvu, mahdld, hoten\n" + "\tFROM public.\"EmployeeInformation\"\n" + "\tWHERE \"ID\" like '%6%9%';");
            ResultSet rs1 = stmt.executeQuery(
                    "SELECT \"ID\", ten, totnghiep, cosocap, chuyennganh, chuyenmon, minhchung, nam, hsd, level\n" + "\tFROM public.\"BangCap\";");
            ResultSet rs2 = stmt.executeQuery(
                    "SELECT \"ID\", tenduan, chuyennganh, \"from\", mact, chuyenmon, vitri, \"to\", minhchung\n" + "\tFROM public.\"LichSuCongTac\";");
            while(rs.next()){
                rs1.moveToInsertRow();

                rs1.updateString(1, rs.getString(1));
                rs1.updateString(2, "MS Web Expert Certified");// ten chung chi
                rs1.updateBoolean(3, false);// tot nghiep
                rs1.updateString(4, "Microsoft");// co so cap
                rs1.updateString(5, "Information Technology");// chuyennganh
                rs1.updateString(6, "Website");// chuyenmon
                rs1.updateString(7, "");// minhchung
                rs1.updateInt(8, 2020);// nam cap
                rs1.updateInt(9, 2026);// hsd : 0 neu ko co hsd
                rs1.updateString(10, "Professional");// level

                rs1.insertRow();
            }
            *//*while(rs.next()){
                rs2.moveToInsertRow();

                rs2.updateString(1, rs.getString(1));
                rs2.updateString(2, "Lắp đặt hạ tầng mạng");// ten du an
                rs2.updateString(3, "Information Technology");// chuyen nganh
                rs2.updateInt(4, 2015);// from
                rs2.updateString(5, "This");// ma cong ty
                rs2.updateString(6, "Website");// chuyen mon
                rs2.updateString(7, "Side Assistant");// vi tri
                rs2.updateInt(8, 2017);// to
                rs2.updateString(9, "");// minh chung

                rs2.insertRow();
            }*//*

        }catch(Exception e){
            e.printStackTrace();
        }*/

        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(
                    "E:\\OneDrive - Hanoi University of Science and Technology\\Documents\\Eclipse Projects\\ProjectI\\src\\main\\export_pdf.pdf"));
        }catch(DocumentException|FileNotFoundException e){
            e.printStackTrace();
        }
        document.open();

        PdfPTable table = new PdfPTable(3);
        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("Biểu mẫu");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        PdfPTable pdfPTable = new PdfPTable(3);
        pdfPTable.addCell(c1);
        pdfPTable.addCell(c1);
        pdfPTable.addCell(c1);
        pdfPTable.setHeaderRows(1);
        pdfPTable.addCell(c1);
        pdfPTable.addCell(c1);
        pdfPTable.addCell(c1);
        table.addCell(pdfPTable);
        try{
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_CENTER);

            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Color.BLACK);
            paragraph.setFont(font);
            paragraph.add(new String("Biểu mẫu".getBytes(StandardCharsets.UTF_8)));
            Phrase phrase = new Phrase();
            phrase.add(new String("Biểu mẫu".getBytes(StandardCharsets.UTF_8)));
            document.add(phrase);
            document.add(paragraph);
            document.add(table);
            document.newPage();
            document.add(paragraph);
            document.add(table);
        }catch(DocumentException e){
            e.printStackTrace();
        }
        document.close();
    }
}
