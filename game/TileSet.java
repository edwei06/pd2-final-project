// TileSet.java

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileSet {
    private List<Tile> tiles;

    public TileSet() {
        this.tiles = new ArrayList<>();
       
        String[] suits = {"Wong", "Tong", "Tiao"};
        for (String suit : suits) {
            for (int rank = 1; rank <= 9; rank++) {
                for (int i = 0; i < 4; i++) { 
                    tiles.add(new Tile(suit, rank));
                }
            }
        }
        String[] suitsForWord={"East","South","West","North","Red","Green","White Dragon"};
        for (String suit : suitsForWord) {
            for (int i = 0; i < 4; i++) { 
                tiles.add(new Tile(suit, 1));
            }
        }
    }

    public void shuffle() {

        Collections.shuffle(tiles);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Tile> drawTiles(int count) {
        List<Tile> drawn = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (!tiles.isEmpty()) {
                drawn.add(tiles.remove(0));
            }
        }
        return drawn;
    }
}
