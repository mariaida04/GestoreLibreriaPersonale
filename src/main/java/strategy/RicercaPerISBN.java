package strategy;

import builder.Libro;
import java.util.ArrayList;
import java.util.List;

public class RicercaPerISBN implements LibreriaStrategy {
    private String isbn;

    public RicercaPerISBN(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public List<Libro> esegui(List<Libro> lib) {
        List<Libro> ret = new ArrayList<>();
        for (Libro l : lib) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                ret.add(l);
            }
        }
        return ret;
    }
}
