package singleton;

import builder.Libro;

import java.util.ArrayList;
import java.util.List;

public class Libreria {
    private static Libreria instance = null;
    private List<Libro> libri = new ArrayList<>();

    private Libreria() {}

    public static Libreria getInstance(){
        if (instance == null) {
            instance = new Libreria();
        }
        return instance;
    }

    public void aggiungiLibro(Libro libro) {
        if (!libri.contains(libro)) {
            libri.add(libro);
            }
    }

    public void rimuoviLibro(Libro libro) {
        libri.remove(libro);
    }

    public boolean modificaLibro(String isbn, Libro nuovo) {
        for (int i = 0; i < libri.size(); i++) {
            if (libri.get(i).getIsbn().equals(isbn)) {
                libri.set(i,nuovo);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Libreria{" + libri +
                '}';
    }
}
