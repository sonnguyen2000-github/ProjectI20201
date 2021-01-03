package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class A11Controller implements Initializable{
    @FXML
    TableView<A11> a11Table;
    @FXML
    TableColumn<A11, Integer> sttCol;
    @FXML
    TableColumn<A11, String> hotenCol;
    @FXML
    TableColumn<A11, String> vitriCol;

    public void setResult(ObservableList<SearchViewController.SearchField> result){
        int stt_ = 0;
        for(SearchViewController.SearchField searchField : result){
            for(Person person : searchField.getResults()){
                A11 a11 = new A11();
                a11.setStt(++stt_);
                a11.setHoten(person.getHoten());
                a11.setVitri(searchField.getVitri());

                a11Table.getItems().add(a11);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        a11Table.setItems(FXCollections.observableArrayList());

        sttCol.setCellValueFactory(new PropertyValueFactory<>("stt"));
        hotenCol.setCellValueFactory(new PropertyValueFactory<>("hoten"));
        vitriCol.setCellValueFactory(new PropertyValueFactory<>("vitri"));
    }

    public static class A11{
        private int stt;
        private String hoten;
        private String vitri;

        public A11(){
        }

        public int getStt(){
            return stt;
        }

        public void setStt(int stt){
            this.stt = stt;
        }

        public String getHoten(){
            return hoten;
        }

        public void setHoten(String hoten){
            this.hoten = hoten;
        }

        public String getVitri(){
            return vitri;
        }

        public void setVitri(String vitri){
            this.vitri = vitri;
        }
    }
}
