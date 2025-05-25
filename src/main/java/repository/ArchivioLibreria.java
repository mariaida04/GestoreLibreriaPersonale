package repository;

import builder.Libro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;


public class ArchivioLibreria {
    private static final String nomeFile = "libreria.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void salva(List<Libro> libri) {
        try (Writer writer = new FileWriter(nomeFile)) {
            gson.toJson(libri,writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Libro> carica() {
        try (FileReader reader = new FileReader(nomeFile)) {
            Type tipo = new TypeToken<List<Libro>>() {}.getType();
            return gson.fromJson(reader, tipo);
        }
        catch (IOException e) {
            return new java.util.ArrayList<>(); //se il file non esiste viene caricata una lista vuota
        }
    }
}
