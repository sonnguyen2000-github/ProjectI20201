package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Person;
import main.PostgresqlConn;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class B11Controller implements Initializable{
    private final B11 finalCvht = new B11();
    @FXML
    TableView<B11> b11Table;
    @FXML
    TableColumn<B11, Integer> ttns_sttCol;
    @FXML
    TableColumn<B11, String> ttns_hotenCol, ttns_idCol, ttns_vitriCol, ttns_trinhdoCol;
    @FXML
    TableColumn<B11, Date> ttns_dobCol;
    @FXML
    TableColumn<B11, String> cvht_hotenCol, cvht_diachiCol, cvht_chucdanhCol, cvht_ngLienLacCol, cvht_dt_fax_emailCol;
    @FXML
    TableColumn<B11, Integer> cvht_soNamLamViecCol;

    public void setResult(ObservableList<SearchViewController.SearchField> result) throws SQLException{
        int stt_ = 0;
        PostgresqlConn postgresql = new PostgresqlConn();
        Connection connection = postgresql.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs;
        for(SearchViewController.SearchField searchField : result){
            for(Person person : searchField.getResults()){
                B11 b11 = new B11();
                /* Thong tin nhan su
                 * */
                b11.setStt(++stt_);
                b11.setTtns_hoten(person.getHoten());
                b11.setId(person.getID());
                b11.setVitri(person.getChucvu());
                rs = stmt.executeQuery(
                        "SELECT \"ID\", ngaysinh, diachi, gioitinh, quequan\n" + "FROM public.\"PrivateEmployeeInformation\"\n" + "WHERE \"ID\" like '%" + person.getID() + "%';");
                if(rs.next()){
                    b11.setDob(rs.getDate(2));
                }else{
                    b11.setDob(new Date(0));
                }
                b11.setTrinhdo(person.getTrinhdo());
                /* Cong viec hien tai
                 * */
                b11.setCvht_hoten(finalCvht.getCvht_hoten());
                b11.setDiachi(finalCvht.getDiachi());
                b11.setChucdanh(person.getTrinhdo());
                rs = stmt.executeQuery(
                        "SELECT date_part('year', now()) - date_part('year', \"from\") + 1\n" + "FROM public.\"HDLD\"\n" + "WHERE mahdld like '%" + person.getMahdld() + "%';");
                if(rs.next()){
                    b11.setSoNamLamViec(rs.getInt(1));
                }
                b11.setNgLienLac(finalCvht.getNgLienLac());
                b11.setDt_fax_email(finalCvht.getDt_fax_email());
                /**/
                b11Table.getItems().add(b11);
            }
        }
        connection.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        b11Table.setItems(FXCollections.observableArrayList());

        ttns_sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));
        ttns_hotenCol.setCellValueFactory(new PropertyValueFactory<>("ttns_hoten"));
        ttns_idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ttns_vitriCol.setCellValueFactory(new PropertyValueFactory<>("vitri"));
        ttns_dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
        ttns_trinhdoCol.setCellValueFactory(new PropertyValueFactory<>("trinhdo"));

        cvht_hotenCol.setCellValueFactory(new PropertyValueFactory<>("cvht_hoten"));
        cvht_diachiCol.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        cvht_chucdanhCol.setCellValueFactory(new PropertyValueFactory<>("chucdanh"));
        cvht_soNamLamViecCol.setCellValueFactory(new PropertyValueFactory<>("soNamLamViec"));
        cvht_ngLienLacCol.setCellValueFactory(new PropertyValueFactory<>("ngLienLac"));
        cvht_dt_fax_emailCol.setCellValueFactory(new PropertyValueFactory<>("dt_fax_email"));

        try{
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/data/admin.json"));

            finalCvht.setCvht_hoten((String) jsonObject.get("name"));
            finalCvht.setChucdanh((String) jsonObject.get("position"));
            finalCvht.setDiachi((String) jsonObject.get("address"));
            finalCvht.setNgLienLac((String) jsonObject.get("contactor"));
            finalCvht.setDt_fax_email((String) jsonObject.get("email"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static class B11{

        private int stt;
        private String ttns_hoten;
        private String id;
        private String vitri;
        private Date dob;
        private String trinhdo;
        private String cvht_hoten;
        private String diachi;
        private String chucdanh;
        private int soNamLamViec;
        private String ngLienLac;
        private String dt_fax_email;

        public B11(){
        }

        public int getStt(){
            return stt;
        }

        public void setStt(int stt){
            this.stt = stt;
        }

        public String getTtns_hoten(){
            return ttns_hoten;
        }

        public void setTtns_hoten(String ttns_hoten){
            this.ttns_hoten = ttns_hoten;
        }

        public String getCvht_hoten(){
            return cvht_hoten;
        }

        public void setCvht_hoten(String cvht_hoten){
            this.cvht_hoten = cvht_hoten;
        }

        public String getId(){
            return id;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getVitri(){
            return vitri;
        }

        public void setVitri(String vitri){
            this.vitri = vitri;
        }

        public Date getDob(){
            return dob;
        }

        public void setDob(Date dob){
            this.dob = dob;
        }

        public String getTrinhdo(){
            return trinhdo;
        }

        public void setTrinhdo(String trinhdo){
            this.trinhdo = trinhdo;
        }

        public String getDiachi(){
            return diachi;
        }

        public void setDiachi(String diachi){
            this.diachi = diachi;
        }

        public String getChucdanh(){
            return chucdanh;
        }

        public void setChucdanh(String chucdanh){
            this.chucdanh = chucdanh;
        }

        public int getSoNamLamViec(){
            return soNamLamViec;
        }

        public void setSoNamLamViec(int soNamLamViec){
            this.soNamLamViec = soNamLamViec;
        }

        public String getNgLienLac(){
            return ngLienLac;
        }

        public void setNgLienLac(String ngLienLac){
            this.ngLienLac = ngLienLac;
        }

        public String getDt_fax_email(){
            return dt_fax_email;
        }

        public void setDt_fax_email(String dt_fax_email){
            this.dt_fax_email = dt_fax_email;
        }

    }
}
