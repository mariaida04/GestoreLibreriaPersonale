package gui;

import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import command.AggiungiLibroCommand;
import command.Command;
import command.ModificaLibroCommand;
import command.RimuoviLibroCommand;
import factoryMethod.LibroFactory;
import singleton.Libreria;
import javax.swing.*;

public class LibreriaController {
    private Frame frame;
    private LibroFactory libroFactory;

    public LibreriaController(Frame frame) {
        this.frame = frame;
        this.libroFactory = new LibroFactory();
    }

    public void aggiunta(String titolo, String autore, String isbn, String genere, String valutazioneStr, String statoStr) {
        try {
            Valutazione valutazione = Valutazione.valueOf(valutazioneStr);
            StatoLettura stato = StatoLettura.valueOf(statoStr);

            Libro libro = LibroFactory.creaLibro(titolo, autore, isbn, genere, valutazione, stato);

            Command comando = new AggiungiLibroCommand(Libreria.getInstance(), libro);
            comando.esegui();

            frame.mostraLibreria();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Errore nella creazione del libro: " + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    //completare!!!
    public void rimozione(String titolo) {
        Libreria libreria = Libreria.getInstance();
        Libro daRimuovere = null;

        for(Libro libro: libreria.getLibri()) {
            if (libro.getTitolo().equalsIgnoreCase(titolo)) {
                daRimuovere = libro;
                break;
            }
        }
        if (daRimuovere != null) {
            Command command = new RimuoviLibroCommand(libreria,daRimuovere);
            command.esegui();
            frame.mostraLibreria();
        }
        else {
            JOptionPane.showMessageDialog(frame, "Nessun libro trovato con il titolo inserito.",
                    "Libro non trovato", JOptionPane.WARNING_MESSAGE);
        }
    }

    //aggiungere opzione che se campo = vuoto, rimane invariato
    public void modifica(String isbnDaModificare, String nuovoTitolo, String nuovoAutore,
                         String nuovoGenere, String nuovoIsbn, String nuovaValutazione, String nuovoStato) {
        Libreria libreria = Libreria.getInstance();
        Libro daModificare = null;

        for (Libro libro : libreria.getLibri()) {
            if (libro.getIsbn().equalsIgnoreCase(isbnDaModificare)) {
                daModificare = libro;
                break;
            }
        }
        if (daModificare != null) {
            try {
                Valutazione nuovaVal = Valutazione.valueOf(nuovaValutazione);
                StatoLettura nuovoSt = StatoLettura.valueOf(nuovoStato);

                Libro modificato = LibroFactory.creaLibro(nuovoTitolo,nuovoAutore,nuovoIsbn,nuovoGenere,nuovaVal,nuovoSt);

                Command command = new ModificaLibroCommand(libreria, modificato, isbnDaModificare);
                command.esegui();

                frame.mostraLibreria();
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Errore nei dati inseriti: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(frame, "Libro da modificare non trovato.", "Errore", JOptionPane.WARNING_MESSAGE);
        }
    }
}