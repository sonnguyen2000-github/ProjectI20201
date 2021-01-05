package controller;

import javafx.application.Platform;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.AutoCompleteBox;
import main.ChungChi;
import main.Person;
import main.PostgresqlConn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchFrameController implements Initializable{
    /**
     * variables
     */
    protected ObservableList<String> choosenCN, choosenCM;
    protected ObservableList<Pair<ChungChi, Boolean>> choosenCC;
    protected ObservableList<Person> tableInfo, choosenPerson;
    protected PostgresqlConn postgresqlConn;
    /**
     * @FXML var
     */
    @FXML
    BorderPane frame;
    @FXML
    ListView<String> cnlist, cmlist;
    @FXML
    ListView<Pair<ChungChi, Boolean>> cclist;
    @FXML
    ListView<Person> personlist;
    @FXML
    TextField knText, kntdText;
    @FXML
    ComboBox<String> cnBox, cmBox, tdBox, vitriBox;
    @FXML
    ComboBox<ChungChi> ccBox;
    @FXML
    ComboBox<String> levelBox;
    @FXML
    TableView<Person> infoTable;
    @FXML
    TableColumn<Person, String> idColumn, hotenColumn, tdColumn, cvColumn, hdldColumn;
    @FXML
    Button searchBtn, refreshBtn, printBtn, addAllBtn, clearAllBtn;
    @FXML
    Button alterBtn;
    @FXML
    CheckBox tdCheck, dtCheck, tnCheck;
    private ObservableList<Person> results;
    private SearchAlterFrameController searchAlterFrameController;
    private String appendAlter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            /*
             * Database Connection
             * */
            postgresqlConn = new PostgresqlConn();
            Connection connection = postgresqlConn.getConnection();
            Statement statement = connection
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            /*
             *  set Boxes
             * */
            ResultSet rs = statement.executeQuery("SELECT distinct chuyennganh\n" + "FROM public.\"ChuyenMon\";");
            ObservableList<String> tempList = FXCollections.observableArrayList();
            // begin
            // cnBox
            tempList.add("");
            while(rs.next()){
                tempList.add(rs.getString(1));
            }
            cnBox.setItems(tempList);
            // cn Box done
            // level Box
            rs = statement.executeQuery("SELECT distinct level\n" + "FROM public.\"BangCap\";");
            tempList = FXCollections.observableArrayList();
            tempList.add("");
            while(rs.next()){
                tempList.add(rs.getString(1));
            }
            levelBox.setItems(tempList);
            //end level Box
            // tdBox
            String[] trinhdos = {"High School Graduated", "College Graduated", "University Graduated", "Engineer"};
            tempList = FXCollections.observableArrayList();
            tempList.add("");
            tempList.addAll(trinhdos);
            tdBox.setItems(tempList);
            // tdBox done
            // ccBox
            ccBox.setItems(FXCollections.observableArrayList());
            rs = statement.executeQuery("SELECT distinct ten, chuyennganh, chuyenmon\n" + "FROM public.\"BangCap\"\n" +
                                        "WHERE not totnghiep;");
            ObservableList<ChungChi> temp = ccBox.getItems();
            temp.add(null);
            while(rs.next()){
                temp.add(new ChungChi(rs.getString(1), "", "", "", rs.getString(2), rs.getString(3), 0, 0));
            }
            new AutoCompleteBox(ccBox);
            // ccBox done
            // vitriBox
            tempList = FXCollections.observableArrayList();
            tempList.add("");
            String[] vitri = {"Assistant", "Manager", "Leader", "Tester", "Business"};
            tempList.addAll(vitri);
            vitriBox.setItems(tempList);
            /*
             * end of box initialization
             * */
            //
            /*
             * cclist initialization
             * */
            choosenCC = FXCollections.observableArrayList();
            cclist.setItems(choosenCC);
            // end of ccList initialization
            /*
             *  Table setup
             * */
            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            hotenColumn.setCellValueFactory(new PropertyValueFactory<>("hoten"));
            tdColumn.setCellValueFactory(new PropertyValueFactory<>("trinhdo"));
            cvColumn.setCellValueFactory(new PropertyValueFactory<>("chucvu"));
            hdldColumn.setCellValueFactory(new PropertyValueFactory<>("mahdld"));
            tableInfo = FXCollections.observableArrayList();
            infoTable.setItems(tableInfo);
            //end of table setup
            //
            /*
             *  person list set up
             * */
            choosenPerson = FXCollections.observableArrayList();
            personlist.setItems(choosenPerson);
            //
            /*
             * choosenCN,CM set up
             * */
            choosenCN = FXCollections.observableArrayList();
            choosenCM = FXCollections.observableArrayList();
            cnlist.setItems(choosenCN);
            cmlist.setItems(choosenCM);
            //
            // create view temp1
            dropOldTempView();
            createTempView();
            //
            // close connection
            connection.close();
            //

        }catch(Exception throwables){
            throwables.printStackTrace();
            Platform.exit();
        }finally{
            setResults(FXCollections.observableArrayList());
        }
    }

    //drop old temp1 view
    public void dropOldTempView(){
        try{
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String search_old_temp_view = "select TABLE_NAME\n" + "from INFORMATION_SCHEMA.VIEWS\n" +
                                          "where TABLE_NAME = 'temp1' ;";
            ResultSet rs = stmt.executeQuery(search_old_temp_view);
            System.out.println("\n--------------------------------\n");
            System.out.println(search_old_temp_view);
            System.out.println("\n--------------------------------\n");
            if(rs.next()){
                String drop_old_temp_view = "DROP VIEW temp1;";
                stmt.executeUpdate(drop_old_temp_view);
                System.out.println(drop_old_temp_view);
                System.out.println("\n--------------------------------\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //create temp1 view
    public void createTempView(){
        try{
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            BufferedReader reader = new BufferedReader(new FileReader(
                    "E:\\OneDrive - Hanoi University of Science and Technology\\20201\\Project I\\createtemp1.sql"));
            StringBuilder temp = new StringBuilder();
            while(reader.ready()){
                temp.append(reader.readLine()).append("\n");
            }
            System.out.println("-----------------------\n\t" + temp.toString() + "\n------------------");
            stmt.executeUpdate(temp.toString());
            reader.close();
            connection.close();
        }catch(Exception throwables){
            throwables.printStackTrace();
        }

    }

    public ObservableList<Person> getResults(){
        return results;
    }

    public void setResults(ObservableList<Person> results){
        this.results = results;
    }

    /**
     * @FXML methods
     */
    @FXML//for cnBox
    public void setCmBoxAndChooseCN(){
        if(cnBox.getValue() != null){//
            try{
                Connection connection = postgresqlConn.getConnection();
                Statement stmt = connection
                        .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT distinct chuyenmon\n" + "FROM public.\"ChuyenMon\"\n" +
                                                 "WHERE nhom in (select distinct nhom from public.\"ChuyenMon\"\n" +
                                                 "where chuyennganh like '%" + cnBox.getValue() + "%');");
                ObservableList<String> tempList = FXCollections.observableArrayList();
                tempList.add("");
                while(rs.next()){
                    tempList.add(rs.getString(1));
                }
                cmBox.setItems(tempList);
                //
                connection.close();
                //
            }catch(Exception throwables){
                throwables.printStackTrace();
            }finally{
                if(!cnBox.getValue().isEmpty()){
                    chooseCN(cnBox.getValue());
                }
            }
        }
    }

    /**
     * Methods
     */
    // chooseCN
    public void chooseCN(String cn){
        try{
            Connection connection = postgresqlConn.getConnection();
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT distinct chuyennganh\n" + "FROM public.\"ChuyenMon\"\n" +
                                             "WHERE nhom in (select distinct nhom \n" +
                                             "\t\t\t  from public.\"ChuyenMon\"\n" +
                                             "\t\t\t  where chuyennganh like '%" + cn + "%');");
            while(rs.next()){
                String temp = rs.getString(1);
                if(!choosenCN.contains(temp)){
                    choosenCN.add(temp);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML// for cmBox
    public void chooseCM(){
        if(cmBox.getValue() != null && !cmBox.getValue().isEmpty()){
            String chuyenmon_ = cmBox.getValue();
            try{
                setChoosenCM(chuyenmon_);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setChoosenCM(String chuyenmon_) throws SQLException{
        Connection connection = postgresqlConn.getConnection();
        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT chuyenmon\n" + "FROM public.\"ChuyenMon\"\n" +
                                         "WHERE chuyennganh in ( select chuyennganh from public.\"ChuyenMon\"\n" +
                                         "\t\t\t\t   \twhere chuyenmon like '%" + chuyenmon_ + "%');");
        while(rs.next()){
            String temp = rs.getString(1);
            if(!choosenCM.contains(temp)){
                choosenCM.add(temp);
            }
        }
    }

    @FXML//for ccBox
    public void chooseCC(){
        if(ccBox.getValue() != null){
            ChungChi temp = ccBox.getValue();
            if(levelBox.getValue() != null){
                temp.setMucdo(levelBox.getValue());
            }
            boolean cctd_ = tdCheck.isSelected();
            choosenCC.add(new Pair<>(temp, cctd_){
                @Override
                public String toString(){
                    return temp + " | tìm kiếm tđ: " + cctd_;
                }
            });
        }
    }

    @FXML
    public void editPersonList(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            if(personlist.getSelectionModel().getSelectedItem() == null){
                return;
            }
            if(event.getClickCount() == 2){
                try{
                    editExtraInfo(personlist.getSelectionModel().getSelectedItem(), true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            editList(event);
        }
    }

    //
    public void editExtraInfo(Person person, boolean editable) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ExtraInfo.fxml"));
        Scene extra = new Scene(loader.load());
        //
        Stage stage = new Stage();
        stage.setTitle("DETAIL");
        stage.setScene(extra);
        ExtraInfoController extraInfoController = loader.getController();
        extraInfoController.setExtraInfo(person, editable);
        stage.show();
    }

    @FXML//for cclist, cnList, cmList
    public void editList(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY){
            ListView<String> listView = (ListView<String>) event.getSource();
            if(listView.getSelectionModel().getSelectedItem() == null){
                return;
            }
            Alert dialog = new Alert(AlertType.CONFIRMATION);
            dialog.setTitle("");
            dialog.setHeaderText("REMOVE FROM LIST ?");
            dialog.setContentText("");
            Optional<ButtonType> rs = dialog.showAndWait();
            if(rs.isPresent() && rs.get() == ButtonType.OK){
                listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
            }
        }
    }

    @FXML// for infoTable
    public void editTable(MouseEvent event){
        TableView<Person> table = (TableView<Person>) event.getSource();
        if(table.getSelectionModel().getSelectedItem() == null){
            return;
        }
        if(event.getButton() == MouseButton.SECONDARY){
            Alert dialog = new Alert(AlertType.CONFIRMATION);
            dialog.setTitle("");
            dialog.setHeaderText("REMOVE FROM TABLE ?");
            dialog.setContentText("");
            Optional<ButtonType> rs = dialog.showAndWait();
            if(rs.isPresent() && rs.get() == ButtonType.OK){
                table.getItems().remove(table.getSelectionModel().getSelectedIndex());
            }
        }
        if(event.getButton() == MouseButton.PRIMARY){
            if(event.getClickCount() == 2){
                try{
                    editExtraInfo(table.getSelectionModel().getSelectedItem(), false);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void switchCheckBox(MouseEvent event){
        if(event.getSource() == tdCheck){
            dtCheck.setSelected(!tdCheck.isSelected());
        }else{
            if(event.getSource() == dtCheck){
                tdCheck.setSelected(!dtCheck.isSelected());
            }
        }
    }

    @FXML
    public void search(MouseEvent event){// Tim kiem btn
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            Connection connection;
            Statement stmt;
            StringBuilder query = new StringBuilder();
            //
            query.append(selectRelation());
            query.append(appendCN());
            query.append(appendCM());
            query.append(appendTD());
            query.append(appendKN());
            query.append(appendKNTT());
            query.append(appendVT());
            query.append(appendCC());
            query.append(appendAlter());
            query.append(closeQuery());
            //
            System.out.println("\n-----------------\n" + query + "\n-----------------\n");
            //
            connection = postgresqlConn.getConnection();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query.toString());
            while(rs.next()){
                tableInfo.add(new Person(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                         rs.getString(5)));
            }
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //
    public String selectRelation(){
        return "SELECT distinct p1.id, p1.hoten, p1.trinhdo, p1.chucvu, p1.mahdld\n" +
               "FROM temp1 as p1 left outer join public.\"LichSuCongTac\" as p2\n" +
               "ON p1.id = p2.\"ID\"\nWHERE TRUE ";
    }

    // append CN
    public String appendCN(){
        if(choosenCN.isEmpty()){
            return "";
        }else{
            StringBuilder buffer = new StringBuilder();
            boolean isTn = tnCheck.isSelected();
            //
            buffer.append("\nAND ");
            buffer.append("\n( ");
            buffer.append("false ");
            for(String cn : choosenCN){
                buffer.append("\nor ");
                buffer.append("\n( ");
                //
                if(isTn){
                    buffer.append("p1.id in (select distinct main.\"ID\"\n" + "from public.\"BangCap\" main\n" +
                                  "where totnghiep and chuyennganh like '%").append(cn).append("%') ");
                }else{
                    buffer.append("p1.chuyennganh like '%");
                    buffer.append(cn);
                    buffer.append("%' ");
                    //
                    buffer.append("or ");// or in p2
                    //
                    buffer.append("p2.chuyennganh like '%");
                    buffer.append(cn);
                    buffer.append("%' ");
                }
                //
                buffer.append(") ");
            }
            buffer.append(") ");
            //
            return buffer.toString();
        }
    }

    //
    // appendCM
    public String appendCM(){
        if(choosenCM.isEmpty()){
            return "";
        }else{
            StringBuilder buffer = new StringBuilder();
            //
            buffer.append("\nAND ");// AND (...or...)
            buffer.append("\n( ");
            buffer.append("false ");
            for(String cm : choosenCM){
                buffer.append("\nor ");
                buffer.append("\n( ");
                //
                buffer.append("p1.chuyenmon like '%");
                buffer.append(cm);
                buffer.append("%' ");
                //
                buffer.append("or ");// or
                //
                buffer.append("p2.chuyenmon like '%");
                buffer.append(cm);
                buffer.append("%' ");
                //
                buffer.append(") ");
            }
            buffer.append(") ");
            //
            return buffer.toString();
        }
    }

    //
    //appendTD
    public String appendTD(){
        if(tdBox.getValue() == null || tdBox.getValue().isEmpty()){
            return "";
        }else{
            StringBuilder buffer = new StringBuilder();
            String td = tdBox.getValue();
            //
            buffer.append("\nAND ");
            //
            switch(td){
                case "High School Graduated":
                    return "";
                case "College Graduated":
                    buffer.append("( p1.trinhdo not like '%High School Graduated%') ");
                    return buffer.toString();
                case "University Graduated":
                    buffer.append(
                            "( p1.trinhdo not like '%High School Graduated%' and p1.trinhdo not like '%College Graduated%') ");
                    return buffer.toString();
            }
            buffer.append("p1.trinhdo like '%");
            buffer.append(td);
            buffer.append("%' ");
            //
            return buffer.toString();
        }
    }

    //
    // appendKN
    public String appendKN(){
        if(knText.getText() == null || knText.getText().isEmpty() || choosenCN.isEmpty()){
            return "";
        }else{
            try{
                int kn = Integer.parseInt(knText.getText());// can cause exception
                if(kn == 0){
                    return "";
                }
                int now = LocalDate.now().getYear();
                StringBuilder buffer = new StringBuilder();
                //
                buffer.append("\nAND ");
                buffer.append(now).append(" + 1 - ");
                //
                buffer.append("(");
                //
                buffer.append("select min(bangcap.nam)\nfrom public.\"BangCap\" bangcap");
                buffer.append("\nwhere bangcap.\"ID\" = p1.id");
                buffer.append("\nand totnghiep");
                buffer.append("\nand");
                //
                buffer.append("\n( false");
                //buffer.append("\nselect distinct chuyennganh\n" + "from public.\"BangCap\"\n" + "where false ");
                for(String cn : choosenCN){
                    buffer.append("\nor chuyennganh like '%");
                    buffer.append(cn);
                    buffer.append("%' ");
                }
                buffer.append(") ");
                //
                buffer.append("\ngroup by bangcap.\"ID\"");
                //
                buffer.append(") ");
                //
                buffer.append(" >= ");
                buffer.append(kn);
                //
                return buffer.toString();
            }catch(Exception e){
                e.printStackTrace();
                //
                return "";
            }
        }
    }

    //
    // appendKNTT
    public String appendKNTT(){
        if(kntdText.getText() == null || kntdText.getText().isEmpty() || (choosenCN.isEmpty() && choosenCM.isEmpty())){
            return "";
        }else{
            try{
                int kntt = Integer.parseInt(kntdText.getText());// can cause exception
                if(kntt == 0){
                    return "";
                }
                StringBuilder buffer = new StringBuilder();
                //
                buffer.append("\nAND ");
                //
                buffer.append("(");
                //
                buffer.append(
                        "select SUM(lichsu.\"to\" - lichsu.\"from\" + 1)\nfrom public.\"LichSuCongTac\" lichsu");
                buffer.append("\nwhere lichsu.\"ID\" = p1.id");
                if(!choosenCN.isEmpty()){
                    buffer.append("\nand");
                    //
                    buffer.append("\n( false");
                    //buffer.append("\nselect distinct chuyennganh\n" + "from public.\"BangCap\"\n" + "where false ");
                    for(String cn : choosenCN){
                        buffer.append("\nor chuyennganh like '%");
                        buffer.append(cn);
                        buffer.append("%' ");
                    }
                    //
                    buffer.append(") ");
                }
                if(!choosenCM.isEmpty()){
                    buffer.append("\nand");
                    //
                    buffer.append("\n( false");
                    //buffer.append("\nselect distinct chuyenmon\n" + "from public.\"BangCap\"\n" + "where false ");
                    for(String cm : choosenCM){
                        buffer.append("\nor chuyenmon like '%");
                        buffer.append(cm);
                        buffer.append("%' ");
                    }
                    //
                    buffer.append(") ");
                }
                //
                buffer.append("\ngroup by lichsu.\"ID\"");
                //
                buffer.append(") ");
                //
                buffer.append(" >= ");
                buffer.append(kntt);
                //
                return buffer.toString();

            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    //
    // appendVT
    public String appendVT(){
        if(vitriBox.getValue() == null || vitriBox.getValue().isEmpty()){
            return "";
        }else{
            try{
                String vt = vitriBox.getValue();
                StringBuilder buffer = new StringBuilder();
                //
                buffer.append("\nAND ");
                //
                buffer.append("upper(p2.vitri) like upper('%");
                buffer.append(vt);
                buffer.append("%') ");
                if(!choosenCM.isEmpty() || !choosenCN.isEmpty()){
                    buffer.append("\n\tand ");
                    buffer.append("\n( ");
                    buffer.append("false ");
                    for(String cn : choosenCN){
                        buffer.append("\nor ");
                        buffer.append("p2.chuyennganh like '%");
                        buffer.append(cn);
                        buffer.append("%' ");
                    }
                    //
                    for(String cm : choosenCM){
                        buffer.append("\nor ");
                        buffer.append("p2.chuyenmon like '%");
                        buffer.append(cm);
                        buffer.append("%'");
                    }
                    buffer.append(") ");
                }
                //
                return buffer.toString();
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    //
    // appendCC
    public String appendCC(){
        if(choosenCC.isEmpty()){
            return "";
        }else{
            StringBuilder buffer = new StringBuilder();
            for(Pair<ChungChi, Boolean> pair : cclist.getItems()){
                buffer.append("\nAND ");
                ChungChi cc = pair.getKey();
                if(pair.getValue()){
                    buffer.append("p1.id in ");
                    buffer.append("\n( ");
                    buffer.append("select distinct main.\"ID\"\n" + "from public.\"BangCap\" main\n" +
                                  "where chuyennganh like '%");
                    buffer.append(cc.getCn());
                    buffer.append("%' and chuyenmon like '%");
                    buffer.append(cc.getCm());
                    buffer.append("%' and level like '%");
                    buffer.append(cc.getMucdo());
                    buffer.append("%' and ");
                    buffer.append("\n( ");
                    buffer.append("hsd = 0 or hsd - ");
                    buffer.append(LocalDate.now().getYear());
                    buffer.append(" >= 0 ");
                    //
                    buffer.append(") ");
                    //
                    buffer.append(") ");

                }else{
                    buffer.append("p1.id in ");
                    buffer.append("\n( ");
                    buffer.append(
                            "select distinct main.\"ID\"\n" + "from public.\"BangCap\" main\n" + "where ten like '%");
                    buffer.append(cc.getTencc());
                    buffer.append("%' and level like '%");
                    buffer.append(cc.getMucdo());
                    buffer.append("%' and ");
                    buffer.append("\n( ");
                    buffer.append("hsd = 0 or hsd - ");
                    buffer.append(LocalDate.now().getYear());
                    buffer.append(" >= 0 ");
                    //
                    buffer.append(") ");
                    //
                    buffer.append(") ");
                }
            }
            //            if(tdCheck.isSelected()){
            //                //
            //                buffer.append("\nAND");
            //                buffer.append("\n( ");
            //                buffer.append("false ");
            //                for(ChungChi cc : cclist.getItems()){
            //                    buffer.append("\nor");
            //                    buffer.append("\n( ");
            //                    buffer.append("p1.chuyennganh like '%");
            //                    buffer.append(cc.getCn());
            //                    buffer.append("%' and p1.chuyenmon like '%");
            //                    buffer.append(cc.getCm());
            //                    buffer.append("%' and p1.level like '%");
            //                    buffer.append(cc.getMucdo());
            //                    buffer.append("%' and ");
            //                    buffer.append("\n( ");
            //                    buffer.append("p1.hsd = 0 or p1.hsd - ");
            //                    buffer.append(LocalDate.now().getYear());
            //                    buffer.append(" >= 0");
            //                    //
            //                    buffer.append(") ");
            //                    //
            //                    buffer.append(") ");
            //                }
            //                buffer.append(")");
            //            }else{
            //                if(dtCheck.isSelected()){
            //                    for(ChungChi cc : cclist.getItems()){
            //                        buffer.append("\nAND ");
            //                        //
            //                        buffer.append("p1.id in ");
            //                        buffer.append("\n( ");
            //                        buffer.append("select distinct main.\"ID\"\n" + "from public.\"BangCap\" main\n" +
            //                                      "where ten like '%");
            //                        buffer.append(cc.getTencc());
            //                        buffer.append("%' and level like '%");
            //                        buffer.append(cc.getMucdo());
            //                        buffer.append("%' and ");
            //                        buffer.append("\n( ");
            //                        buffer.append("hsd = 0 or hsd - ");
            //                        buffer.append(LocalDate.now().getYear());
            //                        buffer.append(" >= 0 ");
            //                        //
            //                        buffer.append(") ");
            //                        //
            //                        buffer.append(") ");
            //                    }
            //                }
            //            }
            return buffer.toString();
        }

    }

    //appen alter
    public String appendAlter(){
        return appendAlter == null ? "" : appendAlter;
    }

    //close query
    public String closeQuery(){
        return "\n\nORDER BY p1.hoten;";
    }

    @FXML
    public void searchAlter(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/SearchAlterFrame.fxml"));
            Stage stage = new Stage();
            stage.setTitle("BỔ SUNG");
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            searchAlterFrameController = loader.getController();
            stage.show();
            stage.setOnHidden(windowEvent -> {
                System.out.println("alter search frame hided");
                appendAlter = searchAlterFrameController.getBuilder().toString();
            });
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    public void refresh(MouseEvent event){// Tai lai btn
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        tableInfo.clear();
    }

    @FXML
    public void addAll(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            choosenPerson.addAll(tableInfo);
        }
    }

    @FXML
    public void export(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            setResults(choosenPerson);
            close();
        }
    }

    public void close(){
        Stage stage = (Stage) infoTable.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clearAll(MouseEvent event){
        if(event.getButton() == MouseButton.PRIMARY){
            choosenPerson.clear();
        }
    }
}
