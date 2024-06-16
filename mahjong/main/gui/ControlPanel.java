import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ControlPanel extends JPanel {
    private JButton chiLowButton, chiMidButton, chiUpButton, pongButton, gangButton, cancelButton, huButton;
    private GamePanel gamePanel;
    private int buttonX = 512;
    private int buttonY = 584;

    public ControlPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        initButton();
    }

    public void initButton() {
        chiLowButton = createButton("吃第一張");
        chiMidButton = createButton("吃中間張");
        chiUpButton = createButton("吃最後張");
        pongButton = createButton("碰");
        gangButton = createButton("槓");
        cancelButton = createButton("取消");
        huButton = createButton("胡");

        chiLowButton.setVisible(false);
        chiMidButton.setVisible(false);
        chiUpButton.setVisible(false);
        pongButton.setVisible(false);
        gangButton.setVisible(false);
        cancelButton.setVisible(false);
        huButton.setVisible(false);

        gamePanel.add(chiLowButton);
        gamePanel.add(chiMidButton);
        gamePanel.add(chiUpButton);
        gamePanel.add(pongButton);
        gamePanel.add(gangButton);
        gamePanel.add(cancelButton);
        gamePanel.add(huButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setVisible(false);
                System.out.println(button.getText() + " 按鈕被點擊並隱藏");
            }
        });
        return button;
    }

    public void showChiLowButton() {
        chiLowButton.setBounds(buttonX + 80, buttonY, 90, 30);
        chiLowButton.setVisible(true);
    }

    public void showChiMidButton() {
        chiMidButton.setBounds(buttonX + 160, buttonY, 90, 30);
        chiMidButton.setVisible(true);
    }

    public void showChiUpButton() {
        chiUpButton.setBounds(buttonX + 240, buttonY, 90, 30);
        chiUpButton.setVisible(true);
    }

    public void showpongButton() {
        pongButton.setBounds(buttonX + 160, buttonY - 40, 60, 30);
        pongButton.setVisible(true);
    }

    public void showGangButton() {
        gangButton.setBounds(buttonX + 240, buttonY - 40, 60, 30);
        gangButton.setVisible(true);
    }

    public void showCancelButton() {
        cancelButton.setBounds(buttonX + 160, buttonY + 40, 60, 30);
        cancelButton.setVisible(true);
    }

    public void showHuButton() {
        huButton.setBounds(buttonX + 80, buttonY - 40, 60, 30);
        huButton.setVisible(true);
    }
}