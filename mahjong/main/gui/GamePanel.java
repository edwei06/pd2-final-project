import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

class GamePanel extends JPanel {
    private List<String> playerTiles = new ArrayList<>(Arrays.asList("1Tong", "2Tong", "3Tong", "4Tong", "5Tong", "6Tong", "7Tong", "8Tong", "9Tong", "1Tiao", "2Tiao", "3Tiao"));
    private String[] eatenTiles = {"1Tong", "2Tong", "3Tong"}; // 示例吃牌
    public Control control = new Control(this);
    private List<JButton> playerTileButtons;
    static final int TILE_WIDTH = 40;
    static final int TILE_HEIGHT = 56;
    static final int SMALL_TILE_WIDTH = 30;
    static final int SMALL_TILE_HEIGHT = 42;
    static final int TABLE_WIDTH = 900;
    static final int TABLE_HEIGHT = 675;
    static final int TABLE_START_X_POS = 62;
    static final int TABLE_START_Y_POS = 46;

    private int hoverTileIndex = -1;
    private int selectedTileIndex = -1;

    private JButton chiLowButton, chiMidButton, chiUpButton, pongButton, gangButton, cancelButton, huButton, replaceTileButton;

    private List<String> discardedTiles; // 用于保存打出的牌
    private List<String> rightPlayerDiscardedTiles;
    private List<String> topPlayerDiscardedTiles;
    private List<String> leftPlayerDiscardedTiles;

    private Map<String, Image> tileImages; // 用于保存牌的图像

    public GamePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        setBackground(new Color(34, 139, 34));  // Deep green background to simulate a Mahjong table

        discardedTiles = new ArrayList<>();
        rightPlayerDiscardedTiles = new ArrayList<>();
        topPlayerDiscardedTiles = new ArrayList<>();
        leftPlayerDiscardedTiles = new ArrayList<>();
        playerTileButtons = new ArrayList<>();  // Initialize the playerTileButtons list

        loadTileImages();
        simulateDrawTile();
        initializePlayerTileButtons();
    }

    private void initializePlayerTileButtons() {
        int x = 130;
        int y = 650;
        for (int i = 0; i < playerTiles.size(); i++) {
            JButton tileButton = createTileButton(playerTiles.get(i), x + i * 38, y);
            playerTileButtons.add(tileButton);
            add(tileButton);
        }
    }

    private JButton createTileButton(String tile, int x, int y) {
        ImageIcon originalIcon = new ImageIcon(tileImages.get(tile));
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 56, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JButton tileButton = new JButton(scaledIcon);
        tileButton.setBounds(x, y, 40, 56);
        tileButton.setFocusPainted(false);
        tileButton.setContentAreaFilled(false);
        tileButton.setBorderPainted(false);

        tileButton.addActionListener(e -> handleTileClick(tile));

        tileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tileButton.setBounds(x, y - 20, 40, 56);  // Move up when hovered
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                tileButton.setBounds(x, y, 40, 56);  // Move back when not hovered
            }
        });

        return tileButton;
    }

    private void handleTileClick(String tile) {
        discardedTiles.add(tile);
        playerTiles.remove(tile);
        simulateDrawTile(); // Simulate drawing a new tile
        updatePlayerTileButtons();
        repaint();
        simulateOtherPlayersDiscard(tile);
    }

    private void updatePlayerTileButtons() {
        for (JButton button : playerTileButtons) {
            remove(button);
        }
        playerTileButtons.clear();

        int x = 130;
        int y = 650;
        for (int i = 0; i < playerTiles.size(); i++) {
            JButton tileButton = createTileButton(playerTiles.get(i), x + i * 38, y);
            playerTileButtons.add(tileButton);
            add(tileButton);
        }

        revalidate();
        repaint();
    }

    private void loadTileImages() {
        tileImages = new HashMap<>();
        String[] suits = {"Tong", "Tiao", "Wong"};
        for (String suit : suits) {
            for (int i = 1; i <= 9; i++) {
                String tileName = i + suit;
                String resourcePath = "./resource/" + tileName + ".png";
                URL resource = getClass().getResource(resourcePath);
                if (resource != null) {
                    tileImages.put(tileName, new ImageIcon(resource).getImage());
                } else {
                    System.err.println("Image not found: " + resourcePath);
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 繪製網格線
        drawGridLines(g);
        // 繪製麻將桌和牌
        drawTable(g);
        // drawPlayerTiles(g);
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
        int x = 112;
        int y = 559;
        // 繪製玩家的手牌
        for (int i = 0; i < playerTiles.size(); i++) {
            if (i == hoverTileIndex || i == selectedTileIndex) {
                drawTileImage(g,250 + i * (65),1120, playerTiles.get(i), 0,80, 112);  // 向上移動的效果
            } else {
                drawTileImage(g,250 + i * (65),1120 + 20, playerTiles.get(i), 0, 80 , 112);
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

    private void drawTileImage(Graphics g, int x, int y, String tileText, double rotate, int width, int height) {
        Image tileImage = tileImages.get(tileText);
        if (tileImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            AffineTransform originalTransform = g2d.getTransform();
            AffineTransform transform = new AffineTransform();

            transform.rotate(Math.toRadians(rotate), x + width / 2.0, y + height / 2.0);
            g2d.setTransform(transform);
            g2d.drawImage(tileImage, x, y, width, height, this);
            g2d.setTransform(originalTransform);
            g2d.dispose();
        }
    }

    private void drawEatenTiles(Graphics g) {
        // 繪製自己右側的吃牌
        int x = TABLE_START_X_POS + TABLE_WIDTH - TILE_WIDTH;  // 玩家右側的牌桌邊緣
        int y = TABLE_START_Y_POS + TABLE_HEIGHT - TILE_HEIGHT;

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTileImage(g, x - i * TILE_WIDTH, y, eatenTiles[i], 0, TILE_WIDTH, TILE_HEIGHT);
        }

        // 繪製上方玩家的吃牌
        x = TABLE_START_X_POS;  // 上方玩家右側的牌桌邊緣
        y = TABLE_START_Y_POS + SMALL_TILE_HEIGHT;

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTileImage(g, x + (i + 1) * SMALL_TILE_WIDTH, y, eatenTiles[i], 0, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製左側玩家的吃牌
        x = TABLE_START_X_POS + SMALL_TILE_HEIGHT;
        y = TABLE_START_Y_POS + TABLE_HEIGHT;  // 左側玩家右側的牌桌邊緣

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTileImage(g, x, y - (i + 1) * SMALL_TILE_WIDTH, eatenTiles[i], 0, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製右側玩家的吃牌
        x = TABLE_START_X_POS + TABLE_WIDTH - SMALL_TILE_HEIGHT; //
        y = TABLE_START_Y_POS + SMALL_TILE_WIDTH;  // 右側玩家右側的牌桌邊緣

        for (int i = 0; i < eatenTiles.length; i++) {
            drawTileImage(g, x, y + i * SMALL_TILE_WIDTH, eatenTiles[i], 0, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
    }

    private void drawDiscardedTiles(Graphics g) {
        // 繪製自己打出的牌
        int centerX = getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH;
        int centerY = getHeight() / 2 - SMALL_TILE_HEIGHT / 2 + 50;
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTileImage(g, centerX + (i % 6) * SMALL_TILE_WIDTH, centerY + (i / 6) * SMALL_TILE_HEIGHT, discardedTiles.get(i), 0, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製左邊玩家打出的牌
        centerX = getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH;
        centerY = getHeight() / 2 - 50 - 2 * SMALL_TILE_HEIGHT;
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTileImage(g, centerX - (i / 6) * SMALL_TILE_HEIGHT, centerY + (i % 6) * SMALL_TILE_WIDTH, discardedTiles.get(i), 90, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製上方玩家打出的牌
        centerX = getWidth() - (getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH);
        centerY = getHeight() - (getHeight() / 2 - SMALL_TILE_HEIGHT / 2 + 150);
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTileImage(g, centerX - (i % 6) * SMALL_TILE_WIDTH, centerY - (i / 6) * SMALL_TILE_HEIGHT, discardedTiles.get(i), 180, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

        // 繪製右邊玩家打出的牌
        centerX = getWidth() - (getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH);
        centerY = getHeight() - (getHeight() / 2 - 50 - 2 * SMALL_TILE_HEIGHT +100);
        for (int i = 0; i < discardedTiles.size(); i++) {
            drawTileImage(g, centerX  + (i / 6) * SMALL_TILE_HEIGHT, centerY - (i % 6) * SMALL_TILE_WIDTH, discardedTiles.get(i), 270, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }

    }

    public void handleTileClick(int x, int y) {
        int baseX = 512 - 400;
        int baseY = 384 + 275;
        for (int i = 0; i < playerTiles.size(); i++) {
            Rectangle tileRect = new Rectangle(baseX + i * TILE_WIDTH, baseY, TILE_WIDTH, TILE_HEIGHT);
            if (tileRect.contains(new Point(x, y))) {
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

            public void actionPerformed(ActionEvent e) {
                switch (playerIndex) {
                    case 0 -> rightPlayerDiscardedTiles.add(tile);
                    case 1 -> topPlayerDiscardedTiles.add(tile);
                    case 2 -> leftPlayerDiscardedTiles.add(tile);
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

    public void handleTileHover(int x, int y) {
        hoverTileIndex = -1;
        int baseX = 512 - 380;
        int baseY = 384 + 275;
        for (int i = 0; i < playerTiles.size(); i++) {
            Rectangle tileRect = new Rectangle(baseX + i * TILE_WIDTH, baseY, TILE_WIDTH, TILE_HEIGHT);
            if (tileRect.contains(new Point(x, y))) {
                hoverTileIndex = i;
                break;
            }
        }
        repaint();
    }

    private void simulateDrawTile() {
        // 模擬從後端抽一張新牌
        String[] possibleTiles = {"1Tong", "2Tong", "3Tong", "4Tong", "5Tong", "6Tong", "7Tong", "8Tong", "9Tong", "1Tiao", "2Tiao", "3Tiao", "4Tiao"};
        Random rand = new Random();
        String newTile = possibleTiles[rand.nextInt(possibleTiles.length)];
        playerTiles.add(newTile);
    }
}
