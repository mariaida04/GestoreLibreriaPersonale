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
                Valutazione v1 = l1.getValutazione();
                Valutazione v2 = l2.getValutazione();

                if (v1 == null && v2 == null) return 0;
                if (v1 == null) return 1;
                if (v2 == null) return -1;
                return v1.compareTo(v2);
            }
        });
        return copia;
    }
}
