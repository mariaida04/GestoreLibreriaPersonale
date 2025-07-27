package strategy;

import builder.Libro;
import builder.StatoLettura;
import java.util.ArrayList;
import java.util.List;

public class FiltraPerStatoLettura implements LibreriaStrategy {
    private StatoLettura stato;

    public FiltraPerStatoLettura(StatoLettura stato) {
        this.stato = stato;
    }

    @Override
    public List<Libro> esegui(List<Libro> lib) {
        List<Libro> ret = new ArrayList<>();
        for (Libro l : lib) {
            if (l.getStato() != null && l.getStato() == stato) {
                ret.add(l);
            }
        }
        return ret;
    }
}
