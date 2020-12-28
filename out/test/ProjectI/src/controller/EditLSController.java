package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.LichSu;
import main.PostgresqlConn;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditLSController implements Initializable{
    protected String id;
    protected LichSu lichSu;
    protected PostgresqlConn postgresql;
    protected boolean addNew;
    //
    @FXML
    TextField tenda, congty, vitrict, cnda, cmda, from, to;
    @FXML
    Button clearBtn, saveBtn, alterBtn, cancelBtn, attachBtn;

    @FXML
    public void clear(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        tenda.setText("");
        congty.setText("");
        vitrict.setText("");
        cnda.setText("");
        cnda.setText("");
        cmda.setText("");
        from.setText("");
        to.setText("");
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
            controller.setPerson(id, lichSu);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        // more is needed
        if(!tenda.getText().isEmpty() && !vitrict.getText().isEmpty() && !cnda.getText().isEmpty() &&
           !cmda.getText().isEmpty() && !from.getText().isEmpty() && !to.getText().isEmpty()){
            try{
                String tenda_old = addNew ? "" : lichSu.getTenda();
                String vitrict_old = addNew ? "" : lichSu.getVitri();
                //
                String tenda_new = tenda.getText();
                String ct = congty.getText();
                String vitrict_new = vitrict.getText();
                String cn = cnda.getText();
                String cm = cmda.getText();
                int ctfrom = Integer.parseInt(from.getText());
                int ctto = Integer.parseInt(to.getText());
                //
                Connection connection = postgresql.getConnection();
                Statement stmt = connection
                        .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery(
                        "SELECT \"ID\", tenduan, chuyennganh, \"from\", congty, chuyenmon, vitri, \"to\", minhchung\n" +
                        "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id + "%' \n" +
                        "AND tenduan like '%" + tenda_old + "%'\n" + "AND vitri like '%" + vitrict_old + "%';");
                if(addNew){
                    rs.moveToInsertRow();
                }else{
                    rs.next();
                }
                rs.updateString(1, id);
                rs.updateString(2, tenda_new);
                rs.updateString(5, ct);
                rs.updateString(7, vitrict_new);
                rs.updateString(3, cn);
                rs.updateString(6, cm);
                rs.updateInt(4, ctfrom);
                rs.updateInt(8, ctto);
                if(addNew){
                    rs.updateString(9, "");
                    rs.insertRow();
                    //
                    LichSu temp = new LichSu(tenda_new, vitrict_new, cn, cm, ctfrom, ctto);
                    this.setLS(id, temp);
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

    public void setLS(String person, LichSu lichSu) throws SQLException{
        this.id = person;
        this.lichSu = lichSu;
        // more is needed
        Connection connection = postgresql.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery(
                "SELECT \"ID\", tenduan, chuyennganh, \"from\", congty, chuyenmon, vitri, \"to\", minhchung\n" +
                "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id + "%' \n" + "AND tenduan like '%" +
                lichSu.getTenda() + "%'\n" + "AND vitri like '%" + lichSu.getVitri() + "%';");
        if(rs.next()){
            tenda.setText(rs.getString(2));
            congty.setText(rs.getString(5));
            vitrict.setText(rs.getString(7));
            cnda.setText(rs.getString(3));
            cmda.setText(rs.getString(6));
            from.setText(rs.getInt(4) + "");
            to.setText(rs.getInt(8) + "");
        }
        connection.close();
    }

    public void setAddNew(boolean addNew){
        this.addNew = addNew;
        this.attachBtn.setDisable(addNew);
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
        attachment.setProve(id, lichSu);
        stage.show();
    }

    public void setPerson(String id){
        this.id = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.postgresql = new PostgresqlConn();
    }
}
