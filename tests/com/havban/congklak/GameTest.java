package com.havban.congklak;

import static org.junit.Assert.*;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class GameTest {

    Game game = null;

    @org.junit.After
    public void after(){
        if(game != null)
            game.exit();
    }

    @org.junit.Test
    public void run() throws Exception {

        game = new Game();

        new Thread(game).start();

        Thread.sleep(1000);

        assert game.isRunning();

        game.exit();

        Thread.sleep(1000);
    }

    @org.junit.Test
    public void exit() throws Exception {

        game = new Game();

        new Thread(game).start();

        assert game.isRunning();

        game.exit();

        Thread.sleep(1000);

        assert !game.isRunning();
    }

    @org.junit.Test
    public void errorException() throws Exception {

        game = new Game();

        Thread t = new Thread(game);
        t.start();

        t.interrupt();

        game.exit();

        Thread.sleep(1000);

        assert !game.isRunning();
    }

}