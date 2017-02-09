package com.havban.congklak.models.impl;

import com.havban.congklak.models.Hole;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class DefaultHole implements Hole {

    private int seeds;
    private int boardSize;

    private boolean isKacang;

    public DefaultHole(int seeds, int boardSize){
        this.seeds = seeds;
        this.boardSize = boardSize;
        this.isKacang = false;
    }

    @Override
    public int getNumberOfSeed() {
        return seeds;
    }

    @Override
    public boolean incSeed() {
        seeds++;
        return true;
    }

    @Override
    public int takeAllSeed() {
        int tmp = seeds;
        seeds = 0;
        return tmp;
    }

    @Override
    public void addSeed(int newSeeds) {
        seeds += newSeeds;
    }

    public String toString(){
        return ""+seeds;
    }

    public void setKacang(boolean status){
        isKacang = status;
    }

    public boolean isKacang(){
        return isKacang;
    }
}
