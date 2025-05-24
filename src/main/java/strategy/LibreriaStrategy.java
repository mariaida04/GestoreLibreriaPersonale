package strategy;

import builder.Libro;
import singleton.Libreria;
import java.util.List;

public interface LibreriaStrategy {

    List<Libro> esegui(List<Libro> lib);
}
