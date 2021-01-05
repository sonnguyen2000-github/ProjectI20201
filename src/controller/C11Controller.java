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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class C11Controller implements Initializable{
    @FXML
    TableView<C11> c11Table;
    @FXML
    TableColumn<C11, Integer> sttCol;
    @FXML
    TableColumn<C11, String> hotenCol, kncmCol;

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
                c11.setKncm("");
                rs = stmt.executeQuery(
                        "SELECT \"ID\", tenduan, chuyennganh, \"from\", mact, chuyenmon, vitri, \"to\", minhchung\n" +
                        "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + person.getID() + "%';");
                while(rs.next()){
                    LichSu lichSu = new LichSu();
                    lichSu.setTenda(rs.getString(2));
                    lichSu.setVitri(rs.getString(7));
                    lichSu.setCn(rs.getString(3));
                    c11.setKncm(c11.getKncm() + "\n" + lichSu);
                }
                rs = stmt.executeQuery(
                        "SELECT \"ID\", ten, totnghiep, cosocap, chuyennganh, chuyenmon, minhchung, nam, hsd, level\n" +
                        "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + person.getID() + "%';");
                while(rs.next()){
                    ChungChi chungChi = new ChungChi();
                    chungChi.setTencc(rs.getString(2));
                    chungChi.setMucdo(rs.getString(10));
                    c11.setKncm(c11.getKncm() + "\n" + chungChi);
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
        kncmCol.setCellValueFactory(new PropertyValueFactory<>("kncm"));
    }


    public static class C11{
        private int stt;
        private String hoten;
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

        public String getKncm(){
            return kncm;
        }

        public void setKncm(String kncm){
            this.kncm = kncm;
        }
    }
}
