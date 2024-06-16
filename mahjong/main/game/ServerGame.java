package mahjong.main.game;

import java.util.ArrayList;
import java.util.TreeMap;

import mahjong.main.game.player.Player;
import mahjong.main.game.player.Tile;

/**
 * 負責排組的邏輯運算
 */
public class ServerGame {
    private int NowPlayer = 0;
    public ArrayList<Tile> tiles;
    private TreeMap<Integer, Player> players;



    
    private Tile draw(){
        Tile tileDrawn = this.tiles.get(0);
        this.tiles.remove(0);
        return tileDrawn;
    }

    public Player getPlayer(int playerId){
        return players.get(playerId);
    }

    public void tick(){


    }
}
