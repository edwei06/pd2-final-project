package mahjong.main.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import mahjong.main.game.action.ServerGame;

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
public class Sever implements Runnable{
    private final ServerGame game;
    private ServerSocket serverSocket;
    private final ArrayList<ClientHandler> clientHandlers;

    public Sever (ServerGame game , final int port){
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    public void acceptClient(){
        System.out.println("Accepting Clients !!!");
        while (true) {
            System.out.println("Waiting for client...");
            try {
                Socket clienSocket = serverSocket.accept();
                System.out.println("A new client is connected.");
                // TODO : 讓clientHandler 處理這個clientSocket
                //傳入clientSocket, this sever,gmae的東西 ... 
                ClientHandler clientHandler = new ClientHandler();
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();;

            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

}