package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditEmployeeController implements Initializable{
    protected PostgresqlConn postgresql;
    protected Person person;
    protected boolean adding;
    //
    @FXML
    TextField manv, mahdld, hoten, trinhdo, chucvu;
    @FXML
    DatePicker from, to;
    @FXML
    Button clearBtn;
    @FXML
    Button saveBtn;
    @FXML
    Button cancelBtn;
    //

    @FXML
    public void clear(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        manv.setText("");
        mahdld.setText("");
        hoten.setText("");
        trinhdo.setText("");
        chucvu.setText("");
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        try{
            String id = manv.getText();
            String hdld = mahdld.getText();
            String hoten_new = hoten.getText();
            String trinhdo_new = trinhdo.getText();
            String chucvu_new = chucvu.getText();
            LocalDate from_ = from.getValue();
            LocalDate to_ = to.getValue();
            //
            String mahdld_old = adding ? "" : person.getMahdld();
            //
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(
                    "SELECT mahdld, \"from\", \"to\"\n" + "FROM public.\"HDLD\"\n" + "WHERE mahdld like '%" + mahdld_old + "%';");
            if(adding){
                rs.moveToInsertRow();
            }else{
                rs.next();
            }
            rs.updateString(1, hdld);
            rs.updateDate(2, Date.valueOf(from_));
            rs.updateDate(3, Date.valueOf(to_));
            if(adding){
                rs.insertRow();
            }else{
                rs.updateRow();
            }
            //
            rs = stmt.executeQuery(
                    "SELECT \"ID\", trinhdo, chucvu, mahdld, hoten\n" + "FROM public.\"EmployeeInformation\"\n" + "WHERE mahdld like '%" + mahdld_old + "%';");
            if(adding){
                rs.moveToInsertRow();
            }else{
                rs.next();
            }
            rs.updateString(1, id);
            rs.updateString(2, trinhdo_new);
            rs.updateString(3, chucvu_new);
            rs.updateString(4, hdld);
            rs.updateString(5, hoten_new);
            if(adding){
                rs.insertRow();
                this.setPerson(new Person(id, hoten_new, trinhdo_new, chucvu_new, hdld, Date.valueOf(from_),
                        Date.valueOf(to_)));
                this.setAdding(false);
            }else{
                rs.updateRow();
            }
            //
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    public void setAdding(boolean adding){
        this.adding = adding;
        manv.setDisable(!adding);
        mahdld.setDisable(!adding);
    }

    public void setPerson(Person person){
        this.person = person;
        /*
         * needed more
         * */
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(
                    "SELECT \"ID\",hoten, trinhdo, chucvu, p1.mahdld, \"from\", \"to\"\n" + "FROM public.\"EmployeeInformation\" p1, public.\"HDLD\" p2\n" + "WHERE p1.mahdld = p2.mahdld AND p1.mahdld like '%" + person.getMahdld() + "%';");
            if(rs.next()){
                manv.setText(rs.getString(1));
                hoten.setText(rs.getString(2));
                trinhdo.setText(rs.getString(3));
                chucvu.setText(rs.getString(4));
                mahdld.setText(rs.getString(5));
                from.setValue(LocalDate.parse(rs.getDate(6).toString()));
                to.setValue(LocalDate.parse(rs.getDate(7).toString()));
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
        from.setValue(LocalDate.now());
        to.setValue(LocalDate.now());
        //
        setAdding(false);
    }
    //

}
