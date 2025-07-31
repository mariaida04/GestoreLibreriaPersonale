package strategy;

import builder.Libro;
import java.util.ArrayList;
import java.util.List;

public class RicercaPerTitolo implements LibreriaStrategy {
    private String titolo;

    public RicercaPerTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public List<Libro> esegui(List<Libro> lib) {
        List<Libro> ret = new ArrayList<>();
        for (Libro l : lib) {
            if (l.getTitolo().toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", "")
                    .contains(titolo.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", ""))) {
                ret.add(l);
            }
        }
        return ret;
    }
}
