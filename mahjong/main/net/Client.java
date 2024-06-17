package mahjong.main.net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mahjong.main.game.ClientGame;
import mahjong.main.gui.client.GameMainFrame;

public class Client implements Runnable{
    private boolean isRunning;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private ClientGame game;
    // TODO
    private final GameMainFrame mainFrame;

    public ClientGame getGame(){
        return game;
    }

}
