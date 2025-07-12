import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import factoryMethod.LibroFactory;
import org.junit.jupiter.api.*;
import strategy.LibreriaStrategy;
import strategy.OrdinaPerTitolo;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OrdinaPerTitoloTest {

    @Test
    public void testOrdinamentoAlfabetico() {
        Libro l1 = LibroFactory.creaLibro("Il piccolo principe", "Antoine de Saint-Exupéry", "9788448785","Fiaba",
                Valutazione.DUE_STELLE,StatoLettura.COMPLETATO);
        Libro l2 = LibroFactory.creaLibro("il codice da Vinci", "Dan Brown", "9789476585","Thriller",
                Valutazione.UNA_STELLA,StatoLettura.IN_LETTURA);
        Libro l3 = LibroFactory.creaLibro("Harry Potter e la pietra filosofale", "J.K. Rowling", "9784137885","Fantasy",
                Valutazione.CINQUE_STELLE,StatoLettura.COMPLETATO);
        Libro l4 = LibroFactory.creaLibro("1984", "George Orwell", "9787548785","Romanzo",
                Valutazione.CINQUE_STELLE,StatoLettura.COMPLETATO);

        List<Libro> lista = new ArrayList<>();
        lista.add(l1);
        lista.add(l2);
        lista.add(l3);
        lista.add(l4);

        LibreriaStrategy strategy = new OrdinaPerTitolo();
        List<Libro> ordinati = strategy.esegui(lista);

        assertEquals(l4, ordinati.get(0));
        assertEquals(l3, ordinati.get(1));
        assertEquals(l2, ordinati.get(2));
        assertEquals(l1, ordinati.get(3));

    }
}
