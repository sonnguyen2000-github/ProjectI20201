package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChooseSpecViewController implements Initializable{
    protected PostgresqlConn postgresql;
    @FXML
    ListView<Pair<ChungChi, Boolean>> ccList;
    @FXML
    ListView<Object> additionalList;
    @FXML
    Button cancelBtn;
    @FXML
    ComboBox<String> levelBox;
    @FXML
    ComboBox<String> tdBox;
    @FXML
    TextField cnTxt;
    @FXML
    TextField cmTxt;
    @FXML
    ComboBox<String> vtBox;
    @FXML
    CheckBox cntdCheckBox;
    @FXML
    CheckBox cctdCheckBox;
    @FXML
    Button saveBtn;
    @FXML
    TextField ccTxt;

    @FXML
    public void save(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        //more
        cancel(event);
    }

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void chooseCC(KeyEvent event){
        if(event.getCode() != KeyCode.ENTER || ccTxt.getText().isEmpty()){
            return;
        }
        String tencc_ = ccTxt.getText();
        String mucdo_ = levelBox.getValue() == null ? "" : levelBox.getValue();
        ChungChi temp = new ChungChi(tencc_, mucdo_, "", "", "", "", 0, 0);
        boolean cctd_ = cctdCheckBox.isSelected();
        ccList.getItems().add(new Pair<>(temp, cctd_){
            @Override
            public String toString(){
                return temp + " | tìm kiếm td: " + cctd_;
            }
        });
        ccTxt.clear();
    }

    @FXML
    public void chooseCN(KeyEvent event){
        if(event.getCode() != KeyCode.ENTER){
            return;
        }
        if(cnTxt.getText() != null && !cnTxt.getText().isEmpty()){
            String chuyennganh_ = cnTxt.getText();
            Boolean cntd_ = cntdCheckBox.isSelected();
            additionalList.getItems().add(new Pair<>("chuyennganh", new Pair<>(chuyennganh_, cntd_)){
                @Override
                public String toString(){
                    return chuyennganh_ + " | tìm kiếm tđ: " + cntd_;
                }
            });
            cnTxt.clear();
        }
    }

    @FXML
    public void chooseCM(KeyEvent event){
        if(event.getCode() != KeyCode.ENTER || cmTxt.getText() == null || cmTxt.getText().isEmpty()){
            return;
        }
        String chuyenmon_ = cmTxt.getText();
        Boolean cmtd_ = cntdCheckBox.isSelected();
        additionalList.getItems().add(new Pair<>("chuyenmon", new Pair<>(chuyenmon_, cmtd_)){
            @Override
            public String toString(){
                return chuyenmon_ + " | tìm kiếm tđ: " + cmtd_;
            }
        });
        cmTxt.clear();
    }

    @FXML
    public void chooseVitri(){
        if(vtBox.getValue() == null || vtBox.getValue().isEmpty()){
            return;
        }
        String vitri_ = vtBox.getValue();
        Pair<String, String> pair = new Pair<>("vitri", vitri_){
            @Override
            public String toString(){
                return "Đã từng công tác ở vị trí: " + getValue();
            }
        };
        additionalList.getItems().add(pair);
    }

    @FXML
    public void chooseTD(){
        if(tdBox.getValue() == null || tdBox.getValue().isEmpty()){
            return;
        }
        String trinhdo_ = tdBox.getValue();
        Pair<String, String> pair = new Pair<>("trinhdo", trinhdo_){
            @Override
            public String toString(){
                return "Trình độ: " + trinhdo_;
            }
        };
        additionalList.getItems().add(pair);
    }

    @FXML
    public void editCCList(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !ccList.getItems().isEmpty()){
            try{
                attachSpecs();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            editList(event);
        }
    }

    public void attachSpecs() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AttachSpecs.fxml"));
        Stage stage = new Stage();
        stage.setTitle("");
        stage.setScene(new Scene(loader.load()));
        stage.setResizable(false);
        AttachSpecsController controller = loader.getController();
        ChungChi temp = ccList.getSelectionModel().getSelectedItem().getKey();
        controller.setSpecs(temp.getCn(), temp.getCm());
        stage.setOnHidden(windowEvent -> {
            Pair<String, String> pair = controller.getSpecs();
            temp.setCn(pair.getKey());
            temp.setCm(pair.getValue());
        });
        stage.show();
    }

    @FXML
    public void editList(MouseEvent event){
        ListView listView = (ListView) event.getSource();
        if(event.getButton() != MouseButton.SECONDARY || listView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("XOÁ ?");
        alert.setContentText("");
        Optional<ButtonType> optional = alert.showAndWait();
        if(optional.isPresent() && optional.get() == ButtonType.OK){
            Object object = listView.getSelectionModel().getSelectedItem();
            listView.getItems().remove(object);
        }
    }

    public ObservableList<Object> getList(){
        ObservableList<Object> temp = FXCollections.observableArrayList();
        temp.addAll(ccList.getItems());
        temp.addAll(additionalList.getItems());

        return temp;
    }

    public void setList(List<Object> specs){
        for(Object spec : specs){
            if(spec instanceof Pair<?, ?>){
                if(((Pair<?, ?>) spec).getKey() instanceof ChungChi){
                    ccList.getItems().add((Pair<ChungChi, Boolean>) spec);
                }else{
                    additionalList.getItems().add(spec);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ccList.setItems(FXCollections.observableArrayList());
        additionalList.setItems(FXCollections.observableArrayList());
        levelBox.setItems(FXCollections.observableArrayList());
        tdBox.setItems(FXCollections.observableArrayList());
        vtBox.setItems(FXCollections.observableArrayList());

        try{
            postgresql = new PostgresqlConn();
            Connection connection = postgresql.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;

            // tdBox
            String[] trinhdos = {"High School Graduated", "College Graduated", "University Graduated", "Engineer"};
            tdBox.getItems().add("");
            tdBox.getItems().addAll(trinhdos);
            // levelBox
            rs = stmt.executeQuery("SELECT distinct level\n" + "FROM public.\"BangCap\";");
            levelBox.getItems().add("");
            while(rs.next()){
                levelBox.getItems().add(rs.getString(1));
            }
            // vtBox
            String[] vitri_ = {"Assistant", "Manager", "Leader", "Tester", "Business"};
            vtBox.getItems().add("");
            vtBox.getItems().addAll(vitri_);
            // ccTxt
            rs = stmt.executeQuery("SELECT distinct ten\n" + "FROM public.\"BangCap\"\n" + "WHERE not totnghiep;");
            ObservableList<String> temp = FXCollections.observableArrayList();
            while(rs.next()){
                temp.add(rs.getString(1));
            }
            TextFields.bindAutoCompletion(ccTxt, temp);
            temp.clear();
            // cnTxt
            rs = stmt.executeQuery("SELECT distinct chuyennganh\n" + "FROM public.\"ChuyenMon\";");
            while(rs.next()){
                temp.add(rs.getString(1));
            }
            TextFields.bindAutoCompletion(cnTxt, temp);
            temp.clear();
            // cmTxt
            rs = stmt.executeQuery("SELECT distinct chuyenmon\n" + "FROM public.\"ChuyenMon\";");
            while(rs.next()){
                temp.add(rs.getString(1));
            }
            TextFields.bindAutoCompletion(cmTxt, temp);
            temp.clear();
            // end of initialization
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return ccList.getItems().toString() + additionalList.getItems().toString();
    }

}
