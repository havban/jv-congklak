package com.havban.congklak;

import com.havban.congklak.models.Board;
import com.havban.congklak.models.Hole;
import com.havban.congklak.models.Player;
import com.havban.congklak.models.Position;
import com.havban.congklak.models.impl.DefaultBoard;
import com.havban.congklak.models.impl.DefaultPlayer;

import java.util.Scanner;

/**
 * Created by hidayat.febiansyah on 2/3/17.
 */
public class Game implements Runnable{


    private boolean isRunning = true;
    private Scanner scanner;

    private Board board;

    private static final int P1_SEQ = 1;
    private static final int P2_SEQ = 2;
    private Player p1, p2;

    public Game(){

        scanner = new Scanner(System.in);

        System.out.println(board);

        System.out.println("Welcome to congklak\nPlayer 1 first, then player 2\n");

        System.out.println("Please input Player 1's name: ");
        String player1Name = scanner.nextLine();

        System.out.println("Please input Player 2's name: ");
        String player2Name = scanner.nextLine();

        p1 = new DefaultPlayer(P1_SEQ, player1Name);
        p2 = new DefaultPlayer(P2_SEQ, player2Name);

        try {
            board = new DefaultBoard(p1, p2);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }

    }

    public void run(){

        int input;

        while(isRunning){
            printBoard();

            input = requestInput(p1, board);

            board.walk(p1, new Position(p1, input));

            input = requestInput(p2, board);

            board.walk(p2, new Position(p2, input));

        }
    }

    public void exit(){
        isRunning = false;
    }

    public boolean isRunning(){ return isRunning; }

    private int requestInput(Player p, Board board){
        String inputStr;
        int input;
        input = -1;
        while(input<0) {
            try {
                System.out.println(p+", please choose hole: ");
                inputStr = scanner.nextLine();
                input = Integer.parseInt(inputStr);

                Hole h = board.getHole(p, input);

                if(input<0 || input>board.getSize()
                        || ( h != null && h.getNumberOfSeed() < 1))
                    throw new NumberFormatException();

            } catch (NumberFormatException nfe){
                input = -1;
                System.err.println("Please input only valid hole number and not empty");
            }
        }

        return input;
    }

    private void printBoard(){
        System.out.println(board.toString());
    }
}
