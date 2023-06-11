package com.ensah.game1.bll;

public abstract class Cell {
    protected Position p;//stocke la position de la cellule

    protected  int player;

    public Cell(Position p, int player) {
        this.p = p;
        this.player = player;
    }
    public void setPosition(Position p){
        this.p=p;
    }
    public Position getPosition(){
        return p;
    }
    public int getPlayer(){return player;}
    public abstract String toString();//une methode qui definit comment l'output doit etre afficher

}
