package ba.unsa.etf.rpr;/*package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class Zadatak2Test {
    private GeografijaDAO dao = GeografijaDAO.getInstance();

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }

    @Test
    public void gradSlika() {
        Grad grad = new Grad();
        grad.setNaziv("Sarajevo");
        grad.setSlika("sarajevo.png");
        assertEquals("sarajevo.png", grad.getSlika());
    }

    @Test
    void testIzmijeniGrad() {
        Grad bech = dao.glavniGrad("Austrija");
        String slika;
        if (bech.getSlika().equals("bech.png"))
            slika = "austrija.png";
        else
            slika = "bech.png";
        bech.setSlika(slika);
        dao.izmijeniGrad(bech);

        Grad b2 = dao.glavniGrad("Austrija");
        assertEquals(slika, b2.getSlika());
    }

    @Test
    void testDodajGrad() {
        Drzava francuska = dao.nadjiDrzavu("Francuska");
        Grad sarajevo = new Grad(0, "Sarajevo", 350000, francuska);
        // Ne moze "bascarsija.png" biti default vrijednost za sliku :D
        assertNotEquals("bascarsija.png", sarajevo.getSlika());
        sarajevo.setSlika("bascarsija.png");
        dao.dodajGrad(sarajevo);

        Grad s2 = null;
        for(Grad grad : dao.gradovi()) {
            if (grad.getNaziv().equals("Sarajevo"))
                s2 = grad;
        }
        assertNotNull(s2);

        assertEquals("bascarsija.png", s2.getSlika());
    }

    @Test
    void testNadjiGrad() {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava francuska = dao.nadjiDrzavu("Francuska");

        Grad sarajevo = new Grad(0, "Sarajevo", 350000, francuska);
        assertNotEquals("skenderija.png", sarajevo.getSlika());
        sarajevo.setSlika("skenderija.png");
        dao.dodajGrad(sarajevo);

        Grad s2 = dao.nadjiGrad("Sarajevo");
        assertNotNull(s2);

        assertEquals("skenderija.png", s2.getSlika());
    }
}
*/