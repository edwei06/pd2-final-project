package mahjong.main.net;

import mahjong.main.game.player.Player;

/**
 * 屬於sever端用來處理client封包交流、處理等的class，
 * 並且一個client會有一個ClientHandler來處理
 */
public class ClientHandler implements Runnable{
    public int clientId;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    public void sendUpdate(Player player){
        // TODO 通過輸出流傳給client
    }
}
