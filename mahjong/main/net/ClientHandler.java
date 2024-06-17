package mahjong.main.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mahjong.main.game.player.Player;
import mahjong.main.net.packet.ClientPacket;

/**
 * 屬於sever端用來處理client封包交流、處理等的class，
 * 並且一個client會有一個ClientHandler來處理
 * @param clientSocket
 * @param server
 * @param playerId

 */

public class ClientHandler implements Runnable{
    private boolean isRunning;
    public int clientId;
    private final Server server;
    private final Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public boolean replied;

    public int getClientId(){
        return clientId;
    }
    
    public ClientHandler(final Socket clientSocket, final Server server, int playerId){
        this.server = server;
        this.clientSocket = clientSocket;   
        this.clientId = playerId;

        try{
            this.outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.clientSocket.getInputStream());
        }catch(IOException  e){
            e.printStackTrace();
        }

        initialClientCommunication();
    }
    

    private void initialClientCommunication(){
        try{
            outputStream.writeInt(clientId); //讓client知道他的id
            server.sendUpdates(this); //讓client能有server上的player資料
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;
        startRecieveMessageLoop();
    }  

    // client to server
    private void startRecieveMessageLoop(){
        while (isRunning) {
            try{
            final ClientPacket pocket= (ClientPacket) inputStream.readObject();
                server.processPacket(this, pocket);
                replied = true;
            } catch (final IOException e){
                e.printStackTrace();
            } catch (final ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    // server to client 傳遞 Player
    public void sendUpdate(Player player){
        if(!isRunning){
            server.closeServer();
        }
        try{
            replied = false;
            outputStream.writeObject(player);
            outputStream.reset();
        }catch (final IOException e){
            e.printStackTrace();
        }
    }
}
