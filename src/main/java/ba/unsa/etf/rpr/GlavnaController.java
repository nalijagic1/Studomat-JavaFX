package ba.unsa.etf.rpr;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class GlavnaController {

    public TableView<Grad> tableViewGradovi;
    public TableColumn colGradId;
    public TableColumn colGradNaziv;
    public TableColumn colGradStanovnika;
    public TableColumn colPosta;
    public TableColumn<Grad, String> colGradDrzava;
    private GeografijaDAO dao;
    private static Locale l = Locale.getDefault();
    private ObservableList<Grad> listGradovi;

    public GlavnaController() {
        dao = GeografijaDAO.getInstance();
        listGradovi = FXCollections.observableArrayList(dao.gradovi());
    }

    @FXML
    public void initialize() {
        tableViewGradovi.setItems(listGradovi);
        colGradId.setCellValueFactory(new PropertyValueFactory("id"));
        colGradNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));
        colGradStanovnika.setCellValueFactory(new PropertyValueFactory("brojStanovnika"));
        colPosta.setCellValueFactory(new PropertyValueFactory("postanskiBroj"));
        colGradDrzava.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDrzava().getNaziv()));
    }

    public void actionDodajGrad(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Jezici",l);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"), bundle);
            GradController gradController = new GradController(null, dao.drzave(),l);
            loader.setController(gradController);
            root = loader.load();
            stage.setTitle(bundle.getString("grad"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding(event -> {
                Grad grad = gradController.getGrad();
                if (grad != null) {
                    // Ovdje ne smije doći do izuzetka jer se prozor neće zatvoriti
                    try {
                        dao.dodajGrad(grad);
                        listGradovi.setAll(dao.gradovi());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionDodajDrzavu(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Jezici",l);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"), bundle);
            DrzavaController drzavaController = new DrzavaController(null, dao.gradovi());
            loader.setController(drzavaController);
            root = loader.load();
            stage.setTitle(bundle.getString("drzava"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding(event -> {
                Drzava drzava = drzavaController.getDrzava();
                if (drzava != null) {
                    // Ovdje ne smije doći do izuzetka, jer se prozor neće zatvoriti
                    try {
                        dao.dodajDrzavu(drzava);
                        listGradovi.setAll(dao.gradovi());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionIzmijeniGrad(ActionEvent actionEvent) {
        Grad grad = tableViewGradovi.getSelectionModel().getSelectedItem();
        if (grad == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Jezici",l);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"), bundle);
            GradController gradController = new GradController(grad, dao.drzave(),l);
            loader.setController(gradController);
            root = loader.load();
            stage.setTitle(bundle.getString("grad"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding(event -> {
                Grad noviGrad = gradController.getGrad();
                if (noviGrad != null) {
                    // Ovdje ne smije doći do izuzetka jer se prozor neće zatvoriti
                    try {
                        dao.izmijeniGrad(noviGrad);
                        listGradovi.setAll(dao.gradovi());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionObrisiGrad(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("Jezici",l);
        Grad grad = tableViewGradovi.getSelectionModel().getSelectedItem();
        if (grad == null) return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("brisanje"));
        alert.setHeaderText(bundle.getString("delete")+" " + grad.getNaziv());
        alert.setContentText(bundle.getString("potvrda")+" " + grad.getNaziv() + "?");
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dao.obrisiGrad(grad);
            listGradovi.setAll(dao.gradovi());
        }
    }

    public void dajIzvjestaj(ActionEvent actionEvent) {
        try {
            new GradoviReport().showReport(dao.getConn());
        } catch (JRException e1) {
            e1.printStackTrace();
        }


    }


    // Metoda za potrebe testova, vraća bazu u polazno stanje
    public void resetujBazu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        dao = GeografijaDAO.getInstance();
    }

    public void promijeniJezik(ActionEvent actionEvent) {
        System.out.println(l);
        ChoiceDialog jezici = new ChoiceDialog();
        jezici.getItems().add("English");
        jezici.getItems().add("Bosanski");
        jezici.showAndWait();

        if (jezici.getSelectedItem() == null)return;
        else if(jezici.getSelectedItem().equals("Bosanski")){
            l =  new Locale("bs","BA");
        }else{
            l = new Locale("en","US");
        }
        Stage PRIJASNJA = (Stage) tableViewGradovi.getScene().getWindow();
        PRIJASNJA.close();
        Stage stage = new Stage();
        Parent root = null;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Jezici",l);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"), bundle);
            GlavnaController ctrl = new GlavnaController();
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle(bundle.getString("svjestkiGradovi"));
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}