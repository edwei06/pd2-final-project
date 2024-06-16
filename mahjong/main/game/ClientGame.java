package mahjong.main.game;

import mahjong.main.game.action.ActionSet;
import mahjong.main.game.player.Player;
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

    public void processPlayer(Player updatePlayer){
        this.player = updatePlayer;
    }
}
