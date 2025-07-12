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
        header.setBackground(Color.BLUE);
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

        //listener per effettuare l'operazione

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
    }

    public void setController(LibreriaController controller) {
        this.controller = controller;
    }

    private void aggiungiLibro() {
        JTextField titolo = new JTextField();
        JTextField autore = new JTextField();
        JTextField isbn = new JTextField();
        JTextField genere = new JTextField();

        JComboBox<String> valutazione = new JComboBox<>(new String[] {
            "Seleziona","UNA_STELLA","DUE_STELLE","TRE_STELLE","QUATTRO_STELLE","CINQUE_STELLE"
        });

        JComboBox<String> stato = new JComboBox<>(new String[] {
                "Seleziona","DA_LEGGERE","IN_LETTURA","COMPLETATO"
        });

        JPanel form = new JPanel(new GridLayout(0,1));
        form.add((new JLabel("Titolo:")));
        form.add((titolo));
        form.add((new JLabel("Autore:")));
        form.add((autore));
        form.add((new JLabel("ISBN:")));
        form.add((isbn));
        form.add((new JLabel("Genere:")));
        form.add((genere));
        form.add((new JLabel("Valutazione:")));
        form.add((valutazione));
        form.add((new JLabel("Stato lettura:")));
        form.add((stato));

        //per mostrare la finestra di dialogo
        int result = JOptionPane.showConfirmDialog(this,form,"Aggiungi un nuovo libro",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
                String titoloNext = titolo.getText().trim();
                String autoreNext = autore.getText().trim();
                String isbnNext = isbn.getText().trim();
                String genereNext = genere.getText().trim();
                String valutazioneSel = (String) valutazione.getSelectedItem();
                String statoSel = (String) stato.getSelectedItem();

            if (titoloNext.isEmpty() || autoreNext.isEmpty() || isbnNext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Titolo, autore ed ISBN devono essere compilati.");
                return;
            }

            String valEff = valutazioneSel.equals("Seleziona") ? null : valutazioneSel;
            String statoEff = statoSel.equals("Seleziona") ? null : statoSel;

            controller.aggiunta(titoloNext,autoreNext,isbnNext,genereNext, valEff,statoEff);
        }
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
        String titoloAttuale = (String) tableModel.getValueAt(rigaSelezionata, 0);
        String autoreAttuale = (String) tableModel.getValueAt(rigaSelezionata, 1);
        String genereAttuale = (String) tableModel.getValueAt(rigaSelezionata, 3);
        String valAttuale = (String) tableModel.getValueAt(rigaSelezionata, 4);
        String statoAttuale = (String) tableModel.getValueAt(rigaSelezionata, 5);

        JTextField nuovoTitolo = new JTextField(titoloAttuale);
        JTextField nuovoAutore = new JTextField(autoreAttuale);
        JTextField nuovoGenere = new JTextField(genereAttuale);
        JTextField nuovoIsbn = new JTextField(isbnOrig);

        JComboBox<String> nuovaValutazione = new JComboBox<>(new String[] {
                "Seleziona","UNA_STELLA","DUE_STELLE","TRE_STELLE","QUATTRO_STELLE","CINQUE_STELLE"
        });
        nuovaValutazione.setSelectedItem(valAttuale != null ? valAttuale : "Seleziona");

        JComboBox<String> nuovoStato = new JComboBox<>(new String[] {
                "Seleziona","DA_LEGGERE","IN_LETTURA","COMPLETATO"
        });
        nuovoStato.setSelectedItem(statoAttuale != null ? statoAttuale : "Seleziona");

        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new Label("Nuovo titolo:"));
        panel.add(nuovoTitolo);
        panel.add(new Label("Nuovo autore:"));
        panel.add(nuovoAutore);
        panel.add(new Label("Nuovo ISBN:"));
        panel.add(nuovoIsbn);
        panel.add(new Label("Nuovo genere:"));
        panel.add(nuovoGenere);
        panel.add(new Label("Nuova valutazione:"));
        panel.add(nuovaValutazione);
        panel.add(new Label("Nuovo stato:"));
        panel.add(nuovoStato);

        int res = JOptionPane.showConfirmDialog(this,panel, "Modifica libro",
                JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {
            String titolo = nuovoTitolo.getText().trim();
            String autore = nuovoAutore.getText().trim();
            String isbnNuovo = nuovoIsbn.getText().trim();
            String genere = nuovoGenere.getText().trim();
            String valutazioneSel = (String) nuovaValutazione.getSelectedItem();
            String statoSel = (String) nuovoStato.getSelectedItem();

            if (titolo.isEmpty() || autore.isEmpty() || isbnNuovo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Titolo, autore e ISBN non possono essere vuoti.");
                return;
            }

            //se viene modificato l'ISBN, verifica che non sia già presente
            if (!isbnNuovo.equalsIgnoreCase(isbnOrig)) {
                for (Libro l : Libreria.getInstance().getLibri()) {
                    if (l.getIsbn().equalsIgnoreCase(isbnNuovo)) {
                        JOptionPane.showMessageDialog(this, "Esiste già un libro con questo nuovo ISBN.");
                        return;
                    }
                }
            }

            String valEff = valutazioneSel.equals("Seleziona") ? null : valutazioneSel;
            String statoEff = statoSel.equals("Seleziona") ? null : statoSel;

            controller.modifica(isbnOrig, titolo, autore, isbnNuovo, genere, valEff, statoEff);
        }
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
}
