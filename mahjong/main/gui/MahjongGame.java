import java.awt.*;
import javax.swing.*;

public class MahjongGame extends JFrame {
    public MahjongGame() {
        setTitle("Mahjong Game");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 初始化遊戲界面
        initUI();
    }

    private void initUI() {
        // 創建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 添加遊戲面板
        GamePanel gamePanel = new GamePanel();
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MahjongGame game = new MahjongGame();
            game.setVisible(true);
        });
    }
}
