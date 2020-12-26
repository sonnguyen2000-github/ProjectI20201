package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForAdminController implements Initializable{
    protected PostgresqlConn postgresql;

    @FXML
    ImageView icon;
    @FXML
    Button searchBtn, cancelBtn, infoBtn, msgBtn, mngBtn;
    @FXML
    Button specifcBtn;
    @FXML
    SubScene subScene;

    @FXML
    public void search(MouseEvent event) throws IOException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/SearchView.fxml"));
        subScene.setRoot(loader.load());
    }

    @FXML
    public void info(MouseEvent event) throws IOException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/InfoAll.fxml"));
        subScene.setRoot(loader.load());
    }

    @FXML
    public void manageColumn(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        //
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ManageColumn.fxml"));
            subScene.setRoot(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    public void cancel(MouseEvent event) throws IOException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        //
        Parent start = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
        Stage startStage = new Stage();
        startStage.setTitle("LOGIN");
        startStage.setScene(new Scene(start));
        startStage.setResizable(false);
        startStage.show();
        //
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void specific(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Specification.fxml"));
            subScene.setRoot(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void message(MouseEvent event) throws IOException{
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Message.fxml"));
        subScene.setRoot(loader.load());
        MessageController controller = loader.getController();
        controller.setAdmin(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        postgresql = new PostgresqlConn();
        try{
            setSubScene();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setSubScene() throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("../fxml/Welcome.fxml"));
        subScene.setRoot(parent);
    }
}
