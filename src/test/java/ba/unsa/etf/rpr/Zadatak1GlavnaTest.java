package ba.unsa.etf.rpr;/*package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class Zadatak1GlavnaTest {
    Stage theStage;
    GlavnaController ctrl;
    GeografijaDAO dao = GeografijaDAO.getInstance();

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/glavna.fxml"));
        ctrl = new GlavnaController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();

        stage.toFront();

        theStage = stage;
    }

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }

    @AfterEach
    public void zatvoriDrzavu(FxRobot robot) {
        if (robot.lookup("#btnCancel").tryQuery().isPresent())
            robot.clickOn("#btnCancel");
    }

    @Test
    public void testDodajGradPostanskiBroj(FxRobot robot) {
        // Otvaranje forme za dodavanje
        robot.clickOn("#btnDodajGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        // Postoji li fieldNaziv
        robot.clickOn("#fieldNaziv");
        robot.write("Sarajevo");

        robot.clickOn("#fieldBrojStanovnika");
        robot.write("350000");

        robot.clickOn("#fldPostanskiBroj");
        robot.write("71000");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je Sarajevo dodano u bazu?
        GeografijaDAO dao = GeografijaDAO.getInstance();
        assertEquals(6, dao.gradovi().size());

        boolean pronadjeno = false;
        for(Grad grad : dao.gradovi())
            if (grad.getNaziv().equals("Sarajevo") && grad.getPostanskiBroj() == 71000)
                pronadjeno = true;
        assertTrue(pronadjeno);
    }

    @Test
    public void testIzmijeniGradPostanskiBroj(FxRobot robot) {
        // 74211 ne smije biti default poštanski broj za Graz jer je to "varanje"
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad graz = dao.nadjiGrad("Graz");
        assertNotEquals(74211, graz.getPostanskiBroj());

        // Mijenjamo grad Graz
        robot.clickOn("Graz");
        robot.clickOn("#btnIzmijeniGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        robot.clickOn("#fldPostanskiBroj");
        // Brišemo stari sadržaj polja, ako postoji
        robot.press(KeyCode.END).release(KeyCode.END);
        for (int i=0; i<5; i++)
            robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.write("74211");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        // Da li je promijenjen poštanski broj Graza?
        graz = dao.nadjiGrad("Graz");
        assertEquals(74211, graz.getPostanskiBroj());
    }

    @Test
    public void testKolonaPostanskiBroj(FxRobot robot) {
        // Postavljamo poštanski broj za Graz da možemo testirati tabelu
        robot.clickOn("Graz");
        robot.clickOn("#btnIzmijeniGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        robot.clickOn("#fldPostanskiBroj");
        // Brišemo stari sadržaj polja, ako postoji
        robot.press(KeyCode.END).release(KeyCode.END);
        for (int i=0; i<5; i++)
            robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.write("74211");

        // Klik na dugme Ok
        robot.clickOn("#btnOk");

        TableView<Grad> tableViewGradovi = robot.lookup("#tableViewGradovi").queryAs(TableView.class);
        assertNotNull(tableViewGradovi);

        boolean found = false;
        for (TableColumn column : tableViewGradovi.getColumns()) {
            if (column.getText().equals("Poštanski broj")) {
                found = true;
            }
        }
        assertTrue(found);

        // Klikamo na Graz koristeći poštanski broj
        robot.clickOn("74211");
        robot.clickOn("#btnIzmijeniGrad");

        // Čekamo da dijalog postane vidljiv
        robot.lookup("#fieldNaziv").tryQuery().isPresent();

        theStage.hide();

        TextField fld = robot.lookup("#fieldNaziv").queryAs(TextField.class);
        assertEquals("Graz", fld.getText());
    }
}
*/