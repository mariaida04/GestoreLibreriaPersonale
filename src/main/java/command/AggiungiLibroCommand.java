package command;

import builder.Libro;
import singleton.Libreria;

public class AggiungiLibroCommand implements Command{
    private final Libreria libreria;
    private final Libro libro;

    public AggiungiLibroCommand(Libreria libreria, Libro libro) {
        this.libreria = libreria;
        this.libro = libro;
    }

    @Override
    public void esegui() {
        libreria.aggiungiLibro(libro);
    }
}
