//package com.ensah.game1.AI;
//
//import java.util.List;
//import game1.ChessBoard;
//
//public class ChessAINoUndo {
//    private static final int MAX_DEPTH = 3; // Maximum depth for the Minimax algorithm
//
//    public Move findBestMove(Board board) {
//        int bestScore = Integer.MIN_VALUE;
//        Move bestMove = null;
//
//        List<Move> legalMoves = board.generateLegalMoves();
//        for (Move move : legalMoves) {
//            board.makeMove(move);
//
//            int score = minimax(board, MAX_DEPTH, false);
//
//            board.undoLastMove(); // Assuming there's no explicit "undo move" method
//
//            if (score > bestScore) {
//                bestScore = score;
//                bestMove = move;
//            }
//        }
//
//        return bestMove;
//    }
//
//    private int minimax(Board board, int depth, boolean isMaximizingPlayer) {
//        if (depth == 0 || board.isGameOver()) {
//            return evaluate(board);
//        }
//
//        if (isMaximizingPlayer) {
//            int maxScore = Integer.MIN_VALUE;
//            List<Move> legalMoves = board.generateLegalMoves();
//
//            for (Move move : legalMoves) {
//                board.makeMove(move);
//
//                int score = minimax(board, depth - 1, false);
//
//                board.undoLastMove(); // Assuming there's no explicit "undo move" method
//
//                maxScore = Math.max(maxScore, score);
//            }
//
//            return maxScore;
//        } else {
//            int minScore = Integer.MAX_VALUE;
//            List<Move> legalMoves = board.generateLegalMoves();
//
//            for (Move move : legalMoves) {
//                board.makeMove(move);
//
//                int score = minimax(board, depth - 1, true);
//
//                board.undoLastMove(); // Assuming there's no explicit "undo move" method
//
//                minScore = Math.min(minScore, score);
//            }
//
//            return minScore;
//        }
//    }
//
//    private int evaluate(Board board) {
//        // Evaluation function to assign a score to the board position
//        // You can implement your own evaluation function here
//
//        // For simplicity, we return a random score between -100 and 100
//        return (int) (Math.random() * 201) - 100;
//    }
//}
