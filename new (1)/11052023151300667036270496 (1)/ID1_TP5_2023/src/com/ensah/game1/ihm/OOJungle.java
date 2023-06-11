package com.ensah.game1.ihm;

import com.ensah.data.DataBaseException;
import com.ensah.game1.bll.ChessBoard;

import java.util.Scanner;

public class OOJungle {
    private static ChessBoard board;

    public static void main(String[] args) throws DataBaseException {

        while(true){
            DISPLAY.Happy();
            Scanner scan = new Scanner(System.in);
            System.out.println();
            System.out.println("play ? (y/n):   ");
            String response = scan.nextLine();
            if(response.toLowerCase().equals("y")){
                DISPLAY.CHOSEYOURMODE();
                System.out.println("1) player vs player(offline mode)");
                System.out.println("2) player vs player (online mode)");
                System.out.println("3) player vs the machine");
                System.out.println("0) exit");

                String mode=scan.nextLine();
                while(mode.equals("1") || mode.equals("2") || mode.equals("3") || mode.equals("0")){
                    if(mode.equals("1")){
                        System.out.println();
                        System.out.println("chose an option:");
                        System.out.println("1) play in console");
                        System.out.println("2) play in a user interface");
                        System.out.println("0) go back");
                        String mode2="";
                        while(!mode2.equals("1") && !mode2.equals("2") && !mode2.equals("0")){
                            mode2=scan.nextLine();
                            if(mode2.equals("1")){
                                new ChessBoard().playVsHuman();
                                break;
                            }
                            else if(mode2.equals("2")){

                                new ChessGame().main(null);
                                break;
                            }
                            else{
                                break;
                            }
                        }
                    }else if(mode.equals("2")){
                        new ChessBoard().playONLine();
                    }else if(mode.equals("3")){
                        System.out.println();
                        System.out.println("chose dificulty");
                        System.out.println("1) easy: (algorithm that relises on picking random moves)");
                        System.out.println("\u001B[90m2) medium (algorithm that relises on evaluating your moves) (not currently available)\u001B[0m");
                        String mode3= scan.nextLine();
                        while(mode3.equals("1")){
                            if(mode3.equals("1")){
                                new ChessBoard().playVsAi();
                                break;
                            }else if(mode3.equals("2")){
                                System.out.println("sadly this mode isn't available yet");
                            }else{
                                System.out.println("invalid output");
                            }
                        }
                    }else{System.out.println("invalid output");
                        mode=null;
                    }
                }

            }else if(response.toLowerCase().equals("n")){
                System.out.println("you decide to exit then, farewell");
                break;
            }
            else{
                System.out.println("sorry, invalid input; press n to exit or y to access the game");
            }
        }

    }

}