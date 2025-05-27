package command;

import builder.Libro;
import singleton.Libreria;

public class RimuoviLibroCommand implements Command{
    private final Libreria libreria;
    private final Libro libro;

    public RimuoviLibroCommand(Libreria libreria, Libro libro) {
        this.libreria = libreria;
        this.libro = libro;
    }

    @Override
    public void esegui() {
        libreria.rimuoviLibro(libro);
    }
}
