package com.ensah.game1.bll;

public class Riviere extends Cell {
    public Riviere(Position p , int player){
        super(p,player);
        this.player=player;
        this.p=p;

    }
    public String toString(){
        return "\u001B[34mR\u001B[0m";
    }

}
