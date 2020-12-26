package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForEmployeeController implements Initializable{
    @FXML
    Label idLabel;
    @FXML
    SubScene subScene;
    @FXML
    Button privateInfoBtn;
    @FXML
    Button paperInfoBtn;
    @FXML
    Button msgBtn;
    @FXML
    Button logoutBtn;
    private String id;

    @FXML
    public void privateInfo(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditPrivateInformation.fxml"));
            subScene.setRoot(loader.load());
            EditPrivateInformationController controller = loader.getController();
            controller.setPerson(id);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void paperInfo(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EmployeePaper.fxml"));
            subScene.setRoot(loader.load());
            //
            EmployeePaperController controller = loader.getController();
            controller.setPerson(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void message(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Message.fxml"));
            subScene.setRoot(loader.load());
            MessageController controller = loader.getController();
            controller.setAdmin(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/Login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("LOGIN");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            //
            this.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            close();
        }
    }

    public void close(){
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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

    public void setPerson(String id){
        this.id = id;
        //
        idLabel.setText(idLabel.getText() + id);
    }
}
