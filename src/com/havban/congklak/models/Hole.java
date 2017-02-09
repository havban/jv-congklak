package com.havban.congklak.models;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public interface Hole {

    int getNumberOfSeed();

    boolean incSeed();

    int takeAllSeed();

    void addSeed(int newSeeds);

    boolean isKacang();

    void setKacang(boolean status);

}
