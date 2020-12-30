package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Forms11Controller implements Initializable{
    @FXML
    Button cancelBtn;
    @FXML
    Tab a11Tab, b11Tab, c11Tab;
    @FXML
    SubScene a11Sub, b11Sub, c11Sub;

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void setResult(ObservableList<SearchViewController.SearchField> result) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/A11.fxml"));
        a11Sub.setRoot(loader.load());
        A11Controller a11Controller = loader.getController();
        a11Controller.setResult(result);
        /**/
        loader = new FXMLLoader(getClass().getResource("/fxml/B11.fxml"));
        b11Sub.setRoot(loader.load());
        B11Controller b11Controller = loader.getController();
        b11Controller.setResult(result);
        /**/
        loader = new FXMLLoader(getClass().getResource("/fxml/C11.fxml"));
        c11Sub.setRoot(loader.load());
        C11Controller c11Controller = loader.getController();
        c11Controller.setResult(result);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
}
