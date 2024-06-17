import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel implements ActionListener {
    private JTextField ipAddress;
    private JTextField portNumber;
    private JButton join;
    private GameMainFrame mainFrame;

    public Login(GameMainFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(500, 100));

        ipAddress = new JTextField("localhost");
        ipAddress.setPreferredSize(new Dimension(100, 30));
        add(ipAddress);

        portNumber = new JTextField("1234");
        portNumber.setPreferredSize(new Dimension(100, 30));
        add(portNumber);

        join = new JButton("Join");
        join.addActionListener(this);
        add(join);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == join) {
            mainFrame.showGamePanel();
            System.out.println("IP Address: " + ipAddress.getText());
            System.out.println("Port Number: " + portNumber.getText());
        }
    }
}
