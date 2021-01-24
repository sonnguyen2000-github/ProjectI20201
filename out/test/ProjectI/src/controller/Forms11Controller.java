package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Forms11Controller implements Initializable{
    @FXML
    Button cancelBtn, exportBtn;
    @FXML
    Tab a11Tab, b11Tab, c11Tab;
    @FXML
    SubScene a11Sub, b11Sub, c11Sub;
    private A11Controller a11Controller;
    private B11Controller b11Controller;
    private C11Controller c11Controller;

    @FXML
    public void cancel(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void exportPdf(MouseEvent event){
        if(event.getButton() != MouseButton.PRIMARY){
            return;
        }
        try{
            Document document = new Document();
            document.setPageSize(new Rectangle(720, 420));
            PdfWriter.getInstance(document, new FileOutputStream(
                    "E:\\OneDrive - Hanoi University of Science and Technology\\Documents\\Eclipse Projects\\ProjectI\\src\\main\\export_pdf_result.pdf"));
            document.open();
            /**/
            PdfPTable a11PdfTable = new PdfPTable(3);

            PdfPCell pdfPCell = new PdfPCell(new Phrase("STT"));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            a11PdfTable.addCell(pdfPCell);

            PdfPCell pdfPCell1 = new PdfPCell(new Phrase("HO TEN"));
            pdfPCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            a11PdfTable.addCell(pdfPCell1);

            PdfPCell pdfPCell2 = new PdfPCell(new Phrase("VI TRI CONG TAC"));
            pdfPCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            a11PdfTable.addCell(pdfPCell2);
            a11PdfTable.setHeaderRows(1);

            for(A11Controller.A11 a11 : a11Controller.a11Table.getItems()){
                a11PdfTable.addCell(a11.getStt() + "");
                a11PdfTable.addCell(a11.getHoten());
                a11PdfTable.addCell(a11.getVitri());
            }

            Paragraph a11Paragraph = new Paragraph("BIEU MAU A11\n\n");
            a11Paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(a11Paragraph);
            document.add(a11PdfTable);
            /**/
            PdfPTable b11PdfTable_Header = new PdfPTable(2);

            PdfPCell pdfPCell3 = new PdfPCell(new Phrase("Thong tin nhan su"));
            pdfPCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable_Header.addCell(pdfPCell3);

            PdfPCell pdfPCell4 = new PdfPCell(new Phrase("Cong viec hien tai"));
            pdfPCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable_Header.addCell(pdfPCell4);

            b11PdfTable_Header.setWidthPercentage(110f);

            PdfPTable b11PdfTable = new PdfPTable(12);

            PdfPCell pdfPCell5 = new PdfPCell(new Phrase("STT"));
            pdfPCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell5);

            PdfPCell pdfPCell6 = new PdfPCell(new Phrase("Ho ten"));
            pdfPCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell6);

            PdfPCell pdfPCell7 = new PdfPCell(new Phrase("So dinh danh"));
            pdfPCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell7);

            PdfPCell pdfPCell8 = new PdfPCell(new Phrase("Vi tri"));
            pdfPCell8.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell8);

            PdfPCell pdfPCell9 = new PdfPCell(new Phrase("Ngay sinh"));
            pdfPCell9.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell9);

            PdfPCell pdfPCell16 = new PdfPCell(new Phrase("Trinh do"));
            pdfPCell16.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell16);
            b11PdfTable.setHeaderRows(1);

            PdfPCell pdfPCell10 = new PdfPCell(new Phrase("Ten nguoi su dung lao dong"));
            pdfPCell10.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell10);

            PdfPCell pdfPCell11 = new PdfPCell(new Phrase("Dia chi"));
            pdfPCell11.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell11);

            PdfPCell pdfPCell12 = new PdfPCell(new Phrase("Chuc danh"));
            pdfPCell12.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell12);

            PdfPCell pdfPCell13 = new PdfPCell(new Phrase("So nam lam viec"));
            pdfPCell13.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell13);

            PdfPCell pdfPCell14 = new PdfPCell(new Phrase("Nguoi lien lac"));
            pdfPCell14.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell14);

            PdfPCell pdfPCell15 = new PdfPCell(new Phrase("Dien thoai/Fax/Email"));
            pdfPCell15.setHorizontalAlignment(Element.ALIGN_CENTER);
            b11PdfTable.addCell(pdfPCell15);
            b11PdfTable.setHeaderRows(1);

            for(B11Controller.B11 b11 : b11Controller.b11Table.getItems()){
                b11PdfTable.addCell(b11.getStt() + "");
                b11PdfTable.addCell(b11.getTtns_hoten());
                b11PdfTable.addCell(b11.getId());
                b11PdfTable.addCell(b11.getVitri());
                b11PdfTable.addCell(b11.getDob().toString());
                b11PdfTable.addCell(b11.getTrinhdo());

                b11PdfTable.addCell(b11.getCvht_hoten());
                b11PdfTable.addCell(b11.getDiachi());
                b11PdfTable.addCell(b11.getChucdanh());
                b11PdfTable.addCell(b11.getSoNamLamViec() + "");
                b11PdfTable.addCell(b11.getNgLienLac());
                b11PdfTable.addCell(b11.getDt_fax_email());
            }

            b11PdfTable.setWidthPercentage(110f);

            Paragraph b11Paragraph = new Paragraph("BIEU MAU B11\n\n");
            b11Paragraph.setAlignment(Element.ALIGN_CENTER);
            document.newPage(); // new page
            document.add(b11Paragraph);
            document.add(b11PdfTable_Header);
            document.add(b11PdfTable);
            /**/
            PdfPTable c11PdfTable = new PdfPTable(5);

            PdfPCell pdfPCell17 = new PdfPCell(new Phrase("STT"));
            pdfPCell17.setHorizontalAlignment(Element.ALIGN_CENTER);
            c11PdfTable.addCell(pdfPCell17);

            PdfPCell pdfPCell18 = new PdfPCell(new Phrase("Ten nhan su chu chot"));
            pdfPCell18.setHorizontalAlignment(Element.ALIGN_CENTER);
            c11PdfTable.addCell(pdfPCell18);

            PdfPCell pdfPCell19 = new PdfPCell(new Phrase("Tu ngay"));
            pdfPCell19.setHorizontalAlignment(Element.ALIGN_CENTER);
            c11PdfTable.addCell(pdfPCell19);

            PdfPCell pdfPCell20 = new PdfPCell(new Phrase("Den ngay"));
            pdfPCell20.setHorizontalAlignment(Element.ALIGN_CENTER);
            c11PdfTable.addCell(pdfPCell20);

            PdfPCell pdfPCell21 = new PdfPCell(
                    new Phrase("Cong ty/Du an/Chuc vu/Kinh nghiem chuyen mon va quan ly co lien quan"));
            pdfPCell21.setHorizontalAlignment(Element.ALIGN_CENTER);
            c11PdfTable.addCell(pdfPCell21);
            c11PdfTable.setHeaderRows(1);

            for(C11Controller.C11 c11 : c11Controller.c11Table.getItems()){
                c11PdfTable.addCell(c11.getStt() + "");
                c11PdfTable.addCell(c11.getHoten());
                c11PdfTable.addCell(c11.getTungay());
                c11PdfTable.addCell(c11.getDenngay());
                c11PdfTable.addCell(c11.getKncm());
            }

            Paragraph c11Paragraph = new Paragraph("BIEU MAU C11\n\n");
            c11Paragraph.setAlignment(Element.ALIGN_CENTER);
            document.newPage();
            document.add(c11Paragraph);
            document.add(c11PdfTable);
            /**/
            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("EXPORT SUCCESSFULLY.");
            alert.setContentText("");
            alert.show();
        }catch(Exception e){
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText("EXPORT FAILED.");
            alert.setContentText("");
            alert.show();
        }
    }

    public void setResult(ObservableList<SearchViewController.SearchField> result) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/A11.fxml"));
        a11Sub.setRoot(loader.load());
        a11Controller = loader.getController();
        a11Controller.setResult(result);
        /**/
        loader = new FXMLLoader(getClass().getResource("/fxml/B11.fxml"));
        b11Sub.setRoot(loader.load());
        b11Controller = loader.getController();
        b11Controller.setResult(result);
        /**/
        loader = new FXMLLoader(getClass().getResource("/fxml/C11.fxml"));
        c11Sub.setRoot(loader.load());
        c11Controller = loader.getController();
        c11Controller.setResult(result);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
}
