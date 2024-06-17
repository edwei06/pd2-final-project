package mahjong.main.game;

import java.util.ArrayList;
import java.util.Scanner;

import mahjong.main.game.action.Action;
import mahjong.main.game.action.ActionSet;
import mahjong.main.game.player.Player;
import mahjong.main.game.player.Tile;
import mahjong.main.net.Client;

public class ClientGame {
    private int playerId;
    private Client client;

    private Player player;

    public ClientGame(Client client, int playerId){
        this.playerId = playerId;
        this.client = client;
    }

    public Player getPlayer(){
        return player;
    }

    public void processPlayer(Player updatePlayer, boolean isInit){
        this.player = updatePlayer;
        System.out.println("your hand :");
        for( Tile tile : (ArrayList<Tile>)this.player.getHandTiles()){
            if(tile == null) break;
            System.out.print(tile.getTileString() +" , ");
        }
        if(isInit) return;
        System.out.println();
        if(this.player.getTileDrawn() == null) System.out.println("drawnTile : none");
        else System.out.println("drawnTile : " + this.player.getTileDrawn().getTileString());
        System.out.println("Avaliable actions :");
        ArrayList<Action> avaliableActions = this.player.getActionSet().getAvaliableAcitons();
        System.out.print("(" + 9 + ") noAction");
        for(int i=0; i<avaliableActions.size(); i++){
            System.out.print("(" + i + ") " +avaliableActions.get(i) + " , ");
        }
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        int actionChoose = Integer.parseInt(scanner.nextLine());
        if(actionChoose == 9){
            this.player.getActionSet().setChosenAction(null);
            return;
        }else if(actionChoose == 0){
            this.player.setDiscardTile(this.player.getTileDrawn());
        }
        System.out.println(avaliableActions.get(actionChoose));
        this.player.getActionSet().setChosenAction(avaliableActions.get(actionChoose));
        
    }

    public ActionSet getActionSet(){
        return player.getActionSet();
    }
}
