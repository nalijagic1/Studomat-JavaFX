package ba.unsa.etf.rpr;/*package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class Zadatak1Test {
    private GeografijaDAO dao = GeografijaDAO.getInstance();

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }

    @Test
    public void gradSlika() {
        Grad grad = new Grad();
        grad.setNaziv("Sarajevo");
        grad.setPostanskiBroj(71000);
        assertEquals(71000, grad.getPostanskiBroj());
    }

    @Test
    void testIzmijeniGrad() {
        Grad bech = dao.glavniGrad("Austrija");
        int broj;
        if (bech.getPostanskiBroj() == 12345)
            broj = 54321;
        else
            broj = 12345;
        bech.setPostanskiBroj(broj);
        dao.izmijeniGrad(bech);

        Grad b2 = dao.glavniGrad("Austrija");
        assertEquals(broj, b2.getPostanskiBroj());
    }

    @Test
    void testDodajGrad() {
        Drzava francuska = dao.nadjiDrzavu("Francuska");
        Grad sarajevo = new Grad(0, "Sarajevo", 350000, francuska);
        // Ne moze 33000 biti default vrijednost za poštanski broj :)
        assertNotEquals(33000, sarajevo.getPostanskiBroj());
        sarajevo.setPostanskiBroj(33000);
        dao.dodajGrad(sarajevo);

        Grad s2 = null;
        for(Grad grad : dao.gradovi()) {
            if (grad.getNaziv().equals("Sarajevo"))
                s2 = grad;
        }
        assertNotNull(s2);

        assertEquals(33000, s2.getPostanskiBroj());
    }

    @Test
    void testNadjiGrad() {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava francuska = dao.nadjiDrzavu("Francuska");

        Grad sarajevo = new Grad(0, "Sarajevo", 350000, francuska);
        assertNotEquals(71000, sarajevo.getPostanskiBroj());
        sarajevo.setPostanskiBroj(71000);
        dao.dodajGrad(sarajevo);

        Grad s2 = dao.nadjiGrad("Sarajevo");
        assertNotNull(s2);

        assertEquals(71000, s2.getPostanskiBroj());
    }
}
*/