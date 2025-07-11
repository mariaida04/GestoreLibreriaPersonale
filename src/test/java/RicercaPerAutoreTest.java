import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import org.junit.jupiter.api.*;
import strategy.LibreriaStrategy;
import strategy.RicercaPerAutore;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RicercaPerAutoreTest {

    private Libro l1,l2,l3,l4;
    private List<Libro> lista;

    @BeforeEach
    public void listaLibri() {
        l1 = new Libro.Builder("harry Potter e la pietra filosofale", "j k rowling", "9788448785").genere("Fantasy")
                .stato(StatoLettura.COMPLETATO).valutazione(Valutazione.DUE_STELLE).build();
        l2 = new Libro.Builder("il codice da Vinci", "Dan Brown", "9789476585").genere("Thriller")
                .stato(StatoLettura.IN_LETTURA).valutazione(Valutazione.UNA_STELLA).build();
        l3 = new Libro.Builder("Harry Potter e la pietra filosofale", " J.K. Rowling ", "9784137885").genere("Fantasy")
                .stato(StatoLettura.COMPLETATO).valutazione(Valutazione.CINQUE_STELLE).build();
        l4 = new Libro.Builder("1984", "George Orwell", "9787548785").genere("Romanzo")
                .stato(StatoLettura.DA_LEGGERE).valutazione(Valutazione.CINQUE_STELLE).build();

        lista = new ArrayList<>();
        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);
    }

    @Test
    public void testRicercaAutore() {
        LibreriaStrategy strategy = new RicercaPerAutore("jkRowling");
        List<Libro> risultato = strategy.esegui(lista);

        assertEquals(2, risultato.size());
        assertTrue(risultato.contains(l1));
        assertFalse(risultato.contains(l2));
        assertTrue(risultato.contains(l3));
        assertFalse(risultato.contains(l4));
    }

    @Test
    public void testRicercaAutoreNonPresente() {
        LibreriaStrategy strategy = new RicercaPerAutore("Italo Calvino");
        List<Libro> risultato = strategy.esegui(lista);

        assertTrue(risultato.isEmpty(), "La lista dovrebbe essere vuota se l'autore non è presente.");
    }
}
