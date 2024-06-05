package mahjong.main.gui;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class MahjongGame extends JFrame {

    public MahjongGame() {
        setTitle("Mahjong Game");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MahjongGame game = new MahjongGame();
            game.setVisible(true);
        });
    }
}
