import java.util.List;

public class MoveLogics {

    // Method to check if a player can Pong
    public static boolean canPong(List<Tile> handTiles, Tile determinedTile) {
        int matchingCount = 0;
        
        for (Tile tile : handTiles) {
            if (tile.getSuit().equals(determinedTile.getSuit()) && tile.getRank() == determinedTile.getRank()) {
                matchingCount++;
            }
        }
        
        return matchingCount >= 2;
    }

    // Method to check if a player can Kong (additional for reference)
    public static boolean canKong(List<Tile> handTiles, Tile determinedTile) {
        int matchingCount = 0;
        
        for (Tile tile : handTiles) {
            if (tile.getSuit().equals(determinedTile.getSuit()) && tile.getRank() == determinedTile.getRank()) {
                matchingCount++;
            }
        }
        
        return matchingCount >= 3;
    }

    // Method to check if a player can Chow (additional for reference)
    public static boolean canChow(List<Tile> handTiles, Tile determinedTile) {
        boolean rankMinus2 = false;
        boolean rankMinus1 = false;
        boolean rankPlus1 = false;
        boolean rankPlus2 = false;
        
        for (Tile tile : handTiles) {
            if ((tile.getRank() + 2) == determinedTile.getRank() && tile.getSuit().equals(determinedTile.getSuit())) {
                rankMinus2 = true;
            }
            if ((tile.getRank() + 1) == determinedTile.getRank() && tile.getSuit().equals(determinedTile.getSuit())) {
                rankMinus1 = true;
            }
            if ((tile.getRank() - 1) == determinedTile.getRank() && tile.getSuit().equals(determinedTile.getSuit())) {
                rankPlus1 = true;
            }
            if ((tile.getRank() - 2) == determinedTile.getRank() && tile.getSuit().equals(determinedTile.getSuit())) {
                rankPlus2 = true;
            }
        }
        
        return (rankMinus2 && rankMinus1) || (rankMinus1 && rankPlus1) || (rankPlus1 && rankPlus2);
    }
}
