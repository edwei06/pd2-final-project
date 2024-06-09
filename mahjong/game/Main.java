//Main.java
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
      
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");
        Player player4 = new Player("Player4");
        List<Player> players = Arrays.asList(player1,player2,player3,player4);
        boolean needDraw=true;

       
        Game game = new Game(players);
        Scanner scanner = new Scanner(System.in);

        outerloop:
        while (true) {
            //
            Player currentPlayer = game.getGameState().getCurrentPlayer();
            System.out.println("Current player: " + currentPlayer.getPlayerId());

            //Draw a tile. Now,it's my turn!!
            if(needDraw){
                Tile drawnTile = game.tileSet.drawTiles(1).get(0);
                currentPlayer.addTile(drawnTile);
                System.out.println("Drew tile: " + drawnTile); 
            }


            
            System.out.println("player1" + player1.getHandTiles());
            System.out.println("player2" + player2.getHandTiles());
            System.out.println("player3" + player3.getHandTiles());
            System.out.println("player4" + player4.getHandTiles());

            
            if (game.checkWinCondition(currentPlayer)) {
                System.out.println(game.checkWinCondition(currentPlayer));
                System.out.println(currentPlayer.getPlayerId() + " wins!");
                break outerloop;
            }

            //select tile to discard
            int index;
            while (true) {
                System.out.println("Enter the index of the tile to discard (or -1 to end the game): ");
                index = scanner.nextInt();
                if (index == -1) {
                    break outerloop;
                }
                if (index < 0 || index >= currentPlayer.getHandTiles().size()) {
                    System.out.println("Invalid index. Try again.");
                    continue;
                }
                break;
            }

            
            Tile tileToDiscard = currentPlayer.getHandTiles().get(index);
            currentPlayer.removeTile(tileToDiscard);
            game.getGameState().addDiscardedTile(tileToDiscard);
            System.out.println("Discarded tile: " + tileToDiscard);
            //handling special moves
            //game.getGameState().getPlayers();
            int currentIndex = -1;
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getPlayerId().equals(game.getGameState().getCurrentPlayerId())) {
                    currentIndex = i;
                    break;
                }
            }
            //handling win
            for(int i=1;i<4;i++){
                if (game.canWin(players.get((currentIndex+i)%4))) {
                    //System.out.println("###########"+game.checkWinCondition(players.get((currentIndex+i)%4)));
                    System.out.println("player"+(currentIndex+i+1)%4+"win");
                    break outerloop;
                }
            }
            //hadling Pong
            int pongOrNot=0;
            for(int i=1;i<4;i++){
                if (game.canPong(players.get((currentIndex+i)%4))) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        System.out.print("player"+ (currentIndex+i+1)%4+" Pong or not?(1 -1)");
                        pongOrNot = Integer.parseInt(reader.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (pongOrNot==1) {
                        game.handlePong(players.get((currentIndex+i)%4));
                        needDraw=false;
                        continue outerloop;
                    }
                }
            }
             //hadling Kong
             int KongOrNot=0;
             for(int i=1;i<4;i++){
                 if (game.canKong(players.get((currentIndex+i)%4))) {
                     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                     try {
                         System.out.print("player"+ (currentIndex+i+1)%4+" Kong or not?(1 -1)");
                         KongOrNot = Integer.parseInt(reader.readLine());
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     if (KongOrNot==1) {
                         game.handleKong(players.get((currentIndex+i)%4));
                         needDraw=true;//because Kong need draw a tile
                         continue outerloop;
                     }
                 }
             }
             //handling chow   
             int chowOrNot=0;
             for(int i=1;i<2;i++){
                 if (game.canChow(players.get((currentIndex+i)%4))) {
                     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                     try {
                        System.out.print("player"+ (currentIndex+i+1)%4+" Chow or not?(1 -1)");
                        chowOrNot = Integer.parseInt(reader.readLine());
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                     if (chowOrNot==1) {
                         game.handleChow(players.get((currentIndex+i)%4));
                         needDraw=false;
                         continue outerloop;
                     }
                 }
             }        
            System.out.println("Discarded tiles: " + game.getGameState().getDiscardedTiles());
            game.getGameState().switchToNextPlayer();
            needDraw=true;
        }

        scanner.close();
    }
}


