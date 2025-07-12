package singleton;

import builder.Libro;
import repository.ArchivioLibreria;
import strategy.LibreriaStrategy;

import java.util.ArrayList;
import java.util.List;

public class Libreria {
    private static Libreria instance = null;
    private List<Libro> libri = new ArrayList<>();

    private Libreria() {
        this.libri = ArchivioLibreria.carica();
    }

    public static Libreria getInstance(){
        if (instance == null) {
            instance = new Libreria();
        }
        return instance;
    }

    public void aggiungiLibro(Libro libro) {
        for (Libro l : libri) {
            if (l.getIsbn().trim().equalsIgnoreCase(libro.getIsbn().trim())) {
                System.out.println("Libro già presente con ISBN: " + libro.getIsbn());
                return;
            }
        }
        libri.add(libro);
        ArchivioLibreria.salva(libri);
    }

    public void rimuoviLibro(Libro libro) {
        if (libri.contains(libro)) {
            libri.remove(libro);
            ArchivioLibreria.salva(libri);
        }
        else {
            System.out.println("Libro non presente!");
        }
    }

    public boolean modificaLibro(String isbn, Libro nuovo) {
        for (int i = 0; i < libri.size(); i++) {
            if (libri.get(i).getIsbn().trim().equalsIgnoreCase(isbn.trim())) {
                libri.set(i,nuovo);
                ArchivioLibreria.salva(libri);
                return true;
            }
        }
        return false;
    }

    public List<Libro> getLibri() {
        return new ArrayList<>(libri);  //per sicurezza viene restituita la copia
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

    public void reset() {
        libri.clear();
        ArchivioLibreria.salva(libri);
    }

    public Libro ottieniLibroDaIsbn(String isbn) {
        for (Libro l : libri) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                return l;
            }
        }
        return null;
    }
}
