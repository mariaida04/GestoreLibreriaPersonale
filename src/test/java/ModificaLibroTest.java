import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import factoryMethod.LibroFactory;
import factoryMethod.LibroConcreteFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.Libreria;
import static org.junit.jupiter.api.Assertions.*;

public class ModificaLibroTest {

    private LibroFactory factory;

    @BeforeEach
    public void svuota() {
        factory = new LibroConcreteFactory();
        Libreria.getInstance().reset();
    }

    @Test
    public void testModificaLibro() {
        Libreria libreria = Libreria.getInstance();

        Libro originale = factory.creaLibro("I promessi sposi", "Alessandro Manzoni", "9785721938","Romanzo",
                Valutazione.TRE_STELLE,StatoLettura.IN_LETTURA);
        libreria.aggiungiLibro(originale);

        Libro modificato = factory.creaLibro("I promessi sposi", "Alessandro Manzoni", "9785721938","Romanzo",
                Valutazione.CINQUE_STELLE,StatoLettura.COMPLETATO);
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

        Libro nuovo = factory.creaLibro("I promessi sposi", "Alessandro Manzoni", "9785721938","Romanzo",
                Valutazione.TRE_STELLE,StatoLettura.IN_LETTURA);

        boolean modifica = libreria.modificaLibro("9785721938", nuovo);

        assertFalse(modifica);
        assertEquals(0,libreria.getLibri().size());
    }
}
