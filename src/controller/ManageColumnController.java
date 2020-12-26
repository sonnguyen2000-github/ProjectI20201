package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.PostgresqlConn;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageColumnController implements Initializable{
    protected PostgresqlConn postgresql;

    @FXML
    ListView<String> ccColumn;
    @FXML
    ListView<String> lsColumn;
    @FXML
    Button addBtn;


    @FXML
    public void delete(MouseEvent event){
        if(event.getButton() != MouseButton.SECONDARY){
            return;
        }/**/
        ListView<String> listView = (ListView<String>) event.getSource();
        String object = listView.getSelectionModel().getSelectedItem();
        if(object == null || object.isEmpty()){
            return;
        }
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("");
        dialog.setHeaderText("XOÁ THÔNG TIN ?");
        dialog.setContentText("");
        Optional<ButtonType> option;
        if((listView.equals(ccColumn) && listView.getSelectionModel().getSelectedIndex() >= 10) ||
           listView.equals(lsColumn) && listView.getSelectionModel().getSelectedIndex() >= 9){
            option = dialog.showAndWait();
            if(option.isPresent() && option.get() == ButtonType.OK){
                try{
                    String query = "ALTER TABLE public.\"BangCap\" DROP COLUMN " + object + ";";
                    Connection connection = postgresql.getConnection();
                    Statement stmt = connection
                            .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    if(listView.equals(ccColumn)){
                        stmt.executeUpdate(query);
                    }else{
                        if(listView.equals(lsColumn)){
                            query = "ALTER TABLE public.\"LichSuCongTac\" DROP COLUMN " + object + ";";
                            stmt.executeUpdate(query);
                        }
                    }
                    listView.getItems().remove(object);
                    connection.close();
                    refresh();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void refresh(){
        ObservableList<String> ccList = FXCollections.observableArrayList();
        ObservableList<String> lsList = FXCollections.observableArrayList();
        ccColumn.setItems(ccList);
        lsColumn.setItems(lsList);
        Map<String, String> ccColumn_Names = new LinkedHashMap<>();
        Map<String, String> lsColumn_Names = new LinkedHashMap<>();
        //
        ccColumn_Names.put("ID", "ID");
        ccColumn_Names.put("ten", "Tên chứng chỉ");
        ccColumn_Names.put("totnghiep", "Tốt nghiệp");
        ccColumn_Names.put("cosocap", "Cơ sỏ cấp chứng chỉ");
        ccColumn_Names.put("chuyennganh", "Chuyên ngành");
        ccColumn_Names.put("chuyenmon", "Chuyên môn");
        ccColumn_Names.put("minhchung", "Minh chứng");
        ccColumn_Names.put("nam", "Năm cấp");
        ccColumn_Names.put("hsd", "Hiệu lực đến");
        ccColumn_Names.put("level", "Mức độ");
        //
        lsColumn_Names.put("ID", "ID");
        lsColumn_Names.put("tenduan", "Tên dự án");
        lsColumn_Names.put("chuyennganh", "Chuyên ngành");
        lsColumn_Names.put("chuyenmon", "Chuyên môn");
        lsColumn_Names.put("congty", "Công ty");
        lsColumn_Names.put("vitri", "Vị trí");
        lsColumn_Names.put("from", "Bắt đầu từ");
        lsColumn_Names.put("to", "Thời gian kết thúc");
        lsColumn_Names.put("minhchung", "Minh chứng");
        //
        String query = "SELECT COLUMN_NAME \n" + "FROM INFORMATION_SCHEMA.COLUMNS\n" + "WHERE TABLE_NAME = ?\n" +
                       "AND COLUMN_NAME not in (SELECT COLUMN_NAME \n" +
                       "\t\t\t\t\t\tFROM INFORMATION_SCHEMA.COLUMNS\n" + "\t\t\t\t\t\tWHERE TABLE_NAME = ?\n" +
                       "\t\t\t\t\t  \tORDER BY ORDINAL_POSITION ASC\n" + "\t\t\t\t\t    LIMIT ?)\n" +
                       "ORDER BY ORDINAL_POSITION ASC";
        try{
            Connection connection = postgresql.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            //
            stmt.setString(1, "BangCap");
            stmt.setString(2, "BangCap");
            stmt.setInt(3, 0);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String temp = rs.getString(1);
                if(ccColumn_Names.containsKey(temp)){
                    ccList.add(ccColumn_Names.get(temp));
                }else{
                    ccList.add(temp);
                }
            }
            //
            stmt.setString(1, "LichSuCongTac");
            stmt.setString(2, "LichSuCongTac");
            stmt.setInt(3, 0);
            rs = stmt.executeQuery();
            while(rs.next()){
                String temp = rs.getString(1);
                if(lsColumn_Names.containsKey(temp)){
                    lsList.add(lsColumn_Names.get(temp));
                }else{
                    lsList.add(temp);
                }
            }
            //
            connection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @FXML
    public void add(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        //TO DO:more + refresh()
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("");
        alert.setHeaderText("Chứng chỉ hay công tác ?");
        alert.setContentText("");
        alert.getButtonTypes().clear();
        ButtonType ccOption = new ButtonType("Chứng chỉ");
        ButtonType lsOption = new ButtonType("Lịch sử");
        ButtonType cancel = new ButtonType("Thoát");
        alert.getButtonTypes().addAll(ccOption, lsOption, cancel);
        Optional<ButtonType> option = alert.showAndWait();
        //
        if(option.isPresent() && option.get() != cancel){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AlterColumn.fxml"));
                Stage stage = new Stage();
                stage.setTitle("");
                stage.setScene(new Scene(loader.load()));
                stage.setResizable(false);
                stage.setOnHidden(windowEvent -> refresh());
                AlterColumnController controller = loader.getController();
                boolean isChungChi = option.get().equals(ccOption);
                controller.setChungChi(isChungChi);
                stage.show();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        refresh();
    }
}
