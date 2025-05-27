package gui;

import singleton.Libreria;
import javax.swing.*;
import java.awt.*;

public class LibreriaGUI {
/*
    public LibreriaGUI() {
        frame = new JFrame("Gestore Libreria Personale");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //per far sì che la x faccia terminare il processo
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);  //per far apparire al centro la finestra

        //layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        //area testuale
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //pulsante per visualizzare la libreria
        JButton visualizzaButton = new JButton("Visualizza libreria");
        visualizzaButton.addActionListener(e -> mostraLibreria());

        //aggiungi al pannello
        panel.add(visualizzaButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        //imposta il contenuto e mostra la finestra
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void mostraLibreria() {
        Libreria libreria = Libreria.getInstance();
        textArea.setText(libreria.toString()); //stampa ogni libro
    }
*/
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new LibreriaGUI());
        new Frame();
    }
}
