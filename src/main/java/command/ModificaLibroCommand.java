package command;

import builder.Libro;
import singleton.Libreria;

public class ModificaLibroCommand implements Command {
    private final Libreria libreria;
    private final Libro libro;
    private final String isbn;

    public ModificaLibroCommand(Libreria libreria, Libro libro, String isbn) {
        this.libreria = libreria;
        this.libro = libro;
        this.isbn = isbn;
    }

    @Override
    public void esegui() {
        libreria.modificaLibro(isbn,libro);
    }
}
