package strategy;

import builder.Libro;
import builder.Valutazione;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrdinaPerValutazione implements LibreriaStrategy {

    public OrdinaPerValutazione() {}

    @Override
    public List<Libro> esegui(List<Libro> lib) {
        List<Libro> copia = new ArrayList<>(lib);
        copia.sort(new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l1.getValutazione().compareTo(l2.getValutazione());
            }
        });
        return copia;
    }
}
