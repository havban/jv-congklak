package com.havban.congklak.models.impl;

import com.havban.congklak.models.Hole;
import com.havban.congklak.models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hidayat.febiansyah on 2/10/17.
 */
public class DefaultHoleTest {

    final static int SEEDS = 1;
    final static int BOARD_SIZE = 2;

    final static String TO_STRING = ""+SEEDS;

    Hole hole;

    @Before
    public void before(){
        hole = new DefaultHole(SEEDS, BOARD_SIZE);
    }

    @Test
    public void getNumberOfSeed() throws Exception {
        assert(hole.getNumberOfSeed() == SEEDS);
    }

    @Test
    public void incSeed() throws Exception {
        hole.incSeed();
        assert(hole.getNumberOfSeed() == SEEDS+1);
    }

    @Test
    public void takeAllSeed() throws Exception {
        hole.takeAllSeed();
        assert(hole.getNumberOfSeed() == 0);
    }

    @Test
    public void addSeed() throws Exception {
        hole.addSeed(SEEDS);
        assert(hole.getNumberOfSeed() == SEEDS+SEEDS);
    }

    @Test
    public void toStringTest() throws Exception {
        assert(hole.toString().equals(TO_STRING));
    }

    @Test
    public void setKacang() throws Exception {
        hole.setKacang(false);
        assert(hole.isKacang() == false);
        hole.setKacang(true);
        assert(hole.isKacang() == true);
    }

    @Test
    public void isKacang() throws Exception {
        hole.setKacang(true);
        assert(hole.isKacang() == true);
    }

}