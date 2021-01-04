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
import main.Person;
import main.PostgresqlConn;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoAllController implements Initializable{
    protected PostgresqlConn postgresql;
    protected ObservableList<Person> mainList;
    //
    @FXML
    TableView<Person> infoTable;
    @FXML
    TableColumn<Person, String> id, hoten, trinhdo, chucvu, mahdld;
    @FXML
    TableColumn<Person, Date> from, to;
    @FXML
    Button refreshBtn, addNewBtn;

    @FXML
    public void refresh(MouseEvent event) throws SQLException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        doRefresh();
    }

    public void doRefresh() throws SQLException{
        mainList = FXCollections.observableArrayList();
        infoTable.setItems(mainList);
        //
        Connection connection = postgresql.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT \"ID\",hoten, trinhdo, chucvu, p1.mahdld, \"from\", \"to\"\n" +
                                         "FROM public.\"EmployeeInformation\" p1, public.\"HDLD\" p2\n" +
                                         "WHERE p1.mahdld = p2.mahdld ORDER BY hoten;");
        while(rs.next()){
            String id_ = rs.getString(1);
            String hoten_ = rs.getString(2);
            String trinhdo_ = rs.getString(3);
            String chucvu_ = rs.getString(4);
            String mahdld_ = rs.getString(5);
            Date from_ = rs.getDate(6);
            Date to_ = rs.getDate(7);
            mainList.add(new Person(id_, hoten_, trinhdo_, chucvu_, mahdld_, from_, to_));
        }
        connection.close();
    }

    @FXML
    public void addNew(MouseEvent event) throws IOException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditEmployee.fxml"));
        Stage stage = new Stage();
        stage.setTitle("NEW EMPLOYEE");
        stage.setScene(new Scene(loader.load(), 300, 338));
        stage.setResizable(false);
        stage.setOnHidden(windowEvent -> {
            try{
                doRefresh();
            }catch(SQLException throwables){
                throwables.printStackTrace();
            }
        });
        EditEmployeeController controller = loader.getController();
        controller.setAdding(true);
        stage.show();
    }

    @FXML
    public void editTable(MouseEvent event) throws IOException{
        if(infoTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        if(event.getButton() == MouseButton.SECONDARY){
            ContextMenu contextMenu = infoTable.getContextMenu();
            contextMenu.show(infoTable, event.getSceneX(), event.getSceneY());
        }else{
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditEmployee.fxml"));
                Stage stage = new Stage();
                stage.setTitle("NEW EMPLOYEE");
                stage.setScene(new Scene(loader.load(), 300, 338));
                stage.setResizable(false);
                EditEmployeeController controller = loader.getController();
                controller.setPerson(infoTable.getSelectionModel().getSelectedItem());
                stage.setOnHidden(windowEvent -> {
                    try{
                        doRefresh();
                    }catch(SQLException throwables){
                        throwables.printStackTrace();
                    }
                });
                stage.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        //
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        hoten.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        trinhdo.setCellValueFactory(new PropertyValueFactory<>("trinhdo"));
        chucvu.setCellValueFactory(new PropertyValueFactory<>("chucvu"));
        mahdld.setCellValueFactory(new PropertyValueFactory<>("mahdld"));
        from.setCellValueFactory(new PropertyValueFactory<>("from"));
        to.setCellValueFactory(new PropertyValueFactory<>("to"));
        //
        try{
            doRefresh();
        }catch(Exception e){
            e.printStackTrace();
        }
        //
        MenuItem menuItem = new MenuItem("Xoá");
        menuItem.setOnAction(event -> {
            try{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION");
                alert.setHeaderText("DELETE RECORD ?");
                alert.setContentText("");
                Optional<ButtonType> opt = alert.showAndWait();
                if(opt.isPresent() && opt.get() == ButtonType.OK){
                    Person temp = infoTable.getSelectionModel().getSelectedItem();
                    Connection connection = postgresql.getConnection();
                    Statement stmt = connection
                            .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stmt.executeUpdate(
                            "DELETE FROM public.\"HDLD\"\n" + "WHERE mahdld like '%" + temp.getMahdld() + "%';");
                    connection.close();
                    //
                    doRefresh();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        MenuItem menuItem1 = new MenuItem("Thông tin");
        menuItem1.setOnAction(event -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ExtraInfo.fxml"));
                Stage stage = new Stage();
                stage.setTitle("");
                stage.setScene(new Scene(loader.load()));
                stage.setResizable(false);
                ExtraInfoController controller = loader.getController();
                Person person = infoTable.getSelectionModel().getSelectedItem();
                controller.setExtraInfo(person, false);
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(menuItem1, menuItem);
        infoTable.setContextMenu(contextMenu);
    }

}
