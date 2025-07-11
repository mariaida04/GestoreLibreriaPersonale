package gui;

import javax.swing.*;

public class LibreriaGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                Frame frame = new Frame();
                LibreriaController controller = new LibreriaController(frame);
                frame.setController(controller);
                frame.setVisible(true);
        });
    }
}
