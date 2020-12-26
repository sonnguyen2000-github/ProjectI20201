package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchAlterFrameController implements Initializable{
    private PostgresqlConn postgresql;
    private StringBuilder builder;
    @FXML
    Button okBtn, cancelBtn;
    @FXML
    ComboBox<String> alterCCBox;
    @FXML
    ComboBox<String> alterLSBox;
    @FXML
    TextArea infoCC, infoLS;

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        close();
    }

    @FXML
    public void ok(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        String alterCC = alterCCBox.getValue();
        String alterLS = alterLSBox.getValue();
        if((alterCC == null || alterCC.isEmpty()) && (alterLS == null || alterLS.isEmpty())){
            close();
        }
        //
        String infoCC_ = infoCC.getText() + "";
        String infoLS_ = infoLS.getText() + "";
        try{
            if(alterCC != null){
                builder.append("\nAND ");
                builder.append("\nupper(p1." + alterCC + ") ");
                builder.append("like upper('%" + infoCC_ + "%') ");
            }
            //
            if(alterLS != null){
                builder.append("\nAND ");
                builder.append("upper(p2." + alterLS + ") ");
                builder.append("like upper('%" + infoLS_ + "%') ");
            }
            //
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        builder = new StringBuilder();
        //
        ObservableList<String> ccList = FXCollections.observableArrayList();
        ObservableList<String> lsList = FXCollections.observableArrayList();
        alterCCBox.setItems(ccList);
        alterLSBox.setItems(lsList);
        //
        String query = "SELECT COLUMN_NAME \n" + "FROM INFORMATION_SCHEMA.COLUMNS\n" + "WHERE TABLE_NAME = ?\n" + "AND COLUMN_NAME not in (SELECT COLUMN_NAME \n" + "\t\t\t\t\t\tFROM INFORMATION_SCHEMA.COLUMNS\n" + "\t\t\t\t\t\tWHERE TABLE_NAME = ?\n" + "\t\t\t\t\t  \tORDER BY ORDINAL_POSITION ASC\n" + "\t\t\t\t\t    LIMIT ?)\n" + "ORDER BY ORDINAL_POSITION DESC";
        try{
            Connection connection = postgresql.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            //
            stmt.setString(1, "BangCap");
            stmt.setString(2, "BangCap");
            stmt.setInt(3, 10);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ccList.add(rs.getString(1));
            }
            //
            stmt.setString(1, "LichSuCongTac");
            stmt.setString(2, "LichSuCongTac");
            stmt.setInt(3, 9);
            rs = stmt.executeQuery();
            while(rs.next()){
                lsList.add(rs.getString(1));
            }
            //
            connection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        //
    }

    public StringBuilder getBuilder(){
        return builder;
    }

    public void close(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
