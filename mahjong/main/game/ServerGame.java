package mahjong.main.game;

import java.util.ArrayList;
import java.util.Collections;
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
    private int maxPriority;
    public Player actPlayer;
    private TreeMap<Integer, ArrayList<Tile>> initialTileHands;

    private void initDistributeTileHands(){
        this.tiles = initTiles();
        for(int playerId =0; playerId<4; playerId++){
            ArrayList<Tile> tempTile = new ArrayList<Tile>();
            for(int tileNumber = 0; tileNumber<16; tileNumber++){
                tempTile.add(draw());
            }
            this.initialTileHands.put(playerId, tempTile);
        }
    }

    private ArrayList<Tile> initTiles(){
        ArrayList<Tile> temptiles=new ArrayList<>();
        String[] suits = {"Wong", "Tong", "Tiao"};
        for (String suit : suits) {
            for (int rank = 1; rank <= 9; rank++) {
                for (int i = 0; i < 4; i++) { 
                    temptiles.add(new Tile(suit, rank));
                }
            }
        }
        String[] suitsForWord={"East","South","West","North","Red","Green","White Dragon"};
        for (String suit : suitsForWord) {
            for (int i = 0; i < 4; i++) { 
                temptiles.add(new Tile(suit, 1));
            }
        }
        Collections.shuffle(temptiles);
        return temptiles;
    }
    
    private Tile draw(){
        Tile tileDrawn = this.tiles.get(0);
        this.tiles.remove(0);
        return tileDrawn;
    }

    public Player getPlayer(int playerId){
        return players.get(playerId);
    }

    public int spawnPlayer(int playerId){
        players.put(playerId, new Player(initialTileHands.get(playerId), playerId));
        playerId++;
        return playerId;
    }

    public void updateActionSet(int playerId, Player player){
        
    }
    public static void main(String[] args){
        ServerGame serverGame =new ServerGame();
        serverGame.tiles=serverGame.initTiles();
        int count=0;
        for(Tile tile :serverGame.tiles){
            System.out.println(tile.getSuit()+tile.getRank());
            count++;
        }
        System.out.println(count);
    }
}
