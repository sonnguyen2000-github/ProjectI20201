package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.ChungChi;
import main.LichSu;
import main.Person;
import main.PostgresqlConn;

import java.net.URL;
import java.sql.*;
import java.util.Random;
import java.util.ResourceBundle;

public class C11Controller implements Initializable{
    @FXML
    TableView<C11> c11Table;
    @FXML
    TableColumn<C11, Integer> sttCol;
    @FXML
    TableColumn<C11, String> hotenCol, fromCol, toCol, kncmCol;

    public void setResult(ObservableList<SearchViewController.SearchField> result) throws SQLException{
        int stt_ = 0;
        PostgresqlConn postgresql = new PostgresqlConn();
        Connection connection = postgresql.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs;
        for(SearchViewController.SearchField searchField : result){
            for(Person person : searchField.getResults()){
                C11 c11 = new C11();

                c11.setStt(++stt_);
                c11.setHoten(person.getHoten());
                c11.setTungay("");
                c11.setDenngay("");
                c11.setKncm("");

                rs = stmt.executeQuery(
                        "SELECT \"ID\", tenduan, chuyennganh, \"from\", mact, chuyenmon, vitri, \"to\", minhchung\n" + "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + person.getID() + "%';");
                while(rs.next()){
                    LichSu lichSu = new LichSu();
                    lichSu.setTenda(rs.getString(2));
                    lichSu.setVitri(rs.getString(7));
                    lichSu.setCn(rs.getString(3));
                    lichSu.setCm(rs.getString(6));
                    lichSu.setFrom(rs.getInt(4));
                    lichSu.setTo(rs.getInt(8));
                    lichSu.setCongty("this");

                    int month = new Random().nextInt(9) + 1;
                    c11.setTungay(
                            c11.getTungay() + "\n--------\n" + lichSu.getFrom() + "-" + month + "-" + (new Random().nextInt(
                                    30) + 1) + "\n\n\n");
                    c11.setDenngay(
                            c11.getDenngay() + "\n--------\n" + lichSu.getFrom() + "-" + (month + (month % 2 == 0 ? 2 : 3)) + "-" + (new Random().nextInt(
                                    30) + 1) + "\n\n\n");

                    c11.setKncm(c11.getKncm() + "\n--------\n" + lichSu);
                }

                c11Table.getItems().add(c11);
            }
        }
        connection.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        c11Table.setItems(FXCollections.observableArrayList());

        sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));
        hotenCol.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        fromCol.setCellValueFactory(new PropertyValueFactory<>("tungay"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("denngay"));
        kncmCol.setCellValueFactory(new PropertyValueFactory<>("kncm"));
    }


    public static class C11{
        private int stt;
        private String hoten;
        private String tungay, denngay;
        private String kncm;

        public C11(){
        }

        public int getStt(){
            return stt;
        }

        public void setStt(int stt){
            this.stt = stt;
        }

        public String getHoten(){
            return hoten;
        }

        public void setHoten(String hoten){
            this.hoten = hoten;
        }

        public String getTungay(){
            return tungay;
        }

        public void setTungay(String tungay){
            this.tungay = tungay;
        }

        public String getDenngay(){
            return denngay;
        }

        public void setDenngay(String denngay){
            this.denngay = denngay;
        }

        public String getKncm(){
            return kncm;
        }

        public void setKncm(String kncm){
            this.kncm = kncm;
        }
    }
}
