import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Control {
    private JButton chiLowButton, chiMidButton, chiUpButton, pongButton, gangButton, cancelButton, huButton;
    private GamePanel gamePanel;
    private int buttonX = 512;
    private int buttonY = 584;

    public Control(GamePanel gamePanel) {
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

        hideAllButtons();

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
                hideAllButtons();
                button.setEnabled(true); // 保證按鈕再次顯示時是可用的
                System.out.println(button.getText() + " 按鈕被點擊並隱藏");
            }
        });
        return button;
    }

    public void hideAllButtons() {
        chiLowButton.setVisible(false);
        chiMidButton.setVisible(false);
        chiUpButton.setVisible(false);
        pongButton.setVisible(false);
        gangButton.setVisible(false);
        cancelButton.setVisible(false);
        huButton.setVisible(false);
    }

    public void disableAllButtons() {
        chiLowButton.setEnabled(false);
        chiMidButton.setEnabled(false);
        chiUpButton.setEnabled(false);
        pongButton.setEnabled(false);
        gangButton.setEnabled(false);
        cancelButton.setEnabled(false);
        huButton.setEnabled(false);
    }

    public void enableButton(JButton button) {
        disableAllButtons(); // 禁用所有按鈕
        button.setEnabled(true); // 啟用當前按鈕
    }

    public void showChiLowButton() {
        chiLowButton.setBounds(buttonX + 80, buttonY, 90, 30);
        chiLowButton.setVisible(true);
        enableButton(chiLowButton);
    }

    public void showChiMidButton() {
        chiMidButton.setBounds(buttonX + 160, buttonY, 90, 30);
        chiMidButton.setVisible(true);
        enableButton(chiMidButton);
    }

    public void showChiUpButton() {
        chiUpButton.setBounds(buttonX + 240, buttonY, 90, 30);
        chiUpButton.setVisible(true);
        enableButton(chiUpButton);
    }

    public void showpongButton() {
        pongButton.setBounds(buttonX + 160, buttonY - 40, 60, 30);
        pongButton.setVisible(true);
        enableButton(pongButton);
    }

    public void showGangButton() {
        gangButton.setBounds(buttonX + 240, buttonY - 40, 60, 30);
        gangButton.setVisible(true);
        enableButton(gangButton);
    }

    public void showCancelButton() {
        cancelButton.setBounds(buttonX + 160, buttonY + 40, 60, 30);
        cancelButton.setVisible(true);
        enableButton(cancelButton);
    }

    public void showHuButton() {
        huButton.setBounds(buttonX + 80, buttonY - 40, 60, 30);
        huButton.setVisible(true);
        enableButton(huButton);
    }
}
