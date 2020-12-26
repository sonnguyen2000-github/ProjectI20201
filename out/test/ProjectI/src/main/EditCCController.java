package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditCCController implements Initializable{
    protected String id;
    protected ChungChi chungChi;
    protected PostgresqlConn postgresql;
    protected boolean addNew;
    //
    @FXML
    TextField tencc, mucdo, cosocap, cn, cm, nam, hsd;
    @FXML
    CheckBox totnghiep;
    @FXML
    Button saveBtn, clearBtn, attachBtn, alterBtn, cancelBtn;

    //
    @FXML
    public void clear(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        //
        tencc.setText("");
        totnghiep.setSelected(false);
        cosocap.setText("");
        cn.setText("");
        cm.setText("");
        nam.setText("");
        hsd.setText("");
        //
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        if(!tencc.getText().isEmpty() && !cosocap.getText().isEmpty() && !cn.getText().isEmpty() && !nam.getText().isEmpty()){
            try{
                String tencc_old = addNew ? "" : chungChi.getTencc();
                String ten = tencc.getText();
                String mucdo_ = mucdo.getText();
                boolean tn = totnghiep.isSelected();
                String csc = cosocap.getText();
                String cncc = cn.getText();
                String cmcc = cm.getText();
                int namcap = Integer.parseInt(nam.getText());
                int hethan = hsd.getText().isEmpty() ? 0 : Integer.parseInt(hsd.getText());
                //
                Connection connection = postgresql.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(
                        "SELECT \"ID\", ten, totnghiep, cosocap, chuyennganh, chuyenmon, minhchung, nam, hsd, level\n" + "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND ten like '%" + tencc_old + "%';");
                if(addNew){
                    rs.moveToInsertRow();
                    rs.updateString(1, id);
                }else{
                    rs.next();
                }
                rs.updateString(2, ten);
                rs.updateBoolean(3, tn);
                rs.updateString(4, csc);
                rs.updateString(5, cncc);
                rs.updateString(6, cmcc);
                rs.updateInt(8, namcap);
                rs.updateInt(9, hethan);
                rs.updateString(10, mucdo_);
                //
                if(addNew){
                    rs.updateString(7, "");
                    rs.insertRow();
                    //
                    ChungChi temp = new ChungChi(ten, mucdo_, csc, "", cncc, cmcc, namcap, hethan);
                    this.setCC(id, temp);
                    this.setAddNew(false);
                }else{
                    rs.updateRow();
                }
                //
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("");
                alert.setContentText("");
                alert.showAndWait();
            }finally{
                cancel(event);
            }
        }
    }

    @FXML
    public void attach(MouseEvent event) throws IOException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Attachment.fxml"));
        Stage stage = new Stage();
        stage.setTitle("ATTACH");
        stage.setScene(new Scene(loader.load(), 600, 252));
        stage.setResizable(false);
        AttachmentController attachment = loader.getController();
        attachment.setProve(id, chungChi);
        stage.show();
    }

    @FXML
    public void alter(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        //
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AlterInfo.fxml"));
            Stage stage = new Stage();
            stage.setTitle("BỔ SUNG");
            stage.setScene(new Scene(loader.load(), 260, 270));
            stage.setResizable(false);
            AlterInfoController controller = loader.getController();
            controller.setPerson(id, chungChi);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setAddNew(boolean addNew){
        this.addNew = addNew;
        this.attachBtn.setDisable(addNew);
    }

    public void setPerson(String id){
        this.id = id;
    }

    public void setCC(String person, ChungChi chungChi) throws SQLException{
        this.id = person;
        this.chungChi = chungChi;
        //
        Connection connection = postgresql.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(
                "SELECT \"ID\", ten, totnghiep, cosocap, chuyennganh, chuyenmon, minhchung, nam, hsd, level\n" + "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND ten like '%" + chungChi.getTencc() + "%';");
        if(rs.next()){
            tencc.setText(rs.getString(2));
            totnghiep.setSelected(rs.getBoolean(3));
            mucdo.setText(rs.getString(10));
            cosocap.setText(rs.getString(4));
            cn.setText(rs.getString(5));
            cm.setText(rs.getString(6));
            nam.setText(rs.getInt(8) + "");
            hsd.setText(rs.getInt(9) + "");
        }
        connection.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.postgresql = new PostgresqlConn();
    }
}
