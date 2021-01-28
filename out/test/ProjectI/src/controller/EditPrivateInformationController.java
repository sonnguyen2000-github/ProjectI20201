package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.PostgresqlConn;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EditPrivateInformationController implements Initializable{
    protected String id;
    protected PostgresqlConn postgresql;

    //
    @FXML
    TextField addressField, originField;
    @FXML
    ChoiceBox<String> genderField;
    @FXML
    Button clearBtn, cancelBtn, saveBtn;
    @FXML
    DatePicker birthdayPicker;
    //

    @FXML
    public void clear(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        addressField.setText("");
        originField.setText("");
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        setPerson(id);
    }

    @FXML
    public void save(MouseEvent event) throws SQLException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        /*
         * more is needed
         * */
        boolean empty = false;
        LocalDate birthday = birthdayPicker.getValue();
        String sex = genderField.getValue();
        String address = addressField.getText();
        String origin = originField.getText();
        //
        Connection connection = postgresql.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(
                "SELECT \"ID\", ngaysinh, diachi, gioitinh, quequan\n" + "FROM public.\"PrivateEmployeeInformation\"\n" + "WHERE \"ID\" like '%" + id + "%';");
        if(!rs.next()){
            empty = true;
            //
            rs.moveToInsertRow();
            rs.updateString(1, id);
        }

        rs.updateDate(2, Date.valueOf(birthday));
        rs.updateString(4, sex + "");
        rs.updateString(3, address + "");
        rs.updateString(5, origin + "");
        //
        if(empty){
            rs.insertRow();
        }else{
            rs.updateRow();
        }
        connection.close();
    }

    public void setPerson(String id){
        this.id = id;
        //
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(
                    "SELECT \"ID\", ngaysinh, diachi, gioitinh, quequan\n" + "FROM public.\"PrivateEmployeeInformation\"\n" + "WHERE \"ID\" like '%" + id + "%';");
            if(rs.next()){
                birthdayPicker.setValue(LocalDate.parse(rs.getDate(2).toString()));
                genderField.setValue(rs.getString(4));
                addressField.setText(rs.getString(3));
                originField.setText(rs.getString(5));
            }
            connection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.postgresql = new PostgresqlConn();
        //
        String[] genders = {"Male", "Female", "Prefer not to say"};
        ObservableList<String> genderList = FXCollections.observableArrayList();
        genderList.addAll(Arrays.asList(genders));
        genderField.setItems(genderList);
        //
        birthdayPicker.setValue(LocalDate.parse("2000-01-01"));
        //
    }
}
