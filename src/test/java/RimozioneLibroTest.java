import builder.Libro;
import factoryMethod.LibroConcreteFactory;
import factoryMethod.LibroFactory;
import org.junit.jupiter.api.*;
import singleton.Libreria;
import static org.junit.jupiter.api.Assertions.*;

public class RimozioneLibroTest {

    private LibroFactory factory;

    @BeforeEach
    public void svuota() {
        factory = new LibroConcreteFactory();
        Libreria.getInstance().reset();
    }

    @Test
    public void testRimuoviLibro() {
        Libreria libreria = Libreria.getInstance();
        Libro libro = factory.creaLibro("Il piccolo principe","Antoine de Saint-Exupéry", "9785721938",null,null,null);

        libreria.aggiungiLibro(libro);

        assertEquals(1, libreria.getLibri().size());

        libreria.rimuoviLibro(libro);

        assertEquals(0, libreria.getLibri().size());
        assertFalse(libreria.getLibri().contains(libro));
    }

    @Test
    public void testRimuoviLibroNonPresente() {
        Libreria libreria = Libreria.getInstance();
        Libro libro = factory.creaLibro("Il piccolo principe","Antoine de Saint-Exupéry", "9785721938",null,null,null);

        libreria.rimuoviLibro(libro);

        assertEquals(0,libreria.getLibri().size());
    }
}
