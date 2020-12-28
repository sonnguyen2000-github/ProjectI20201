package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.ChungChi;
import main.LichSu;
import main.PostgresqlConn;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeePaperController implements Initializable{
    protected ObservableList<ChungChi> ccList;
    protected ObservableList<LichSu> lsList;
    protected String id;
    protected PostgresqlConn postgresqlConn;

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
    @FXML
    Button refeshBtn;
    @FXML
    MenuButton addBtn;
    @FXML
    MenuItem addCCBtn, addLSBtn;


    @FXML
    public void refresh(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        this.setPerson(this.id);
    }

    @FXML
    public void addNewCC() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditCC.fxml"));
        Stage stage = new Stage();
        stage.setTitle("NEW");
        stage.setScene(new Scene(loader.load(), 301, 437));
        stage.setResizable(false);
        EditCCController ccController = loader.getController();
        ccController.setPerson(id);
        ccController.setAddNew(true);
        //
        stage.setOnHidden(windowEvent -> setPerson(id));
        stage.show();
    }

    @FXML
    public void addNewLS() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditLS.fxml"));
        Stage stage = new Stage();
        stage.setTitle("NEW");
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        EditLSController lsController = loader.getController();
        lsController.setPerson(id);
        lsController.setAddNew(true);
        //
        stage.setOnHidden(windowEvent -> setPerson(id));
        stage.show();
    }

    public void setPerson(String id){
        ccList = FXCollections.observableArrayList();
        lsList = FXCollections.observableArrayList();
        //
        ccTable.setItems(ccList);
        lsTable.setItems(lsList);
        //
        this.id = id;
        String tencc, mucdo, cosocap, cncc, cmcc, minhchung;
        int namcap, hsd;
        //
        String tenda, vitri, cnda, cmda;
        int from, to;
        try{
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            rs = stmt.executeQuery(
                    "SELECT distinct \"ID\", ten, totnghiep, cosocap, chuyennganh, chuyenmon, minhchung, nam, hsd, level\n" +
                    "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id + "%';");
            while(rs.next()){/**/
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
                    "SELECT distinct \"ID\", tenduan, chuyennganh, \"from\", congty, chuyenmon, vitri, \"to\"\n" +
                    "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id + "%';");
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
            ccTable.setOnMouseClicked(event -> {
                editTable(event);
                try{
                    editRecords(event);
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
            lsTable.setOnMouseClicked(event -> {
                editTable(event);
                try{
                    editRecords(event);
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
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
                    //delete query should be here
                    try{
                        Connection connection = postgresqlConn.getConnection();
                        Statement stmt = connection
                                .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        Object object = temp.getSelectionModel().getSelectedItem();
                        if(object instanceof ChungChi){
                            ChungChi chungChi = (ChungChi) object;
                            stmt.executeUpdate("DELETE FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id +
                                               "%' and ten like '%" + chungChi.getTencc() + "%';");
                        }else{
                            if(object instanceof LichSu){
                                LichSu lichSu = (LichSu) object;
                                stmt.executeUpdate(
                                        "DELETE FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id +
                                        "%' and tenduan  like '%" + lichSu.getTenda() + "%' and vitri like '%" +
                                        lichSu.getVitri() + "%';");
                            }
                        }
                        temp.getItems().remove(temp.getSelectionModel().getSelectedIndex());
                        connection.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    //
                }
            }
        }
    }

    public void editRecords(MouseEvent event) throws IOException, SQLException{
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TableView tableView = (TableView) event.getSource();
            if(tableView.getSelectionModel().getSelectedItem() != null){
                Object obj = tableView.getSelectionModel().getSelectedItem();
                if(obj instanceof ChungChi){
                    ChungChi chungChi = (ChungChi) obj;
                    //
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditCC.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("EDIT");
                    stage.setScene(new Scene(loader.load(), 301, 437));
                    stage.setResizable(false);
                    stage.setOnHidden(windowEvent -> setPerson(id));
                    //
                    EditCCController controller = loader.getController();
                    controller.setCC(id, chungChi);
                    stage.show();
                }else{
                    if(obj instanceof LichSu){
                        LichSu lichSu = (LichSu) obj;
                        //
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditLS.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("EDIT");
                        stage.setScene(new Scene(loader.load()));
                        stage.setResizable(false);
                        stage.setOnHidden(windowEvent -> setPerson(id));
                        //
                        EditLSController controller = loader.getController();
                        controller.setLS(id, lichSu);
                        stage.show();
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
}
