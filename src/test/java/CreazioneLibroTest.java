import factoryMethod.LibroFactory;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreazioneLibroTest {

    @Test
    public void testCreazioneLibroSenzaISBN() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
                LibroFactory.creaLibro("Il piccolo principe", "Antoine de Saint-Exupéry", " ",null,null,null);
    });

        assertEquals("ISBN obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroSenzaTitolo() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            LibroFactory.creaLibro(" ", "Antoine de Saint-Exupéry", "9788663741 ",null,null,null);
        });

        assertEquals("Titolo obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroSenzaAutore() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            LibroFactory.creaLibro("Il piccolo principe", " ", "9788663741",null,null,null);
        });

        assertEquals("Autore obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroAutoreNull() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            LibroFactory.creaLibro("Il piccolo principe", null, "9788663741",null,null,null);
        });

        assertEquals("Autore obbligatorio", e.getMessage());
    }
}
