package strategy;

import builder.Libro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdinaPerTitolo implements LibreriaStrategy {

    public OrdinaPerTitolo() {}

    @Override
    public List<Libro> esegui(List<Libro> lib)
    {   List<Libro> copia = new ArrayList<>(lib);
        copia.sort(new Comparator<Libro>() {
            @Override
            public int compare(Libro l1, Libro l2) {
                return l1.getTitolo().compareToIgnoreCase(l2.getTitolo());
            }
        });
        return copia;
    }
}
