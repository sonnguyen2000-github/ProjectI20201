package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Person;

import java.util.Optional;

public class ResultController{
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
        this.resultList.setItems(resultList);
    }
}
