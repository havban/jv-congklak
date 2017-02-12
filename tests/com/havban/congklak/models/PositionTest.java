package com.havban.congklak.models;

import com.havban.congklak.models.impl.DefaultPlayer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hidayat.febiansyah on 2/10/17.
 */
public class PositionTest {

    final static int PLAYER_ID = 1;
    final static int POS_SEQ = 1;

    final static String TO_STRING = PLAYER_ID+"."+POS_SEQ;

    Player p;
    Position pos;

    @Before
    public void before(){

        p = new DefaultPlayer(PLAYER_ID, "");
        pos = new Position(p, POS_SEQ);
    }

    @Test
    public void getPlayer() throws Exception {

        assert (pos.getPlayer().equals(p));
    }

    @Test
    public void getSeq() throws Exception {

        assert (pos.getSeq() == POS_SEQ);
    }

    @Test
    public void toStringTest() throws Exception {

        assert (pos.toString().equals(TO_STRING));
    }

    @Test
    public void hashCodeTest() throws Exception {
        assert(pos.hashCode() == TO_STRING.hashCode());
    }

    @Test
    public void getKey() throws Exception {
        assert(pos.getKey().equals(TO_STRING));
    }

    @Test
    public void equals() throws Exception {
        Player p2 = new DefaultPlayer(PLAYER_ID, "");
        Position pos2 = new Position(p, POS_SEQ);
        assert pos.equals(pos2);

        assert pos.equals(pos);
        assert !pos.equals(null);
        assert !pos.equals(p2);

        Position pos3 = new Position(p, POS_SEQ+1);
        assert !pos.equals(pos3);
    }

}