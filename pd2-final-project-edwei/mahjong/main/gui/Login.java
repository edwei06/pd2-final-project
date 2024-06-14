import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener{
    JTextField ipAddress;
    JTextField portNumber;
    JButton join = new JButton("join");
    Login(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setSize(500,100);

        ipAddress = new JTextField("localhost");
        ipAddress.setPreferredSize(new Dimension(100,30));
        this.add(ipAddress);

        portNumber = new JTextField("" + 1234);
        portNumber.setPreferredSize(new Dimension(100,30));
        this.add(portNumber);

        join.addActionListener(this);
        this.add(join);

        this.pack();
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == join){
            MahjongGame game = new MahjongGame();
            game.setVisible(true);
            //System.out.println("penis");
        }
    }

}
