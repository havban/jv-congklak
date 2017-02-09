package com.havban.congklak.models.impl;

import com.havban.congklak.models.Board;
import com.havban.congklak.models.Hole;
import com.havban.congklak.models.Player;
import com.havban.congklak.models.Position;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class DefaultBoard implements Board {
    private int size;
    private int seedPerHole;
    private Player[] players= new Player[2];

    private Map<Position, Hole> holes = new HashMap<>();

    // player has cross to opponent hole
    private boolean hasCrossed = false;

    //get last position
    private Position lastPosition = null;

    public DefaultBoard(int boardSize, int seedPerHole, Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;

        size = boardSize;
        this.seedPerHole = seedPerHole;
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

        if(!cp.equals(p.getPlayer()))
            hasCrossed = true;

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

        Position nextPos = new Position(p, nextSeq);

        //if it is kacang, and not mine, then get next
        if(!p.equals(cp) && getHole(p, nextSeq).isKacang()) {
            if (nextSeq == size - 1) {
                System.out.println("All holes are 'Kacang'.. exiting");
                System.exit(0);
            }
            return getNextPosition(cp, nextPos);
        }

        return nextPos;
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
                lastPosition = pos;
                return;
            }
            else if(getHole(pos).getNumberOfSeed()>1)
                walk(cp, pos);
            else if(cp.equals(pos.getPlayer()) && hasCrossed){
                //grab opponent seeds (across)
                Hole h1 = getHoleAcross(pos);
                if(h1!=null && h1.getNumberOfSeed()>0) {
                    int opponentSeeds = 0;

                    //only grab across hole if it's not kacang
                    if(!h.isKacang())
                        opponentSeeds = h1.takeAllSeed();
                    int mySeed = getHole(pos).takeAllSeed();
                    getMainHole(cp).addSeed(opponentSeeds+mySeed);
                }

                lastPosition = pos;
            }
            hasCrossed = false;

            return;
        }

        step(cp, getNextPosition(cp, pos), seeds-1);
    }

    public String toString(){
        if(size == 0 && holes.size()<(size*2)+2){
            return "";
        }

        StringBuffer buff = new StringBuffer();

        buff.append("P2");
        for(int i=0;i<size;i++)
            buff.append("\t").append(i);
        buff.append("\n");
        for(int i=0;i<size;i++)
            buff.append("\t ").append(getHole(players[1], i));

        buff.append("\n");
        buff.append(getHole(players[0], size));
        for(int i=0;i<=size;i++)
            buff.append("\t");

        buff.append(getHole(players[1], size));

        buff.append("\n");
        for(int i=size-1;i>=0;i--)
            buff.append("\t ").append(getHole(players[0], i));

        buff.append("\nP1");
        for(int i=size-1;i>=0;i--)
            buff.append("\t").append(i);

        return buff.toString();
    }

    public Hole getNextHole(Player p, Position pos){
        return getHole(getNextPosition(p, pos));
    }

    public Position getLastPosition(){
        return lastPosition;
    }

    @Override
    public void wrapRound() {
        Hole h1 = getHole(players[0], size);
        Hole h2 = getHole(players[1], size);

        int sum1 = 0, sum2 = 0;

        for(int i=0;i<size;i++)
            sum1+=getHole(players[0],i).takeAllSeed();
        for(int i=0;i<size;i++)
            sum2+=getHole(players[1],i).takeAllSeed();

        h1.addSeed(sum1);
        h2.addSeed(sum2);
    }

    @Override
    public int getStoreSeedCount(Player p) {
        return getHole(p, size).getNumberOfSeed();
    }

    @Override
    public void startNextRound() {

        int total1 = getMainHole(players[0]).takeAllSeed();
        int total2 = getMainHole(players[1]).takeAllSeed();

        //reset kacang
        for(int i=size-1; i>=0; i--){
            getHole(players[0], i).setKacang(false);
            getHole(players[2], i).setKacang(false);
        }

        List<Hole> kacang1 = new ArrayList<>();
        List<Hole> kacang2 = new ArrayList<>();

        //init holes
        for(int i=size-1; i>=0; i--){
            Hole h1 = getHole(players[0], i);
            if(total1>=seedPerHole) {
                h1.addSeed(seedPerHole);
                total1 -= seedPerHole;
            }else if(kacang1.size()<3){
                kacang1.add(h1);
                h1.setKacang(true);
            }else{
                h1.setKacang(true);
            }

            Hole h2 = getHole(players[1], i);
            if(total1>=seedPerHole) {
                h2.addSeed(seedPerHole);
                total2 -= seedPerHole;
            }else if(kacang2.size()<3){
                kacang2.add(h2);
                h2.setKacang(true);
            }else{
                h2.setKacang(true);
            }
        }
        if(kacang1.size()>0) {
            while(total1>0){
                for(Hole h: kacang1) {
                    h.addSeed(1);
                    total1--;
                }
            }
        }
        if(kacang2.size()>0) {
            while(total2>0){
                for(Hole h: kacang2) {
                    h.addSeed(1);
                    total1--;
                }
            }
        }
        getMainHole(players[0]).addSeed(total1);
        getMainHole(players[0]).addSeed(total2);
    }

}
