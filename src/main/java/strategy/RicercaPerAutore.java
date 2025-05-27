package strategy;

import builder.Libro;

import java.util.ArrayList;
import java.util.List;

public class RicercaPerAutore implements LibreriaStrategy {
    private String autore;

    public RicercaPerAutore(String autore) {
        this.autore = autore;
    }

    @Override
    public List<Libro> esegui(List<Libro> lib) {
        List<Libro> ret = new ArrayList<>();
        for (Libro l : lib) {
            if ((l.getAutore().toLowerCase().replaceAll("[\\s.-]", "")    //per evitare che non trovi J.K.Rowling se cercato senza punti ad es.
                    .equals(autore.toLowerCase().replaceAll("[\\s.-]", "")))) {
                ret.add(l);
            }
        }
        return ret;
    }
}
