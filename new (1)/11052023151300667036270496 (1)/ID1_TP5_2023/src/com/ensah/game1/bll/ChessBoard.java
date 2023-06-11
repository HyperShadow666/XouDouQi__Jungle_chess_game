package com.ensah.game1.bll;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.ensah.data.DataBaseException;
import com.ensah.game1.server.ClientProg;
import com.ensah.game1.server.DatabaseLogging;
import com.ensah.game1.server.ServerApp;


public class ChessBoard {

    private int player1Score;
    private int player2Score;

    // c is a private 2-dimensional array of game1.Cell storing the board
    // components
    private Cell c[][] = new Cell[9][7];

    // a1 is a private 1-demensional array of Animal storing the animals
    // for player 1
    private Animal a1[] = new Animal[8];
    // a2 is a private 1-demensional array of Animal storing the animals
    // for player 2
    private Animal a2[] = new Animal[8];

    // a1ALLLegalMoves is a private arraylist of Animal storing all the
    //animal's legal moves for player 1
    public ArrayList <Position> a1ALLLegalMoves = new ArrayList<>();
    // a2ALLLegalMoves is a private arraylist of game1.Animal storing all the
    //animal's legal moves for player 2
    public ArrayList <Position> a2ALLLegalMoves = new ArrayList<>();
    //
    //
    public Map<Animal, ArrayList<Position>> animala1moves = new HashMap<>();
    //
    //
    public Map<Animal, ArrayList<Position>> animala2moves = new HashMap<>();

    //boolean that checks wether or not P1 won
    public boolean player1win;


    //boolean that checks wether or not P2 won
    public boolean player2win;

    //stack that saves all the board moves sequentially
    Stack<int[]> boardMoves = new Stack<>();

    NoteWriter noteWriter = new NoteWriter(this, -1,-1,-1);
    String filePath = "note.txt";



    // game1.ChessBoard is a default constructor for setting up the chess board
    public ChessBoard() {

        // game1.Vide game1.Cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                c[i][j] = new Vide(new Position(i,j),-1);
            }
        }

        // Fixed game1.Sanctuaire game1.Position
        c[0][3] = new Sanctuaire(new Position(0, 3),1);
        c[8][3] = new Sanctuaire( new Position(8, 3),2);

        // Fixed game1.Riviere game1.Position
        c[3][1] = new Riviere(new Position(3, 1),-1 );
        c[3][2] = new Riviere( new Position(3, 2),-1);
        c[3][4] = new Riviere( new Position(3, 4),-1);
        c[3][5] = new Riviere(new Position(3, 5),-1);
        c[4][1] = new Riviere(new Position(4, 1),-1);
        c[4][2] = new Riviere(new Position(4, 2),-1);
        c[4][4] = new Riviere(new Position(4, 4),-1);
        c[4][5] = new Riviere(new Position(4, 5),-1);
        c[5][1] = new Riviere(new Position(5, 1),-1);
        c[5][2] = new Riviere(new Position(5, 2),-1);
        c[5][4] = new Riviere( new Position(5, 4),-1);
        c[5][5] = new Riviere( new Position(5, 5),-1);

        // Fixed Player1's game1.Trap game1.Position
        c[0][2] = new Trap(1, new Position(0, 2));
        c[0][4] = new Trap(1, new Position(0, 4));
        c[1][3] = new Trap(1, new Position(1, 3));

        // Fixed Player2's game1.Trap game1.Position
        c[8][2] = new Trap(2, new Position(8, 2));
        c[8][4] = new Trap(2, new Position(4, 4));
        c[7][3] = new Trap(2, new Position(7, 3));

        // Player1's Chess Start game1.Position
        a1[0] = new Mouse(1, new Position(2, 0));
        a1[1] = new Cat(1, new Position(1, 5));
        a1[2] = new Wolf(1, new Position(2, 4));
        a1[3] = new Dog(1, new Position(1, 1));
        a1[4] = new Leopard(1, new Position(2, 2));
        a1[5] = new Tiger(1, new Position(0, 6));
        a1[6] = new Lion(1, new Position(0, 0));
        a1[7] = new Elephant(1, new Position(2, 6));

        // Player2's Chess Start game1.Position
        a2[0] = new Mouse(2, new Position(6, 6));
        a2[1] = new Cat(2, new Position(7, 1));
        a2[2] = new Wolf(2, new Position(6, 2));
        a2[3] = new Dog(2, new Position(7, 5));
        a2[4] = new Leopard(2, new Position(6, 4));
        a2[5] = new Tiger(2, new Position(8, 0));
        a2[6] = new Lion(2, new Position(8, 6));
        a2[7] = new Elephant(2, new Position(6, 0));
    }

    private void scanScoreforPlayer2() {
        int s=0;
        for (Animal animal : a1){
            if(a1.length==0){
                break;
            }
            if(animal.getDead()== true){

                s=s+animal.getPieceValue();
            }
        }
        player2Score= s;
    }

    public boolean Player1Play()throws InputMismatchException{
        try{



            Scanner input = new Scanner(System.in);

            scanScoreforPlayer1();
            scanScoreforPlayer2();

            updatea1ALLLegalMoves();
            getAllMovesa1();
            System.out.println(animala1moves);
            //geta1ALLLegalPositions();
            // Player 1--------------------------------------------------------
            String p1AnimalDisplay = "";

            // do {

            // Print the Chess Board with all items for player 1
            System.out.print(this);

            // Print the choices that give Player 1 to choose the chess
            System.out.println("\u001B[31mPlayer 1: Please select an animal to move\u001B[0m :\n");
            for (int animal = 0; animal < a1.length; animal++) {
                p1AnimalDisplay += (a1[animal].getName() + ",    ");
            }
            System.out.println(p1AnimalDisplay.substring(0, p1AnimalDisplay
                    .length() - 2));
            int p1 = input.nextInt();

            // Warning Message for Player 1(Input number must be not dead
            // animal(s))
            while (p1 < 0 || p1 > 7 || a1[p1].getDead()) { // <--Cuation!!!!!
                System.out
                        .println("Invalid Input. Please select an animal again!");
                p1 = input.nextInt();
            }

            // Player 1 that input row which the chess need
            System.out.println("Please enter the row number(0-8) to move :");
            int p1Row = input.nextInt();
            // Warning Message for Player 1(Input numbers must be from 0 to
            // 8)
            while (p1Row < 0 || p1Row > 8) {
                System.out.println("Invalid Input. Please enter 0-8!");
                System.out
                        .println("Please enter the row number(0-8) to move :");
                p1Row = input.nextInt();
            }

            // Player 1 that input column which the chess need
            System.out.println("Please enter the column number(0-6) to move :");
            int p1Column = input.nextInt();
            // Warning Message for Player 1(Input numbers must be from 0 to
            // 6)
            while (p1Column < 0 || p1Column > 6) {
                System.out.println("Invalid Input. Please enter 0-6!");
                System.out
                        .println("Please enter the column number(0-6) to move :");
                p1Column = input.nextInt();
            }

            System.out.println("");

            // Call execute to check the animal the next step position that
            // it
            // is correct or not. Otherwise, output "Invalid move!"
            if(a1[p1].execute(p1Row, p1Column, a1, a2, c, this) == false){
                System.out.println("Invalid move!\n");
                return false;
            }
            else{



                scanScoreforPlayer1();
                scanScoreforPlayer2();

                noteWriter.setPlayer(p1);
                noteWriter.setColumn(p1Column);
                noteWriter.setRow(p1Row);
                noteWriter.writeToFile(filePath);

                return true;
            }

        }catch(InputMismatchException exc){
            System.out.println("input error, you input a string dummy !");
            return false;
        }
    }

    public boolean Player2Play() throws InputMismatchException{
        try{

            Scanner input = new Scanner(System.in);

            scanScoreforPlayer2();
            scanScoreforPlayer1();

            updatea2ALLLegalMoves();
            getAllMovesa2();
            System.out.println(animala2moves);
            //geta2ALLLegalPositions();
            // Player 2--------------------------------------------------------
            String p2AnimalDisplay = "";

            // Print the Chess Board with all items for player 2
            System.out.print(this);

            // Print the choices that give Player 2 to choose the chess
            System.out.println("\u001B[32mPlayer 2: Please select an animal to move\u001B[0m :");
            for (int animal = 0; animal < a2.length; animal++) {
                p2AnimalDisplay += (a2[animal].getName() + ",    ");
            }
            System.out.println(p2AnimalDisplay.substring(0, p2AnimalDisplay
                    .length() - 2));
            int p2 = input.nextInt();

            // Warning Message for Player 2(Input number must be not dead
            // animal(s))
            while (p2 < 0 || p2 > 7 || a2[p2].getDead()) { // <--Cuation!!!!!
                System.out
                        .println("Invalid Input. Please select an animal again!");
                p2 = input.nextInt();
            }

            // Player 2 that input row which the chess need
            System.out.println("Please enter the row number(0-8) to move :");
            int p2Row = input.nextInt();
            // Warning Message for Player 2(Input numbers must be from 0 to 8)
            while (p2Row < 0 || p2Row > 8) {
                System.out.println("Invalid Input. Please enter 0-8!");
                System.out
                        .println("Please enter the row number(0-8) to move :");
                p2Row = input.nextInt();
            }

            // Player 2 that input column which the chess need
            System.out.println("Please enter the column number(0-6) to move :");
            int p2Column = input.nextInt();
            // Warning Message for Player 2(Input numbers must be from 0 to 6)
            while (p2Column < 0 || p2Column > 6) {
                System.out.println("Invalid Input. Please enter 0-6!");
                System.out
                        .println("Please enter the column number(0-6) to move :");
                p2Column = input.nextInt();
            }

            System.out.println("");

            // Call execute to check the animal the next step position that it
            // is correct or not. Otherwise, output "Invalid move!"
            if (a2[p2].execute(p2Row, p2Column, a2, a1, c, this) == false){
                System.out.println("Invalid move!\n");
                return false;}
            else{

                //save the lastMove

                scanScoreforPlayer2();
                scanScoreforPlayer1();

                noteWriter.setPlayer(p2);
                noteWriter.setColumn(p2Column);
                noteWriter.setRow(p2Row);
                noteWriter.writeToFile(filePath);
                return true;
            }
        } catch (InputMismatchException exc){
            System.out.println("input error, you used a string dummy !");
            return false;
        }
    }



    private void scanScoreforPlayer1() {

        int s=0;

        for (Animal animal : a2){
            if(a2.length==0){
                break;
            }
            if(animal.getDead()){

                s=s+animal.getPieceValue();
            }
        }
        player1Score= s;
    }

    // play is a method to start and control the game which returns no result
    public void playVsHuman() throws InputMismatchException{
        try{

            Scanner rep = new Scanner(System.in);
            String resp = null;
            String[] lastGameMoves = FileLoader.processLastLine(filePath);


            System.out.println("chose your mode:");
            System.out.println("1) new game");

            if(lastGameMoves== null){
                System.out.println("2) continue the last game  \u001B[35m(no record found)\u001B[0m");

            }
            String lastTwoCharacters =  lastGameMoves[lastGameMoves.length-1].substring(lastGameMoves[lastGameMoves.length-1].length() - 2);

            if((lastTwoCharacters).equals("03") || (lastTwoCharacters).equals("83")){

                System.out.println("2) continue the last game  \u001B[35m(already ended)\u001B[0m");
            }

            else{

                System.out.println("2) continue the last game  \u001B[33m(not ended yet !)\u001B[0m");
            }

            resp = rep.nextLine();


            if(resp.equals("2")){

                int i = 0;

                for(String move : lastGameMoves) {
                    if(i%2==0){

                        boolean nextphase= false;
                        do{

                            char[] moveChar = move.toCharArray();
                            int p1 = Character.getNumericValue(moveChar[0]);
                            int p1Row = Character.getNumericValue(moveChar[1]);
                            int p1Column = Character.getNumericValue(moveChar[2]);
                            nextphase = a1[p1].execute(p1Row,p1Column,a1,a2, c, this);


                        } while(nextphase==false);

                        if(player1win){
                            System.out.println(this);
                            System.out.println("player 1 won : ");
                            break;
                        }
                        nextphase= false;
                        i++;
                    }else{

                        boolean nextphase= false;
                        do{

                            char[] moveChar = move.toCharArray();
                            int p1 = Character.getNumericValue(moveChar[0]);
                            int p1Row = Character.getNumericValue(moveChar[1]);
                            int p1Column = Character.getNumericValue(moveChar[2]);
                            nextphase = a2[p1].execute(p1Row,p1Column,a2,a1, c, this);

                        }while(nextphase==false);

                        if(player2win){
                            System.out.println(this);
                            System.out.println("player 2 won :");
                            break;
                        }
                        nextphase= false;
                        i++;
                    }
                }

                //who's turn now ? :check:
                if(i%2==0){

                    // Loop Players' Input
                    while (!player1win && !player2win) {


                        boolean nextphase= false;
                        do{

                            nextphase=Player1Play();

                        } while(nextphase==false);

                        if(player1win){
                            System.out.println("player 1 won : ");
                            break;
                        }
                        nextphase= false;

                        do{

                            nextphase= Player2Play();
                        }while(nextphase==false);

                        if(player2win){
                            System.out.println("player 2 won :");
                            break;
                        }
                    }
                }
                else{

                    while (!player1win && !player2win) {
                        boolean nextphase= false;
                        do{

                            nextphase= Player2Play();
                        }while(nextphase==false);

                        if(player2win){
                            System.out.println("player 2 won :");
                            break;
                        }
                        nextphase= false;

                        do{

                            nextphase=Player1Play();

                        } while(nextphase==false);

                        if(player1win){
                            System.out.println("player 1 won : ");
                            break;
                        }
                    }

                }

            }

            if(resp.equals("1")){

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("error has been occured, we cannot save this game in our database");
                }

                // Loop Players' Input
                while (!player1win && !player2win) {


                    boolean nextphase= false;
                    do{

                        nextphase=Player1Play();

                    } while(nextphase==false);

                    if(player1win){
                        System.out.println("player 1 won : ");
                        break;
                    }
                    nextphase= false;

                    do{

                        nextphase= Player2Play();
                    }while(nextphase==false);

                    if(player2win){
                        System.out.println("player 2 won :");
                        break;
                    }
                }
            }
        }catch (InputMismatchException ex) {
            System.out.println("input error");
            return;
        }
    }






    public void updatea2ALLLegalMoves() {

        if(a2ALLLegalMoves.size()!=0){

            a2ALLLegalMoves.removeAll(a2ALLLegalMoves);
        }

        for(Animal a2Animal: a2){
            if(a2Animal.isDead== false){
                a2Animal.addLegalMove(a2,a1,c, this);
                a2ALLLegalMoves.addAll(a2Animal.getLegalMoves);

            }
        }
    }

    public void updatea1ALLLegalMoves() {
        if(a1ALLLegalMoves.size()!=0){

            a1ALLLegalMoves.removeAll(a1ALLLegalMoves);
        }

        for(Animal a1Animal: a1){
            if(a1Animal.isDead== false){
                a1Animal.addLegalMove(a1,a2,c, this);
                a1ALLLegalMoves.addAll(a1Animal.getLegalMoves);

            }
        }

    }

    public void getAllMovesa1(){
        if(animala1moves.size() !=0){

            animala1moves.clear();
        }
        for(Animal a1Animal: a1){
            if(a1Animal.isDead== false){
                a1Animal.addLegalMove(a1, a2, c, this);
                animala1moves.put(a1Animal, a1Animal.getLegalMoves );
            }
        }
    }

    public void getAllMovesa2(){
        if(animala2moves.size() !=0){

            animala2moves.clear();
        }
        for(Animal a2Animal: a2){
            if(a2Animal.isDead== false){
                a2Animal.addLegalMove(a2, a1, c, this);
                animala2moves.put(a2Animal, a2Animal.getLegalMoves );
            }
        }
    }


    public void playVsAi(){

        System.out.println("player 1: you \tvs\t player 2: AI");
        while (true) {


            boolean nextphase= false;
            do{
                nextphase = Player1Play();

            } while(nextphase==false);

            if(player1win){
                System.out.println("player 1 won :");
                break;
            }

            Boolean chosedPiece=false;

            scanScoreforPlayer1();
            scanScoreforPlayer2();
            int random_index;

            updatea2ALLLegalMoves();
            getAllMovesa2();
            System.out.println(animala2moves);
            //geta2ALLLegalPositions();

            while(!chosedPiece){
                Random random = new Random();

                random_index = random.nextInt(8);
                if(a2[random_index].isDead==true){
                    chosedPiece=false;

                }else{chosedPiece=true;}

                if(chosedPiece)
                {
                    int random_choice = random.nextInt(animala2moves.get(a2[random_index]).size());
                    if(a2[random_index].execute(animala2moves.get(a2[random_index]).get(random_choice).getX(), animala2moves.get(a2[random_index]).get(random_choice).getY(), a2, a1, c, this) == false){
                        System.out.println("Invalid move!\n");
                        chosedPiece= false;}
                    else{chosedPiece=true;

                        noteWriter.setPlayer(random_index);
                        noteWriter.setColumn(animala2moves.get(a2[random_index]).get(random_choice).getX());
                        noteWriter.setRow(animala2moves.get(a2[random_index]).get(random_choice).getY());
                        noteWriter.writeToFile(filePath);

                    }
                }
            }

            if(player2win){
                System.out.println("player 2 won :");
                break;
            }

        }
    }
//    public void playVsAi2(){
////        ChessAI ai = new ChessAI();
//
//        System.out.println("player 1: you \tvs\t player 2: AI");
//        while (true) {
//
//
//            boolean nextphase= false;
//            do{
//                nextphase = Player1Play();
//
//            } while(nextphase==false);
//
//            if(player1win){
//                System.out.println("player 1 won :");
//                break;
//            }
//
//            Boolean chosedPiece=false;
//
//            scanScoreforPlayer1();
//            scanScoreforPlayer2();
//
//
//            updatea2ALLLegalMoves();
//            getAllMovesa2();
//            System.out.println(animala2moves);
//            //geta2ALLLegalPositions();
//
//
//
////            int [] decision = ai.findBestMove(this);
//
//            if((a2[decision[0]]).execute(decision[1], decision[2], a2, a1, c, this) == false){
//                System.out.println("#################impossible################");
//            }
//
//
//            if(player2win){
//                System.out.println("player 2 won :");
//                break;
//            }
//        }
//
//
//    }

    // public void AIvsAI(){

    //     System.out.println("player 1: AI \tvs\t player 2: AI");

    //     boolean nextphase= false;



    //         while (!player1win ||!player2win) {

    //             int random_index;
    //             Boolean chosedPiece=false;

    //         updatea1ALLLegalMoves();
    //         getAllMovesa1();
    //         System.out.println(animala1moves);
    //         geta1ALLLegalPositions();

    //         while(!chosedPiece){
    //             Random random = new Random();

    //             random_index = random.nextInt(8);

    //             if(a1[random_index].isDead==true){
    //                 chosedPiece=false;
    //             }

    //             int random_position= random.nextInt(animala1moves.get(a1[random_index]).size());

    //             if(a1[random_index].execute(animala1moves.get(a1[random_index]).get(random_position).getX(), animala1moves.get(a1[random_index]).get(random_position).getY(), a1, a2, c) == false){
    //                 System.out.println("Invalid move!\n");

    //                 System.out.println(a1[random_index]+"tried to play "+ animala1moves.get(a1[random_index]).get(random_position));

    //                 chosedPiece= false;}

    //             else{chosedPiece=true;
    //                 System.out.print(this);
    //                 scanScoreforPlayer1();

    //                     try {
    //                     Thread.sleep(2000); // 2000 milliseconds = 2 seconds
    //                 } catch (InterruptedException e) {
    //                     e.printStackTrace();

    //                 }
    //             }


    //         }


    //         chosedPiece=false;

    //         updatea2ALLLegalMoves();
    //         getAllMovesa2();
    //         System.out.println(animala2moves);
    //         System.out.println("\n");
    //         geta2ALLLegalPositions();

    //         while(!chosedPiece){
    //             Random random = new Random();

    //             random_index = random.nextInt(8);
    //             if(a2[random_index].isDead==true){
    //                 chosedPiece=false;
    //             }
    //             int random_position= random.nextInt(animala2moves.get(a2[random_index]).size());

    //             if(a2[random_index].execute(animala2moves.get(a2[random_index]).get(random_position).getX(), animala2moves.get(a2[random_index]).get(random_position).getY(), a2, a1, c) == false){
    //                 System.out.println("Invalid move!\n");

    //                 System.out.println(a2[random_index]+" played "+ animala2moves.get(a2[random_index]).get(random_position));

    //                 chosedPiece= false;}
    //             else{chosedPiece=true;
    //                 System.out.print(this);
    //                 scanScoreforPlayer2();

    //                 try {
    //                     Thread.sleep(2000); // 2000 milliseconds = 2 seconds
    //                 } catch (InterruptedException e) {
    //                     e.printStackTrace();
    //                 }
    //             }
    //         }
    //     }

    // }


    public void playONLine() throws DataBaseException{
        String choice = "";
        while(!choice.equals("1") && !choice.equals("2")){

            Scanner sc = new Scanner(System.in);
            System.out.println("1)  start server\n2)  join a server");
            choice = sc.nextLine();
            if(choice.equals("1")){
                openserver();
            }
            if(choice.equals("2")){
                joinserver();
            }
        }
    }

    public void openserver() throws DataBaseException {
        boolean noProblem;
        Scanner sc = new Scanner(System.in);

        Scanner input = new Scanner(System.in);
        System.out.println("please entrer the network port number (Example: 5000)");
        int port  = sc.nextInt();
        ServerApp server=null;
        try {
            server = new ServerApp(port);
        } catch (Exception e) {
            System.out.println("error occured when connecting, plz try later -_-");
            e.printStackTrace();
        }
        while(!player1win && !player2win){


            noProblem = true;

            if (noProblem) {

                try {

                    String receivedData;

                    // reads message from client
                    receivedData = (String) server.getIn().readObject();

                    //if it wants to close connection
                    if (receivedData == "end_connection") {

                        System.out.println("Closing connection");

                        // close connection
                        server.getIn().close();
                        server.getOut().close();
                        server.getSocket().close();

                    } else {

                        //print the message received
                        System.out.println("Move received :" + receivedData);

                        char [] resp_caracters = ((String) receivedData).toCharArray();

                        int p1 = Character.getNumericValue(resp_caracters[0]);
                        int p1row = Character.getNumericValue(resp_caracters[1]);
                        int p1column = Character.getNumericValue(resp_caracters[2]);

                        if(a1[p1].execute(p1row,p1column,a1,a2, c, this)==false){

                            System.out.println("well this wasn't suppose to happen");

                        }
                        DatabaseLogging.insertUpdate("Player 1 played:  "+ p1+" "+p1row+" "+p1column);
                        DatabaseLogging.insertUpdate("Player 1 score:  "+ player1Score);


                        if(player1win){
                            System.out.println("player 1 won : ");
                            break;
                        }

                    }



                    //  server.getOut().flush();
                }

                catch (Exception e) {
                    System.out.println("Connection  with client is lost. Waiting for reconnection...");
                    // e.printStackTrace();
                    try {
                        // close connection
                        server.getIn().close();

                        server.getOut().close();
                        server.getSocket().close();
                    } catch (Exception ex) {
                        System.err.println("Cant close connection...");
                    }

                    noProblem = false;
                }
            }else{

                try {
                    // close connection
                    server.getIn().close();
                    server.getOut().close();
                    server.getSocket().close();
                } catch (Exception ex) {
                    System.err.println("Cant close connection...");
                }

            }

            boolean nextphase= false;

            do{

                updatea2ALLLegalMoves();
                getAllMovesa2();
                System.out.println(animala2moves);
                //geta2ALLLegalPositions();
                // Player 2--------------------------------------------------------
                String p2AnimalDisplay = "";

                // Print the Chess Board with all items for player 2
                System.out.print(this);

                // Print the choices that give Player 2 to choose the chess
                System.out.println("Player 2: Please select an animal to move :");
                for (int animal = 0; animal < a2.length; animal++) {
                    p2AnimalDisplay += (a2[animal].getName() + ",    ");
                }
                System.out.println(p2AnimalDisplay.substring(0, p2AnimalDisplay
                        .length() - 2));

                    int p2 = input.nextInt();


                // Warning Message for Player 2(Input number must be not dead
                // animal(s))
                while (p2 < 0 || p2 > 7 || a2[p2].getDead()) { // <--Cuation!!!!!
                    System.out.println("Invalid Input. Please select an animal again!");
                    DatabaseLogging.insertUpdate("Player 2 selected an invalid animal");

                    try {
                        p2 = input.nextInt();
                    }catch (InputMismatchException e){}
                }

                // Player 2 that input row which the chess need
                System.out.println("Please enter the row number(0-8) to move :");
                int p2Row = input.nextInt();
                // Warning Message for Player 2(Input numbers must be from 0 to 8)
                while (p2Row < 0 || p2Row > 8) {
                    System.out.println("Invalid Input. Please enter 0-8!");
                    System.out.println("Please enter the row number(0-8) to move :");
                    DatabaseLogging.insertUpdate("Player 2 selected an invalid row");
                    try{
                        p2Row = input.nextInt();
                    }catch (InputMismatchException e){}
                }

                // Player 2 that input column which the chess need
                System.out.println("Please enter the column number(0-6) to move :");
                int p2Column = input.nextInt();
                // Warning Message for Player 2(Input numbers must be from 0 to 6)
                while (p2Column < 0 || p2Column > 6) {
                    System.out.println("Invalid Input. Please enter 0-6!");
                    System.out.println("Please enter the column number(0-6) to move :");
                    DatabaseLogging.insertUpdate("Player 2 selected an invalid column");
                    try{
                        p2Column = input.nextInt();
                    }catch (InputMismatchException e){}
                }

                System.out.println("");

                // Call execute to check the animal the next step position that it
                // is correct or not. Otherwise, output "Invalid move!"
                if (a2[p2].execute(p2Row, p2Column, a2, a1, c, this) == false){
                    System.out.println("Invalid move!\n");
                    nextphase= false;}
                else{

                    //save the lastMove


                    nextphase= true;
                    scanScoreforPlayer2();
                    System.out.println(this);
                    DatabaseLogging.insertUpdate("Player 2 played:  "+ p2+" "+p2Row+" "+p2Column);
                    DatabaseLogging.insertUpdate("Player 2 score:  "+ player1Score);

                    try {
                        server.getOut().writeObject(Integer.toString(p2)+Integer.toString(p2Row)+Integer.toString(p2Column));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        System.out.println("error, could not connect with the other player, please try again -_-");
                    }


                    // noteWriter.setPlayer(p2);
                    // noteWriter.setColumn(p2Column);
                    // noteWriter.setRow(p2Row);
                    // noteWriter.writeToFile(filePath);
                }
            }while(nextphase==false);

            if(player2win){
                System.out.println("player 2 won :");
                break;
            }

        }



    }
    public void joinserver() throws DataBaseException{

        Scanner sc = new Scanner(System.in);
        System.out.println("please entrer the network IP Adresse of the server number (for localhost the ip adresse is: 127.0.0.1)");
        String ipAdresse =  sc.next().trim();
        System.out.println("please entrer the network port number user by the server (Example: 5000)");
        int port  = sc.nextInt();



        //connect to server
        ClientProg client = ClientProg.getConnnection(ipAdresse, port);
        DatabaseLogging.insertUpdate("Client" + client.getSocket().getRemoteSocketAddress().toString()  + " is connected to the server");



        Scanner s = new Scanner(System.in);

        if(client.isStart2()){

            String piece= "";
            String row= "";
            String column= "";



            // Loop Players' Input
            while (!player1win && !player2win) {

                Scanner input = new Scanner(System.in);
                boolean nextphase= false;
                Object rep= null;
                do{

                    updatea1ALLLegalMoves();
                    getAllMovesa1();
                    //System.out.println(animala1moves);
                    //geta1ALLLegalPositions();
                    // Player 1--------------------------------------------------------
                    String p1AnimalDisplay = "";

                    // do {

                    // Print the Chess Board with all items for player 1
                    System.out.print(this);

                    // Print the choices that give Player 1 to choose the chess
                    System.out.println("Player 1: Please select an animal to move :");
                    for (int animal = 0; animal < a1.length; animal++) {
                        p1AnimalDisplay += (a1[animal].getName() + ",    ");
                    }
                    System.out.println(p1AnimalDisplay.substring(0, p1AnimalDisplay
                            .length() - 2));
                    int p1 = input.nextInt();

                    // Warning Message for Player 1(Input number must be not dead
                    // animal(s))
                    while (p1 < 0 || p1 > 7 || a1[p1].getDead()) { // <--Cuation!!!!!
                        System.out
                                .println("Invalid Input. Please select an animal again!");
                        DatabaseLogging.insertUpdate("Player 1 selected an invalid move");
                        //append the message to the log

                        try {
                            p1 = input.nextInt();
                        }catch (InputMismatchException e){}
                    }
                    piece = Integer.toString(p1);

                    // Player 1 that input row which the chess need
                    System.out.println("Please enter the row number(0-8) to move :");
                    int p1Row = input.nextInt();
                    // Warning Message for Player 1(Input numbers must be from 0 to
                    // 8)
                    while (p1Row < 0 || p1Row > 8) {
                        System.out.println("Invalid Input. Please enter 0-8!");
                        DatabaseLogging.insertUpdate("Player 1 selected an invalid row");
                        System.out.println("Please enter the row number(0-8) to move :");
                        try{
                        p1Row = input.nextInt();}catch (InputMismatchException e){}
                    }
                    row= Integer.toString(p1Row);

                    // Player 1 that input column which the chess need
                    System.out.println("Please enter the column number(0-6) to move :");
                    int p1Column = input.nextInt();
                    // Warning Message for Player 1(Input numbers must be from 0 to
                    // 6)
                    while (p1Column < 0 || p1Column > 6) {
                        System.out.println("Invalid Input. Please enter 0-6!");
                        DatabaseLogging.insertUpdate("Player 1 selected an invalid column");
                        System.out
                                .println("Please enter the column number(0-6) to move :");
                        try{
                        p1Column = input.nextInt();}catch (InputMismatchException e){}
                    }
                    column= Integer.toString(p1Column);

                    System.out.println("");

                    // Call execute to check the animal the next step position that
                    // it
                    // is correct or not. Otherwise, output "Invalid move!"
                    if(a1[p1].execute(p1Row, p1Column, a1, a2, c, this) == false){
                        System.out.println("Invalid move!\n");
                        DatabaseLogging.insertUpdate("Player 1 selected an invalid move");
                        nextphase= false;
                    }
                    else{

                        //save the lastMove


                        nextphase=true;
                        scanScoreforPlayer1();

                        // noteWriter.setPlayer(p1);
                        // noteWriter.setColumn(p1Column);
                        // noteWriter.setRow(p1Row);
                        // noteWriter.writeToFile(filePath);

                        System.out.print("you played:  "+ piece+" "+row+" "+column);
                        DatabaseLogging.insertUpdate("Player 1 played:  "+ piece+" "+row+" "+column);
                        DatabaseLogging.insertUpdate("Player 1 score:  "+ player1Score);




                        //send message
                        rep = client.send(piece+row+column);
                        System.out.println(this);

                    }


                } while(nextphase==false);

                if(player1win){
                    System.out.println("player 1 won : ");
                    DatabaseLogging.insertUpdate("Player 1 won the game");
                    break;
                }

                nextphase= false;


                //print server response
                System.out.println("response :   "+rep);

                char [] resp_caracters = ((String) rep).toCharArray();

                // System.out.println(resp_caracters[0]);
                // System.out.println(resp_caracters[1]);
                // System.out.println(resp_caracters[2]);

                int p2 = Character.getNumericValue(resp_caracters[0]);
                int p2row = Character.getNumericValue(resp_caracters[1]);
                int p2column = Character.getNumericValue(resp_caracters[2]);
                DatabaseLogging.insertUpdate("Player 2 played:  "+ p2+" "+p2row+" "+p2column);


                if(a2[p2].execute(p2row,p2column,a2,a1, c, this)==false){

                    System.out.println("well this wasn't suppose to happen");

                }


                if(player2win){
                    System.out.println("player 2 won : ");
                    DatabaseLogging.insertUpdate("Player 2 won the game");
                    break;
                }

            }

        }
    }



    public void undoMove() {
        if(!boardMoves.isEmpty()){
            int[] move = boardMoves.peek();
            if(move[0]==1){
                int p1 =move[1];
                int p1Row = move[2];
                int p1Column = move[3];
                int enemy = move[4];
                if(!a1[p1].execute(p1Row, p1Column, a1, a2, c, this)){
                    System.out.println("###########erreur##################");
                    System.out.println(p1 +""+ p1Row +""+ p1Column +""+ enemy);
                    System.out.println("###########erreur##################");
                }else{
                    // revive the enemy
                    if(!(enemy == -1)){
                        a2[enemy].setDead(false);
                    }
                    boardMoves.pop();


                }

            }
            else{
                int p2 =move[1];
                int p2Row = move[2];
                int p2Column = move[3];
                int enemy = move[4];
                if(!a2[p2].execute(p2Row, p2Column, a2, a1, c, this)){
                    System.out.println("###########erreur##################");
                    System.out.println(p2 +""+ p2Row +""+ p2Column +""+ enemy);
                    System.out.println("###########erreur##################");
                }
                else{
                    //revive the enemy
                    if(!(enemy == -1)){
                        a2[enemy].setDead(false);
                    }
                    boardMoves.pop();
                }

            }
        }

    }





    // toString is a method to return the chess board in a standard output
    // format
    public String toString() {

        System.out.println("\n \nplayer 1 score : "+player1Score+" ------------------ playe 2 score: "+player2Score);
        System.out.println("\n");

        StringBuffer output = new StringBuffer();

        for (int i = 0; i < 9; i++) {
            output.append(i + "|\t ");

            for (int j = 0; j < 7; j++) {

                //
                boolean check = false;

                for (int z = 0; z < a1.length; z++) {
                    if (a1[z].getDead())
                        continue;
                    Position p = a1[z].getPosition();
                    if (p.getX() == i && p.getY() == j) {
                        output.append(a1[z].toString() + "\t\t");
                        check = true;
                    }
                }

                if (check) {
                    continue;
                }

                for (int z1 = 0; z1 < a2.length; z1++) {
                    if (a2[z1].getDead())
                        continue;
                    Position p1 = a2[z1].getPosition();
                    if (p1.getX() == i && p1.getY() == j) {
                        output.append(a2[z1].toString() + "\t\t");
                        check = true;
                    }
                }

                if (check) {
                    continue;
                }

                output.append(c[i][j] + "\t\t");
            }

            output.append(" \n");
            output.append(" \n");
        }

        output.append("\t0\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6 \n\n");
        return output.toString();
    }
    // public void geta1ALLLegalPositions(){
    //     for(Position p: a1ALLLegalMoves){
    //         System.out.println(p);
    //     }
    // }
    // public void geta2ALLLegalPositions(){
    //     for(Position p: a2ALLLegalMoves){
    //         System.out.println(p);
    //     }

    // }

    public void setPlayer1Win(boolean b) {
        player1win = true;
    }

    public void setPlayer2Win(boolean b) {
        player2win = true;
    }
    public Animal[] getA1() {
        return a1;
    }

    public Animal[] getA2() {
        return a2;
    }

    public Cell[][] getC() {
        return c;
    }




}