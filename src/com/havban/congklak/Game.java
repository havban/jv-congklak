package com.havban.congklak;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class Game implements Runnable{

    private boolean isRunning = true;

    public Game(){
        // TODO
    }

    public void run(){

        while(isRunning){
            try {
                Thread.sleep(1000);

                System.out.println("running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit(){
        isRunning = false;
    }
}
