package com.ensah.game1.bll;

import java.util.ArrayList;

public abstract class Animal {
    public ArrayList <Position> getLegalMoves = new ArrayList<>();
    int player;
    protected Position p;
    protected String name;
    protected final int pieceValue;
    protected boolean isDead;
    protected int lastKilledEnemy;

    public Animal(int player,Position p,String name, int pieceValue){
        this.player=player;
        this.p=p;
        this.name=name;
        this.pieceValue=pieceValue;
    }
    public void setDead(boolean isDead){
        this.isDead=isDead;
    }

    public boolean getDead(){
        return this.isDead;
    }

    public void setPosition(Position p){
        this.p=p;
    }
    public Position getPosition(){return p;}

    public String getName(){return this.name;}

    public abstract boolean execute(int x, int y, Animal a1[], Animal a2[], Cell[][] cell,ChessBoard board);
    //La méthode retourne un booléen qui indique si le mouvement de l'animal est valide ou non.
    //les deux entiers "x" et "y", qui représentent les coordonnées de la position de destination vers laquelle l'animal va se déplacer
    //un tableau d'objets game1.Animal "a1", qui représente les animaux du joueur 1
    //un tableau d'objets game1.Animal "a2", qui représente les animaux du joueur 2
    //une matrice d'objets game1.Cell "cell", qui représente l'état actuel du plateau de jeu
    public int getPieceValue() {
        return pieceValue;
    }
    
    public int getPlayer() {
        return player;
    }
    
    /** methode that updates and adds all the legal moves possible for the animal */
    public abstract void addLegalMove( Animal a1[], Animal a2[], Cell[][] cell, ChessBoard board);

    public void checkIfLastPieceEaten(ChessBoard board){
        if(this.player==1){
            for(Animal a: board.getA2()){
                if(a.isDead==false){
                    return;
                }
            }
            board.setPlayer1Win(true);
        }else{
            for(Animal a: board.getA2()){
                if(a.isDead==false){
                    return;
                }
            }
            board.setPlayer2Win(true);
        }
    }
}
