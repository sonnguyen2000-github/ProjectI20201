package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExtraInfoController implements Initializable{
    protected ObservableList<ChungChi> ccList;
    protected ObservableList<LichSu> lsList;
    protected PostgresqlConn postgresqlConn;
    protected Person person;
    protected boolean editable;
    //
    @FXML
    TableView<ChungChi> ccTable;
    @FXML
    TableView<LichSu> lsTable;
    @FXML
    TableColumn<ChungChi, String> tencc, mucdo, cosocap, cncc, cmcc;
    @FXML
    TableColumn<ChungChi, Integer> namcap, hsd;
    @FXML
    TableColumn<LichSu, String> tenda, vitri, cnda, cmda;
    @FXML
    TableColumn<LichSu, Integer> from, to;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ccList = FXCollections.observableArrayList();
        lsList = FXCollections.observableArrayList();
        //
        ccTable.setItems(ccList);
        lsTable.setItems(lsList);
        //
        tencc.setCellValueFactory(new PropertyValueFactory<>("tencc"));
        mucdo.setCellValueFactory(new PropertyValueFactory<>("mucdo"));
        cosocap.setCellValueFactory(new PropertyValueFactory<>("cosocap"));
        cncc.setCellValueFactory(new PropertyValueFactory<>("cn"));
        cmcc.setCellValueFactory(new PropertyValueFactory<>("cm"));
        namcap.setCellValueFactory(new PropertyValueFactory<>("nam"));
        hsd.setCellValueFactory(new PropertyValueFactory<>("hsd"));
        //
        tenda.setCellValueFactory(new PropertyValueFactory<>("tenda"));
        vitri.setCellValueFactory(new PropertyValueFactory<>("vitri"));
        cnda.setCellValueFactory(new PropertyValueFactory<>("cn"));
        cmda.setCellValueFactory(new PropertyValueFactory<>("cm"));
        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        to.setCellValueFactory(new PropertyValueFactory<>("to"));
        //
        postgresqlConn = new PostgresqlConn();

    }

    public void setExtraInfo(Person person, boolean editable){
        String tencc, mucdo, cosocap, cncc, cmcc, minhchung;
        int namcap, hsd;
        //
        String tenda, vitri, cnda, cmda;
        int from, to;
        //
        this.editable = editable;
        this.person = person;
        try{
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            String id = person.getID();
            //
            rs = stmt.executeQuery(
                    "SELECT distinct \"ID\", ten, totnghiep, cosocap, chuyennganh, chuyenmon, minhchung, nam, hsd, level\n" + "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id + "%';");
            while(rs.next()){
                tencc = rs.getString(2);
                mucdo = rs.getString(10);
                minhchung = rs.getString(7);
                cosocap = rs.getString(4);
                cncc = rs.getString(5);
                cmcc = rs.getString(6);
                namcap = rs.getInt(8);
                hsd = rs.getInt(9);
                //
                ccList.add(new ChungChi(tencc, mucdo, cosocap, minhchung, cncc, cmcc, namcap, hsd));
            }
            //
            rs = stmt.executeQuery(
                    "SELECT distinct \"ID\", tenduan, chuyennganh, \"from\", congty, chuyenmon, vitri, \"to\"\n" + "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id + "%';");
            while(rs.next()){
                tenda = rs.getString(2);
                vitri = rs.getString(7);
                cnda = rs.getString(3);
                cmda = rs.getString(6);
                from = rs.getInt(4);
                to = rs.getInt(8);
                lsList.add(new LichSu(tenda, vitri, cnda, cmda, from, to));
            }
            //
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(editable){
                ccTable.setOnMouseClicked(this::editTable);
                lsTable.setOnMouseClicked(this::editTable);
            }
        }
    }

    public void editTable(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            TableView temp = (TableView) event.getSource();
            if(temp.getSelectionModel().getSelectedItem() != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("");
                alert.setHeaderText("DELETE RECORD ?");
                alert.setContentText("");
                Optional<ButtonType> rs = alert.showAndWait();
                if(rs.isPresent() && rs.get() == ButtonType.OK){
                    temp.getItems().remove(temp.getSelectionModel().getSelectedIndex());
                }
            }
        }
    }

}
