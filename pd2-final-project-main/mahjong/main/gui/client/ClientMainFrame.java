
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class ClientMainFrame extends JFrame {
    //private Client client;

    public ClientMainFrame() {
        super("Game");

        setMenuPanel();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                System.exit(0);
            }
        });

        pack();
        setVisible(true);
    }

    public void setMenuPanel() {
        getContentPane().removeAll();

        add(new ClientMenuPanel());

        pack();
        revalidate();
        repaint();
    }

    public boolean startGame(final String ipAddress, final int port) {
        System.out.println("Starting game.");
        //try {
            //client = new Client(this, ipAddress, port);
            System.out.println("IP Address: " + ipAddress);
            System.out.println("Port Number: " + port);
        /*} catch (final ConnectException e) {
            System.out.println("Connection refused.");
            return false;
        } catch (final IOException e) {
            e.printStackTrace();
        }*/

        getContentPane().removeAll();

        /*final ClientGamePanel gamePanel = new ClientGamePanel(client.getGame());

        add(gamePanel, BorderLayout.CENTER);

        gamePanel.requestFocusInWindow(); // must be after adding gamePanel to this*/
        add(new GamePanel());
        pack();
        revalidate();
        repaint();

        //client.run();
        return true;
    }

    public static void main(final String[] args) {
        new ClientMainFrame();
    }
}
