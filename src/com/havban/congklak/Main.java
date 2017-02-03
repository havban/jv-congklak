package com.havban.congklak;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Game game = new Game();

        new Thread(game).start();

        System.out.println("sleeping..");
        Thread.sleep(10000);


        System.out.println("exiting..");

        game.exit();
    }
}
