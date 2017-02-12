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

    final static String TO_STRING_0 = "P2\t0\t1\t2\t3\t4\t5\t6\n"+
            "\t 0\t 0\t 0\t 0\t 0\t 0\t 0\n"+
            "0\t\t\t\t\t\t\t\t0\n"+
            "\t 0\t 0\t 0\t 0\t 0\t 0\t 0\n"+
            "P1\t6\t5\t4\t3\t2\t1\t0";

    final static String TO_STRING_SEED_PER_HOLE = "P2\t0\t1\t2\t3\t4\t5\t6\n"+
            "\t 7\t 7\t 7\t 7\t 7\t 7\t 7\n"+
            "0\t\t\t\t\t\t\t\t0\n"+
            "\t 7\t 7\t 7\t 7\t 7\t 7\t 7\n"+
            "P1\t6\t5\t4\t3\t2\t1\t0";


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

        assert(b.getMainHole(null) == null);
        assert(b.getMainHole(0) == main1);
        assert(b.getMainHole(6) == null);
    }


    @Test
    public void getHole() throws Exception {

        Hole h1 = b.getHole(p1, 2);
        assert(h1.getNumberOfSeed() == SEED_PER_HOLE);
        Hole h2 = b.getHole(p2, 3);
        assert(h2.getNumberOfSeed() == SEED_PER_HOLE);
        Hole main = b.getHole(p1, BOARD_SIZE);
        assert(main.getNumberOfSeed() == 0);

        assert b.getHole(null,1) == null;
        assert b.getHole(null) == null;
        assert b.getHole(new Position(p1, -1)) == null;
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

        b.walk(p1, new Position(p1, -1));

        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }

        b.getHole(p1, BOARD_SIZE-1).addSeed(2);

        b.walk(p1, new Position(p1, BOARD_SIZE-1));
        assert b.getLastPosition().equals(new Position(p2, 0));
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

        assert b.getNextPosition(null, null) == null;

        assert b.getHoleAcross(null) == null;

        assert b.getHoleAcross(new Position(p2, 1)) != null;

        assert b.getHoleAcross(new Position(p1, b.getSize())) == null;

        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
            h.setKacang(true);
        }

        assert b.getNextPosition(p2, new Position(p2,BOARD_SIZE)) == null;

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

        db.getHole(p1, 3).addSeed(2);
        db.getHole(p1, 6).addSeed(2);
        db.step(p1, new Position(p1, 2), 2);
        assert(db.getLastPosition().getSeq() == 9 - BOARD_SIZE - 1);

        for(Hole h: db.getHoles().values()){
            h.takeAllSeed();
        }

        db.getHole(p2, 1).addSeed(9);
        db.step(p1, new Position(p1, BOARD_SIZE), 3);
        assert(db.getLastPosition().getSeq() == 4);

    }

    @Test
    public void toStringTest() throws Exception {
        assert(b.toString().equals(TO_STRING_SEED_PER_HOLE));

        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }
        assert(b.toString().equals(TO_STRING_0));

        Board b2 = new DefaultBoard(0, 0, p1, p2);
        assert b2.toString().equals("");
    }

    @Test
    public void getNextHole() throws Exception {
        assert(b.getNextHole(p1, new Position(p1, 0))
                .equals(b.getHole(p1, 1)));

        assert(b.getNextHole(p1, new Position(p2, BOARD_SIZE-1))
                .equals(b.getHole(p1, 0)));
    }

    @Test
    public void getLastPosition() throws Exception {
        assert(b.getLastPosition() == null);

        DefaultBoard db = (DefaultBoard) b;

        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }
        db.step(p1, new Position(p1, 2), 1);
        assert(b.getLastPosition().getSeq() == 2);
    }

    @Test
    public void wrapRound() throws Exception {
        b.wrapRound();
        int eachPlayerSeedsCount = SEED_PER_HOLE * BOARD_SIZE;
        assert(b.getStoreSeedCount(p1) == eachPlayerSeedsCount);
        assert(b.getStoreSeedCount(p2) == eachPlayerSeedsCount);
    }

    @Test
    public void getStoreSeedCount() throws Exception {
        assert(b.getStoreSeedCount(p1) == 0);

        b.getHole(p1, BOARD_SIZE).addSeed(2);
        assert(b.getStoreSeedCount(p1) == 2);
    }

    @Test
    public void startNextRound() throws Exception {
        b.startNextRound();

        int eachPlayerSeedsCount = SEED_PER_HOLE * BOARD_SIZE;

        for(int i =0;i<BOARD_SIZE;i++){
            assert(b.getHole(p1, i).getNumberOfSeed() == SEED_PER_HOLE);
            assert(b.getHole(p2, i).getNumberOfSeed() == SEED_PER_HOLE);
        }

        b.getMainHole(p1).addSeed(
                b.getHoleAcross(new Position(p1, 0)).takeAllSeed());

        b.wrapRound();
        System.out.println(b.getMainHole(p1).getNumberOfSeed() == eachPlayerSeedsCount+SEED_PER_HOLE);
        System.out.println(b.getMainHole(p2).getNumberOfSeed() == eachPlayerSeedsCount-SEED_PER_HOLE);

        b.startNextRound();
        System.out.println(b);

        for(int i =1;i<BOARD_SIZE-1;i++){
            assert(b.getHole(p1, i).getNumberOfSeed() == SEED_PER_HOLE);
            assert(b.getHole(p2, i).getNumberOfSeed() == SEED_PER_HOLE);
        }

        assert(b.getMainHole(p1).getNumberOfSeed() == SEED_PER_HOLE);

        Hole acrossHole = b.getHoleAcross(new Position(p1, BOARD_SIZE-1));
        assert(acrossHole.getNumberOfSeed() == 0);
        assert(acrossHole.isKacang());


        //p1
        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }

        b.getMainHole(p1).addSeed(eachPlayerSeedsCount+(2*SEED_PER_HOLE)-1);
        b.getMainHole(p2).addSeed(eachPlayerSeedsCount-(2*SEED_PER_HOLE)+1);
        b.startNextRound();
        System.out.println(b);
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-1)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-2)).getNumberOfSeed() == 1);
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-2)).isKacang());

        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }

        b.getMainHole(p1).addSeed(eachPlayerSeedsCount+(4*SEED_PER_HOLE));
        b.getMainHole(p2).addSeed(eachPlayerSeedsCount-(4*SEED_PER_HOLE));
        b.startNextRound();
        System.out.println(b);
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-1)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-2)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-3)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-4)).getNumberOfSeed() == 0);

        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-1)).isKacang());
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-2)).isKacang());
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-3)).isKacang());
        assert(b.getHoleAcross(new Position(p1, BOARD_SIZE-4)).isKacang());

        //p2
        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }

        b.getMainHole(p2).addSeed(eachPlayerSeedsCount+(2*SEED_PER_HOLE)-1);
        b.getMainHole(p1).addSeed(eachPlayerSeedsCount-(2*SEED_PER_HOLE)+1);
        b.startNextRound();
        System.out.println(b);
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-1)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-2)).getNumberOfSeed() == 1);
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-2)).isKacang());

        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }

        for(Hole h: b.getHoles().values()){
            h.takeAllSeed();
        }

        b.getMainHole(p2).addSeed(eachPlayerSeedsCount+(4*SEED_PER_HOLE));
        b.getMainHole(p1).addSeed(eachPlayerSeedsCount-(4*SEED_PER_HOLE));
        b.startNextRound();
        System.out.println(b);
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-1)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-2)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-3)).getNumberOfSeed() == 0);
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-4)).getNumberOfSeed() == 0);

        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-1)).isKacang());
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-2)).isKacang());
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-3)).isKacang());
        assert(b.getHoleAcross(new Position(p2, BOARD_SIZE-4)).isKacang());

    }

}