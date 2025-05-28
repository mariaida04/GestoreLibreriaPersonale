package gui;

import singleton.Libreria;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    private JTextArea textArea;
    private JButton aggiungi;
    private JButton rimuovi;
    private JButton modifica;

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

    private void mostraLibreria() {
        Libreria libreria = Libreria.getInstance();
        textArea.setText(libreria.toString()); //stampa ogni libro
    }
}

//textField per inserire un campo di testo in cui scrivere (es. per la ricerca di autore, libro, ecc)