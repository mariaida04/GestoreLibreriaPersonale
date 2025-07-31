import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import factoryMethod.LibroFactory;
import factoryMethod.LibroConcreteFactory;
import org.junit.jupiter.api.*;
import strategy.FiltraPerStatoLettura;
import strategy.LibreriaStrategy;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FiltraPerStatoLetturaTest {

    private LibroFactory factory;
    private Libro l1,l2,l3,l4,l5;
    private List<Libro> lista;

    @BeforeEach
    public void listaLibri() {

        factory = new LibroConcreteFactory();
        l1 = factory.creaLibro("Il piccolo principe", "Antoine de Saint-Exupéry", "9788448785","Fiaba",
                 Valutazione.DUE_STELLE,StatoLettura.COMPLETATO);
        l2 = factory.creaLibro("il codice da Vinci", "Dan Brown", "9789476585","Thriller",
                Valutazione.UNA_STELLA,StatoLettura.IN_LETTURA);
        l3 = factory.creaLibro("Harry Potter e la pietra filosofale", "J.K. Rowling", "9784137885","Fantasy",
                Valutazione.CINQUE_STELLE,StatoLettura.COMPLETATO);
        l4 = factory.creaLibro("1984", "George Orwell", "9787548785","Romanzo",
                Valutazione.CINQUE_STELLE,StatoLettura.COMPLETATO);
        l5 = factory.creaLibro("Piccole donne", "Louisa May Alcott", "9787572635","Romanzo",null,null);

        lista = new ArrayList<>();
        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);
        lista.add(l5);
    }

    @Test
    public void testFiltroStato() {

        LibreriaStrategy strategy = new FiltraPerStatoLettura(StatoLettura.COMPLETATO);
        List<Libro> risultato = strategy.esegui(lista);

        assertEquals(3,risultato.size(), "Il numero di libri completati non corrisponde.");
        assertTrue(risultato.contains(l1));
        assertFalse(risultato.contains(l2));
        assertTrue(risultato.contains(l3));
        assertTrue(risultato.contains(l4));
        assertFalse(risultato.contains(l5));
    }

    @Test
    public void testFiltroStatoNonPresente() {

        LibreriaStrategy strategy = new FiltraPerStatoLettura(StatoLettura.DA_LEGGERE);
        List<Libro> risultato = strategy.esegui(lista);

        assertTrue(risultato.isEmpty(), "La lista dovrebbe essere vuota in quanto lo stato non è presente");
    }
}
