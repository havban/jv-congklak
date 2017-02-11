package com.havban.congklak.models.impl;

import com.havban.congklak.models.Board;
import com.havban.congklak.models.Hole;
import com.havban.congklak.models.Player;
import com.havban.congklak.models.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hidayat.febiansyah on 2/10/17.
 */
public class DefaultBoardTest {

    final int TOTAL_SCORE_COUNT = 10;
    final int BOARD_SIZE = 7;
    final int SEED_PER_HOLE = 7;


    final static int PLAYER1_ID = 1;
    final static String PLAYER1_NAME = "NAME1";
    final static int PLAYER2_ID = 2;
    final static String PLAYER2_NAME = "NAME2";

    Player p1;
    Player p2;

//    final static String TO_STRING = "Player "+PLAYER_ID+" - "+PLAYER_NAME;

    Board b;

    @Before
    public void before(){
        p1 = new DefaultPlayer(PLAYER1_ID, PLAYER1_NAME);
        p2 = new DefaultPlayer(PLAYER2_ID, PLAYER2_NAME);

        b = new DefaultBoard(BOARD_SIZE, SEED_PER_HOLE, p1, p2);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getHoles() throws Exception {
        assert(b.getHoles().size() == ((BOARD_SIZE+1)*2));
        for(int i=0;i<BOARD_SIZE;i++){
            assert(b.getHoles().get(new Position(p1, i))
                    .getNumberOfSeed() == SEED_PER_HOLE);
            assert(b.getHoles().get(new Position(p2, i))
                    .getNumberOfSeed() == SEED_PER_HOLE);
        }

        assert(b.getHoles().get(new Position(p1, BOARD_SIZE))
                .getNumberOfSeed() == 0);
        assert(b.getHoles().get(new Position(p2, BOARD_SIZE))
                .getNumberOfSeed() == 0);

    }

    @Test
    public void getMainHole() throws Exception {
        Hole main1 = b.getMainHole(p1);
        Hole main2 = b.getMainHole(p2);

        Hole main1_ori = b.getHole(p1, BOARD_SIZE);
        Hole main2_ori = b.getHole(p2, BOARD_SIZE);

        assert(!main1.equals(main2));

        assert(main1.equals(main1_ori));
        assert(main2.equals(main2_ori));

        assert(main1.getNumberOfSeed()==0);
        assert(main2.getNumberOfSeed()==0);
    }


    @Test
    public void getHole() throws Exception {

        Hole h1 = b.getHole(p1, 2);
        assert(h1.getNumberOfSeed() == SEED_PER_HOLE);
        Hole h2 = b.getHole(p2, 3);
        assert(h2.getNumberOfSeed() == SEED_PER_HOLE);
        Hole main = b.getHole(p1, BOARD_SIZE);
        assert(main.getNumberOfSeed() == 0);
    }

    @Test
    public void getSize() throws Exception {
        assert(b.getSize() == BOARD_SIZE);
    }

    @Test
    public void getHoleAcross() throws Exception {
        int currentHoleNumber = 0;
        int acrossHoleNumber = BOARD_SIZE-1-currentHoleNumber;
        Hole currentHole = b.getHole(p1, currentHoleNumber);

        Hole hAcross = b.getHoleAcross(new Position(p1, currentHoleNumber));

        assert(b.getHoleAcross(new Position(p1, currentHoleNumber))
                .equals(b.getHole(p2, acrossHoleNumber)));
    }

    @Test
    public void walk() throws Exception {
        b.walk(p1, new Position(p1, 0));
        assert(b.getLastPosition().equals(new Position(p1, BOARD_SIZE)));
    }

    @Test
    public void getNextPosition() throws Exception {
        assert(b.getNextPosition(p1, new Position(p1, 0))
            .equals(new Position(p1, 1)));

        assert(b.getNextPosition(p1, new Position(p1, BOARD_SIZE-1))
                .equals(new Position(p1, BOARD_SIZE)));

        //jump side
        assert(b.getNextPosition(p1, new Position(p2, BOARD_SIZE-1))
                .equals(new Position(p1, 0)));

        //jump kacang
        b.getHole(p1, 0).setKacang(true);

        assert(b.getNextPosition(p2, new Position(p2, BOARD_SIZE))
                .equals(new Position(p1, 1)));

        b.getHole(p1, 1).setKacang(true);

        assert(b.getNextPosition(p2, new Position(p2, BOARD_SIZE))
                .equals(new Position(p1, 2)));

        b.getHole(p1, 2).setKacang(true);

        assert(b.getNextPosition(p2, new Position(p2, BOARD_SIZE))
                .equals(new Position(p1, 3)));

    }

    @Test
    public void step() throws Exception {
        DefaultBoard db = (DefaultBoard) b;

        for(Hole h: db.getHoles().values()){
            h.takeAllSeed();
        }

        //stepping on one
        //the position is directly the next one from origin
        db.step(p1, new Position(p1, 2), 1);
        assert(db.getLastPosition().equals(new Position(p1, 2)));

        for(Hole h: db.getHoles().values()){
            h.takeAllSeed();
        }

        db.step(p1, new Position(p1, 2), 2);
        assert(db.getLastPosition().equals(new Position(p1, 3)));

        for(Hole h: db.getHoles().values()){
            h.takeAllSeed();
        }

        db.getHole(p1, 3).addSeed(2);
        db.step(p1, new Position(p1, 2), 2);
        assert(db.getLastPosition().getSeq() == 6);

        for(Hole h: db.getHoles().values()){
            h.takeAllSeed();
        }

//        db.getHole(p1, 3).addSeed(2);
//        db.getHole(p1, 6).addSeed(2);
//        db.step(p1, new Position(p1, 2), 2);
//        assert(db.getLastPosition().getSeq() == 9 - BOARD_SIZE);

    }

    @Test
    public void toStringTest() throws Exception {

    }

    @Test
    public void getNextHole() throws Exception {

    }

    @Test
    public void getLastPosition() throws Exception {

    }

    @Test
    public void wrapRound() throws Exception {

    }

    @Test
    public void getStoreSeedCount() throws Exception {

    }

    @Test
    public void startNextRound() throws Exception {

    }

}