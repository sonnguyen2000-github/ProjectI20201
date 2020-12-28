package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.ChungChi;
import main.LichSu;
import main.PostgresqlConn;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AttachmentController implements Initializable{
    protected String id;
    protected ChungChi chungChi;
    protected LichSu lichSu;
    protected PostgresqlConn postgresqlConn;

    @FXML
    TextField fileDirectory;
    @FXML
    Button confirmBtn, clearBtn, cancelBtn;

    /**
     * query should be here
     */
    @FXML
    public void confirm(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        if(!fileDirectory.getText().isEmpty()){
            String prove = fileDirectory.getText();
            if(this.chungChi != null){
                try{
                    String ten = chungChi.getTencc();
                    Connection connection = postgresqlConn.getConnection();
                    Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    stmt.executeUpdate(
                            "UPDATE public.\"BangCap\"\n" + "SET minhchung = '" + prove + "'\n" + "WHERE \"ID\" like '%" + id + "%' and ten like '%" + ten + "%';");
                    connection.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                if(this.lichSu != null){
                    try{
                        String tenda = lichSu.getTenda();
                        String vitri = lichSu.getVitri();
                        Connection connection = postgresqlConn.getConnection();
                        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        stmt.executeUpdate(
                                "UPDATE public.\"LichSuCongTac\"\n" + "SET minhchung = '" + prove + "'\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND tenduan like '%" + tenda + "%'\n" + "AND vitri like '%" + vitri + "%';");
                        connection.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        cancel(event);
    }

    @FXML
    public void clear(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        fileDirectory.setText("");
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
    public void chooseFile(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("E:\\Document in E\\"));
            File file = chooser.showOpenDialog(confirmBtn.getScene().getWindow());
            if(file != null){
                fileDirectory.appendText(file.getName() + "; ");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresqlConn = new PostgresqlConn();
    }

    public void setProve(String person, ChungChi chungChi){
        this.id = person;
        this.chungChi = chungChi;
        //
        String ten = chungChi.getTencc();
        try{
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(
                    "SELECT minhchung\n" + "FROM public.\"BangCap\"\n" + "WHERE \"ID\" like '%" + id + "%' and ten like '%" + ten + "%';");
            if(rs.next()){
                fileDirectory.setText(rs.getString(1));
            }
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setProve(String person, LichSu lichSu){
        this.id = person;
        this.lichSu = lichSu;
        //
        String tenda = lichSu.getTenda();
        String vitri = lichSu.getVitri();
        //
        try{
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(
                    "SELECT minhchung\n" + "FROM public.\"LichSuCongTac\"\n" + "WHERE \"ID\" like '%" + id + "%'\n" + "AND tenduan like '%" + tenda + "%'\n" + "AND vitri like '%" + vitri + "%';");
            if(rs.next()){
                fileDirectory.setText(rs.getString(1));
            }
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
