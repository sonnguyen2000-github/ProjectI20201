package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.PostgresqlConn;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MessageController implements Initializable{
    protected PostgresqlConn postgresql;
    protected boolean admin;
    protected Map<String, String> messageMap;

    @FXML
    ListView<String> messages;
    @FXML
    Button okBtn;
    @FXML
    Button addBtn;
    @FXML
    TextArea messageText;

    @FXML
    public void nothing(MouseEvent event){
        /*
         * prevent textarea from un-purposed clicked
         * */
        if(event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY){
            return;
        }
    }

    @FXML
    public void add(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        okBtn.setDisable(false);
        messageText.setEditable(true);
        messageText.setText("");
    }

    @FXML
    public void ok(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        String info = messageText.getText();
        if(info.isEmpty()){
            return;
        }
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT matn, tinnhan\n" + "FROM public.\"Tinnhan\";");
            rs.moveToInsertRow();
            Calendar cld = Calendar.getInstance();
            String id = cld.get(Calendar.YEAR) + "" + cld.get(Calendar.MONTH) + "" + cld.get(Calendar.DAY_OF_MONTH) +
                        "" + cld.get(Calendar.HOUR_OF_DAY) + "" + cld.get(Calendar.MINUTE) + "" +
                        cld.get(Calendar.SECOND);
            rs.updateString(1, id);
            rs.updateString(2, info);
            rs.insertRow();
            //
            connection.close();
            //
            messages.getItems().add(id);
            messageMap.put(id, info);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            okBtn.setDisable(true);
            messageText.setEditable(false);
        }
    }

    @FXML
    public void edit(MouseEvent event){
        if(messages.getItems().isEmpty()){
            return;
        }
        String id = messages.getSelectionModel().getSelectedItem();
        if(id.isEmpty()){
            return;
        }
        if(event.getButton() == MouseButton.SECONDARY){
            if(admin){
                delete(id);
            }
        }else{
            if(event.getButton() == MouseButton.PRIMARY){
                read(id);
            }
        }
    }

    public void delete(String id){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("DELETE MESSAGE?");
        alert.setContentText("");
        Optional<ButtonType> rs = alert.showAndWait();
        if(rs.isPresent() && rs.get() == ButtonType.OK){
            try{
                Connection connection = postgresql.getConnection();
                Statement stmt = connection
                        .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stmt.executeUpdate("DELETE FROM public.\"Tinnhan\"\n" + "WHERE matn like '%" + id + "%';");
                //
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                messages.getItems().remove(id);
                messageMap.remove(id);
                //
                messageText.setText("");
            }
        }
    }

    public void read(String id){
        String info = messageMap.get(id);
        messageText.setText(info);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        setAdmin(false);
        //
        ObservableList<String> messageId = FXCollections.observableArrayList();
        messages.setItems(messageId);
        messageMap = new LinkedHashMap<>();
        //
        try{
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT matn, tinnhan\n" + "FROM public.\"Tinnhan\";");
            String id;
            while(rs.next()){
                id = rs.getString(1);
                messageMap.put(id, rs.getString(2));
                messageId.add(id);
            }
            connection.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public void setAdmin(boolean admin){
        this.admin = admin;
        addBtn.setDisable(!admin);
    }
}
