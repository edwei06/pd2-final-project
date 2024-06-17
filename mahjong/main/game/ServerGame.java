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
    private int nowPlayer = 0;
    public ArrayList<Tile> tiles;
    private TreeMap<Integer, Player> players = new TreeMap<Integer, Player>();
    private int maxPriority;
    public Player actPlayer;
    private TreeMap<Integer, ArrayList<Tile>> initialTileHands = new TreeMap<Integer, ArrayList<Tile>>();
    boolean closeGame = false;

    public void initDistributeTileHands(){
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

    public void updateActionSet(int playerId, Player updatePlayer){
        players.put(playerId, updatePlayer);
    }

    public void init(){
        players.get(nowPlayer).drawTile(draw());
    }

    public void updateServerGame(){
        this.maxPriority = 0;
        actPlayer = null;
        //知道主要動作的玩家
        for(Player player : players.values()){
            int playerPriority = player.getPriority();
            System.out.println("player" + player.getPlayerId() + " 's priority is " + playerPriority);
            if(maxPriority < playerPriority){
                maxPriority = playerPriority;
                actPlayer = player;
            }
        }

        if(actPlayer != null) actPlayer.updateHandTile();
        if(maxPriority == 0) { //無人需要操作
            players.get(nowPlayer).drawTile(draw());
        }else if(maxPriority == 1){ // 有人要打牌(打牌時只會有一個人有動作)
            for(Player player : players.values()){
                // 更新其他玩家的avaliableActions及drawnTile
                if(player.equals(actPlayer)) continue;
                System.out.println("player" + actPlayer.getPlayerId() + " 's discardTile :" + actPlayer.getDiscardTile());
                player.drawFromOther(actPlayer.getDiscardTile(), nowPlayer);
                player.updateEatenAndDiscardedTiles(actPlayer.getPlayersEatenTiles(), actPlayer.getPlayersDiscardedTiles());
            }
            nowPlayer ++;
        }else{ // 吃、碰、槓、胡才要處理其他玩家的資訊
            for(Player player : players.values()){
                if(player.equals(actPlayer)) continue;
                player.setTileDrawn(null);
                player.updateEatenAndDiscardedTiles(actPlayer.getPlayersEatenTiles(), actPlayer.getPlayersDiscardedTiles());
            }
            if(maxPriority == 4){ //槓牌要抽一張
                actPlayer.drawTile(draw());
            }else if(maxPriority == 5){
                closeGame = true;
            }
            nowPlayer = actPlayer.getPlayerId();
        }
    }

    public boolean getCloseGame(){
        return closeGame;
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
