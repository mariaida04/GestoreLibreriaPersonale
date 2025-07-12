import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import factoryMethod.LibroFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArchivioLibreriaTest {
    private static File tempFile;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @BeforeAll
    static void setup() throws IOException {
        tempFile = File.createTempFile("test_libreria",".json");
        tempFile.deleteOnExit();
    }

    @Test
    public void testSalvaECarica() throws IOException {
        List<Libro> libriDaSalvare = new ArrayList<>();
        Libro l1 = LibroFactory.creaLibro("1984","George Orwell","9786662434","Romanzo",
                Valutazione.QUATTRO_STELLE,StatoLettura.IN_LETTURA);
        libriDaSalvare.add(l1);

        try (Writer writer = new FileWriter(tempFile)) {
            gson.toJson(libriDaSalvare, writer);
        }

        List<Libro> libriCaricati;
        try (Reader reader = new FileReader(tempFile)) {
            Type tipo = new TypeToken<List<Libro>>() {}.getType();
            libriCaricati = gson.fromJson(reader,tipo);
        }

        assertEquals(libriDaSalvare.size(), libriCaricati.size());
        assertEquals(libriDaSalvare.get(0).getTitolo(), libriCaricati.get(0).getTitolo());
        assertEquals(libriDaSalvare.get(0).getIsbn(), libriCaricati.get(0).getIsbn());
    }
}