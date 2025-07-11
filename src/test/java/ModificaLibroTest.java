import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.Libreria;
import static org.junit.jupiter.api.Assertions.*;

public class ModificaLibroTest {

    @BeforeEach
    public void svuota() {
        Libreria.getInstance().reset();
    }

    @Test
    public void testModificaLibro() {
        Libreria libreria = Libreria.getInstance();

        Libro originale = new Libro.Builder("I promessi sposi", "Alessandro Manzoni", "9785721938").genere("Romanzo")
                .valutazione(Valutazione.TRE_STELLE).stato(StatoLettura.IN_LETTURA).build();
        libreria.aggiungiLibro(originale);

        Libro modificato = new Libro.Builder("I promessi sposi", "Alessandro Manzoni", "9785721938").genere("Romanzo")
                .valutazione(Valutazione.CINQUE_STELLE).stato(StatoLettura.COMPLETATO).build();
        boolean modifica = libreria.modificaLibro("9785721938",modificato);

        assertTrue(modifica);
        assertEquals(1,libreria.getLibri().size());
        Libro presente = libreria.getLibri().get(0);
        assertEquals(Valutazione.CINQUE_STELLE, presente.getValutazione());
        assertEquals(StatoLettura.COMPLETATO, presente.getStato());
    }

    @Test
    public void testModificaLibroNonPresente() {
        Libreria libreria = Libreria.getInstance();

        Libro nuovo = new Libro.Builder("I promessi sposi", "Alessandro Manzoni", "9785721938").genere("Romanzo")
                .valutazione(Valutazione.TRE_STELLE).stato(StatoLettura.IN_LETTURA).build();

        boolean modifica = libreria.modificaLibro("9785721938", nuovo);

        assertFalse(modifica);
        assertEquals(0,libreria.getLibri().size());
    }
}
