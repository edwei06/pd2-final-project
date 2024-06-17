package mahjong.main.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

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
    boolean isRunning;
    private final ServerGame game;
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers;
    private int connetingClientNumber = 0;
    private boolean gameStarting = false;
    private CountDownLatch latch;

    public Server (ServerGame game , final int port){
        this.game = game;
        try{
            this.serverSocket = new ServerSocket(port);
            this.isRunning = true;
            System.out.println("Server is open!!");
            System.out.println("ip is :" + InetAddress.getLocalHost().getHostAddress() + " , port is :" + port);
        }catch(IOException e){
            e.printStackTrace();
        }
        clientHandlers = new ArrayList<ClientHandler>();
    }


    @Override
    public void run() {
        game.initDistributeTileHands();
        new Thread(() -> acceptClientLoop()).start();
    }

    public void acceptClientLoop(){
        System.out.println("Accepting Clients !!!");
        while (true) {
            System.out.println("Waiting for client...");
            try {
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("A new client is connected.");
                //傳入clientSocket, this sever,gmae的東西 ... 
                connetingClientNumber = game.spawnPlayer(connetingClientNumber);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this, connetingClientNumber-1 );
                 
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
                // while(!clientHandler.replied){
                //     System.out.println(clientHandler.replied);
                // };
                System.out.println("connetingClientNumber :" +connetingClientNumber);
                if(connetingClientNumber >= 4){
                    if(!gameStarting){
                        System.out.println("Start Game!");
                        game.init();
                        new Thread(() -> startGameLoop()).start();
                        gameStarting = true;
                    }
                }
                

            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

    public void startGameLoop(){
        while(gameStarting){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendUpdatesToAll();
            game.updateServerGame();
            if(game.getCloseGame()){
                closeServer();
            }
            if(game.tiles.size() == 0) closeServer();
            else System.out.println("remaining tiles :" +game.tiles.size());
        }
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
        latch.countDown();
    }

    // server to all client
    public void sendUpdatesToAll(){
        latch = new CountDownLatch(clientHandlers.size());
        for(ClientHandler clientHandler : clientHandlers){
            sendUpdates(clientHandler);
            // wait client reply packet
            //while(!clientHandler.replied);
        }
        try {
            latch.await();  // 等待所有客户端调用 countDown()
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        Server server = new Server(new ServerGame(), 1234);
        server.run();
    }
}