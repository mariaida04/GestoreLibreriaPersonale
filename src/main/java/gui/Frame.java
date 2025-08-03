package gui;

import builder.Libro;
import singleton.Libreria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class Frame extends JFrame {
    private JTable tabella;
    private DefaultTableModel tableModel;
    private JButton aggiungi;
    private JButton rimuovi;
    private JButton modifica;
    private LibreriaController controller;
    private JComboBox<String> ricercaBox;
    private JTextField campoRicerca;
    private JComboBox<String> ordinaBox;
    private JComboBox<String> filtraBox;
    private JTextField campoFiltra;

    public Frame() {
        super("Gestione Libreria Personale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //per far sì che la x faccia terminare il processo
        setSize(800, 600);
        setLocationRelativeTo(null);  //per far apparire al centro la finestra
        setLayout(new BorderLayout());

        //COMANDI
        //layout
        JPanel comandiPanel = new JPanel();
        comandiPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

        //pulsante per visualizzare la libreria
        JButton visualizzaButton = new JButton("Visualizza libreria");
        visualizzaButton.addActionListener(e -> mostraLibreria());

        aggiungi = new JButton("Aggiungi libro");
        rimuovi = new JButton("Rimuovi libro");
        modifica = new JButton("Modifica libro");

        comandiPanel.add(visualizzaButton);
        comandiPanel.add(aggiungi);
        comandiPanel.add(rimuovi);
        comandiPanel.add(modifica);

        add(comandiPanel, BorderLayout.CENTER);

        //AREA TESTUALE
        String[] colonne = {"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"};

        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabella = new JTable(tableModel);
        JTableHeader header = tabella.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(tabella);
        scrollPane.setPreferredSize(new Dimension(800,300));
        add(scrollPane, BorderLayout.SOUTH);


        //CAMPO PER LA RICERCA
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new BoxLayout(opPanel,BoxLayout.Y_AXIS));

        //pannello ricerca
        JPanel ricerca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoRicerca = new JTextField(15);

        //menù a tendina per selezionare l'attributo per cui cercare
        String[] attributi = {"Seleziona","Titolo","Autore","ISBN"};
        ricercaBox = new JComboBox<>(attributi);

        //bottone ricerca
        JButton cercaButton = new JButton("Cerca");

        ricerca.add(new Label("Cerca per:"));
        ricerca.add(ricercaBox);
        ricerca.add(campoRicerca);
        ricerca.add(cercaButton);

        //pannello filtra
        JPanel filtra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoFiltra = new JTextField(15);

        //menù a tendina per selezionare l'attributo per cui filtrare
        String[] filtri = {"Seleziona","Genere","Stato"};
        filtraBox = new JComboBox<>(filtri);

        //bottone filtra
        JButton filtraButton = new JButton("Filtra");

        filtra.add(new Label("Filtra per:"));
        filtra.add(filtraBox);
        filtra.add(campoFiltra);
        filtra.add(filtraButton);

        //pannello ordina
        JPanel ordina = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //menù a tendina per selezionare l'attributo per cui ordinare
        String[] ordinamento = {"Seleziona","Titolo","Valutazione"};
        ordinaBox = new JComboBox<>(ordinamento);

        //bottone ordina
        JButton ordinaButton = new JButton("Ordina");

        ordina.add(new Label("Ordina per:"));
        ordina.add(ordinaBox);
        ordina.add(ordinaButton);

        opPanel.add(ricerca);
        opPanel.add(filtra);
        opPanel.add(ordina);

        add(opPanel, BorderLayout.NORTH);

        setVisible(true);

        aggiungi.addActionListener((e -> aggiungiLibro()));
        rimuovi.addActionListener(e -> rimuoviLibro());
        modifica.addActionListener(e -> modificaLibro());
        cercaButton.addActionListener(e -> ricercaLibro());
        ordinaButton.addActionListener(e -> gestioneOrdinamento());
        filtraButton.addActionListener(e -> gestioneFiltro());

        mostraLibreria();
    }

    public void setController(LibreriaController controller) {
        this.controller = controller;
    }

    private void aggiungiLibro() {
        LibroFormResult dati = mostraDialogoLibro(null,null,null,null,null,null);
        if (dati == null)
            return;

        if (dati.titolo.isEmpty() || dati.autore.isEmpty() || dati.isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Titolo, autore ed ISBN devono essere compilati.");
            return;
        }

        String valEff = dati.valutazione.equals("Seleziona") ? null : dati.valutazione;
        String statoEff = dati.stato.equals("Seleziona") ? null : dati.stato;

        controller.aggiunta(dati.titolo,dati.autore,dati.isbn,dati.genere,valEff,statoEff);
    }

    private void rimuoviLibro() {
        String isbn = JOptionPane.showInputDialog(this, "Inserisci il codice ISBN del libro da rimuovere:");
        if (isbn != null && !isbn.isBlank()) {
            controller.rimozione(isbn.trim());
        }
    }

    private void modificaLibro() {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un libro dalla tabella.");
            return;
        }

        String isbnOrig = (String) tableModel.getValueAt(rigaSelezionata, 2);

        LibroFormResult dati = mostraDialogoLibro(
                (String) tableModel.getValueAt(rigaSelezionata, 0),
                (String) tableModel.getValueAt(rigaSelezionata, 1),
                isbnOrig,
                (String) tableModel.getValueAt(rigaSelezionata, 3),
                (String) tableModel.getValueAt(rigaSelezionata, 4),
                (String) tableModel.getValueAt(rigaSelezionata, 5)
        );

        if (dati == null)
            return;

        if (dati.titolo.isEmpty() || dati.autore.isEmpty() || dati.isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Titolo, autore ed ISBN devono essere compilati.");
            return;
        }

        //se viene modificato l'ISBN, verifica che non sia già presente
        if (!dati.isbn.equalsIgnoreCase(isbnOrig)) {
                for (Libro l : Libreria.getInstance().getLibri()) {
                    if (l.getIsbn().equalsIgnoreCase(dati.isbn)) {
                        JOptionPane.showMessageDialog(this, "Esiste già un libro con questo nuovo ISBN.");
                        return;
                    }
                }
            }

        String valEff = dati.valutazione.equals("Seleziona") ? null : dati.valutazione;
        String statoEff = dati.stato.equals("Seleziona") ? null : dati.stato;

        controller.modifica(isbnOrig, dati.titolo, dati.autore, dati.isbn, dati.genere, valEff, statoEff);
    }

    private void ricercaLibro() {
        String attributo = (String) ricercaBox.getSelectedItem();
        String valore = campoRicerca.getText().trim();

        if (!valore.isEmpty()) {
            controller.ricerca(attributo,valore);
        }
        else {
            JOptionPane.showMessageDialog(this, "Inserisci un valore da cercare.");
        }
    }

    private void gestioneOrdinamento() {
        String criterio = (String) ordinaBox.getSelectedItem();
        controller.ordina(criterio);
    }

    private void gestioneFiltro() {
        String criterio = (String) filtraBox.getSelectedItem();
        String valore = campoFiltra.getText().trim();

        if (valore.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci un valore per cui filtrare.");
            return;
        }
        controller.filtra(criterio, valore);
    }

    //per mostrare una lista filtrata o ordinata
    public void mostraLibri(List<Libro> lista) {
        tableModel.setRowCount(0);

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La libreria è vuota.");
        }
        else {
            for (Libro l : lista) {
                Object[] riga = { l.getTitolo(), l.getAutore(), l.getIsbn(),
                        l.getGenere(), l.getValutazione() != null ? l.getValutazione().name() : "",
                        l.getStato() != null ? l.getStato().name() : ""
                };
                tableModel.addRow(riga);
            }
        }
    }

    //per mostrare tutta la libreria
    public void mostraLibreria() {
        mostraLibri(Libreria.getInstance().getLibri());
    }

    //metodo ausiliario per costruire il form
    private LibroFormResult mostraDialogoLibro(String titoloIn, String autoreIn, String isbnIn, String genereIn, String valutazioneIn, String statoIn) {
        JTextField titoloField = new JTextField(titoloIn != null ? titoloIn : "");
        JTextField autoreField = new JTextField(autoreIn != null ? autoreIn : "");
        JTextField isbnField = new JTextField(isbnIn != null ? isbnIn : "");
        JTextField genereField = new JTextField(genereIn != null ? genereIn : "");

        JComboBox<String> valutazioneBox = new JComboBox<>(new String[] {
                "Seleziona","UNA_STELLA","DUE_STELLE","TRE_STELLE","QUATTRO_STELLE","CINQUE_STELLE"
        });
        valutazioneBox.setSelectedItem(valutazioneIn != null ? valutazioneIn : "Seleziona");

        JComboBox<String> statoBox = new JComboBox<>(new String[] {
                "Seleziona","DA_LEGGERE","IN_LETTURA","COMPLETATO"
        });
        statoBox.setSelectedItem(statoIn != null ? statoIn : "Seleziona");

        JPanel form = new JPanel(new GridLayout(0,1));
        form.add(new Label("Titolo:"));
        form.add(titoloField);
        form.add(new Label("Autore:"));
        form.add(autoreField);
        form.add(new Label("ISBN:"));
        form.add(isbnField);
        form.add(new Label("Genere:"));
        form.add(genereField);
        form.add(new Label("Valutazione:"));
        form.add(valutazioneBox);
        form.add(new Label("Stato:"));
        form.add(statoBox);

        int risultato = JOptionPane.showConfirmDialog(this,form,"Dati libro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (risultato == JOptionPane.OK_OPTION) {
            return new LibroFormResult(titoloField.getText().trim(),
                    autoreField.getText().trim(),
                    isbnField.getText().trim(),
                    genereField.getText().trim(),
                    (String) valutazioneBox.getSelectedItem(),
                    (String) statoBox.getSelectedItem()
            );
        }
        return null;
    }

    //classe di supporto che contiene i dati
    private static class LibroFormResult {
        String titolo;
        String autore;
        String isbn;
        String genere;
        String valutazione;
        String stato;

        public LibroFormResult(String titolo, String autore, String isbn, String genere, String valutazione, String stato) {
            this.titolo = titolo;
            this.autore = autore;
            this.isbn = isbn;
            this.genere = genere;
            this.valutazione = valutazione;
            this.stato = stato;
        }
    }
}
