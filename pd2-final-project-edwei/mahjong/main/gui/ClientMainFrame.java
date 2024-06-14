import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ConnectException;

import javax.swing.JFrame;

class ClientMainFrame extends JFrame {
    //private Client client;

    public ClientMainFrame() {
        super("Game");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                /*if (client != null) {
                    client.disconnect();
                }*/

                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }

    public static void main(final String[] args) {
        new ClientMainFrame();
    }
}
