// Move.java
//package com.example.game;

import java.util.List;

public class Move {
    private String playerId;
    private String moveType; // e.g., "PONG", "KONG", "CHOW"
    private List<Tile> tiles;

    public Move(String playerId, String moveType, List<Tile> tiles) {
        this.playerId = playerId;
        this.moveType = moveType;
        this.tiles = tiles;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getMoveType() {
        return moveType;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}