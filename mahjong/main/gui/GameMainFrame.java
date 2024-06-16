import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMainFrame extends JFrame {
    private GamePanel gamePanel;

    public GameMainFrame() {
        setTitle("Mahjong Game");
        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
    
        gamePanel = new GamePanel();
    
        mainPanel.add(gamePanel, BorderLayout.CENTER);  // Add gamePanel to mainPanel

        add(mainPanel);
        simulateBackendSignal();
    }
    
    private void simulateBackendSignal() {
        // 模擬後端信號來顯示控制按鈕
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.control.showCancelButton();
            }
        });
        timer.setRepeats(false); // 只執行一次
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameMainFrame game = new GameMainFrame();
            game.setVisible(true);
        });
    }
}