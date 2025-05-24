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
            if (l.getAutore().equalsIgnoreCase(autore)) {
                ret.add(l);
            }
        }
        return ret;
    }
}
