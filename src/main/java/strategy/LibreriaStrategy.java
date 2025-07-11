package strategy;

import builder.Libro;
import java.util.List;

public interface LibreriaStrategy {

    List<Libro> esegui(List<Libro> lib);
}
