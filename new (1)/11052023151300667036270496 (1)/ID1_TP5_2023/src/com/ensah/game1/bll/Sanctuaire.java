package com.ensah.game1.bll;

public class Sanctuaire extends Cell {
    public Sanctuaire(Position p,int player){
        super(p,player);
        this.p=p;
        this.player=player;
    }
    public String toString(){
        return "\u001B[33mS\u001B[0m";
    }
    @Override
    public int getPlayer() {
        // TODO Auto-generated method stub
        return super.getPlayer();
    }
    @Override
    public Position getPosition() {
        // TODO Auto-generated method stub
        return super.getPosition();
    }
    
}
