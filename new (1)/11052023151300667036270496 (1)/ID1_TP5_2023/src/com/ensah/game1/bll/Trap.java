package com.ensah.game1.bll;

public class Trap extends Cell {
    public Trap(int player,Position p){
        super(p,player);
        this.player=player;
        this.p=p;
    }

    public String toString(){return "\u001B[35m#\u001B[0m";}

}
