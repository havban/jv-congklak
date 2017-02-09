package com.havban.congklak;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Game game = new Game();

        new Thread(game).start();

    }
}
