package strategy;

import builder.Libro;
import singleton.Libreria;

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
            if (l.getGenere().equalsIgnoreCase(genere) || l.getGenere().contains(genere)) {   //gestire casi di upper case o lower case
                ret.add(l);
            }
        }
        return ret;
    }
}
