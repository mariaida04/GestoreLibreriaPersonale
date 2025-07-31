import factoryMethod.LibroFactory;
import factoryMethod.LibroConcreteFactory;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreazioneLibroTest {

    @Test
    public void testCreazioneLibroSenzaISBN() {
        LibroFactory factory = new LibroConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
                factory.creaLibro("Il piccolo principe", "Antoine de Saint-Exupéry", " ",null,null,null);
    });

        assertEquals("ISBN obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroSenzaTitolo() {
        LibroFactory factory = new LibroConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
                factory.creaLibro(" ", "Antoine de Saint-Exupéry", "9788663741 ",null,null,null);
        });

        assertEquals("Titolo obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroSenzaAutore() {
        LibroFactory factory = new LibroConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
                factory.creaLibro("Il piccolo principe", " ", "9788663741",null,null,null);
        });

        assertEquals("Autore obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroAutoreNull() {
        LibroFactory factory = new LibroConcreteFactory();
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
                factory.creaLibro("Il piccolo principe", null, "9788663741",null,null,null);
        });

        assertEquals("Autore obbligatorio", e.getMessage());
    }
}
