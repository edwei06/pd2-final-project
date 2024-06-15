import java.awt.event.*;

public class ClientInputHandler extends MouseAdapter {
    private GamePanel gamePanel;

    public ClientInputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int baseX = 512 - 400;
        int baseY = 384 + 275;
        gamePanel.handleTileClick(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int baseX = 512 - 400;
        int baseY = 384 + 275;
        gamePanel.handleTileHover(e.getX(), e.getY());
    }
}
