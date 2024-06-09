// Game.java

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Game {
    private GameState gameState;
    public TileSet tileSet;  

    public Game(List<Player> players) {
        this.gameState = new GameState(players);
        this.tileSet = new TileSet();  
        initializeGame();
        //System.out.println(tileSet.getTiles());
    }

    public GameState getGameState() {
        return gameState;
    }

    private void initializeGame() {
        tileSet.shuffle();  
        for (Player player : gameState.getPlayers()) {
            player.setHandTiles(tileSet.drawTiles(13));
        }
        gameState.setCurrentPlayerId(gameState.getPlayers().get(0).getPlayerId());
    }

    public void playTurn(String playerId, Tile tile) {
        Player currentPlayer = gameState.getCurrentPlayer();
        
        if (!currentPlayer.getPlayerId().equals(playerId)) {
            throw new IllegalArgumentException("Not this player's turn");
        }
        
        if (!currentPlayer.getHandTiles().contains(tile)) {
            throw new IllegalArgumentException("Player does not have this tile");
        }
        
        currentPlayer.removeTile(tile);
        gameState.addDiscardedTile(tile);
        
        gameState.switchToNextPlayer();
    }

    public void handleSpecialMove(String playerId, Move move) {
        Player player = gameState.getPlayerById(playerId);
        
        switch (move.getMoveType()) {
            case "PONG":
                if (canPong(player)) {
                    handlePong(player);
                } else {
                    throw new IllegalArgumentException("Invalid PONG move");
                }
                break;
            case "KONG":
                if (canKong(player)) {
                    handleKong(player);
                } else {
                    throw new IllegalArgumentException("Invalid KONG move");
                }
                break;
            case "CHOW":
                if (canChow(player)) {
                    handleChow(player);
                } else {
                    throw new IllegalArgumentException("Invalid CHOW move");
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown move type");
        }
    }

    public boolean canWin(Player player) {
        Tile discardedTile = gameState.getLastDiscardedTile();
        if (discardedTile == null) {
            return false;
        }
        player.addTile(discardedTile);
        boolean winOrNot =checkWinCondition(player);
        player.removeTile(discardedTile);
        return winOrNot;
        
    }

    public boolean canPong(Player player) {
        Tile discardedTile = gameState.getLastDiscardedTile();
        if (discardedTile == null) {
            return false;
        }
        int sameNumber=0;
        for (int i=0;i<player.getHandTiles().size();i++){
            if(player.getHandTiles().get(i).getRank()==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                sameNumber++;
            }
        }   
        return sameNumber >= 2;
        /*long matchingTilesCount = player.getHandTiles().stream()
            .filter(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank())
            .count();*/
    }

    public void handlePong(Player player) {
        Tile discardedTile = gameState.getLastDiscardedTile();
        int deletedNumber=0;
        for(int i=0;i<player.getHandTiles().size();i++){
            if(discardedTile.rank == player.getHandTiles().get(i).rank && discardedTile.suit == player.getHandTiles().get(i).suit){
                player.removeTile(player.getHandTiles().get(i));
                deletedNumber++;
                break;
            }  
        }
        for(int i=0;i<player.getHandTiles().size();i++){
            if(discardedTile.rank == player.getHandTiles().get(i).rank && discardedTile.suit == player.getHandTiles().get(i).suit){
                player.removeTile(player.getHandTiles().get(i));
                deletedNumber++;
                break;
            }  
        }
        gameState.setCurrentPlayerId(player.getPlayerId());
        if (deletedNumber!=2) {
            System.out.println("ERROR when pong");
        }
    }

    public boolean canKong(Player player) {
        Tile discardedTile = gameState.getLastDiscardedTile();
        if (discardedTile == null) {
            return false;
        }
        int sameNumber=0;
        for (int i=0;i<player.getHandTiles().size();i++){
            if(player.getHandTiles().get(i).getRank()==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                sameNumber++;
            }
        }   
        return sameNumber >= 3;
        
        /*long matchingTilesCount = player.getHandTiles().stream()
            .filter(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank())
            .count();
        */
    }

    public void handleKong(Player player) {
        Tile discardedTile = gameState.getLastDiscardedTile();
        int deletedNumber=0;
        for(int i=0;i<player.getHandTiles().size();i++){
            if(discardedTile.rank == player.getHandTiles().get(i).rank && discardedTile.suit == player.getHandTiles().get(i).suit){
                player.removeTile(player.getHandTiles().get(i));
                deletedNumber++;
                break;
            }  
        }
        for(int i=0;i<player.getHandTiles().size();i++){
            if(discardedTile.rank == player.getHandTiles().get(i).rank && discardedTile.suit == player.getHandTiles().get(i).suit){
                player.removeTile(player.getHandTiles().get(i));
                deletedNumber++;
                break;
            }  
        }
        for(int i=0;i<player.getHandTiles().size();i++){
            if(discardedTile.rank == player.getHandTiles().get(i).rank && discardedTile.suit == player.getHandTiles().get(i).suit){
                player.removeTile(player.getHandTiles().get(i));
                deletedNumber++;
                break;
            }  
        }
        if (deletedNumber!=3) {
            System.out.println("ERROR");
            
        }
        gameState.setCurrentPlayerId(player.getPlayerId());
    }

    public boolean canChow(Player player) {
        Tile discardedTile = gameState.getLastDiscardedTile();
        if (discardedTile == null) {
            return false;
        }
        boolean rankMinus2=false;
        boolean rankMinus1=false;
        boolean rankPlus1=false;
        boolean rankPlus2=false;
        for (int i=0;i<player.getHandTiles().size();i++){
            if((player.getHandTiles().get(i).getRank()+2)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankMinus2=true;
            }
            if((player.getHandTiles().get(i).getRank()+1)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankMinus1=true;
            }
            if((player.getHandTiles().get(i).getRank()-1)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankPlus1=true;
            }
            if((player.getHandTiles().get(i).getRank()-2)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankPlus2=true;
            }
        } 
        return (rankMinus2&&rankMinus1) || (rankMinus1&&rankPlus1) || (rankPlus1&&rankPlus2) ;
        /*List<Tile> handTiles = player.getHandTiles();
    
        boolean condition1 = handTiles.stream().anyMatch(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank() - 2) &&
                            handTiles.stream().anyMatch(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank() - 1);
    
        boolean condition2 = handTiles.stream().anyMatch(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank() - 1) &&
                            handTiles.stream().anyMatch(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank() + 1);
    
        boolean condition3 = handTiles.stream().anyMatch(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank() + 1) &&
                            handTiles.stream().anyMatch(tile -> tile.getSuit().equals(discardedTile.getSuit()) && tile.getRank() == discardedTile.getRank() + 2);
        */
        
    }

    public void handleChow(Player player) {
        Tile discardedTile = gameState.getLastDiscardedTile();
        boolean rankMinus2=false;
        boolean rankMinus1=false;
        boolean rankPlus1=false;
        boolean rankPlus2=false;
        int rankMinus2Index=-1;
        int rankMinus1Index=-1;
        int rankPlus1Index=-1;
        int rankPlus2Index=-1;
        String targetSuit=discardedTile.getSuit();
        for (int i=0;i<player.getHandTiles().size();i++){
            if((player.getHandTiles().get(i).getRank()+2)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankMinus2=true;
                rankMinus2Index=player.getHandTiles().get(i).getRank();
            }
            if((player.getHandTiles().get(i).getRank()+1)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankMinus1=true;
                rankMinus1Index=player.getHandTiles().get(i).getRank();
            }
            if((player.getHandTiles().get(i).getRank()-1)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankPlus1=true;
                rankPlus1Index=player.getHandTiles().get(i).getRank();
            }
            if((player.getHandTiles().get(i).getRank()-2)==discardedTile.getRank()&&player.getHandTiles().get(i).getSuit()==discardedTile.getSuit()){
                rankPlus2=true;
                rankPlus2Index=player.getHandTiles().get(i).getRank();
            }
        }
        System.out.println("How to eat?");
        if(rankMinus2&&rankMinus1){
            System.out.println("Use two small to eat? Press 1");
        }
        if(rankMinus1&&rankPlus1){
            System.out.println("Use middle to eat? Press 2");
        }
        if(rankPlus1&&rankPlus2){
            System.out.println("Use two larger to eat? Press 3");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int whichToEat=-1;
        try {
            whichToEat = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (whichToEat) {
            case 1:
                int deletedNumber=0;
                for(int i=0;i<player.getHandTiles().size();i++){
                    if(rankMinus2Index == player.getHandTiles().get(i).rank && targetSuit == player.getHandTiles().get(i).suit){
                        player.removeTile(player.getHandTiles().get(i));
                        deletedNumber++;
                        break;
                    }  
                }
                for(int i=0;i<player.getHandTiles().size();i++){
                    if(rankMinus1Index == player.getHandTiles().get(i).rank && targetSuit == player.getHandTiles().get(i).suit){
                        player.removeTile(player.getHandTiles().get(i));
                        deletedNumber++;
                        break;
                    }  
                }
                if (deletedNumber!=2) {
                    System.out.println("ERROR when chow1");
                }
                break;
            case 2:
                int deletedNumber2=0;
                for(int i=0;i<player.getHandTiles().size();i++){
                    if(rankMinus1Index == player.getHandTiles().get(i).rank && targetSuit == player.getHandTiles().get(i).suit){
                        player.removeTile(player.getHandTiles().get(i));
                        deletedNumber2++;
                        break;
                    }  
                }
                for(int i=0;i<player.getHandTiles().size();i++){
                    if(rankPlus1Index == player.getHandTiles().get(i).rank && targetSuit == player.getHandTiles().get(i).suit){
                        player.removeTile(player.getHandTiles().get(i));
                        deletedNumber2++;
                        break;
                    }  
                }
                if (deletedNumber2!=2) {
                    System.out.println("ERROR when chow2");
                }
                break;
            case 3:                
                int deletedNumber3=0;
                for(int i=0;i<player.getHandTiles().size();i++){
                    if(rankPlus1Index == player.getHandTiles().get(i).rank && targetSuit == player.getHandTiles().get(i).suit){
                        player.removeTile(player.getHandTiles().get(i));
                        deletedNumber3++;
                        break;
                    }  
                }
                for(int i=0;i<player.getHandTiles().size();i++){
                    if(rankPlus2Index == player.getHandTiles().get(i).rank && targetSuit == player.getHandTiles().get(i).suit){
                        player.removeTile(player.getHandTiles().get(i));
                        deletedNumber3++;
                        break;
                    }  
                }
                if (deletedNumber3!=2) {
                    System.out.println("ERROR when chow3");
                }                
                break;
            default:
                System.err.println("Error when handling Chow");
        }
        gameState.setCurrentPlayerId(player.getPlayerId());
    }
    public boolean checkWinCondition(Player player) {
        List<Tile> handTiles = new ArrayList<>(player.getHandTiles());
    

        for (int i = 0; i < handTiles.size() - 1; i++) {
            if (handTiles.get(i).rank == handTiles.get(i + 1).rank && handTiles.get(i).suit.equals(handTiles.get(i + 1).suit)) {
                List<Tile> remainingTiles = new ArrayList<>(handTiles);
                

                remainingTiles.remove(i);
                remainingTiles.remove(i);
    

                if (canFormSets(remainingTiles)) {
                    return true;
                }
            }
        }
        //System.out.println("++++++++++++++"+handTiles);
        return false;
    }
    private boolean canFormSets(List<Tile> tiles) {
        if (tiles.isEmpty()) {
            return true; 
        }


        Collections.sort(tiles, new Comparator<Tile>() {
            @Override
            public int compare(Tile a, Tile b) {
                int suitComparison = a.getSuit().compareTo(b.getSuit());
                if (suitComparison != 0) {
                    return suitComparison;
                } else {
                    return a.getRank() - b.getRank();
                }
            }
        });

        for (int i = 0; i < tiles.size(); i++) {

            if (i + 2 < tiles.size() &&
                tiles.get(i).rank == tiles.get(i + 1).rank && tiles.get(i).rank == tiles.get(i + 2).rank &&
                tiles.get(i).suit.equals(tiles.get(i + 1).suit) && tiles.get(i).suit.equals(tiles.get(i + 2).suit)) {
                List<Tile> remainingTiles = new ArrayList<>(tiles);
                

                remainingTiles.remove(i);
                remainingTiles.remove(i);
                remainingTiles.remove(i);


                if (canFormSets(remainingTiles)) {
                    return true;
                }
            }

           
            if (i + 2 < tiles.size() &&
                containsRankAndSuit(tiles, tiles.get(i).rank + 1, tiles.get(i).suit) &&
                containsRankAndSuit(tiles, tiles.get(i).rank + 2, tiles.get(i).suit)) {
                List<Tile> remainingTiles = new ArrayList<>(tiles);
                
              
                removeFirstRankAndSuit(remainingTiles, tiles.get(i).rank, tiles.get(i).suit);
                removeFirstRankAndSuit(remainingTiles, tiles.get(i).rank + 1, tiles.get(i).suit);
                removeFirstRankAndSuit(remainingTiles, tiles.get(i).rank + 2, tiles.get(i).suit);

               
                if (canFormSets(remainingTiles)) {
                    return true;
                }
            }
        }

        return false; 
    }
    private boolean containsRankAndSuit(List<Tile> tiles, int rank, String suit) {
        for (Tile tile : tiles) {
            if (tile.getRank() == rank && tile.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }
    private void removeFirstRankAndSuit(List<Tile> tiles, int rank, String suit) {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getRank() == rank && tiles.get(i).getSuit().equals(suit)) {
                tiles.remove(i);
                return;
            }
        }
    }
        

}
