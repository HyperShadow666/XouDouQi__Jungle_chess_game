package com.ensah.game1.ihm;

public class Piece {
    public int isKilled = 0;

    protected int x;
    protected int y;
    protected int real_x;
    protected int real_y;
    protected int test_x;
    protected int test_y;

    protected String name;

    protected int rank;
    protected int player; // player 1 = RED and player 2 = BLUE
    protected String rname;


    public Piece(int x, int y, int test_x, int test_y, String name, boolean isBlue, int rank, int player, String rname) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.rank = rank;
        this.player = player;
        this.rname = rname;
        this.test_x = test_x;
        this.test_y = test_y;


        real_x = x * ChessGame.width;
        real_y = y * ChessGame.height;
    }

    public boolean kill(Piece p) {

        if (player != p.player) {
            if (rank >= p.rank) { // Checking if you are strong.
                if (rank == 7 && p.rank == 0) {
                    ;
                } else {
                    x = p.x;
                    y = p.y;
                    real_x = p.x * ChessGame.width;
                    real_y = p.y * ChessGame.height;
                    ChessGame.pieces.remove(p);
                    p.isKilled = 1;
                    if ((p.player == 1)) {
                        ChessGame.blueplayerscore++;
                    } else {
                        ChessGame.redplayerscore++;
                    }
                    System.out.println("*** BLUE score: " + ChessGame.blueplayerscore);
                    System.out.println("*** RED score: " + ChessGame.redplayerscore);
                    return true;
                }

            } else if (rank == 0 && p.rank == 7) {
                x = p.x;
                y = p.y;
                real_x = p.x * ChessGame.width;
                real_y = p.y * ChessGame.height;
                ChessGame.pieces.remove(p); // RAT kills the ELEPHANT.
                p.isKilled = 1;
                if ((p.player == 1)) {
                    ChessGame.blueplayerscore++;
                } else {
                    ChessGame.redplayerscore++;
                }
                System.out.println("*** BLUE score: " + ChessGame.blueplayerscore);
                System.out.println("*** RED score: " + ChessGame.redplayerscore);
                return true;
            }
            // Killing the BLUE animals in the traps, ranking doesn't matter anymore.
            else if (player == 2 && p.y == 0 && (p.x == 2 || p.x == 4)) {
                x = p.x;
                y = p.y;
                real_x = p.x * ChessGame.width;
                real_y = p.y * ChessGame.height;
                ChessGame.pieces.remove(p);
                p.isKilled = 1;
                if ((p.player == 1)) {
                    ChessGame.blueplayerscore++;
                } else {
                    ChessGame.redplayerscore++;
                }
                System.out.println("*** BLUE score: " + ChessGame.blueplayerscore);
                System.out.println("*** RED score: " + ChessGame.redplayerscore);
                return true;


            } else if (player == 2 && p.y == 1 && p.x == 3) {
                x = p.x;
                y = p.y;
                real_x = p.x * ChessGame.width;
                real_y = p.y * ChessGame.height;
                ChessGame.pieces.remove(p);
                p.isKilled = 1;
                if ((p.player == 1)) {
                    ChessGame.blueplayerscore++;
                } else {
                    ChessGame.redplayerscore++;
                }
                System.out.println("*** BLUE score: " + ChessGame.blueplayerscore);
                System.out.println("*** RED score: " + ChessGame.redplayerscore);
                return true;

            }
            // Killing the RED animals in the traps, ranking doesn't matter anymore.
            else if (player == 1 && p.y == 8 && (p.x == 2 || p.x == 4)) {
                x = p.x;
                y = p.y;
                real_x = p.x * ChessGame.width;
                real_y = p.y * ChessGame.height;
                ChessGame.pieces.remove(p);
                p.isKilled = 1;
                if ((p.player == 1)) {
                    ChessGame.blueplayerscore++;
                } else {
                    ChessGame.redplayerscore++;
                }
                System.out.println("*** BLUE score: " + ChessGame.blueplayerscore);
                System.out.println("*** RED score: " + ChessGame.redplayerscore);
                return true;

            } else if (player == 1 && p.y == 7 && p.x == 3) {
                x = p.x;
                y = p.y;
                real_x = p.x * ChessGame.width;
                real_y = p.y * ChessGame.height;
                ChessGame.pieces.remove(p);
                p.isKilled = 1;
                if ((p.player == 1)) {
                    ChessGame.blueplayerscore++;
                } else {
                    ChessGame.redplayerscore++;
                }
                System.out.println("*** BLUE score: " + ChessGame.blueplayerscore);
                System.out.println("*** RED score: " + ChessGame.redplayerscore);
                return true;

            }
        }
        return false;
    }


    public void move(int px, int py) {
        //check if the kill is possible or not
        boolean executablekill;
        // check if the move is geometrically valid (0<x<7 and 0<y<8)
        if (!(px < 0 || px > 6 || py < 0 || py > 8)) {
            // RED Player
            if (player == 1) {
                if (ChessGame.redCount == 0) {//red player turn
                    // MOVE
                    if (ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height) == null) { // The destination is empty.

                        // The lake coordination
                        if ((px == 1 || px == 2 || px == 4 || px == 5) && (py >= 3 && py <= 5) && rank == 0) {
                            // Piece is a RAT.
                            // RAT get into the lake.
                            x = px;
                            y = py;
                            real_x = px * ChessGame.width;
                            real_y = py * ChessGame.height;
                            // MOVED

                            // --- TURN ----
                            ChessGame.redCount++;
                            ChessGame.blueCount = 0;
                            System.out.println("--------> BLUE turn <----------");
                        } else if ((px == 1 || px == 2 || px == 4 || px == 5) && (py >= 3 && py <= 5) && (rank != 0)) {
                            // Piece is not a RAT.


                            // The code for the board of the game.
                        } else if ((px != 1 && px != 2 && px != 4 && px != 5) || py < 3 || py > 5) {
                            if (px == 3 && py == 0) {
                                //sanctuary
                                System.out.println("Red Player wins:" );
                                x = px;
                                y = py;
                                real_x = px * ChessGame.width;
                                real_y = py * ChessGame.height;
                                ChessGame.displayredWinnerWindow();

                            } else if (px == 3 && py == 8) {

                            } else {
                                // Regular move.
                                x = px;
                                y = py;
                                real_x = px * ChessGame.width;
                                real_y = py * ChessGame.height;

                                // MOVED

                                ChessGame.redCount++;
                                ChessGame.blueCount = 0;
                                System.out.println("--------> BLUE turn <----------");
                            }
                        }

                    } else { // The destination is NOT empty.
                        if (player != ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height).player) { // Checking that there
                            // is an enemy.

                            // The lake coordination
                            if ((px == 1 || px == 2 || px == 4 || px == 5) && py >= 3 && py <= 5 && rank == 0) {
                                //you found the rat and the rat is in the lake
                                executablekill = kill(ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height));
                                if (executablekill) {
                                    ChessGame.redCount++;
                                    ChessGame.blueCount = 0;
                                    System.out.println("--------> BLUE turn <----------");
                                }
//
                            } else if ((px == 1 || px == 2 || px == 4 || px == 5) && py >= 3 && py <= 5 && rank != 0) { // Piece is not a rat


                            } else if ((px != 1 && px != 2 && px != 4 && px != 5) || py < 3 || py > 5) {
                                // Regular killing.
                                executablekill = kill(ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height));
                                if (executablekill) {

                                    ChessGame.redCount++;
                                    ChessGame.blueCount = 0;
                                    System.out.println("--------> BLUE turn <----------");
                                }


                            }

                        }
                    }

                }
            }

            // BLUE Player
            if (player == 2) {
                if (ChessGame.blueCount == 0) {
                    // MOVE
                    if (ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height) == null) { // The destination is empty.

                        // The lake coordination
                        if ((px == 1 || px == 2 || px == 4 || px == 5) && py >= 3 && py <= 5 && rank == 0) { // Piece is a
                            // RAT.
                            // RAT get into the lake.
                            x = px;
                            y = py;
                            real_x = px * ChessGame.width;
                            real_y = py * ChessGame.height;
                            // MOVED

                            // --- TURN ----
                            ChessGame.blueCount++;
                            ChessGame.redCount = 0;
                            System.out.println("--------> RED turn <----------");
                        } else if ((px == 1 || px == 2 || px == 4 || px == 5) && (py >= 3 && py <= 5) && rank != 0) { // Piece is not a RAT.


                            // The code for the board of the game.
                        } else if ((px != 1 && px != 2 && px != 4 && px != 5) || py < 3 || py > 5) {
                            //sanctuary
                            if (px == 3 && py == 0) {

                            } else if (px == 3 && py == 8) {
                                System.out.println("Blue Player wins" );
                                x = px;
                                y = py;
                                real_x = px * ChessGame.width;
                                real_y = py * ChessGame.height;
                                ChessGame.displayblueWinnerWindow();
                            } else {
                                // Regular move.
                                x = px;
                                y = py;
                                real_x = px * ChessGame.width;
                                real_y = py * ChessGame.height;
                                // MOVED
                                // --- TURN ----
                                ChessGame.blueCount++;
                                ChessGame.redCount = 0;
                                System.out.println("--------> RED turn <----------");
                            }
                        }

                    } else { // The destination is NOT empty.
                        if (player != ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height).player) { // Checking that there
                            // is an enemy.

                            // The lake coordination
                            if ((px == 1 || px == 2 || px == 4 || px == 5) && (py >= 3 && py <= 5) && rank == 0) { // Piece is a rat
                                executablekill = kill(ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height));
                                if (executablekill) {

                                    ChessGame.blueCount++;
                                    ChessGame.redCount = 0;
                                    System.out.println("--------> RED turn <----------");
                                }


                            } else if ((px == 1 || px == 2 || px == 4 || px == 5) && py >= 3 && py <= 5 && rank != 0) { // Piece is not a rat

                            } else if ((px != 1 && px != 2 && px != 4 && px != 5) || py < 3 || py > 5) {
                                // Regular killing.
                                executablekill = kill(ChessGame.findPiece(px * ChessGame.width, py * ChessGame.height));
                                if (executablekill) {

                                    ChessGame.blueCount++;
                                    ChessGame.redCount = 0;
                                    System.out.println("--------> RED turn <----------");
                                }
                            }
                        }
                    }
                }
            }

            if (ChessGame.blueplayerscore == 8) {
                ChessGame.displayblueWinnerWindow();
            }
            if (ChessGame.redplayerscore == 8) {
                ChessGame.displayredWinnerWindow();
            }

        }
    }
}