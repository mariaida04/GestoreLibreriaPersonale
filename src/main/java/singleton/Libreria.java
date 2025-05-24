package singleton;

import builder.Libro;
import strategy.LibreriaStrategy;

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
        if (libri.contains(libro)) {
            libri.remove(libro);
        }
        else {
            System.out.println("Libro non presente!");
        }
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
        StringBuilder sb = new StringBuilder();
        for (Libro l : libri) {
            sb.append(l.toString()).append("\n");
        }
        return sb.toString();
    }

    public List<Libro> eseguiStrategy(LibreriaStrategy strategia) {
        return strategia.esegui(libri);
    }
}
