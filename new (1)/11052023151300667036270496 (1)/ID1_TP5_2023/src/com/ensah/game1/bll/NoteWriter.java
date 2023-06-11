package com.ensah.game1.bll;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NoteWriter {
    private ChessBoard board;
    private int player;
    private int row;
    private int column;
    /* this class serves toi write the moves, it takes the board as a parameter as well as the details about the move */
    public NoteWriter(ChessBoard board, int player, int row, int column) {
        this.board = board;
        this.player = player;
        this.row = row;
        this.column = column;
    }

    public void writeToFile(String filepath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
            String d1 = String.valueOf(player);
            String d2 = String.valueOf(row);
            String d3 = String.valueOf(column);
            writer.write(d1+d2+d3+".");
            if((d2+d3).equals("03") || ((d2+d3).equals("83"))){
                writer.write("\n");
            }
            writer.close();
            System.out.println("Text written to file successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
  
