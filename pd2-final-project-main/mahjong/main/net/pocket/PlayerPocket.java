package mahjong.main.net.pocket;

import mahjong.main.game.ClientGame;
import mahjong.main.game.player.Player;

public class PlayerPocket extends ClientPocket {
    private static final long serialVersionUID = -8731243900388342502L;
    private Player player;

    public PlayerPocket(ClientGame clientGame){
        this.player = clientGame.getPlayer();
    }
}
