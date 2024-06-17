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
import java.util.TreeMap;
import mahjong.main.game.action.Action;
import mahjong.main.game.action.ActionSet;
import mahjong.main.game.player.*;
import mahjong.main.game.ClientGame;

class GamePanel extends JPanel {
    private ClientGame game;
    private List<String> playerTiles = new ArrayList<>(Arrays.asList("1Tong", "2Tong", "3Tong", "4Tong", "5Tong", "6Tong", "7Tong", "8Tong", "9Tong", "1Tiao", "2Tiao", "3Tiao"));
    private String[] eatenTiles = {"1Tong", "2Tong", "3Tong"}; // 示例吃牌
    public Control control = new Control(this);
    private List<JButton> playerTileButtons;
    private List<JButton> discardedTileButtons; // Buttons for discarded tiles
    private List<JButton> rightPlayerDiscardedTileButtons;
    private List<JButton> topPlayerDiscardedTileButtons;
    private List<JButton> leftPlayerDiscardedTileButtons;
    private List<JButton> playerEatenTileButtons;
    private List<JButton> rightPlayerEatenTileButtons;
    private List<JButton> topPlayerEatenTileButtons;
    private List<JButton> leftPlayerEatenTileButtons;
    static final int TILE_WIDTH = 40;
    static final int TILE_HEIGHT = 56;
    static final int SMALL_TILE_WIDTH = 30;
    static final int SMALL_TILE_HEIGHT = 42;
    static final int TABLE_WIDTH = 900;
    static final int TABLE_HEIGHT = 675;
    static final int TABLE_START_X_POS = 62;
    static final int TABLE_START_Y_POS = 46;
    ArrayList<Tile> playerTiles = new ArrayList<>();

    private int hoverTileIndex = -1;
    private int selectedTileIndex = -1;

    private JButton chiLowButton, chiMidButton, chiUpButton, pongButton, gangButton, cancelButton, huButton, replaceTileButton;

    private List<String> discardedTiles; // 用于保存打出的牌
    private List<String> rightPlayerDiscardedTiles;
    private List<String> topPlayerDiscardedTiles;
    private List<String> leftPlayerDiscardedTiles;
    private JButton tilesLeftButton;
    // private int totalTilesLeft = 144;  // Example initial count, adjust as necessary


    private Map<String, Image> tileImages; // 用于保存牌的图像
    /*
    public static void main(String[] args){
        Tile tile1 = new Tile("Wong", 1);
        Tile tile2 = new Tile("Wong", 1);
        Tile tile3 = new Tile("Wong", 1);
        Tile tile4 = new Tile("Wong", 3);
        Tile tile5 = new Tile("Wong", 3);
        Tile tile6 = new Tile("Wong", 3);
        Tile tile7 = new Tile("Wong", 4);
        Tile tile8 = new Tile("Wong", 4);
        Tile tile9 = new Tile("Wong", 4);
        Tile tile10 = new Tile("Wong", 5);
        Tile tile11 = new Tile("Wong", 5);
        Tile tile12 = new Tile("Wong", 5);
        Tile tile13 = new Tile("Wong", 7);

        // 將這些牌添加到玩家的手牌中
        ArrayList<Tile> handTiles = new ArrayList<>();
        handTiles.add(tile1);
        handTiles.add(tile2);
        handTiles.add(tile3);
        handTiles.add(tile4);
        handTiles.add(tile5);
        handTiles.add(tile6);
        handTiles.add(tile7);
        handTiles.add(tile8);
        handTiles.add(tile9);
        handTiles.add(tile10);
        handTiles.add(tile11);
        handTiles.add(tile12);
        handTiles.add(tile13);

        Tile determineTile = new Tile("Wong", 7);
        Player player = new Player(handTiles, 1);
        player.getActionSet().setChosenAction(Action.MAHJONG);
        System.out.println(player.getHandTiles().size());
        for (int i = 0; i < player.getHandTiles().size(); i++){
            System.out.println(player.getHandTiles().get(i).suit + player.getHandTiles().get(i).rank);
        }
        for (Tile tile : player.getHandTiles()){
            System.out.println(tile.suit + tile.rank);
            System.out.println("aaa");
        }
        // System.out.println(player.getHandTiles());
        System.out.println(player.getActionSet().getChosenAction());
        System.out.println(player.getActionSet().getAvaliableAcitons());
    }
        */
    public GamePanel(ClientGame game) {
        this.game = game;
        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        setBackground(new Color(34, 139, 34));  // Deep green background to simulate a Mahjong table
        handTiles.add(new Tile("Wong", 1));
        handTiles.add(new Tile("Wong", 1));
        handTiles.add(new Tile("Wong", 1));
        handTiles.add(new Tile("Wong", 3));
        handTiles.add(new Tile("Wong", 3));
        handTiles.add(new Tile("Wong", 3));
        handTiles.add(new Tile("Wong", 4));
        handTiles.add(new Tile("Wong", 4));
        handTiles.add(new Tile("Wong", 4));
        handTiles.add(new Tile("Wong", 5));
        handTiles.add(new Tile("Wong", 5));
        handTiles.add(new Tile("Wong", 5));
        handTiles.add(new Tile("Wong", 7));
    
        Player player = new Player(handTiles, 1);
        discardedTiles = new ArrayList<>();
        rightPlayerDiscardedTiles = new ArrayList<>();
        topPlayerDiscardedTiles = new ArrayList<>();
        leftPlayerDiscardedTiles = new ArrayList<>();
        playerTileButtons = new ArrayList<>();  // Initialize the playerTileButtons list
        discardedTileButtons = new ArrayList<>(); // Initialize the discardedTileButtons list
        rightPlayerDiscardedTileButtons = new ArrayList<>();
        topPlayerDiscardedTileButtons = new ArrayList<>();
        leftPlayerDiscardedTileButtons = new ArrayList<>();
        playerEatenTileButtons = new ArrayList<>();
        rightPlayerEatenTileButtons = new ArrayList<>();
        topPlayerEatenTileButtons = new ArrayList<>();
        leftPlayerEatenTileButtons = new ArrayList<>();    

        // initializeTilesLeftButton();
        loadTileImages();
        simulateDrawTile();
        initializePlayerTileButtons();
  
    }

    private void initializePlayerTileButtons() {
        int x = 130;
        int y = 650;
        playerTiles = new ArrayList<>();
        for (int i = 0; i < playerTiles.size(); i++) {
            JButton tileButton = createTileButton(playerTiles.get(i), x + i * 38, y);
            playerTileButtons.add(tileButton);
            add(tileButton);
        }
    }
/*
    private void initializeTilesLeftButton() {
        tilesLeftButton = new JButton(String.valueOf(totalTilesLeft));
        tilesLeftButton.setBounds(430, 300, 80, 80);  // Position it in the middle and make it a circle
        tilesLeftButton.setFocusPainted(false);
        tilesLeftButton.setContentAreaFilled(false);
        tilesLeftButton.setBorderPainted(false);
        tilesLeftButton.setOpaque(true);
        tilesLeftButton.setBackground(Color.BLACK);
        tilesLeftButton.setForeground(Color.WHITE);
        tilesLeftButton.setFont(new Font("Arial", Font.BOLD, 24));
    
        // Make the button circular
        tilesLeftButton.setPreferredSize(new Dimension(100, 100));
        tilesLeftButton.setMaximumSize(new Dimension(100, 100));
        tilesLeftButton.setMinimumSize(new Dimension(100, 100));
        tilesLeftButton.setHorizontalAlignment(SwingConstants.CENTER);
        tilesLeftButton.setVerticalAlignment(SwingConstants.CENTER);
    
        tilesLeftButton.setEnabled(false);  // Make the button non-interactive
    
        add(tilesLeftButton);
    }
*/
    
    private JButton createTileButton(Tile tile, int x, int y) {
        String tileKey = tile.suit + tile.rank;
        ImageIcon originalIcon = new ImageIcon(tileImages.get(tile));
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 56, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JButton tileButton = new JButton(scaledIcon);
        tileButton.setBounds(x, y, 40, 56);
        tileButton.setFocusPainted(false);
        tileButton.setContentAreaFilled(false);
        tileButton.setBorderPainted(false);

        tileButton.addActionListener(e -> handleTileClick(tileKey));

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
        updateDiscardedTileButtons();
        repaint();
        simulateOtherPlayersDiscard(tile);
        updateEatenTileButtons();
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

    private void updateDiscardedTileButtons() {
        for (JButton button : discardedTileButtons) {
            remove(button);
        }
        discardedTileButtons.clear();

        int baseX = getWidth() / 2 - 50 - 2 * SMALL_TILE_WIDTH;
        int baseY = getHeight() / 2 - SMALL_TILE_HEIGHT / 2 + 50;

        for (int i = 0; i < discardedTiles.size(); i++) {
            int x = baseX + (i % 6) * SMALL_TILE_WIDTH;
            int y = baseY + (i / 6) * SMALL_TILE_HEIGHT;
            JButton tileButton = createDiscardedTileButton(discardedTiles.get(i), x, y);
            discardedTileButtons.add(tileButton);
            add(tileButton);
        }

        revalidate();
        repaint();
    }

    private void initializeEatenTileButtons() {
        // Initialize eaten tiles for player
        int x = TABLE_START_X_POS + TABLE_WIDTH;
        int y = TABLE_START_Y_POS + TABLE_HEIGHT - TILE_HEIGHT;
        for (int i = 0; i < eatenTiles.length; i++) {
            JButton tileButton = createEatenTileButton(eatenTiles[i], x - i * TILE_WIDTH, y, 0);
            playerEatenTileButtons.add(tileButton);
            add(tileButton);
        }
    }
    
    private void updateEatenTileButtons() {
        for (JButton button : playerEatenTileButtons) {
            remove(button);
        }
        playerEatenTileButtons.clear();
    
        int x = TABLE_START_X_POS + TABLE_WIDTH - SMALL_TILE_WIDTH;
        int y = TABLE_START_Y_POS + TABLE_HEIGHT - TILE_HEIGHT;
        for (int i = 0; i < eatenTiles.length; i++) {
            JButton tileButton = createEatenTileButton(eatenTiles[i], x - i * SMALL_TILE_WIDTH, y, 0);
            playerEatenTileButtons.add(tileButton);
            add(tileButton);
        }
    
        updateOtherPlayersEatenTileButtons();
    
        revalidate();
        repaint();
    }
    
    private void updateOtherPlayersEatenTileButtons() {
        for (JButton button : rightPlayerEatenTileButtons) {
            remove(button);
        }
        rightPlayerEatenTileButtons.clear();
    
        for (JButton button : topPlayerEatenTileButtons) {
            remove(button);
        }
        topPlayerEatenTileButtons.clear();
    
        for (JButton button : leftPlayerEatenTileButtons) {
            remove(button);
        }
        leftPlayerEatenTileButtons.clear();
    
        // right player eaten tiles
        int x = TABLE_WIDTH + SMALL_TILE_WIDTH;
        int y = TABLE_START_Y_POS;
        for (int i = 0; i < eatenTiles.length; i++) {
            JButton tileButton = createEatenTileButton(eatenTiles[i], x, y + i * SMALL_TILE_WIDTH, 270);
            rightPlayerEatenTileButtons.add(tileButton);
            add(tileButton);
        }
    
        // top player eaten tiles
        x = TABLE_START_X_POS;
        y = TABLE_START_Y_POS;
        for (int i = 0; i < eatenTiles.length; i++) {
            JButton tileButton = createEatenTileButton(eatenTiles[i], x + i * SMALL_TILE_WIDTH, y, 180);
            topPlayerEatenTileButtons.add(tileButton);
            add(tileButton);
        }
    
        // left player eaten tiles
        x = TABLE_START_X_POS;
        y = TABLE_START_Y_POS + TABLE_HEIGHT - SMALL_TILE_WIDTH;
        for (int i = 0; i < eatenTiles.length; i++) {
            JButton tileButton = createEatenTileButton(eatenTiles[i], x, y - i * SMALL_TILE_WIDTH, 90);
            leftPlayerEatenTileButtons.add(tileButton);
            add(tileButton);
        }
    }
    
    private JButton createEatenTileButton(String tile, int x, int y, int rotation) {
        ImageIcon originalIcon = new ImageIcon(tileImages.get(tile));
        Image scaledImage;
        scaledImage = originalIcon.getImage().getScaledInstance(SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
    
        JButton tileButton = new JButton(scaledIcon);
        if (rotation == 90 || rotation == 270) {
            tileButton.setBounds(x, y, SMALL_TILE_HEIGHT, SMALL_TILE_WIDTH);
        } else {
            tileButton.setBounds(x, y, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
        tileButton.setFocusPainted(false);
        tileButton.setContentAreaFilled(false);
        tileButton.setBorderPainted(false);
    
        // Rotate the button
        tileButton.setIcon(new RotatedIcon(scaledIcon, rotation));
    
        return tileButton;
    }
    

    private JButton createDiscardedTileButton(String tile, int x, int y) {
        ImageIcon originalIcon = new ImageIcon(tileImages.get(tile));
        Image scaledImage = originalIcon.getImage().getScaledInstance(SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton tileButton = new JButton(scaledIcon);
        tileButton.setBounds(x, y, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        tileButton.setFocusPainted(false);
        tileButton.setContentAreaFilled(false);
        tileButton.setBorderPainted(false);

        return tileButton;
    }

    private void updateOtherPlayersDiscardedTileButtons() {
        for (JButton button : rightPlayerDiscardedTileButtons) {
            remove(button);
        }
        rightPlayerDiscardedTileButtons.clear();

        for (JButton button : topPlayerDiscardedTileButtons) {
            remove(button);
        }
        topPlayerDiscardedTileButtons.clear();

        for (JButton button : leftPlayerDiscardedTileButtons) {
            remove(button);
        }
        leftPlayerDiscardedTileButtons.clear();
        // right player
        int centerX = getWidth() / 2 + 150 - 2 * SMALL_TILE_WIDTH;
        int centerY = getHeight() / 2 + 50;
        for (int i = 0; i < rightPlayerDiscardedTiles.size(); i++) {
            int x = centerX + (i / 6) * SMALL_TILE_HEIGHT;
            int y = centerY - (i % 6) * SMALL_TILE_WIDTH;
            JButton tileButton = createDiscardedTileButton(rightPlayerDiscardedTiles.get(i), x, y, 270);
            rightPlayerDiscardedTileButtons.add(tileButton);
            add(tileButton);
        }
        // top player
        centerX = getWidth() - (getWidth() / 2 - 2 * SMALL_TILE_WIDTH);
        centerY = getHeight() - (getHeight() / 2 - SMALL_TILE_HEIGHT / 2 + 150);
        for (int i = 0; i < topPlayerDiscardedTiles.size(); i++) {
            int x = centerX - (i % 6) * SMALL_TILE_WIDTH;
            int y = centerY - (i / 6) * SMALL_TILE_HEIGHT;
            JButton tileButton = createDiscardedTileButton(topPlayerDiscardedTiles.get(i), x, y, 180);
            topPlayerDiscardedTileButtons.add(tileButton);
            add(tileButton);
        }
        // left player
        centerX = getWidth() / 2 - 100 - 2 * SMALL_TILE_WIDTH;
        centerY = getHeight() / 2 - 50 - 2 * SMALL_TILE_HEIGHT;
        for (int i = 0; i < leftPlayerDiscardedTiles.size(); i++) {
            int x = centerX - (i / 6) * SMALL_TILE_HEIGHT;
            int y = centerY + (i % 6) * SMALL_TILE_WIDTH;
            JButton tileButton = createDiscardedTileButton(leftPlayerDiscardedTiles.get(i), x, y, 90);
            leftPlayerDiscardedTileButtons.add(tileButton);
            add(tileButton);
        }
    }

    private JButton createDiscardedTileButton(String tile, int x, int y, int rotation) {
        ImageIcon originalIcon = new ImageIcon(tileImages.get(tile));
        Image scaledImage;
        scaledImage = originalIcon.getImage().getScaledInstance(SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton tileButton = new JButton(scaledIcon);
        if (rotation == 90 || rotation == 270) {
            tileButton.setBounds(x, y, SMALL_TILE_HEIGHT, SMALL_TILE_WIDTH);
        } else {
            tileButton.setBounds(x, y, SMALL_TILE_WIDTH, SMALL_TILE_HEIGHT);
        }
        tileButton.setFocusPainted(false);
        tileButton.setContentAreaFilled(false);
        tileButton.setBorderPainted(false);

        // Rotate the button
        tileButton.setIcon(new RotatedIcon(scaledIcon, rotation));

        return tileButton;
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

    // Helper class for rotating icons
    class RotatedIcon implements Icon {
        private Icon icon;
        private double angle;

        public RotatedIcon(Icon icon, int angle) {
            this.icon = icon;
            this.angle = Math.toRadians(angle);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            int cx = x + icon.getIconWidth() / 2;
            int cy = y + icon.getIconHeight() / 2;
            g2d.rotate(angle, cx, cy);
            icon.paintIcon(c, g2d, x, y);
            g2d.dispose();
        }

        @Override
        public int getIconWidth() {
            return icon.getIconWidth();
        }

        @Override
        public int getIconHeight() {
            return icon.getIconHeight();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 繪製網格線
        drawGridLines(g);
        // 繪製麻將桌和牌
        drawTable(g);
        drawOtherPlayersTiles(g);
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

    private void simulateOtherPlayersDiscard(String tile) {
        Timer timer = new Timer(1000, new ActionListener() {
            int playerIndex = 0;

            public void actionPerformed(ActionEvent e) {
                switch (playerIndex) {
                    case 0 -> rightPlayerDiscardedTiles.add(tile);
                    case 1 -> topPlayerDiscardedTiles.add(tile);
                    case 2 -> leftPlayerDiscardedTiles.add(tile);
                }
                updateOtherPlayersDiscardedTileButtons();
                repaint();
                playerIndex++;
                if (playerIndex >= 3) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }
/*
private void updateTilesLeftButton() {
    // totalTilesLeft--;  // Decrement the number of tiles left
    tilesLeftButton.setText(String.valueOf(totalTilesLeft));
    repaint();
}
*/
private void simulateDrawTile() {
    // Simulate drawing a new tile from the backend
    String[] possibleTiles = {"1Tong", "2Tong", "3Tong", "4Tong", "5Tong", "6Tong", "7Tong", "8Tong", "9Tong", "1Tiao", "2Tiao", "3Tiao", "4Tiao"};
    Random rand = new Random();
    String newTile = possibleTiles[rand.nextInt(possibleTiles.length)];
    playerTiles.add(newTile);
    // updateTilesLeftButton();  // Update the button text
}

}