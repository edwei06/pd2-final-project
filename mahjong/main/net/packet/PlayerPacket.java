package mahjong.main.net.packet;

import mahjong.main.game.ClientGame;
import mahjong.main.game.player.Player;

public class PlayerPacket extends ClientPacket {
    private static final long serialVersionUID = -8731243900388342502L;
    public Player player;

    public PlayerPacket(ClientGame clientGame){
        this.player = clientGame.getPlayer();
    }
}
