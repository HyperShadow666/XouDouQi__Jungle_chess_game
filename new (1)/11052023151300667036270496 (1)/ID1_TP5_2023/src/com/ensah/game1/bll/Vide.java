package com.ensah.game1.bll;

//cette classe permet d'identifier les cases vide qui contient player et position mais ne cotient aucune piece
public class Vide extends Cell {
    public Vide(Position p,int player){
        super(p,player);
        this.p=p;
        this.player=player;
    }
    public String toString(){return "_ ";}


}
