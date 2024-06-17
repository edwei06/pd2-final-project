package mahjong.main.game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import mahjong.main.game.action.Action;
import mahjong.main.game.action.ActionSet;

public class Player {
    private ArrayList<Tile> handTiles; //手牌
    private ActionSet actionSet; //動作指令(吃、碰、槓、胡)
    private TreeMap<Integer ,ArrayList<Tile[]>> playersEatenTiles; //吃碰槓牌堆
    private ArrayList<Tile[]> eatenTileArray;
    private Tile discardTile; //要丟的牌
    private Tile tileDrawn; //抽到的牌
    private int playerId;
    public TreeMap<Integer,ArrayList<Tile>> playersDiscardedTiles;

    public Player(ArrayList<Tile> tiles, int playerId){
        this.playersEatenTiles = new TreeMap<Integer ,ArrayList<Tile[]>>();
        this.eatenTileArray = new ArrayList<Tile[]>();
        this.actionSet = new ActionSet();
        this.handTiles = tiles;
        this.playerId = playerId;
        this.playersDiscardedTiles=new TreeMap<Integer,ArrayList<Tile>>();
    }

    public void updateEatenAndDiscardedTiles(TreeMap<Integer ,ArrayList<Tile[]>> newEatenTiles, TreeMap<Integer,ArrayList<Tile>> newDiscardedTiles){
        this.playersDiscardedTiles = newDiscardedTiles;
        this.playersEatenTiles = newEatenTiles;
    }

    //用來回傳此玩家的Priority
    public int getPriority(){
        Action chosenAction = actionSet.getChosenAction();
        if(chosenAction.equals(Action.DISCARD)) return 1;
        else if(chosenAction.toString().contains("CHOW")) return 2;
        else if (chosenAction.equals(Action.PONG)) return 3;
        else if(chosenAction.equals(Action.KONG)) return 4;
        else if (chosenAction.equals(Action.MAHJONG)) return 5;
        else return 0;
    }
    //這是用來判斷打牌之後人家要不要的
    public void drawFromOther(Tile tile,int discardedPlayerID){
        boolean Kong=ActionLogic.canKong(handTiles, tile);
        boolean Win=ActionLogic.canWin(handTiles, tile);
        boolean Pong=ActionLogic.canPong(handTiles, tile);
        boolean Chowwithlower=false;
        boolean Chowwithmiddel=false;
        boolean Chowwithupper=false;
        if ((discardedPlayerID+1)%4-playerId==0){
            Chowwithlower=ActionLogic.canChowWithLower(handTiles, tile);
            Chowwithmiddel=ActionLogic.canChowWithMiddle(handTiles, tile);
            Chowwithupper=ActionLogic.canChowWithUpper(handTiles, tile);
        }
        if (Kong) {
            actionSet.avaliableActions.add(Action.KONG);
        }
        if (Win) {
            actionSet.avaliableActions.add(Action.MAHJONG);
        }
        if (Pong) {
            actionSet.avaliableActions.add(Action.PONG);
        }
        if (Chowwithlower) {
            actionSet.avaliableActions.add(Action.LOWWERCHOW);
        }
        if (Chowwithmiddel) {
            actionSet.avaliableActions.add(Action.MIIDLECHOW);
        }
        if (Chowwithupper) {
            actionSet.avaliableActions.add(Action.UPPERCHOW);
        }
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
        
        actionSet.avaliableActions.add(Action.DISCARD);
        setTileDrawn(tile);
    }

    

    public void updateHandTile(){
        if (actionSet.getChosenAction()==Action.DISCARD) {
            handTiles.add(tileDrawn);
            handTiles.remove(discardTile);
        }
        else if (actionSet.getChosenAction()==Action.KONG) {
            Tile[] tileArray = new Tile[4];
            tileArray[0]=tileDrawn;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileDrawn;
            tileArray[3]=tileDrawn;
            for(int i=0; i<3;i++){
               handTiles.remove(tileDrawn); 
            }

            eatenTileArray.add(tileArray);
            playersEatenTiles.put(getPlayerId(),eatenTileArray);           
        }
        else if (actionSet.getChosenAction()==Action.PONG) {
            Tile[] tileArray = new Tile[4];
            tileArray[0]=tileDrawn;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileDrawn;
            for(int i=0; i<2;i++){
               handTiles.remove(tileDrawn); 
            }
            eatenTileArray.add(tileArray);
            playersEatenTiles.put(getPlayerId(),eatenTileArray);
            actionSet.getAvaliableAcitons().clear();
            actionSet.getAvaliableAcitons().add(Action.DISCARD);
        }

        else if (actionSet.getChosenAction()==Action.LOWWERCHOW) {
            Tile[] tileArray = new Tile[4];
            Tile tilelow = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()-2);
            Tile tileup = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()-1);
            tileArray[0]=tilelow;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileup;
            //移除手牌
            handTiles.remove(tilelow);
            handTiles.remove(tileup);
            eatenTileArray.add(tileArray);
            playersEatenTiles.put(getPlayerId(),eatenTileArray);
        }
        else if (actionSet.getChosenAction()==Action.MIIDLECHOW) {
            Tile[] tileArray = new Tile[4];
            Tile tilelow = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()-1);
            Tile tileup = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()+1);
            tileArray[0]=tilelow;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileup;
            //移除手牌
            handTiles.remove(tilelow);
            handTiles.remove(tileup);
            eatenTileArray.add(tileArray);
            playersEatenTiles.put(getPlayerId(),eatenTileArray);
        }
        else if (actionSet.getChosenAction()==Action.UPPERCHOW) {
            Tile[] tileArray = new Tile[4];
            Tile tilelow = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()+1);
            Tile tileup = new Tile(tileDrawn.getSuit(),tileDrawn.getRank()+2);
            tileArray[0]=tilelow;
            tileArray[1]=tileDrawn;
            tileArray[2]=tileup;
            //移除手牌
            handTiles.remove(tilelow);
            handTiles.remove(tileup);
            eatenTileArray.add(tileArray);
            playersEatenTiles.put(getPlayerId(),eatenTileArray);
        }
    }

    public void addEatenTiles(Tile[] eatenTiles){
        this.eatenTileArray.add(eatenTiles);
        this.playersEatenTiles.put(getPlayerId(),eatenTileArray);
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
        //Player player = new Player(handTiles, 0);
        //測試getPriority方法

        /*player.getActionSet().setChosenAction(Action.DISCARD);
        System.out.println(player.getPriority());
        player.getActionSet().setChosenAction(Action.LOWWERCHOW);
        System.out.println(player.getPriority());
        player.getActionSet().setChosenAction(Action.MIIDLECHOW);
        System.out.println(player.getPriority());
        player.getActionSet().setChosenAction(Action.PONG);
        System.out.println(player.getPriority());
        player.getActionSet().setChosenAction(Action.KONG);
        System.out.println(player.getPriority());
        player.getActionSet().setChosenAction(Action.MAHJONG);
        System.out.println(player.getPriority());
        */
        //測試drawFromOther方法
        /* 
        Player player0 = new Player(handTiles, 0);
        Player player1 = new Player(handTiles, 1);
        Tile testTile = new Tile("Wong", 5);
        player1.drawFromOther( testTile, 3);
        System.out.println(player1.getActionSet().getAvaliableAcitons()); */
        

        // 要判斷的目標牌
        //Tile determineTile = new Tile("Wong", 7);
        //Player player = new Player(handTiles, 1);

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
        /* 
        player.actionSet.chosenAction=Action.KONG;
        player.tileDrawn =new Tile("Wong", 5);
        player.updateHandTile();
        for(Tile tile : handTiles){
            System.out.println(tile.suit+tile.rank);
        }
        System.out.println("@@@@@@@@@@");
        for(Tile tile : player.eatenTiles.get(player.getPlayerId()).get(0)){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }
        */
        //測試updateHandTile中的碰牌
        /*player.actionSet.chosenAction=Action.PONG;
        player.tileDrawn =new Tile("Wong", 5);
        player.updateHandTile();
        for(Tile tile : handTiles){
            System.out.println(tile.suit+tile.rank);
        }
        System.out.println("@@@@@@@@@@");
        for(Tile tile : player.eatenTiles.get(0).get(0)){
            if (tile != null) {
                System.out.println(tile.suit + tile.rank);
            }
        }
        System.out.println(player.actionSet.getAvaliableAcitons().get(0));*/
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