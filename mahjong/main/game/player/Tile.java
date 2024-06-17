package mahjong.main.game.player;

import java.io.Serializable;

public class Tile implements Serializable{
    public String suit;
    public int rank;
    public Tile(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }
    public String getTileString(){
        return (getRank()+getSuit());
    }

    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tile tile = (Tile) obj;
        return rank == tile.rank && suit.equals(tile.suit);
    }
}
