import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
    private String[] playerTiles = {"1筒", "2筒", "3筒", "4筒", "5筒", "6筒", "7筒", "8筒", "9筒", "1條", "2條", "3條"};
    private String[] eatenTiles = {"1筒", "2筒", "3筒"}; // 示例吃牌

    private static final int TILE_WIDTH = 40;
    private static final int TILE_HEIGHT = 56;
    private static final int SMALL_TILE_WIDTH = 30;
    private static final int SMALL_TILE_HEIGHT = 42;

    private int hoverTileIndex = -1;
    private int selectedTileIndex = -1;

    public GamePanel() {
        setPreferredSize(new Dimension(1024, 768));
        setBackground(new Color(34, 139, 34));  // 深綠色背景模擬麻將桌
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 繪製網格線
        drawGridLines(g);
        // 繪製麻將桌和牌
        drawTable(g);
        drawPlayerTiles(g);
        drawOtherPlayersTiles(g);
        drawEatenTiles(g);
    }

    private void drawGridLines(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        // 繪製垂直線
        for (int i = 0; i <= getWidth(); i += 50) {
            g.drawLine(i, 0, i, getHeight());
        }
        // 繪製水平線
        for (int i = 0; i <= getHeight(); i += 50) {
            g.drawLine(0, i, getWidth(), i);
        }
    }

    private void drawTable(Graphics g) {
        g.setColor(Color.BLACK);
        // 繪製牌桌邊框
        g.drawRect(62, 46, 900, 675);
    }

    private void drawPlayerTiles(Graphics g) {
        int x = 62 + 20 + SMALL_TILE_HEIGHT;  // 從左下角開始對齊邊框: 邊框線坐標讓20px
        int y = 675 + 46 - TILE_HEIGHT;  // 從左下角開始對齊邊框: 邊框坐標扣畫tile高度

        // 繪製玩家的手牌
        for (int i = 0; i < playerTiles.length; i++) {
            if (i == hoverTileIndex || i == selectedTileIndex) {
                drawTile(g, x + i * TILE_WIDTH, y - 20 , playerTiles[i], 0, TILE_WIDTH, TILE_HEIGHT);  // 向上移動的效果
            } else {
                drawTile(g, x + i * TILE_WIDTH, y, playerTiles[i], 0, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }

    private void drawOtherPlayersTiles(Graphics g) {
        // 繪製上方玩家的牌背
        for (int i = 0; i < 16; i++) {
            drawTileBack(g, 900 - SMALL_TILE_HEIGHT - i * SMALL_TILE_WIDTH, 46, false, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
        // 繪製左側玩家的牌背
        for (int i = 0; i < 16; i++) {
            drawTileBack(g, 62, 46 + SMALL_TILE_HEIGHT + i * SMALL_TILE_WIDTH, true, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
        // 繪製右側玩家的牌背
        for (int i = 0; i < 16; i++) {
            drawTileBack(g, 900 - SMALL_TILE_HEIGHT, 900 - 46 - TILE_HEIGHT- i * SMALL_TILE_WIDTH, true, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
    }

    private void drawTileBack(Graphics g, int x, int y, boolean rotate, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (rotate) {
            g2d.rotate(Math.toRadians(90), x + width / 2, y + height / 2);  // 旋轉牌背
        }
        g2d.setColor(Color.GRAY);
        g2d.fillRect(x, y, width, height);  // 繪製麻將牌背
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);  // 繪製牌背的邊框
        g2d.dispose();
    }

    private void drawTile(Graphics g, int x, int y, String tileText, int rotate, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (rotate != 0) {
            g2d.rotate(Math.toRadians(rotate), x + width / 2, y + height / 2);  // 旋轉牌
        }
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);  // 繪製麻將牌
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);  // 繪製牌的邊框

        // 繪製麻將牌上的文字或圖案
        g2d.drawString(tileText, x + width / 3, y + height / 2);
        g2d.dispose();
    }

    private void drawEatenTiles(Graphics g) {
        // 繪製自己右側的吃牌
        int x = 900 + 62 - TILE_WIDTH;  // 玩家右側的牌桌邊緣
        int y = 662;

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x - i * TILE_WIDTH, y, eatenTiles[i], 0, TILE_WIDTH, TILE_HEIGHT);
        }

        // 繪製上方玩家的吃牌
        x = 50;  // 上方玩家右側的牌桌邊緣
        y = 50;

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x + i * SMALL_TILE_WIDTH, y, eatenTiles[i], 180, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製左側玩家的吃牌
        x = 56;
        y = 682;  // 左側玩家右側的牌桌邊緣

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x, y - i * SMALL_TILE_WIDTH, eatenTiles[i], 90, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製右側玩家的吃牌
        x = 938;
        y = 44;  // 右側玩家右側的牌桌邊緣

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x, y + i * SMALL_TILE_WIDTH, eatenTiles[i], 270, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = 102;
        int y = 662;
        for (int i = 0; i < playerTiles.length; i++) {
            Rectangle tileRect = new Rectangle(x + i * TILE_WIDTH, y, TILE_WIDTH, TILE_HEIGHT);
            if (tileRect.contains(e.getPoint())) {
                selectedTileIndex = (selectedTileIndex == i) ? -1 : i;
                repaint();
                break;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = 102;
        int y = 662;
        hoverTileIndex = -1;
        for (int i = 0; i < playerTiles.length; i++) {
            Rectangle tileRect = new Rectangle(x + i * TILE_WIDTH, y, TILE_WIDTH, TILE_HEIGHT);
            if (tileRect.contains(e.getPoint())) {
                hoverTileIndex = i;
                break;
            }
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
}