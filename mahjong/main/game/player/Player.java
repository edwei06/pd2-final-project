package mahjong.main.game.player;

import java.util.ArrayList;
import java.util.List;

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
        boolean Kong=ActionLogic.canKong(handTiles, tile);
        boolean Win=ActionLogic.canWin(handTiles, tile);
        if (Kong) {
            actionSet.avaliableActions.add(Action.KONG);
        }
        if (Win) {
            actionSet.avaliableActions.add(Action.MAHJONG);
        }
        if(!Win&&!Kong){
            actionSet.avaliableActions.add(Action.DISCARD);
        }
        setTileDrawn(tile);
    }

    

    public void updateHandTile(){
        if (actionSet.getChosenAction()==Action.DISCARD) {
            handTiles.add(tileDrawn);
            handTiles.remove(discardTile);
        }
        if (actionSet.getChosenAction()==Action.KONG) {
            Tile[] tileArray = new Tile[4];
            tileArray[0]=tileDrawn;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileDrawn;
            tileArray[3]=tileDrawn;
            for(int i=0; i<3;i++){
               handTiles.remove(tileDrawn); 
            }
            eatenTiles.add(tileArray);
        }
        if (actionSet.getChosenAction()==Action.PONG) {
            Tile[] tileArray = new Tile[4];
            tileArray[0]=tileDrawn;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileDrawn;
            for(int i=0; i<2;i++){
               handTiles.remove(tileDrawn); 
            }
            eatenTiles.add(tileArray);
        }

        if (actionSet.getChosenAction()==Action.LOWWERCHOW) {
            Tile[] tileArray = new Tile[4];
            Tile tilelow = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()-2);
            Tile tileup = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()-1);
            tileArray[0]=tilelow;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileup;
            //移除手牌
            handTiles.remove(tilelow);
            handTiles.remove(tileup);
            eatenTiles.add(tileArray);
        }
        if (actionSet.getChosenAction()==Action.MIIDLECHOW) {
            Tile[] tileArray = new Tile[4];
            Tile tilelow = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()-1);
            Tile tileup = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()+1);
            tileArray[0]=tilelow;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileup;
            //移除手牌
            handTiles.remove(tilelow);
            handTiles.remove(tileup);
            eatenTiles.add(tileArray);
        }
        if (actionSet.getChosenAction()==Action.UPPERCHOW) {
            Tile[] tileArray = new Tile[4];
            Tile tilelow = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()+1);
            Tile tileup = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()+2);
            tileArray[0]=tilelow;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileup;
            //移除手牌
            handTiles.remove(tilelow);
            handTiles.remove(tileup);
            eatenTiles.add(tileArray);
        }
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
    public static void main(String[] args) {
        // 創建一些測試用的牌
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

        // 創建玩家
        Player player = new Player(handTiles, 1);

        // 要判斷的目標牌
        //Tile determineTile = new Tile("Wong", 7);

        // 測試 canWin 方法
        /*boolean canWinResult = ActionLogic.canWin(player.getHandTiles(), determineTile);
        System.out.println("Can Win: " + canWinResult);  // 應該輸出: Can Win: true
        player.drawTile(determineTile);
        System.out.println(player.getActionSet().getAvaliableAcitons());*/

        // 測試不能胡牌的情況
        /*handTiles.remove(tile13);
        handTiles.add(new Tile("Wong", 8));
        boolean canWinResult2 = ActionLogic.canWin(player.getHandTiles(), determineTile);
        System.out.println("Can Win: " + canWinResult2);  // 應該輸出: Can Win: false*/

        //測試updateHandTile中的丟牌
        /*player.actionSet.chosenAction=Action.DISCARD;
        player.tileDrawn =new Tile("Wong", 7);
        player.discardTile =new Tile("Wong", 5);
        player.updateHandTile();
        for(Tile tile : handTiles){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }*/
        //測試updateHandTile中的槓牌
        /*player.actionSet.chosenAction=Action.KONG;
        player.tileDrawn =new Tile("Wong", 5);
        player.updateHandTile();
        for(Tile tile : handTiles){
            System.out.println(tile.suit+tile.rank);
        }
        System.out.println("@@@@@@@@@@");
        for(Tile tile : player.eatenTiles.get(0)){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }*/
        //測試updateHandTile中的碰牌
        /*player.actionSet.chosenAction=Action.PONG;
        player.tileDrawn =new Tile("Wong", 5);
        player.updateHandTile();
        for(Tile tile : handTiles){
            System.out.println(tile.suit+tile.rank);
        }
        System.out.println("@@@@@@@@@@");
        for(Tile tile : player.eatenTiles.get(0)){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }*/
        //測試updateHandTile中的lowwerchow
        /*player.actionSet.chosenAction=Action.LOWWERCHOW;
        player.tileDrawn =new Tile("Wong", 6);
        player.updateHandTile();
        for(Tile tile : handTiles){
            System.out.println(tile.suit+tile.rank);
        }
        System.out.println("@@@@@@@@@@");
        for(Tile tile : player.eatenTiles.get(0)){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }*/

        //測試updateHandTile中的middlechow
        /*player.actionSet.chosenAction=Action.MIIDLECHOW;
        player.tileDrawn =new Tile("Wong", 6);
        player.updateHandTile();
        for(Tile tile : handTiles){
            System.out.println(tile.suit+tile.rank);
        }
        System.out.println("@@@@@@@@@@");
        for(Tile tile : player.eatenTiles.get(0)){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }*/
        
        //測試updateHandTile中的upperchow
        /*player.actionSet.chosenAction=Action.UPPERCHOW;
        player.tileDrawn =new Tile("Wong", 3);
        player.updateHandTile();
        for(Tile tile : handTiles){
            System.out.println(tile.suit+tile.rank);
        }
        System.out.println("@@@@@@@@@@");
        for(Tile tile : player.eatenTiles.get(0)){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }*/

    }
}