// Tile.java
//package com.example.game;

public class Tile implements Comparable<Tile> {
    public String suit;
    public int rank;
    //public Compare()todo
    public Tile(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return suit + rank;
    }

    @Override
    public int compareTo(Tile other) {
        int suitOrder = getSuitOrder(this.suit) - getSuitOrder(other.suit);
        if (suitOrder != 0) {
            return suitOrder;
        } else {
            return this.rank - other.rank;
        }
    }

    private int getSuitOrder(String suit) {
        switch (suit) {
            case "Wong":  
                return 1;
            case "Tong": 
                return 2;
            case "Tiao":  
                return 3;
            case "Honor": 
                return 4;
            default:
                return 5;
        }
    }
}
