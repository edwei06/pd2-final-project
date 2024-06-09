// Player.java
//package com.example.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private String playerId;
    private List<Tile> handTiles;
    public boolean needToDraw;

    public Player(String playerId) {
        this.playerId = playerId;
        this.handTiles = new ArrayList<>();
    }

    public String getPlayerId() {
        return playerId;
    }

    public List<Tile> getHandTiles() {
        return handTiles;
    }

    public void setHandTiles(List<Tile> handTiles) {
        this.handTiles = handTiles;
        sortHandTiles();
    }

    public void removeTile(Tile tile) {
        handTiles.remove(tile);
        sortHandTiles();
    }

    public void addTile(Tile tile) {
        handTiles.add(tile);
        sortHandTiles();
    }

    private void sortHandTiles() {
        Collections.sort(handTiles);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId='" + playerId + '\'' +
                ", handTiles=" + handTiles +
                '}';
    }
}

