package strategy;

import builder.Libro;
import java.util.ArrayList;
import java.util.List;

public class FiltraPerGenere implements LibreriaStrategy {
    private String genere;

    public FiltraPerGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public List<Libro> esegui(List<Libro> lib) {
        List<Libro> ret = new ArrayList<>();
        for(Libro l : lib) {
            if (l.getGenere().toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", "")
                    .contains(genere.toLowerCase().replaceAll("\\p{Punct}", "").replaceAll("[\\s.-]", ""))) {
                ret.add(l);
            }
        }
        return ret;
    }
}
