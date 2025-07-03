package gui;

import builder.Libro;
import builder.StatoLettura;
import builder.Valutazione;
import command.AggiungiLibroCommand;
import command.Command;
import factoryMethod.LibroFactory;
import singleton.Libreria;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class Frame extends JFrame {
    private JTextArea textArea;
    private JButton aggiungi;
    private JButton rimuovi;
    private JButton modifica;
    private LibreriaController controller;

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

        aggiungi.addActionListener((e -> aggiungiLibro()));
        rimuovi.addActionListener(e -> rimuoviLibro());
        modifica.addActionListener(e -> modificaLibro());

        comandiPanel.add(visualizzaButton);
        comandiPanel.add(aggiungi);
        comandiPanel.add(rimuovi);
        comandiPanel.add(modifica);

        add(comandiPanel, BorderLayout.CENTER);

        //AREA TESTUALE
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800,300));
        add(scrollPane, BorderLayout.SOUTH);


        //CAMPO PER LA RICERCA
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new BoxLayout(opPanel,BoxLayout.Y_AXIS));

        //pannello ricerca
        JPanel ricerca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoRicerca = new JTextField(15);

        //menù a tendina per selezionare l'attributo per cui cercare
        String[] attributi = {"Titolo","Autore","ISBN"};
        JComboBox<String> ricercaBox = new JComboBox<>(attributi);

        //bottone ricerca
        JButton cercaButton = new JButton("Cerca");

        ricerca.add(new Label("Cerca per:"));
        ricerca.add(ricercaBox);
        ricerca.add(campoRicerca);
        ricerca.add(cercaButton);

        //pannello filtra
        JPanel filtra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoFiltra = new JTextField(15);

        //menù a tendina per selezionare l'attributo per cui filtrare
        String[] filtri = {"Genere","Stato"};
        JComboBox<String> filtraBox = new JComboBox<>(filtri);

        //bottone filtra
        JButton filtraButton = new JButton("Filtra");

        filtra.add(new Label("Filtra per:"));
        filtra.add(filtraBox);
        filtra.add(campoFiltra);
        filtra.add(filtraButton);

        //pannello ordina
        JPanel ordina = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //menù a tendina per selezionare l'attributo per cui ordinare
        String[] ordinamento = {"Titolo","Valutazione"};
        JComboBox<String> ordinaBox = new JComboBox<>(ordinamento);

        //bottone ordina
        JButton ordinaButton = new JButton("Ordina");

        ordina.add(new JLabel("Ordina per:"));
        ordina.add(ordinaBox);
        opPanel.add(ordinaButton);

        //listener per effettuare l'operazione

        opPanel.add(ricerca);
        opPanel.add(filtra);
        opPanel.add(ordina);

        add(opPanel, BorderLayout.NORTH);

        setVisible(true);

    }

    private void aggiungiLibro() {
        JTextField titolo = new JTextField();
        JTextField autore = new JTextField();
        JTextField isbn = new JTextField();
        JTextField genere = new JTextField();

        JComboBox<String> valutazione = new JComboBox<>(new String[] {
            "UNA_STELLA","DUE_STELLE","TRE_STELLE","QUATTRO_STELLE","CINQUE_STELLE"
        });

        JComboBox<String> stato = new JComboBox<>(new String[] {
                "DA_LEGGERE","IN_LETTURA","COMPLETATO"
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
                controller.aggiunta(titolo.getText(),autore.getText(),isbn.getText(),genere.getText(),
                        (String) valutazione.getSelectedItem(),
                        (String) stato.getSelectedItem());
        }
    }

    //solo titolo va bene???
    private void rimuoviLibro() {
        String titolo = JOptionPane.showInputDialog(this, "Inserisci il titolo del libro da rimuovere:");
        if (titolo != null && !titolo.isBlank()) {
            controller.rimozione(titolo.trim());
        }
    }

    private void modificaLibro() {
        JTextField isbnCercato = new JTextField();
        JTextField nuovoTitolo = new JTextField();
        JTextField nuovoAutore = new JTextField();
        JTextField nuovoGenere = new JTextField();
        JTextField nuovoIsbn = new JTextField();

        JComboBox<String> nuovaValutazione = new JComboBox<>(new String[] {
                "UNA_STELLA","DUE_STELLE","TRE_STELLE","QUATTRO_STELLE","CINQUE_STELLE"
        });

        JComboBox<String> nuovoStato = new JComboBox<>(new String[] {
                "DA_LEGGERE","IN_LETTURA","COMPLETATO"
        });

        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new Label("ISBN del libro da modificare:"));
        panel.add(isbnCercato);
        panel.add(new Label("Nuovo titolo:"));
        panel.add(nuovoTitolo);
        panel.add(new Label("Nuovo autore:"));
        panel.add(nuovoAutore);
        panel.add(new Label("Nuovo genere:"));
        panel.add(nuovoGenere);
        panel.add(new Label("Nuovo ISBN:"));
        panel.add(nuovoIsbn);
        panel.add(new Label("Nuova valutazione:"));
        panel.add(nuovaValutazione);
        panel.add(new Label("Nuovo stato:"));
        panel.add(nuovoStato);

        int res = JOptionPane.showConfirmDialog(this,panel, "Modifica libro",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (res == JOptionPane.OK_OPTION) {
            controller.modifica(isbnCercato.getText().trim(), nuovoTitolo.getText().trim(), nuovoAutore.getText().trim(),
                    nuovoGenere.getText().trim(), nuovoIsbn.getText().trim(),
                    (String) nuovaValutazione.getSelectedItem(), (String) nuovoStato.getSelectedItem());
        }


    }

    public void mostraLibreria() {
        Libreria libreria = Libreria.getInstance();
        textArea.setText(libreria.toString()); //stampa ogni libro
    }
}

//textField per inserire un campo di testo in cui scrivere (es. per la ricerca di autore, libro, ecc)