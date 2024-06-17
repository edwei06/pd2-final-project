package mahjong.main.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import mahjong.main.game.ServerGame;
import mahjong.main.net.packet.ClientPacket;
import mahjong.main.net.packet.DisconnetPacket;
import mahjong.main.net.packet.PlayerPacket;

/**
 * <p>
 * 1. 負責管理sever與client的連接管理
 * </p>
 * 2. 啟動遊戲的循環
 * </p>
 * 3. sever與client數據包處理
 * </p>
 * 4. 進行與client的連通
 * </p>
 */
public class Server implements Runnable{
    private final ServerGame game;
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers;
    private int connetingClientNumber = 0;
    private boolean gameStarting = false;

    public Server (ServerGame game , final int port){
        this.game = game;
        try{
            this.serverSocket = new ServerSocket(port);
        }catch(IOException e){
            e.printStackTrace();
        }
        clientHandlers = new ArrayList<ClientHandler>();
    }


    @Override
    public void run() {
        new Thread(() -> acceptClientLoop()).start();
    }

    public void acceptClientLoop(){
        System.out.println("Accepting Clients !!!");
        while (true) {
            System.out.println("Waiting for client...");
            try {
                Socket clienSocket = serverSocket.accept();
                System.out.println("A new client is connected.");
                //傳入clientSocket, this sever,gmae的東西 ... 
                connetingClientNumber = game.spawnPlayer(connetingClientNumber);
                ClientHandler clientHandler = new ClientHandler(clienSocket, this, connetingClientNumber++ );
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();;
                
                if(connetingClientNumber >= 4){
                    if(!gameStarting){
                        new Thread(() -> startGameLoop());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

    public void startGameLoop(){
        // TODO :實作或呼叫tick() 讓他進行資訊更新
        
    }

    public void processPacket(final ClientHandler clientHandler, final ClientPacket packet){
        if (packet instanceof final PlayerPacket playerPacket) {
            game.updateActionSet(clientHandler.getClientId(), playerPacket.player);
        } else if (packet instanceof DisconnetPacket) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // server to all client
    public void sendUpdatesToAll(){
        for(ClientHandler clientHandler : clientHandlers){
            sendUpdates(clientHandler);
        }
    }

    // server to one client
    public void sendUpdates(ClientHandler clientHandler){
        clientHandler.sendUpdate(game.getPlayer(clientHandler.clientId));
    }

    public void closeServer(){
        try{
            serverSocket.close();
        }catch(final IOException e){
            e.printStackTrace();
        }
    }
}