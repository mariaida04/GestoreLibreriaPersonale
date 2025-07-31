package gui;

import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import command.AggiungiLibroCommand;
import command.Command;
import command.ModificaLibroCommand;
import command.RimuoviLibroCommand;
import factoryMethod.LibroConcreteFactory;
import factoryMethod.LibroFactory;
import singleton.Libreria;
import strategy.*;
import javax.swing.*;
import java.util.List;

public class LibreriaController {
    private Frame frame;
    private final LibroFactory libroFactory;

    public LibreriaController(Frame frame) {
        this.frame = frame;
        this.libroFactory = new LibroConcreteFactory();
    }

    public void aggiunta(String titolo, String autore, String isbn, String genere, String valutazioneStr, String statoStr) {
        try {
            String genereFinale = (genere != null && !genere.isBlank()) ? genere : null;
            Valutazione valutazione = (valutazioneStr != null && !valutazioneStr.equals("Seleziona")) ? Valutazione.valueOf(valutazioneStr) : null;
            StatoLettura stato = (statoStr != null && ! statoStr.equals("Seleziona")) ? StatoLettura.valueOf(statoStr) : null;

            Libro libro = libroFactory.creaLibro(titolo, autore, isbn, genereFinale, valutazione, stato);

            Command comando = new AggiungiLibroCommand(Libreria.getInstance(), libro);
            comando.esegui();

            frame.mostraLibreria();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Errore nella creazione del libro: " + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void rimozione(String isbn) {
        Libreria libreria = Libreria.getInstance();
        Libro daRimuovere = null;

        for (Libro libro: libreria.getLibri()) {
            if (libro.getIsbn().equalsIgnoreCase(isbn)) {
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
            JOptionPane.showMessageDialog(frame, "Nessun libro trovato con il codice ISBN inserito.",
                    "Libro non trovato", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void modifica(String isbnOrig, String nuovoTitolo, String nuovoAutore,
                         String nuovoIsbn, String nuovoGenere, String valutazioneStr, String statoStr) {
        Libreria libreria = Libreria.getInstance();
        Libro libroEsistente = libreria.ottieniLibroDaIsbn(isbnOrig);

        if (libroEsistente == null) {
            JOptionPane.showMessageDialog(frame, "Libro da modificare non trovato.");
            return;
        }

        try {
            Valutazione valutazione = (valutazioneStr != null) ? Valutazione.valueOf(valutazioneStr) : null;
            StatoLettura stato = (statoStr != null) ? StatoLettura.valueOf(statoStr) : null;

            Libro modificato = libroFactory.creaLibro(nuovoTitolo, nuovoAutore, nuovoIsbn, nuovoGenere, valutazione, stato);

            Command command = new ModificaLibroCommand(libreria, modificato, isbnOrig);
            command.esegui();

            frame.mostraLibreria();
        }
        catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Errore: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ricerca(String attributo, String valore) {
        if (attributo == null || attributo.equals("Seleziona")) {
            JOptionPane.showMessageDialog(frame, "Seleziona un attributo valido per la ricerca.");
            return;
        }
        if (valore == null || valore.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Inserisci un valore da cercare.");
            return;
        }

        LibreriaStrategy strategia;

            switch (attributo) {
                case "Titolo":
                    strategia = new RicercaPerTitolo(valore);
                    break;
                case "Autore":
                    strategia = new RicercaPerAutore(valore);
                    break;
                case "ISBN":
                    strategia = new RicercaPerISBN(valore);
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Attributo di ricerca non valido.");
                    return;
            }
            List<Libro> risultati = strategia.esegui(Libreria.getInstance().getLibri());
            if (risultati.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nessun libro trovato.");
            }
            else {
                frame.mostraLibri(risultati);
        }
    }

    public void ordina(String criterio) {
        if (criterio == null || criterio.equals("Seleziona")) {
            JOptionPane.showMessageDialog(frame, "Seleziona un criterio valido per l'ordinamento.");
            return;
        }

        LibreriaStrategy strategia;

        switch (criterio) {
            case "Titolo":
                strategia = new OrdinaPerTitolo();
                break;
            case "Valutazione":
                strategia = new OrdinaPerValutazione();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Criterio di ordinamento non valido.");
                return;
        }
    List<Libro> ordinati = Libreria.getInstance().eseguiStrategy(strategia);
    frame.mostraLibri(ordinati);
    }

    public void filtra(String criterio, String valore) {
        if (criterio == null || criterio.equals("Seleziona")) {
            JOptionPane.showMessageDialog(frame, "Seleziona un criterio valido per il filtraggio.");
            return;
        }

        if (valore == null || valore.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Inserisci un valore per cui filtrare.");
            return;
        }

        LibreriaStrategy strategia;

        switch (criterio) {
            case "Genere":
                strategia = new FiltraPerGenere(valore);
                break;
            case "Stato":
                //controllo se il valore è valido
                StatoLettura stato = convertiStato(valore);
                if (stato == null) {
                    JOptionPane.showMessageDialog(frame, "Stato di lettura non valido.");
                    return;
                }
                strategia = new FiltraPerStatoLettura(stato);
                break;
            default:
                JOptionPane.showMessageDialog(frame,"Criterio di filtro non valido");
                return;
        }

        List<Libro> risultato = strategia.esegui(Libreria.getInstance().getLibri());
        frame.mostraLibri(risultato);
    }

    private StatoLettura convertiStato(String input) {
        input = input.trim().toLowerCase();

        switch (input) {
            case "da leggere":
            case "da_leggere":
            case "daleggere":
                return StatoLettura.DA_LEGGERE;
            case "in lettura":
            case "in_lettura":
            case "inlettura":
                return StatoLettura.IN_LETTURA;
            case "completato":
                return StatoLettura.COMPLETATO;
            default:
                return null;
        }
    }
}