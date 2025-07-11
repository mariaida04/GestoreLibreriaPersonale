import builder.Libro;
import org.junit.jupiter.api.*;
import singleton.Libreria;
import static org.junit.jupiter.api.Assertions.*;

public class RimozioneLibroTest {

    @BeforeEach
    public void svuota() {
        Libreria.getInstance().reset();
    }

    @Test
    public void testRimuoviLibro() {
        Libreria libreria = Libreria.getInstance();
        Libro libro = new Libro.Builder("Il piccolo principe","Antoine de Saint-Exupéry", "9785721938").build();

        libreria.aggiungiLibro(libro);

        assertEquals(1, libreria.getLibri().size());

        libreria.rimuoviLibro(libro);

        assertEquals(0, libreria.getLibri().size());
        assertFalse(libreria.getLibri().contains(libro));
    }

    @Test
    public void testRimuoviLibroNonPresente() {
        Libreria libreria = Libreria.getInstance();
        Libro libro = new Libro.Builder("Il piccolo principe","Antoine de Saint-Exupéry", "9785721938").build();

        libreria.rimuoviLibro(libro);

        assertEquals(0,libreria.getLibri().size());
    }
}
