package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.PostgresqlConn;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AttachSpecsController implements Initializable{
    protected PostgresqlConn postgresql;
    @FXML
    TextField cnTxt;
    @FXML
    TextField cmTxt;
    @FXML
    Button saveBtn;
    @FXML
    Button cancelBtn;
    private String cn, cm;

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        String cn_ = cnTxt.getText();
        String cm_ = cmTxt.getText();
        setSpecs(cn_, cm_);
        cancel(event);
    }

    public void setSpecs(String cn, String cm){
        this.cn = cn;
        this.cm = cm;

        cnTxt.setText(cn);
        cmTxt.setText(cm);
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public Pair<String, String> getSpecs(){
        return new Pair<>(this.cn, this.cm);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String query = "SELECT distinct chuyennganh, chuyenmon\n" + "FROM public.\"ChuyenMon\"\n" +
                           "WHERE nhom like '%%' and chuyennganh like '%%' and chuyenmon like '%%';";
            ResultSet rs = stmt.executeQuery(query);
            ObservableList<String> cnList = FXCollections.observableArrayList();
            ObservableList<String> cmList = FXCollections.observableArrayList();
            String cn_, cm_;
            while(rs.next()){
                cn_ = rs.getString(1);
                cm_ = rs.getString(2);
                if(!cnList.contains(cn_)){
                    cnList.add(cn_);
                }
                if(!cmList.contains(cm_)){
                    cmList.add(cm_);
                }
            }
            TextFields.bindAutoCompletion(cnTxt, cnList);
            TextFields.bindAutoCompletion(cmTxt, cmList);
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
