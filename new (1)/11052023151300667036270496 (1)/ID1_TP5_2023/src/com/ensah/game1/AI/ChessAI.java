//package com.ensah.game1.AI;
//
//import game1.*;
//
//public class ChessAI {
//    private static final int MAX_DEPTH = 3; // Maximum depth for the Minimax algorithm
//
//    public int[] findBestMove(ChessBoard board) {
//        int bestScore = Integer.MIN_VALUE;
//        int[] bestMove = {-1,-1,-1} ;
//
//        for(Animal a2 :  board.getA2()){
//
//
//            if(a2.getDead() == true){continue;}
//            else{
//                board.getAllMovesa2();
//                int i = 0;
//                for(Position position : a2.getLegalMoves){
//                    if(a2.execute(position.getX(), position.getY(), board.getA2(), board.getA1(), board.getC(), board) == false){
//                        System.out.println("#################impossible################");
//                        //continue
//                        return null;
//                    }
//                    else{
//
//                        int score = minimax(board, MAX_DEPTH, true);
//
//                        board.undoMove();
//
//                        if (score > bestScore) {
//                            bestScore = score;
//
//                            bestMove[0] = i;
//                            bestMove[1] = position.getX();
//                            bestMove[2] = position.getY();
//                        }
//                        i++;
//                    }
//
//                }
//            }
//        }
//        return bestMove;
//
//    }
//
//
//    private int minimax(ChessBoard board, int depth, boolean isMaximizingPlayer) {
//        if (depth == 0 || board.player1win || board.player2win) {
//            return evaluate(board);
//            }
//
//        if (isMaximizingPlayer) {
//            int maxScore = Integer.MIN_VALUE;
//
//            for(Animal a1 :  board.getA1()){
//                board.getAllMovesa1();
//                for(Position position : a1.getLegalMoves){
//                    if(a1.execute(position.getX(), position.getY(), board.getA1(), board.getA2(), board.getC(), board) == false){
//                        System.out.println("#################impossible################");
//                        return -1;
//                    }
//                    else{
//
//                        int score = minimax(board, depth - 1, false);
//
//                        System.out.println(board);
//
//                        board.undoMove();
//
//                        maxScore = Math.max(maxScore, score);
//
//
//
//                    }
//                }
//            }
//            return maxScore;
//        }
//
//        else {
//            int minScore = Integer.MAX_VALUE;
//
//            for(Animal a2 :  board.getA2()){
//                board.getAllMovesa2();
//
//                for(Position position : a2.getLegalMoves){
//                    if(a2.execute(position.getX(), position.getY(), board.getA2(), board.getA1(), board.getC(), board) == false){
//                        System.out.println("#################impossible################");
//                        return -1;
//                    }
//                    else{
//
//                        int score = minimax(board, depth - 1, true);
//
//                        System.out.println(board);
//
//                        board.undoMove();
//
//                        minScore = Math.min(minScore, score);
//
//                    }
//
//
//                }
//            }
//            return minScore;
//        }
//
//    }
//
//
//
//
//
//
//
//    private int evaluate(ChessBoard board) {
//        // if the player 1 won; thats a dead loss
//        if(board.player1win){return Integer.MIN_VALUE;}
//
//        // if the player 2 won; that's an epic win
//        if(board.player2win){return Integer.MAX_VALUE;}
//
//        // if none of them won, we evaluate based on the pieces alive
//        return (evaluateP2pieces(board)- evaluateP1pieces(board));
//
//    }
//
//
//    private int evaluateP1pieces(ChessBoard board) {
//        int scoreP2 = 0;
//        for(Animal animal : board.getA2()){
//            if(!animal.getDead()){
//                scoreP2+=animal.getPieceValue();
//            }
//        }
//        return scoreP2;
//    }
//
//
//    private int evaluateP2pieces(ChessBoard board) {
//        int scoreP1 = 0;
//        for(Animal animal : board.getA1()){
//            if(!animal.getDead()){
//                scoreP1+=animal.getPieceValue();
//            }
//        }
//        return scoreP1;
//    }
//
//}
//
