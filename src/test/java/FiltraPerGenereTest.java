import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import org.junit.jupiter.api.*;
import strategy.FiltraPerGenere;
import strategy.LibreriaStrategy;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FiltraPerGenereTest {

    @Test
    public void testFiltroGenere() {
        Libro l1 = new Libro.Builder("Titolo A", "Autore A", "9788804563").genere("Romanzo").valutazione(Valutazione.CINQUE_STELLE).stato(StatoLettura.IN_LETTURA).build();
        Libro l2 = new Libro.Builder("Titolo B", "Autore B", "9788646184").genere("Giallo").valutazione(Valutazione.CINQUE_STELLE).stato(StatoLettura.IN_LETTURA).build();
        Libro l3 = new Libro.Builder("Titolo C", "Autore C", "9788809124").genere("Fantasy").valutazione(Valutazione.CINQUE_STELLE).stato(StatoLettura.IN_LETTURA).build();
        Libro l4 = new Libro.Builder("Titolo D", "Autore D", "9788866165").genere("romanzo").valutazione(Valutazione.CINQUE_STELLE).stato(StatoLettura.IN_LETTURA).build();

        List<Libro> lista = new ArrayList<>();
        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);

        LibreriaStrategy strategy = new FiltraPerGenere("romanzo");
        List<Libro> risultato = strategy.esegui(lista);

        assertEquals(2, risultato.size());
        assertTrue(risultato.contains(l1));
        assertFalse(risultato.contains(l2));
        assertFalse(risultato.contains(l3));
        assertTrue(risultato.contains(l4));
    }
}
