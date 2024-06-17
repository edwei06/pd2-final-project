package mahjong.main.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.TreeMap;

import mahjong.main.game.ClientGame;
import mahjong.main.game.player.Player;
import mahjong.main.gui.client.GameMainFrame;
import mahjong.main.net.packet.ClientPacket;
import mahjong.main.net.packet.PlayerPacket;

public class Client implements Runnable{
    private boolean isRunning;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ClientGame game;
    private final GameMainFrame mainFrame;

    public ClientGame getGame(){
        return game;
    }

    public Client(final GameMainFrame mainFrame, final String ipAddress, final int port){
        try {
            this.socket = new Socket(ipAddress, port);
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mainFrame = mainFrame;

        initialServerCommunication();
    }

    private void initialServerCommunication(){
        try {
            final int clientId = inputStream.readInt();
            game = new ClientGame(this, clientId);

            game.processPlayer(((Player) inputStream.readObject()));

            System.out.println("Finished initial server communication.");
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        isRunning = true;
        new Thread(() -> startReadAndWriteLoop()).start();
    }

    private void startReadAndWriteLoop(){
        while(isRunning){
            try{
                //read
                game.processPlayer((Player) inputStream.readObject());
                game.getActionSet().avaliableActionsClaer();
                //TODO : 更新畫面

                //write
                sendPacket(new PlayerPacket(game));

            } catch (final SocketException e) {
                break;
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPacket(ClientPacket packet){
        try {
            outputStream.writeObject(packet);
            outputStream.reset();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
