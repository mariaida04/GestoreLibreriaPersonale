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
import strategy.*;

import javax.swing.*;
import java.util.List;

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
            e.printStackTrace();
        }
    }

    //completare!!!
    public void rimozione(String isbn) {
        Libreria libreria = Libreria.getInstance();
        Libro daRimuovere = null;

        for(Libro libro: libreria.getLibri()) {
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
        if (daModificare == null) {
            JOptionPane.showMessageDialog(frame, "Libro da modificare non trovato.", "Errore", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String titoloFinale = (nuovoTitolo != null) ? nuovoTitolo : daModificare.getTitolo();
            String autoreFinale = (nuovoAutore != null) ? nuovoAutore : daModificare.getAutore();
            String genereFinale = (nuovoGenere != null) ? nuovoGenere : daModificare.getGenere();
            String isbnFinale = (nuovoIsbn != null) ? nuovoIsbn : daModificare.getIsbn();

            Valutazione valFinale = (nuovaValutazione != null) ? Valutazione.valueOf(nuovaValutazione) : daModificare.getValutazione();
            StatoLettura statoFinale = (nuovoStato != null) ? StatoLettura.valueOf(nuovoStato) : daModificare.getStato();

            Libro modificato = LibroFactory.creaLibro(titoloFinale,autoreFinale,isbnFinale,genereFinale,valFinale,statoFinale);

            Command command = new ModificaLibroCommand(libreria, modificato, isbnDaModificare);
            command.esegui();

            frame.mostraLibreria();
        }
        catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Valori non validi: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Errore nella modifica: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
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
        if (criterio.equals("Seleziona")) {
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
        if (criterio.equals("Seleziona")) {
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
                try {   //controllo se il valore è valido
                    StatoLettura stato = StatoLettura.valueOf(valore.toUpperCase());
                    strategia = new FiltraPerStatoLettura(stato);
                }
                catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(frame,"Stato di lettura non valido.");
                    return;
                }
                break;
            default:
                JOptionPane.showMessageDialog(frame,"Criterio di filtro non valido");
                return;
        }

        List<Libro> risultato = strategia.esegui(Libreria.getInstance().getLibri());
        frame.mostraLibri(risultato);
    }
}