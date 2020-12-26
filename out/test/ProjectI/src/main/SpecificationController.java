package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class SpecificationController implements Initializable{
    @FXML
    TextField groupField;
    @FXML
    TextField nameField;
    @FXML
    TextField specificField;
    @FXML
    Button newBtn;
    @FXML
    Button saveBtn;
    @FXML
    Button preBtn;
    @FXML
    Button nextBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Button cancelBtn;
    private PostgresqlConn postgresql;
    private Connection connection;
    private Statement stmt;
    private ResultSet rs;
    private boolean adding;

    @FXML
    public void add(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        adding = true;
        groupField.setText("");
        nameField.setText("");
        specificField.setText("");
        //
        deleteBtn.setDisable(true);
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        String group = groupField.getText();
        String name = nameField.getText();
        String specific = specificField.getText();
        if(group.isBlank() && name.isBlank() && specific.isBlank()){
            return;
        }
        try{
            if(adding){
                rs.moveToInsertRow();
            }
            rs.updateString(1, group);
            rs.updateString(2, name);
            rs.updateString(3, specific);
            if(adding){
                rs.insertRow();
            }else{
                rs.updateRow();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            adding = false;
            try{
                rs.last();
                set();
            }catch(SQLException throwables){
                throwables.printStackTrace();
            }
            deleteBtn.setDisable(false);
        }
    }

    public void set() throws SQLException{
        rs.moveToCurrentRow();
        if(rs.getString(1) == null){
            groupField.setText("");
            nameField.setText("");
            specificField.setText("");
            return;
        }
        groupField.setText(rs.getString(1));
        nameField.setText(rs.getString(2));
        specificField.setText(rs.getString(3));
    }

    @FXML
    public void pre(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            if(!rs.isFirst()){
                rs.previous();
                set();
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @FXML
    public void next(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            if(!rs.isLast()){
                rs.next();
                set();
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @FXML
    public void delete(MouseEvent event) throws SQLException{
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE CONFIRMATION");
        alert.setHeaderText("DELETE RECORD ?");
        alert.setContentText("");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent() && option.get().equals(ButtonType.OK)){
            rs.deleteRow();
            rs.last();
            set();
        }
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            rs.first();
            set();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }finally{
            if(rs != null){
                deleteBtn.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        try{
            init("", "", "");
            if(rs.first()){
                set();
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void init(String group, String name, String specific) throws SQLException{
        connection = postgresql.getConnection();
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String query = "SELECT distinct nhom, chuyennganh, chuyenmon\n" + "FROM public.\"ChuyenMon\"\n" +
                       "WHERE nhom like '%" + group + "%' and chuyennganh like '%" + name + "%' and chuyenmon like '%" +
                       specific + "%';";
        rs = stmt.executeQuery(query);
        ObservableList<String> groupList = FXCollections.observableArrayList();
        ObservableList<String> nameList = FXCollections.observableArrayList();
        String group_, name_;
        while(rs.next()){
            group_ = rs.getString(1);
            name_ = rs.getString(2);
            if(!groupList.contains(group_)){
                groupList.add(group_);
            }
            if(!nameList.contains(name_)){
                nameList.add(name_);
            }
        }
        TextFields.bindAutoCompletion(groupField, groupList);
        TextFields.bindAutoCompletion(nameField, nameList);
    }
}
