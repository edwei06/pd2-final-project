import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;

class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
    private List<String> playerTiles = new ArrayList<>(Arrays.asList("\uD83C\uDC19", "2筒", "3筒", "4筒", "5筒", "6筒", "7筒", "8筒", "9筒", "1條", "2條", "3條"));
    private String[] eatenTiles = {"1筒", "2筒", "3筒"}; // 示例吃牌

    private static final int TILE_WIDTH = 40;
    private static final int TILE_HEIGHT = 56;
    private static final int SMALL_TILE_WIDTH = 30;
    private static final int SMALL_TILE_HEIGHT = 42;
    private static final int TABLE_WIDTH = 900;
    private static final int TABLE_HEIGHT = 675;
    private static final int TABLE_START_X_POS = 62;
    private static final int TABLE_START_Y_POS = 46;

    private int hoverTileIndex = -1;
    private int selectedTileIndex = -1;

    private JButton chiButton, pengButton, gangButton, huButton;

    private List<String> discardedTiles; // 用于保存打出的牌
    private List<String> rightPlayerDiscardedTiles;
    private List<String> topPlayerDiscardedTiles;
    private List<String> leftPlayerDiscardedTiles;

    public GamePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        setBackground(new Color(34, 139, 34));  // 深綠色背景模擬麻將桌
        addMouseListener(this);
        addMouseMotionListener(this);
        initControlButtons();
        discardedTiles = new ArrayList<>();
        rightPlayerDiscardedTiles = new ArrayList<>();
        topPlayerDiscardedTiles = new ArrayList<>();
        leftPlayerDiscardedTiles = new ArrayList<>();
        simulateDrawTile(); // 模拟游戏开始时抽一张牌
    }

    private void initControlButtons() {
        chiButton = new JButton("吃");
        pengButton = new JButton("碰");
        gangButton = new JButton("槓");
        huButton = new JButton("胡");

        chiButton.setVisible(false);
        pengButton.setVisible(false);
        gangButton.setVisible(false);
        huButton.setVisible(false);

        add(chiButton);
        add(pengButton);
        add(gangButton);
        add(huButton);
    }

    public void showControlButtons() {
        int buttonX = 512;
        int buttonY = 584; // 根據需要調整Y座標

        chiButton.setBounds(buttonX + 80, buttonY, 60, 30);
        pengButton.setBounds(buttonX + 160, buttonY, 60, 30);
        gangButton.setBounds(buttonX + 240, buttonY, 60, 30);
        huButton.setBounds(buttonX + 320, buttonY, 60, 30);
        chiButton.setVisible(true);
        pengButton.setVisible(true);
        gangButton.setVisible(true);
        huButton.setVisible(true);
        repaint();
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
        drawDiscardedTiles(g);
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
        g.drawRect(TABLE_START_X_POS, TABLE_START_Y_POS, TABLE_WIDTH, TABLE_HEIGHT);
    }

    private void drawPlayerTiles(Graphics g) {
        //int x = TABLE_START_X_POS + 20 + SMALL_TILE_HEIGHT;  // 從左下角開始對齊邊框: 邊框線坐標讓20px
        //int y = TABLE_HEIGHT + TABLE_START_Y_POS - TILE_HEIGHT;  // 從左下角開始對齊邊框: 邊框坐標扣畫tile高度

        int x =512;
        int y =384;
        // 繪製玩家的手牌
        for (int i = 0; i < playerTiles.size(); i++) {
            if (i == hoverTileIndex || i == selectedTileIndex) {
                drawTile(g, x - 400 + i * TILE_WIDTH, y + 255, playerTiles.get(i), 0, TILE_WIDTH, TILE_HEIGHT);  // 向上移動的效果
            } else {
                drawTile(g, x - 400 + i * TILE_WIDTH, y + 275, playerTiles.get(i), 0, TILE_WIDTH, TILE_HEIGHT);
            }
        }
    }

    private void drawOtherPlayersTiles(Graphics g) {
        // 繪製上方玩家的牌背
        for (int i = 0; i < 16; i++) {
            drawTileBack(g, TABLE_START_X_POS + TABLE_WIDTH - SMALL_TILE_HEIGHT - 20 - (i + 1) * SMALL_TILE_WIDTH, TABLE_START_Y_POS, false, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
        // 繪製左側玩家的牌背
        for (int i = 0; i < 16; i++) {
            drawTileBack(g, TABLE_START_X_POS, TABLE_START_Y_POS + SMALL_TILE_HEIGHT + 20 + i * SMALL_TILE_WIDTH, true, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
        // 繪製右側玩家的牌背
        for (int i = 0; i < 16; i++) {
            drawTileBack(g, TABLE_WIDTH + TABLE_START_X_POS - SMALL_TILE_HEIGHT, TABLE_HEIGHT + TABLE_START_Y_POS - TILE_HEIGHT - 20 - (i + 1) * SMALL_TILE_WIDTH, true, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
    }

    private void drawTileBack(Graphics g, int x, int y, boolean rotate, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (rotate) {
            g2d.setColor(Color.PINK);
            g2d.fillRect(x, y, height, width);  // 繪製麻將牌背
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, height, width);  // 繪製牌背的邊框
            g2d.dispose();
        } else {
            g2d.setColor(Color.PINK);
            g2d.fillRect(x, y, width, height);  // 繪製麻將牌背
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, width, height);  // 繪製牌背的邊框
            g2d.dispose();
        }
    }

    private void drawTile(Graphics g, int x, int y, String tileText, int rotate, int width, int height) {
        float fontSize = 57;
        Graphics2D g2d = (Graphics2D) g.create();
        if (rotate != 0) {
            g2d.rotate(Math.toRadians(rotate), x, y);  // 旋轉牌
        }
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);  // 繪製麻將牌
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);  // 繪製牌的邊框
        // 设置字体大小
        Font originalFont = g2d.getFont();
        Font newFont = originalFont.deriveFont((float) fontSize);
        g2d.setFont(newFont);
        
        // 繪製麻將牌上的文字或圖案
        g2d.drawString(tileText, x , y + TILE_HEIGHT - 5);
        g2d.dispose();
    }

    private void drawEatenTiles(Graphics g) {
        // 繪製自己右側的吃牌
        int x = TABLE_START_X_POS + TABLE_WIDTH - TILE_WIDTH;  // 玩家右側的牌桌邊緣
        int y = TABLE_START_Y_POS + TABLE_HEIGHT - TILE_HEIGHT;

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x - i * TILE_WIDTH, y, eatenTiles[i], 0, TILE_WIDTH, TILE_HEIGHT);
        }

        // 繪製上方玩家的吃牌
        x = TABLE_START_X_POS;  // 上方玩家右側的牌桌邊緣
        y = TABLE_START_Y_POS + SMALL_TILE_HEIGHT;

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x + (i + 1) * SMALL_TILE_WIDTH, y, eatenTiles[i], 180, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製左側玩家的吃牌
        x = TABLE_START_X_POS + SMALL_TILE_HEIGHT;
        y = TABLE_START_Y_POS + TABLE_HEIGHT;  // 左側玩家右側的牌桌邊緣

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x, y - (i + 1) * SMALL_TILE_WIDTH, eatenTiles[i], 90, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製右側玩家的吃牌
        x = TABLE_START_X_POS + TABLE_WIDTH - SMALL_TILE_HEIGHT; //
        y = TABLE_START_Y_POS + SMALL_TILE_WIDTH;  // 右側玩家右側的牌桌邊緣

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTile(g, x, y + i * SMALL_TILE_WIDTH, eatenTiles[i], 270, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
    }

    private void drawDiscardedTiles(Graphics g) {
        // 繪製自己打出的牌
        int centerX = getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH;
        int centerY = getHeight() / 2 - SMALL_TILE_HEIGHT / 2 + 50;
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTile(g, centerX + (i % 6) * SMALL_TILE_WIDTH, centerY + (i / 6) * SMALL_TILE_HEIGHT, discardedTiles.get(i), 0, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製左邊玩家打出的牌
        centerX = getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH;
        centerY = getHeight() / 2 - 50 - 2 * SMALL_TILE_HEIGHT;
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTile(g, centerX - (i / 6) * SMALL_TILE_HEIGHT, centerY + (i % 6) * SMALL_TILE_WIDTH, discardedTiles.get(i), 90, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製上方玩家打出的牌
        centerX = getWidth() - (getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH);
        centerY = getHeight() - (getHeight() / 2 - SMALL_TILE_HEIGHT / 2 + 150);
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTile(g, centerX - (i % 6) * SMALL_TILE_WIDTH, centerY - (i / 6) * SMALL_TILE_HEIGHT, discardedTiles.get(i), 180, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製右邊玩家打出的牌
        centerX = getWidth() - (getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH);
        centerY = getHeight() - (getHeight() / 2 - 50 - 2 * SMALL_TILE_HEIGHT +100);
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTile(g, centerX  + (i / 6) * SMALL_TILE_HEIGHT, centerY - (i % 6) * SMALL_TILE_WIDTH, discardedTiles.get(i), 270, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = TABLE_START_X_POS + 20 + SMALL_TILE_HEIGHT;
        int y = TABLE_HEIGHT + TABLE_START_Y_POS - TILE_HEIGHT;
        for (int i = 0; i < playerTiles.size(); i++) {
            Rectangle tileRect = new Rectangle(x + i * TILE_WIDTH, y, TILE_WIDTH, TILE_HEIGHT);
            if (tileRect.contains(e.getPoint())) {
                String discardedTile = playerTiles.get(i);
                discardedTiles.add(discardedTile);
                playerTiles.remove(i);
                simulateDrawTile(); // 模擬抽一張新牌
                selectedTileIndex = -1;
                hoverTileIndex = -1;
                repaint();
                // 模擬其他玩家打出相同的牌
                simulateOtherPlayersDiscard(discardedTile);
                break;
            }
        }
    }

    private void simulateOtherPlayersDiscard(String tile) {
        Timer timer = new Timer(1000, new ActionListener() {
            int playerIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (playerIndex) {
                    case 0:
                        rightPlayerDiscardedTiles.add(tile);
                        break;
                    case 1:
                        topPlayerDiscardedTiles.add(tile);
                        break;
                    case 2:
                        leftPlayerDiscardedTiles.add(tile);
                        break;
                }
                repaint();
                playerIndex++;
                if (playerIndex >= 3) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = TABLE_START_X_POS + 20 + SMALL_TILE_HEIGHT;
        int y = TABLE_HEIGHT + TABLE_START_Y_POS - TILE_HEIGHT;
        hoverTileIndex = -1;
        for (int i = 0; i < playerTiles.size(); i++) {
            Rectangle tileRect = new Rectangle(x + i * TILE_WIDTH, y, TILE_WIDTH, TILE_HEIGHT);
            if (tileRect.contains(e.getPoint())) {
                hoverTileIndex = i;
                break;
            }
        }
        repaint();
    }

    private void simulateDrawTile() {
        // 模擬從後端抽一張新牌
        String[] possibleTiles = {"1筒", "2筒", "3筒", "4筒", "5筒", "6筒", "7筒", "8筒", "9筒", "1條", "2條", "3條", "4條"};
        Random rand = new Random();
        String newTile = possibleTiles[rand.nextInt(possibleTiles.length)];
        playerTiles.add(newTile);
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