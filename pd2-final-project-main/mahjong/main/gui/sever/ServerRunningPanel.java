import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mahjong.main.net.Server;

public class ServerRunningPanel extends JPanel {
    /*public void ServerRunningPanel2(final Server server) {
        try {
            add(new JLabel(
                    "Running. Port: " +
                            server.getServerSocket().getLocalPort() +
                            ". IP address: " +
                            InetAddress.getLocalHost().getHostAddress()));
        } catch (final UnknownHostException e) {
            e.printStackTrace();
        }
    }*/
    public ServerRunningPanel() {

        add(new JLabel(
                "Running. Port: " +
                        "localPenis" +
                        ". IP address: " +"6969"));
        
    }
}
