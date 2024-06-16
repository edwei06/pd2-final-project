package mahjong.main.game.player;

import java.util.ArrayList;

import mahjong.main.game.action.Action;
import mahjong.main.game.action.ActionSet;

public class Player {
    private ArrayList<Tile> handTiles; //手牌
    private ActionSet actionSet; //動作指令(吃、碰、槓、胡)
    private ArrayList<Tile[]> eatenTiles; //吃碰槓牌堆
    private Tile discardTile; //要丟的牌
    private Tile tileDrawn; //抽到的牌
    private int playerId;

    public Player(ArrayList<Tile> tiles, int playerId){
        this.eatenTiles = new ArrayList<Tile[]>();
        this.actionSet = new ActionSet();
        this.handTiles = tiles;
        this.playerId = playerId;
    }

    public void drawTile(Tile tile){
        //判斷tile跟handTile的關係 ex. canKONG canMAJONG
        // 修改Player的ActionSet
        setTileDrawn(tile);
    }

    public void updateHandTile(){

    }

    public void addEatenTiles(Tile[] eatenTiles){
        this.eatenTiles.add(eatenTiles);
    }

    public void addAction(Action action){
        actionSet.getAvaliableAcitons().add(action);
    }

    public void setHandTiles(ArrayList<Tile> tiles){
        this.handTiles = tiles;
    }

    public void setDiscardTile(Tile tile){
        this.discardTile = tile;
    }

    public void setTileDrawn(Tile tile){
        this.tileDrawn = tile;
    }

    public Tile getTileDrawn(){
        return tileDrawn;
    }

    public Tile getDiscardTile(){
        return discardTile;
    }

    public int getPlayerId(){
        return playerId;
    }

    public ActionSet getActionSet(){
        return actionSet;
    }

    public ArrayList<Tile> getHandTiles(){
        return handTiles;
    }
    
}
