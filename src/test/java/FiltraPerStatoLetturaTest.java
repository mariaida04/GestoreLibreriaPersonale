import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import org.junit.jupiter.api.*;
import strategy.FiltraPerStatoLettura;
import strategy.LibreriaStrategy;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FiltraPerStatoLetturaTest {

    private Libro l1,l2,l3,l4,l5;
    private List<Libro> lista;

    @BeforeEach
    public void listaLibri() {
        l1 = new Libro.Builder("Il piccolo principe", "Antoine de Saint-Exupéry", "9788448785").genere("Fiaba")
                .stato(StatoLettura.COMPLETATO).valutazione(Valutazione.DUE_STELLE).build();
        l2 = new Libro.Builder("il codice da Vinci", "Dan Brown", "9789476585").genere("Thriller")
                .stato(StatoLettura.IN_LETTURA).valutazione(Valutazione.UNA_STELLA).build();
        l3 = new Libro.Builder("Harry Potter e la pietra filosofale", "J.K. Rowling", "9784137885").genere("Fantasy")
                .stato(StatoLettura.COMPLETATO).valutazione(Valutazione.CINQUE_STELLE).build();
        l4 = new Libro.Builder("1984", "George Orwell", "9787548785").genere("Romanzo")
                .stato(StatoLettura.COMPLETATO).valutazione(Valutazione.CINQUE_STELLE).build();
        l5 = new Libro.Builder("Piccole donne", "Louisa May Alcott", "9787572635").genere("Romanzo").build();


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
