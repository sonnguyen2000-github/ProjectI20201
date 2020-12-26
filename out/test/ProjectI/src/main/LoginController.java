package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    @FXML
    Label timeLabel;
    @FXML
    Button signInBtn, cancelBtn;
    @FXML
    TextField user;
    @FXML
    PasswordField password;

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            return;
        }
        close();
        Platform.exit();
        System.exit(-1);/**/
    }

    public void close(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
        //
    }

    @FXML
    public void signIn(InputEvent event) throws IOException{
        if(event instanceof MouseEvent){
            MouseEvent mouseEvent = (MouseEvent) event;
            if(mouseEvent.getButton() == MouseButton.SECONDARY){
                return;
            }
        }else{
            if(event instanceof KeyEvent){
                KeyEvent keyEvent = (KeyEvent) event;
                if(keyEvent.getCode() != KeyCode.ENTER){
                    return;
                }
            }
        }
        if(user.getText().isEmpty()){
            alert("Username is empty");
            return;
        }
        //
        System.out.println("User: " + user.getText() + " loading...");
        if(user.getText().equalsIgnoreCase("ADMIN")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ForAdmin.fxml"));
            Stage stage = new Stage();
            stage.setTitle("ADMINISTRATOR");
            Scene forAdminScene = new Scene(loader.load());
            stage.setScene(forAdminScene);
            stage.setResizable(false);
            stage.show();
        }else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ForEmployee.fxml"));
            Scene forEmployeeScene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("USER: " + user.getText());
            stage.setResizable(false);
            stage.setScene(forEmployeeScene);
            //
            ForEmployeeController controller = loader.getController();
            controller.setPerson(user.getText());
            stage.show();
        }
        //
        System.out.println("User: " + user.getText() + " loaded");
        this.close();
    }

    public void alert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText(msg);
        alert.setContentText("");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Thread timeThread = new Thread(() -> {
            while(true){
                SimpleDateFormat formater = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
                Platform.runLater(() -> timeLabel.setText(formater.format(new Date())));
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
        //
    }
}
