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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AlterInfoController implements Initializable{
    protected PostgresqlConn postgresql;
    protected String id;
    protected ChungChi chungChi;
    protected LichSu lichSu;
    protected boolean isChungChi;

    @FXML
    ComboBox<String> headers;
    @FXML
    TextArea infoText;
    @FXML
    Button saveBtn, clearBtn, cancelBtn;

    @FXML
    public void choose(){
        String header = headers.getValue();
        if(header == null || header.isEmpty()){
            return;
        }
        //
        String query = isChungChi ? "SELECT \"" + header + "\"\n" + "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND ten like '%" + chungChi.getTencc() + "%';" : "SELECT \"" + header + "\"\n" + "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND tenduan like '%" + lichSu.getTenda() + "%'\n" + "AND vitri like '%" + lichSu.getVitri() + "%';";
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                infoText.setText(rs.getString(1));
            }
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void clear(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        infoText.clear();
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        String update_ = infoText.getText();
        String header = headers.getValue();
        if(header == null || header.isEmpty() || header.isBlank()){
            return;
        }
        if(update_.isBlank() || update_.isEmpty()){
            return;
        }
        //
        String query = isChungChi ? "SELECT \"" + header + "\", \"ID\", ten\n" + "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND ten like '%" + chungChi.getTencc() + "%';" : "SELECT \"" + header + "\", \"ID\", tenduan, vitri\n" + "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND tenduan like '%" + lichSu.getTenda() + "%'\n" + "AND vitri like '%" + lichSu.getVitri() + "%';";
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                rs.updateString(1, update_);
                rs.updateRow();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void setIsChungChi(boolean isChungChi) throws SQLException{
        this.isChungChi = isChungChi;
        //
        String table = isChungChi ? "BangCap" : "LichSuCongTac";
        int except = isChungChi ? 10 : 9;
        String query = "SELECT COLUMN_NAME \n" + "FROM INFORMATION_SCHEMA.COLUMNS\n" + "WHERE TABLE_NAME = '" + table + "'\n" + "AND COLUMN_NAME not in (SELECT COLUMN_NAME \n" + "\t\t\t\t\t\tFROM INFORMATION_SCHEMA.COLUMNS\n" + "\t\t\t\t\t\tWHERE TABLE_NAME = '" + table + "'\n" + "\t\t\t\t\t  \tORDER BY ORDINAL_POSITION ASC\n" + "\t\t\t\t\t    LIMIT " + except + ")\n" + "ORDER BY ORDINAL_POSITION DESC";
        //
        Connection connection = postgresql.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            headers.getItems().add(rs.getString(1));
        }
        connection.close();
    }

    public void setPerson(String id, Object sub){
        this.id = id;
        //
        if(sub instanceof ChungChi){
            this.chungChi = (ChungChi) sub;
            //
            try{
                setIsChungChi(true);
            }catch(SQLException throwables){
                throwables.printStackTrace();
            }
        }else{
            if(sub instanceof LichSu){
                this.lichSu = (LichSu) sub;
                //
                try{
                    setIsChungChi(false);
                }catch(SQLException throwables){
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        //
        ObservableList<String> headerList = FXCollections.observableArrayList();
        headers.setItems(headerList);
        //
    }
}
