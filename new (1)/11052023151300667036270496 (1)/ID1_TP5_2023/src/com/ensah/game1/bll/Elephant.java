package com.ensah.game1.bll;

//game1.Elephant is a subclass of game1.Animal
public class Elephant extends Animal {


    private final static Position [] CANDIDATE_MOVE_POSITIONS = {Position.Up, Position.Down, Position.Left, Position.Right };

    /*-----Methods-----*/
    // game1.Elephant is a contructor which accepts 2 parameters, player and game1.Position
    public Elephant(int player, Position p) {
        super(player, p, "7 = Elephant",900);
        this.player = player;
        this.p = p;
        lastKilledEnemy = -1;
    }

    // execute is a method which checks the validity of the move and performs
    // necessary actions
    public boolean execute(int x, int y, Animal a1[], Animal a2[], Cell[][] cell,ChessBoard board) {

        // Check the surround position(up, down, left, right)
        if ((getPosition().getX() == x + 1 && getPosition().getY() == y)
                || (getPosition().getX() == x - 1 && getPosition().getY() == y)
                || (getPosition().getX() == x && getPosition().getY() == y + 1)
                || (getPosition().getX() == x && getPosition().getY() == y - 1)) {

            // Check the next step position which there is self chess.
            // Otherwise, output "Invalid move!"
            for (int i = 0; i < a1.length; i++) {
                if (a1[i].getPosition().getX() == x
                        && a1[i].getPosition().getY() == y
                        && a1[i].getDead() == false) {
                    return false;
                }
            }

            Animal enemy = null;

            // Check the next step position which there is enemy chess
            for (int i = 0; i < a2.length; i++) {
                if (a2[i].getPosition().getX() == x
                        && a2[i].getPosition().getY() == y
                        && a2[i].getDead() == false) {
                    enemy = a2[i];
                    lastKilledEnemy= i;
                    break;
                }
            }

            Cell c = cell[x][y];

            // If the next step position is enemy chess, it will...
            if (enemy != null) {

                if(c instanceof Trap){

                    //check if the enemy is steping on your trap
                    if(enemy.getPlayer() != c.getPlayer()){

                        
                        board.boardMoves.push(new int[]{this.player,7, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});

                        enemy.setDead(true);
                        getPosition().setX(x);
                        getPosition().setY(y);
                        checkIfLastPieceEaten(board);
                        
                        return true;

                    }
                }

                // If the next step position is enemy mouse, then output
                // "Invalid move!"
                if (enemy instanceof Mouse) {
                    lastKilledEnemy = -1;
                    return false;
                } else {

                    // If the next step position is river, then output
                    // "Invalid move!"
                    if (c instanceof Riviere) {
                        return false;
                    } else {

                        

                        board.boardMoves.push(new int[]{this.player,7, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                        // If the next step position which is enemy chess and
                        // can eat it, then eat it and refresh the new position
                        enemy.setDead(true);
                        getPosition().setX(x);
                        getPosition().setY(y);
                        checkIfLastPieceEaten(board);
                        
                        return true;
                    }
                }
            } else {

                // If the next step position is river, then output
                // "Invalid move!"
                if (c instanceof Riviere) {
                    return false;

                }
                if(c instanceof Sanctuaire){

                    if (c.getPlayer()==this.player){
                        return false;
                    }
                    else{
                        board.boardMoves.push(new int[]{this.player,7, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                        getPosition().setX(x);
                        getPosition().setY(y);
                        if(this.player==1){
                            board.setPlayer1Win(true);
                        }
                        else{
                            board.setPlayer2Win(true);

                        }

                        
                        lastKilledEnemy = -1;

                        
                        return true;

                    }
                } else {

                    
                    
                    board.boardMoves.push(new int[]{this.player,7, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                    // If the next step position is blank cell, then go to next
                    // step position and refresh the new position
                    getPosition().setX(x);
                    getPosition().setY(y);
                    lastKilledEnemy = -1;

                        
                    return true;
                }
            }
        } else {

            // If the next position is unknown step, , then output
            // "Invalid move!" anyway.
            return false;
        }
    }

    // toString is a method which returns "E" and player number
    public String toString() {
        return player==1? "\u001B[31mE\u001B[0m":"\u001B[32mE\u001B[0m";
    }

    @Override
    public void addLegalMove( Animal[] a1, Animal[] a2, Cell[][] cell, ChessBoard b) {
        if(getLegalMoves.size()!=0){
            getLegalMoves.removeAll(getLegalMoves);}


        for (Position currentCandidate : CANDIDATE_MOVE_POSITIONS){

            // note: currentCandidate != currentCandidat haha
            // currentCandidate: UP, DOWN, LEFT, RIGHT
            //currentCandidat: UP + this.position
        Position currentCandidat = new Position(0, 0);
        
        currentCandidat.setX(currentCandidate.getX()+this.getPosition().getX());
        currentCandidat.setY(currentCandidate.getY()+this.getPosition().getY());
        
        if(currentCandidat.getY()>=7 || currentCandidat.getY()<0 || currentCandidat.getX()>=9 || currentCandidat.getX()<0){
            continue;}
            
            boolean addMove= true;
            
            // Check the next step position which there is self chess.
            // Otherwise, output "Invalid move!"
        for (int i = 0; i < a1.length; i++) {
            if (a1[i].getPosition().getX() == currentCandidat.getX()
                    && a1[i].getPosition().getY() == currentCandidat.getY()
                    && a1[i].getDead() == false) {
                addMove=false;
                break;
            }
        }

        if(addMove== true){
            
            Animal enemy = null;

            // Check the next step position which there is enemy chess
            for (int i = 0; i < a2.length; i++) {
                if (a2[i].getPosition().getX() == currentCandidat.getX()
                        && a2[i].getPosition().getY() == currentCandidat.getY()
                        && a2[i].getDead() == false) {
                    enemy = a2[i];
                    break;
                }
            }

            Cell c = cell[currentCandidat.getX()][currentCandidat.getY()];

            // If the next step position is enemy chess, it will...
            if (enemy != null) {

                if(c instanceof Trap){

                    //check if the enemy is steping on your trap
                    if(enemy.getPlayer() != c.getPlayer()){

                        addMove= true;
                        continue;

                    }
                }

                // If the next step position is enemy chess which bigger than
                // self, then output "Invalid move!"
                if (enemy instanceof Mouse) {
                    addMove = false; 
                    continue;
                
                } else {

                    // If the next step position is game1.Riviere, then output
                    // "Invalid move!"
                    

                    if (c instanceof Riviere) {
                        //can't catch a rat in the lake
                        addMove=false;
                        continue;
                        
                    } else {

                        // If the next step position which is enemy chess and
                        // can eat it, then eat it and refresh the new position
                        
                        
                        addMove= true;

                    }
                }
            } else {

                // If the next step position is game1.Riviere, then output
                // "Invalid move!"
                if (c instanceof Riviere) {
                    addMove= false;
                    continue;
                } else {

                    // If the next step position is blank cell, then go to next
                    // step position and refresh the new position
                    
                    
                    addMove=true;
                }


            }
        }

            if(addMove==true){getLegalMoves.add(new Position(currentCandidat.getX(), currentCandidat.getY()));}
        }


    }

}