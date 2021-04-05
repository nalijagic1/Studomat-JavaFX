package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;



public class GradController {
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava;
    public ObservableList<Drzava> listDrzave;
    public TextField fldPostanskiBroj;
    public ImageView Img;
    private Grad grad;
    private Locale l;

    public GradController(Grad grad, ArrayList<Drzava> drzave, Locale l) {
        this.grad = grad;
        listDrzave = FXCollections.observableArrayList(drzave);
        this.l = l;
    }
    public GradController(Grad grad, ArrayList<Drzava> drzave) {
        this.grad = grad;
        listDrzave = FXCollections.observableArrayList(drzave);
    }
    @FXML
    public void initialize() {
        Locale.getDefault();
        choiceDrzava.setItems(listDrzave);
        if (grad != null) {
            fieldNaziv.setText(grad.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(grad.getBrojStanovnika()));
            fldPostanskiBroj.setText(Integer.toString(grad.getPostanskiBroj()));
            Image im = new Image(grad.getSlika());
            Img.setImage(im);

            // choiceDrzava.getSelectionModel().select(grad.getDrzava());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave
            for (Drzava drzava : listDrzave)
                if (drzava.getId() == grad.getDrzava().getId())
                    choiceDrzava.getSelectionModel().select(drzava);
        } else {
            choiceDrzava.getSelectionModel().selectFirst();
        }
    }

    public Grad getGrad() {
        return grad;
    }

    public void clickCancel(ActionEvent actionEvent) {
        grad = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }
    public void promijeniSliku(ActionEvent actionEvent){
        ResourceBundle bundle = ResourceBundle.getBundle("Jezici",l);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(bundle.getString("putanja"));
        dialog.showAndWait();
        String putanja = dialog.getEditor().getText();
        Image i = new Image(putanja);
        Img.setImage(i);
    }

    public void clickOk(ActionEvent actionEvent) {

        Thread t = new Thread(() -> {
            AtomicBoolean sveOk = new AtomicBoolean(true);
            if (fieldNaziv.getText().trim().isEmpty()) {
                fieldNaziv.getStyleClass().removeAll("poljeIspravno");
                fieldNaziv.getStyleClass().add("poljeNijeIspravno");
                sveOk.set(false);
            } else {
                fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
                fieldNaziv.getStyleClass().add("poljeIspravno");
            }
            int brojStanovnika = 0;
            try {
                brojStanovnika = Integer.parseInt(fieldBrojStanovnika.getText());
            } catch (NumberFormatException e) {
                // ...
            }
            if (brojStanovnika <= 0) {
                fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
                fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
                sveOk.set(false);
            } else {
                fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
                fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
            }
            try {
                URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=" + fldPostanskiBroj.getText());
                BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                String json = "", line = null;
                while ((line = ulaz.readLine()) != null)
                    json = json+line;
                System.out.println(json);
                if (json.equals("NOT OK")) {
                        fldPostanskiBroj.getStyleClass().removeAll("poljeIspravno");
                        fldPostanskiBroj.getStyleClass().add("poljeNijeIspravno");
                }else{
                    Platform.runLater(() -> {
                    fldPostanskiBroj.getStyleClass().removeAll("poljeNijeIspravno");
                    fldPostanskiBroj.getStyleClass().add("poljeIspravno");
                    if (!sveOk.get()) return;

                        if (grad == null) grad = new Grad();
                        grad.setNaziv(fieldNaziv.getText());
                        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
                        grad.setDrzava(choiceDrzava.getValue());
                        grad.setPostanskiBroj(Integer.parseInt(fldPostanskiBroj.getText()));
                        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
                        stage.close();});
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }});
        t.start();
    }

    public void pretraga(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Jezici",l);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pretraga.fxml"),bundle);
        stage.setTitle(bundle.getString("pretrazi"));
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        stage.show();
    }
}
