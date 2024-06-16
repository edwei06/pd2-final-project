package sever;
import java.awt.*;
import javax.swing.*;

class ControlPanel extends JPanel {
    public ControlPanel() {
        setPreferredSize(new Dimension(1024, 100));
        setBackground(Color.LIGHT_GRAY);

        // 添加控制按鈕
        JButton shuffleButton = new JButton("Shuffle");
        JButton dealButton = new JButton("Deal");

        add(shuffleButton);
        add(dealButton);
    }
}
