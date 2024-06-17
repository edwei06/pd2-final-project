package mahjong.main.game.player;

import java.util.ArrayList;
import java.util.List;

public class ActionLogic {
    public static boolean canPong(List<Tile> handTiles, Tile determinedTile) {
        int matchingCount = 0;
        
        for (Tile tile : handTiles) {
            if (tile.getSuit().equals(determinedTile.getSuit()) && tile.getRank() == determinedTile.getRank()) {
                matchingCount++;
            }
        }
        
        return matchingCount >= 2;
    }

    public static boolean canKong(List<Tile> handTiles, Tile determinedTile) {
        int matchingCount = 0;
        
        for (Tile tile : handTiles) {
            if (tile.getSuit().equals(determinedTile.getSuit()) && tile.getRank() == determinedTile.getRank()) {
                matchingCount++;
            }
        }
        
        return matchingCount >= 3;
    }

    public static boolean canChowWithLower(List<Tile> handTiles, Tile determinedTile) {
        boolean hasRankMinusOne = false;
        boolean hasRankMinusTwo = false;
        
        for (Tile tile : handTiles) {
            if (tile.getSuit().equals(determinedTile.getSuit())) {
                if (tile.getRank() == determinedTile.getRank() - 1) {
                    hasRankMinusOne = true;
                }
                if (tile.getRank() == determinedTile.getRank() - 2) {
                    hasRankMinusTwo = true;
                }
            }
        }
        
        return hasRankMinusOne && hasRankMinusTwo;
    }

    public static boolean canChowWithMiddle(List<Tile> handTiles, Tile determinedTile) {
        boolean hasRankMinusOne = false;
        boolean hasRankPlusOne = false;
        
        for (Tile tile : handTiles) {
            if (tile.getSuit().equals(determinedTile.getSuit())) {
                if (tile.getRank() == determinedTile.getRank() - 1) {
                    hasRankMinusOne = true;
                }
                if (tile.getRank() == determinedTile.getRank() + 1) {
                    hasRankPlusOne = true;
                }
            }
        }
        
        return hasRankMinusOne && hasRankPlusOne;
    }

    public static boolean canChowWithUpper(List<Tile> handTiles, Tile determinedTile) {
        boolean hasRankPlusOne = false;
        boolean hasRankPlusTwo = false;
        
        for (Tile tile : handTiles) {
            if (tile.getSuit().equals(determinedTile.getSuit())) {
                if (tile.getRank() == determinedTile.getRank() + 1) {
                    hasRankPlusOne = true;
                }
                if (tile.getRank() == determinedTile.getRank() + 2) {
                    hasRankPlusTwo = true;
                }
            }
        }
        
        return hasRankPlusOne && hasRankPlusTwo;
    }

    public static boolean canWin(List<Tile> handTiles, Tile determineTile) {
        // 創建副本以避免改變原來的手牌
        List<Tile> tiles = new ArrayList<>(handTiles);
        // 將 determineTile 添加到手牌中
        tiles.add(determineTile);

        // 檢查是否有一對將牌
        for (int i = 0; i < tiles.size() - 1; i++) {
            Tile tile1 = tiles.get(i);
            Tile tile2 = tiles.get(i + 1);

            if (tile1.getRank() == tile2.getRank() && tile1.getSuit().equals(tile2.getSuit())) {
                // 移除將牌
                List<Tile> remainingTiles = new ArrayList<>(tiles);
                remainingTiles.remove(i);
                remainingTiles.remove(i);

                // 檢查剩下的牌是否能組成刻子或順子
                if (canFormSets(remainingTiles)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean canFormSets(List<Tile> tiles) {
        if (tiles.isEmpty()) {
            return true;
        }

        // 嘗試移除一個刻子
        for (int i = 0; i < tiles.size() - 2; i++) {
            Tile tile1 = tiles.get(i);
            Tile tile2 = tiles.get(i + 1);
            Tile tile3 = tiles.get(i + 2);

            if (tile1.getRank() == tile2.getRank() && tile1.getRank() == tile3.getRank() && tile1.getSuit().equals(tile2.getSuit()) && tile1.getSuit().equals(tile3.getSuit())) {
                List<Tile> remainingTiles = new ArrayList<>(tiles);
                remainingTiles.remove(i);
                remainingTiles.remove(i);
                remainingTiles.remove(i);

                if (canFormSets(remainingTiles)) {
                    return true;
                }
            }
        }

        // 嘗試移除一個順子
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile1 = tiles.get(i);

            if (containsRankAndSuit(tiles, tile1.getRank() + 1, tile1.getSuit()) &&
                containsRankAndSuit(tiles, tile1.getRank() + 2, tile1.getSuit())) {
                List<Tile> remainingTiles = new ArrayList<>(tiles);
                removeFirstRankAndSuit(remainingTiles, tile1.getRank(), tile1.getSuit());
                removeFirstRankAndSuit(remainingTiles, tile1.getRank() + 1, tile1.getSuit());
                removeFirstRankAndSuit(remainingTiles, tile1.getRank() + 2, tile1.getSuit());

                if (canFormSets(remainingTiles)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean containsRankAndSuit(List<Tile> tiles, int rank, String suit) {
        for (Tile tile : tiles) {
            if (tile.getRank() == rank && tile.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    private static void removeFirstRankAndSuit(List<Tile> tiles, int rank, String suit) {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getRank() == rank && tiles.get(i).getSuit().equals(suit)) {
                tiles.remove(i);
                return;
            }
        }
    }
}
