package factoryMethod;

import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;

public class LibroConcreteFactory implements LibroFactory {
    @Override
    public Libro creaLibro(String titolo, String autore, String isbn, String genere,
                                  Valutazione valutazione, StatoLettura stato)
    {   return new Libro.Builder(titolo, autore, isbn).genere(genere).valutazione(valutazione).stato(stato).build();
    }
}
