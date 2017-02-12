package com.havban.congklak.models;

/**
 * Created by hidayat.febiansyah on 2/9/17.
 */
public class Position {

    private  Player player;
    private  int seq;

    private String key;

    public Position(Player player, int seq){
        this.player = player;
        this.seq = seq;
        this.key = player.getId()+"."+seq;
    }

    public Player getPlayer(){
        return player;
    }

    public int getSeq(){
        return seq;
    }

    public String toString(){
        return key;
    }

    @Override
    public int hashCode()
    {
        return key.hashCode();
    }

    public String getKey(){
        return key;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Position p = (Position) o;
        if (!key.equals(p.key))
            return false;
        return true;
    }
}
