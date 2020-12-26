package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.ChungChi;
import main.Person;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchViewController implements Initializable{
    @FXML
    Button searchBtn;
    @FXML
    Button addBtn;
    @FXML
    Button saveBtn;
    @FXML
    TableView<SearchField> tableView;
    @FXML
    TableColumn<SearchField, String> vitri;
    @FXML
    TableColumn<SearchField, Integer> stt;
    @FXML
    TableColumn<SearchField, Integer> kn;
    @FXML
    TableColumn<SearchField, Integer> kntt;
    @FXML
    TableColumn<SearchField, Integer> sl;
    @FXML
    TableColumn<SearchField, String> chuyenmon;
    @FXML
    TextArea vitri_txt;
    @FXML
    TextArea sl_txt;
    @FXML
    TextArea kn_txt;
    @FXML
    TextArea kntt_txt;
    @FXML
    ListView<Object> chuyenmon_list;
    private int stt_;

    @FXML
    public void add(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        SearchField temp = new SearchField();
        temp.setStt(++stt_);
        temp.setVitri("");
        temp.setSl(0);
        temp.setChuyenmon(FXCollections.observableArrayList());
        temp.setKn(0);
        temp.setKntt(0);
        tableView.getItems().add(temp);
        tableView.getSelectionModel().select(temp);
        setTxtArea(temp);
    }

    public void setTxtArea(SearchField temp){
        vitri_txt.setText(temp.getVitri());
        sl_txt.setText(temp.getSl() + "");
        chuyenmon_list.getItems().clear();
        chuyenmon_list.getItems().addAll(temp.getChuyenmon());
        kn_txt.setText(temp.getKn() + "");
        kntt_txt.setText(temp.getKntt() + "");
    }

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        SearchField temp = tableView.getSelectionModel().getSelectedItem();
        if(temp == null){
            return;
        }
        try{
            int sl_ = Integer.parseInt(sl_txt.getText());
            String vitri_ = vitri_txt.getText();
            ObservableList<Object> chuyenmon_ = chuyenmon_list.getItems();
            int kn_ = Integer.parseInt(kn_txt.getText());
            int kntt_ = Integer.parseInt(kntt_txt.getText());

            temp.setSl(sl_);
            temp.setVitri(vitri_);
            temp.getChuyenmon().clear();
            temp.getChuyenmon().addAll(chuyenmon_);
            temp.setKn(kn_);
            temp.setKntt(kntt_);

            tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), temp);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void search(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        //more
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SearchFrame.fxml"));
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            SearchFrameController controller = loader.getController();
            filter(controller, chuyenmon_list.getItems());
            controller.knText.setText(kn_txt.getText());
            controller.kntdText.setText(kntt_txt.getText());
            controller.search(event);
            stage.setOnHidden(windowEvent -> {
                SearchField searchField = tableView.getSelectionModel().getSelectedItem();
                searchField.setResults(controller.getResults());
            });
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void filter(SearchFrameController controller, ObservableList<Object> list) throws SQLException{
        for(Object object : list){
            if(object instanceof Pair<?, ?>){
                if(((Pair<?, ?>) object).getKey() instanceof String){
                    String key = ((Pair<String, ?>) object).getKey();
                    if(key.equals("chuyennganh")){
                        Pair<String, Boolean> pair = ((Pair<String, Pair<String, Boolean>>) object).getValue();
                        if(pair.getValue()){
                            controller.chooseCN(pair.getKey());
                        }else{
                            controller.choosenCN.add(pair.getKey());
                        }
                        controller.tnCheck.setSelected(true);
                    }else if(key.equals("chuyenmon")){
                        Pair<String, Boolean> pair = ((Pair<String, Pair<String, Boolean>>) object).getValue();
                        if(pair.getValue()){
                            controller.setChoosenCM(pair.getKey());
                        }else{
                            controller.choosenCM.add(pair.getKey());
                        }
                    }else if(key.equals("vitri")){
                        String vitri_ = ((Pair<String, String>) object).getValue();
                        controller.vitriBox.setValue(vitri_);
                    }else if(key.equals("trinhdo")){
                        String trinhdo_ = ((Pair<String, String>) object).getValue();
                        controller.tdBox.setValue(trinhdo_);
                    }
                }else if(((Pair<?, ?>) object).getKey() instanceof ChungChi){
                    controller.cclist.getItems().add((Pair<ChungChi, Boolean>) object);
                }
            }
        }
    }

    @FXML
    public void tableHandle(MouseEvent event){
        SearchField temp = tableView.getSelectionModel().getSelectedItem();
        if(temp == null){
            return;
        }
        if(event.getButton() == MouseButton.SECONDARY){
            ContextMenu contextMenu = tableView.getContextMenu();
            contextMenu.show(tableView, event.getSceneX(), event.getSceneY());
        }else{
            if(event.getButton() == MouseButton.PRIMARY){
                setTxtArea(temp);
            }
        }
    }

    @FXML
    public void chooseSpec(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY || event.getClickCount() != 2 ||
           chuyenmon_list.getItems() == null || tableView.getItems().isEmpty()){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ChooseSpecView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            ChooseSpecViewController controller = loader.getController();
            controller.setList(chuyenmon_list.getItems());
            stage.setOnHidden(windowEvent -> {
                chuyenmon_list.getItems().clear();
                chuyenmon_list.getItems().addAll(controller.getList());

                SearchField temp = tableView.getSelectionModel().getSelectedItem();
                temp.getChuyenmon().clear();
                temp.getChuyenmon().addAll(chuyenmon_list.getItems());

                tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex(), temp);
            });
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        tableView.setItems(FXCollections.observableArrayList());
        chuyenmon_list.setItems(FXCollections.observableArrayList());

        stt.setCellValueFactory(new PropertyValueFactory<>("stt"));
        sl.setCellValueFactory(new PropertyValueFactory<>("sl"));
        vitri.setCellValueFactory(new PropertyValueFactory<>("vitri"));
        chuyenmon.setCellValueFactory(new PropertyValueFactory<>("chuyenmon"));
        kn.setCellValueFactory(new PropertyValueFactory<>("kn"));
        kntt.setCellValueFactory(new PropertyValueFactory<>("kntt"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("XOÁ");
        menuItem.setOnAction(event -> {
            SearchField temp = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("XOÁ");
            alert.setContentText("");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.isPresent() && option.get() == ButtonType.OK){
                tableView.getItems().remove(temp);
                stt_--;
                emptyTxtArea();
            }
        });
        MenuItem menuItem1 = new MenuItem("KẾT QUẢ");
        menuItem1.setOnAction(event -> {
            SearchField temp = tableView.getSelectionModel().getSelectedItem();
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Result.fxml"));
                Stage stage = new Stage();
                stage.setTitle("");
                stage.setScene(new Scene(loader.load()));
                stage.setResizable(false);
                ResultController controller = loader.getController();
                controller.setResult(temp.getResults());
                stage.show();
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        contextMenu.getItems().addAll(menuItem, menuItem1);
        tableView.setContextMenu(contextMenu);
    }

    public void emptyTxtArea(){
        vitri_txt.setText("");
        sl_txt.setText("");
        chuyenmon_list.getItems().clear();
        kn_txt.setText("");
        kntt_txt.setText("");
    }

    public class SearchField{
        private int stt, sl, kn, kntt;
        private String vitri;
        private ObservableList<Object> chuyenmon;
        private ObservableList<Person> results;

        public SearchField(){}

        public int getStt(){
            return stt;
        }

        public void setStt(int stt){
            this.stt = stt;
        }

        public int getSl(){
            return sl;
        }

        public final void setSl(int sl){
            this.sl = sl;
        }

        public int getKn(){
            return kn;
        }

        public void setKn(int kn){
            this.kn = kn;
        }

        public int getKntt(){
            return kntt;
        }

        public void setKntt(int kntt){
            this.kntt = kntt;
        }

        public String getVitri(){
            return vitri;
        }

        public void setVitri(String vitri){
            this.vitri = vitri;
        }

        public ObservableList<Object> getChuyenmon(){
            return chuyenmon;
        }

        public void setChuyenmon(ObservableList<Object> chuyenmon){
            this.chuyenmon = chuyenmon;
        }

        public ObservableList<Person> getResults(){
            return results;
        }

        public void setResults(ObservableList<Person> results){
            this.results = results;
        }
    }
}
