import builder.Libro;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreazioneLibroTest {

    @Test
    public void testCreazioneLibroSenzaISBN() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
                new  Libro.Builder("Il piccolo principe", "Antoine de Saint-Exupéry", " ").build();
    });

        assertEquals("ISBN obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroSenzaTitolo() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new  Libro.Builder(" ", "Antoine de Saint-Exupéry", "9788663741 ").build();
        });

        assertEquals("Titolo obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroSenzaAutore() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new  Libro.Builder("Il piccolo principe", " ", "9788663741").build();
        });

        assertEquals("Autore obbligatorio", e.getMessage());
    }

    @Test
    public void testCreazioneLibroAutoreNull() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new  Libro.Builder("Il piccolo principe", null, "9788663741").build();
        });

        assertEquals("Autore obbligatorio", e.getMessage());
    }
}
