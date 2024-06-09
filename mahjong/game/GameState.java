// GameState.java
//package com.example.game;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Player> players;
    
    private String currentPlayerId;
    private List<Tile> discardedTiles;

    public GameState(List<Player> players) {
        this.players = players;
        this.discardedTiles = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public List<Tile> getDiscardedTiles() {
        return discardedTiles;
    }

    public void addDiscardedTile(Tile tile) {
        this.discardedTiles.add(tile);
    }

    public Tile getLastDiscardedTile() {
        if (discardedTiles.isEmpty()) {
            return null; // No discarded tiles yet
        }
        return discardedTiles.get(discardedTiles.size() - 1);
    }

    public Player getPlayerById(String playerId) {
        for (Player player : players) {
            if (player.getPlayerId().equals(playerId)) {
                return player;
            }
        }
        return null; // Player not found
    }

    public Player getCurrentPlayer() {
        for (Player player : players) {
            if (player.getPlayerId().equals(currentPlayerId)) {
                return player;
            }
        }
        return null;
    }

    public void switchToNextPlayer() {
        int currentIndex = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerId().equals(currentPlayerId)) {
                currentIndex = i;
                break;
            }
        }
        int nextIndex = (currentIndex + 1) % players.size();
        setCurrentPlayerId(players.get(nextIndex).getPlayerId());
    }
}

