package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Person;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ResultController implements Initializable{
    @FXML
    Button cancelBtn;
    @FXML
    ListView<Person> resultList;

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void editList(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            if(resultList.getSelectionModel().getSelectedItem() == null){
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("DELETE RECORD ?");
            alert.setContentText("");
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.isPresent() && optional.get() == ButtonType.OK){
                Person person = resultList.getSelectionModel().getSelectedItem();
                resultList.getItems().remove(person);
            }
        }
    }

    public void setResultList(ObservableList<Person> resultList){
        this.resultList.getItems().clear();
        this.resultList.getItems().addAll(resultList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        resultList.setItems(FXCollections.observableArrayList());
    }
}
