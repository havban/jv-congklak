package com.havban.congklak.models.impl;

import com.havban.congklak.models.Board;
import com.havban.congklak.models.Hole;
import com.havban.congklak.models.Player;
import com.havban.congklak.models.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class DefaultBoard implements Board {
    private int size;
    private Player[] players= new Player[2];

    private Map<Position, Hole> holes = new HashMap<>();

    public DefaultBoard(int boardSize, int seedPerHole, Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;

        size = boardSize;
        //init holes
        for(int i=0; i< size; i++){
            holes.put(new Position(p1, i), new DefaultHole(seedPerHole, boardSize));
            holes.put(new Position(p2, i), new DefaultHole(seedPerHole, boardSize));
        }
        holes.put(new Position(p1, size), new DefaultHole(0, boardSize));
        holes.put(new Position(p2, size), new DefaultHole(0, boardSize));
    }

    @Override
    public Map<Position, Hole> getHoles() {
        return holes;
    }

    @Override
    public Hole getMainHole(Player p) {
        if(p==null)
            return null;
        //it's on last seq
        return holes.get(new Position(p, size));
    }

    @Override
    public Hole getMainHole(int playerSeq) {
        if(playerSeq<0 || playerSeq>1)
            return null;

        return holes.get(new Position(players[playerSeq], size));
    }

    @Override
    public Hole getHole(Player p, int seq) {
        if(p==null)
            return null;

        return getHole(new Position(p, seq));
    }

    @Override
    public Hole getHole(Position pos) {
        if(pos==null)
            return null;

        if(pos.getSeq()<0 || pos.getSeq()>size)
            return null;

        return holes.get(pos);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Hole getHoleAcross(Position pos) {
        if(pos==null)
            return null;

        if(pos.getSeq() == size)
            return null;

        Player p = pos.getPlayer();

        p = p.equals(players[0])? players[1]:
                p.equals(players[1])? players[0]: null;

        return getHole(p, size-pos.getSeq()-1);
    }

    @Override
    public void walk(Player cp, Position p) {
        int start = p.getSeq()+1;
        Hole h = getHole(p);

        if(h==null || h.getNumberOfSeed()<1
                || start > size-1)
            return;

        int seeds = h.takeAllSeed();
        step(cp, getNextPosition(cp, p), seeds);
    }

    @Override
    public Position getNextPosition(Player cp, Position pos) {
        if(pos==null)
            return null;

        Player p = pos.getPlayer();
        int nextSeq = pos.getSeq()+1;

        //skipping opponent hole
        if(nextSeq == size && !p.equals(cp)){
            nextSeq = 0;
            p = cp;
        }

        if(nextSeq > size) {
            //switch user
            p = p.equals(players[0]) ? players[1] :
                    p.equals(players[1]) ? players[0] : null;
            //go to first
            nextSeq = 0;
        }


        return new Position(p, nextSeq);
    }

    public void step(Player cp, Position pos, int seeds){

        //increment current hole
        Hole h = getHole(pos);
        if(h!=null)
            h.incSeed();

        //print board
        System.out.println(this);

        if (seeds < 2) {
            if(pos.getSeq() == size){
                return;
            }
            else if(getHole(pos).getNumberOfSeed()>1)
                walk(cp, pos);
            else if(cp.equals(pos.getPlayer())){
                //grab opponent seeds (across)
                Hole h1 = getHoleAcross(pos);
                if(h1!=null && h1.getNumberOfSeed()>0) {
                    int opponentSeeds = h1.takeAllSeed();
                    int mySeed = getHole(pos).takeAllSeed();
                    getMainHole(cp).addSeed(opponentSeeds+mySeed);
                }
            }

            return;
        }

        step(cp, getNextPosition(cp, pos), seeds-1);
    }

    public String toString(){
        if(size == 0 && holes.size()<(size*2)+2){
            return "";
        }

        StringBuffer buff = new StringBuffer();
        for(int i=0;i<size;i++)
            buff.append("\t"+i);
        buff.append("\n");
        for(int i=0;i<size;i++)
            buff.append("\t ").append(getHole(players[1], i));

        buff.append("\n");
        buff.append(getHole(players[0], size));
        for(int i=0;i<size;i++)
            buff.append("\t");

        buff.append(getHole(players[1], size));

        buff.append("\n");
        for(int i=size-1;i>=0;i--)
            buff.append("\t ").append(getHole(players[0], i));

        return buff.toString();
    }

    public Hole getNextHole(Player p, Position pos){
        return getHole(getNextPosition(p, pos));
    }
}
