package factoryMethod;

import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;

public interface LibroFactory {
    //factory method
    Libro creaLibro(String titolo, String autore, String isbn, String genere,
                                  Valutazione valutazione, StatoLettura stato);
}
