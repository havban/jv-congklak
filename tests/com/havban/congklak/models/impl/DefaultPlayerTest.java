package com.havban.congklak.models.impl;

import com.havban.congklak.models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hidayat.febiansyah on 2/10/17.
 */
public class DefaultPlayerTest {

    final static int PLAYER_ID = 1;
    final static String PLAYER_NAME = "NAME1";

    final static String TO_STRING = "Player "+PLAYER_ID+" - "+PLAYER_NAME;

    Player p;

    @Before
    public void before(){
        p = new DefaultPlayer(PLAYER_ID, PLAYER_NAME);
    }

    @Test
    public void getName() throws Exception {
        assert(p.getName().equals(PLAYER_NAME));
    }

    @Test
    public void getId() throws Exception {
        assert(p.getId() == PLAYER_ID);
    }

    @Test
    public void toStringTest() throws Exception {
        assert(p.toString().equals(TO_STRING));
    }

    @Test
    public void equals() throws Exception {
        Player p2 = new DefaultPlayer(PLAYER_ID, PLAYER_NAME);
        assert(p.equals(p2));

        assert !p.equals(null);
        assert !p.equals(new Object());
    }

}