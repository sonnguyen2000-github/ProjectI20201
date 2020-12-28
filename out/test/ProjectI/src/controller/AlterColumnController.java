package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.PostgresqlConn;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AlterColumnController implements Initializable{
    protected PostgresqlConn postgresql;
    protected boolean chungChi;

    @FXML
    TextField columnName;
    @FXML
    TextArea info;
    @FXML
    Button saveBtn;
    @FXML
    Button clearBtn;
    @FXML
    Button cancelBtn;

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        //
        String newName_ = columnName.getText();
        String info_ = info.getText() + "";
        if(newName_.isEmpty()){
            return;
        }
        String table = chungChi ? "BangCap" : "LichSuCongTac";
        String query1 = "ALTER TABLE public.\"" + table + "\"" + "\nADD COLUMN " + newName_ + " character varying;";
        String query2 = "UPDATE public.\"" + table + "\"" + "\nSET " + newName_ + " = '" + info_ + "';";
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate(query1);
            //
            stmt.executeUpdate(query2);
            //
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            cancel(event);
        }
    }

    @FXML
    public void clear(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        columnName.setText("");
        info.setText("");
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setChungChi(boolean chungChi){
        this.chungChi = chungChi;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        setChungChi(false);
    }
}
