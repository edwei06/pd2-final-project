import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class testTileSet {
    private List<Tile> tiles;

    public testTileSet() {
        this.tiles = new ArrayList<>();
       
        
        tiles.add(new Tile("Wong", 1)); tiles.add(new Tile("Wong", 1)); tiles.add(new Tile("Wong", 1));
        tiles.add(new Tile("Wong", 2)); tiles.add(new Tile("Wong", 2)); tiles.add(new Tile("Wong", 2));
        tiles.add(new Tile("Wong", 3)); tiles.add(new Tile("Wong", 3)); tiles.add(new Tile("Wong", 3));
        tiles.add(new Tile("Tong", 1)); tiles.add(new Tile("Tong", 1));
        tiles.add(new Tile("Tiao", 1)); tiles.add(new Tile("Tiao", 1)); tiles.add(new Tile("Tiao", 1));
        tiles.add(new Tile("Tiao", 2)); tiles.add(new Tile("Tiao", 3));
    }

    public void shuffle() {
        
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
