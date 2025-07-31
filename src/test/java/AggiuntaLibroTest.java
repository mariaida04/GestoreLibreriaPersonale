import builder.Libro;
import factoryMethod.LibroFactory;
import factoryMethod.LibroConcreteFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singleton.Libreria;
import static org.junit.jupiter.api.Assertions.*;

public class AggiuntaLibroTest {

    private LibroFactory factory;

    @BeforeEach
    public void svuota() {
        factory = new LibroConcreteFactory();
        Libreria.getInstance().reset();
    }

    @Test
    public void testAggiuntaLibro() {
        Libreria libreria = Libreria.getInstance();

        Libro libro = factory.creaLibro("I promessi sposi", "Alessandro Manzoni", "9785721938","Romanzo",null,null);
        libreria.aggiungiLibro(libro);

        assertEquals(1, libreria.getLibri().size());
        assertTrue(libreria.getLibri().contains(libro));
    }

    @Test
    public void testAggiuntaLibriUgualeISBN() {
        Libreria libreria = Libreria.getInstance();

        Libro l1 = factory.creaLibro("I promessi sposi", "Alessandro Manzoni", "9785721938","Romanzo",null,null);
        Libro l2 = factory.creaLibro("Il piccolo principe", "Antoine de Saint-Exupéry", "9785721938","Fiaba",null,null);

        libreria.aggiungiLibro(l1);
        libreria.aggiungiLibro(l2);

        assertEquals(1, libreria.getLibri().size(), "Solo un libro dovrebbe essere presente.");
        assertTrue(libreria.getLibri().contains(l1), "Il primo libro deve essere presente.");
    }
}
