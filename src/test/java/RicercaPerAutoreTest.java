import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import factoryMethod.LibroFactory;
import factoryMethod.LibroConcreteFactory;
import org.junit.jupiter.api.*;
import strategy.LibreriaStrategy;
import strategy.RicercaPerAutore;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RicercaPerAutoreTest {

    private LibroFactory factory;
    private Libro l1,l2,l3,l4;
    private List<Libro> lista;

    @BeforeEach
    public void listaLibri() {
        factory = new LibroConcreteFactory();
        l1 = factory.creaLibro("harry potter e la camera dei segreti", "j k rowling", "9788448785","Fantasy",
                Valutazione.DUE_STELLE,StatoLettura.COMPLETATO);
        l2 = factory.creaLibro("il codice da Vinci", "Dan Brown", "9789476585","Thriller",
                Valutazione.UNA_STELLA,StatoLettura.IN_LETTURA);
        l3 = factory.creaLibro("Harry Potter e la pietra filosofale", "J.K. Rowling", "9784137885","Fantasy",
                Valutazione.CINQUE_STELLE,StatoLettura.COMPLETATO);
        l4 = factory.creaLibro("1984", "George Orwell", "9787548785","Romanzo",
                Valutazione.CINQUE_STELLE,StatoLettura.COMPLETATO);

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
