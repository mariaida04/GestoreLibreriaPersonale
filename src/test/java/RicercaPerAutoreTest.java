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

    @Test
    public void testRicercaAutore() {
        Libro l1 = new Libro.Builder("harry Potter e la pietra filosofale", "j k rowling", "9788448785").genere("Fantasy")
                .stato(StatoLettura.COMPLETATO).valutazione(Valutazione.DUE_STELLE).build();
        Libro l2 = new Libro.Builder("il codice da Vinci", "Dan Brown", "9789476585").genere("Thriller")
                .stato(StatoLettura.IN_LETTURA).valutazione(Valutazione.UNA_STELLA).build();
        Libro l3 = new Libro.Builder("Harry Potter e la pietra filosofale", "J.K. Rowling", "9784137885").genere("Fantasy")
                .stato(StatoLettura.COMPLETATO).valutazione(Valutazione.CINQUE_STELLE).build();
        Libro l4 = new Libro.Builder("1984", "George Orwell", "9787548785").genere("Romanzo")
                .stato(StatoLettura.DA_LEGGERE).valutazione(Valutazione.CINQUE_STELLE).build();

        List<Libro> lista = new ArrayList<>();
        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);

        LibreriaStrategy strategy = new RicercaPerAutore("jkRowling");
        List<Libro> risultato = strategy.esegui(lista);

        assertEquals(2, risultato.size());
        assertTrue(risultato.contains(l1));
        assertFalse(risultato.contains(l2));
        assertTrue(risultato.contains(l3));
        assertFalse(risultato.contains(l4));
    }
}
