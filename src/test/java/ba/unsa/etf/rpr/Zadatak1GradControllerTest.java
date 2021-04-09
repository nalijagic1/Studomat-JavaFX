package ba.unsa.etf.rpr;/*package ba.unsa.etf.rpr;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class Zadatak1GradControllerTest {
    Stage theStage;
    GradController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        dao.vratiBazuNaDefault();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        ctrl = new GradController(null, dao.drzave());
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }


    @Test
    public void testPoljePostanskiBroj(FxRobot robot) {
        TextField fld = robot.lookup("#fldPostanskiBroj").queryAs(TextField.class);
        assertNotNull(fld);
    }

    @Test
    public void testValidacijaPostanskiBroj(FxRobot robot) {
        // Upisujemo poštanski broj
        robot.clickOn("#fldPostanskiBroj");
        robot.write("12345abc");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        // Poštanski broj nevalidan
        TextField fld = robot.lookup("#fldPostanskiBroj").queryAs(TextField.class);
        Background bg = fld.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
    }

    @Test
    public void testValidacijaPostanskiBroj2(FxRobot robot) {
        // Upisujemo poštanski broj
        robot.clickOn("#fldPostanskiBroj");
        robot.write("71000");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        // Čekamo da se eventualno završi validacija preko mreže (zadatak 6)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextField fld = robot.lookup("#fldPostanskiBroj").queryAs(TextField.class);
        Background bg = fld.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("adff2f"))
                colorFound = true;
        assertTrue(colorFound);
    }

    @Test
    public void testZadatak6(FxRobot robot) {
        // Upisujemo poštanski broj
        robot.clickOn("#fldPostanskiBroj");
        robot.write("12345");
        // Poštanski broj je petocifren, ali će pasti internet validaciju
        // Zato ovaj test prolazi tek nakon što se uradi Zadatak 6

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        // Čekamo da se eventualno završi validacija preko mreže (zadatak 6)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextField fld = robot.lookup("#fldPostanskiBroj").queryAs(TextField.class);
        Background bg = fld.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
    }

    @Test
    public void testVracaPostanskiBroj(FxRobot robot) {
        // Upisujemo grad
        robot.clickOn("#fieldNaziv");
        robot.write("Sarajevo");
        robot.clickOn("#fieldBrojStanovnika");
        robot.write("350000");

        robot.clickOn("#fldPostanskiBroj");
        robot.write("78404");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Grad grad = ctrl.getGrad();
        assertEquals(78404, grad.getPostanskiBroj());
    }
}

*/