package com.ensah.game1.bll;

//game1.Mouse is a subclass of game1.Animal
public class Mouse extends Animal {

    

    private final static Position [] CANDIDATE_MOVE_POSITIONS = {Position.Up, Position.Down, Position.Left, Position.Right };

    /*-----Methods-----*/
    // game1.Mouse is a contructor which accepts 2 parameters, player and game1.Position
    public Mouse(int player, Position p) {
        super(player, p, "0 = Mouse",100);
        lastKilledEnemy = -1;
    }

    // execute is a method which checks the validity of the move and performs
    // necessary actions and returnsa boolean value
    public boolean execute(int x, int y, Animal[] a1, Animal[] a2, Cell[][] cell, ChessBoard board) {

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

                        // save the last position
                        
                        board.boardMoves.push(new int[]{this.player,0, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});

                        enemy.setDead(true);
                        getPosition().setX(x);
                        getPosition().setY(y);
                        checkIfLastPieceEaten(board);
                        
                        return true;

                    }
                }

                // If the next step position is enemy chess which bigger than
                // self, then output "Invalid move!"
                if (enemy instanceof Lion) {
                    lastKilledEnemy = -1;
                    return false;
                } else if (enemy instanceof Tiger) {
                    lastKilledEnemy = -1;
                    return false;
                } else if (enemy instanceof Leopard) {
                    lastKilledEnemy = -1;
                    return false;
                } else if (enemy instanceof Dog) {
                    lastKilledEnemy = -1;
                    return false;
                } else if (enemy instanceof Wolf) {
                    lastKilledEnemy = -1;
                    return false;
                } else if (enemy instanceof Cat) {
                    lastKilledEnemy = -1;
                    return false;
                } else {

                    // If the next step position is river, then output
                    // "Invalid move!"
                    if (c instanceof Riviere) {
                        return false;
                    } else {

                        // save the last position
                        
                        board.boardMoves.push(new int[]{this.player,0, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});

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
                // in the mouse case, mouse can step on the river
                /*
                if (c instanceof Riviere) {
                    return false;
                */
                

                    // If the next step position is blank cell, then go to next
                    // step position and refresh the new position
                }
                if(c instanceof Sanctuaire){

                    if (c.getPlayer()==this.player){
                        return false;
                    }
                    else{
                        getPosition().setX(x);
                        getPosition().setY(y);
                        if(this.player==1){
                            board.setPlayer1Win(true);
                        }
                        else{
                            board.setPlayer2Win(true);

                        }
                        // save the last position
                        board.boardMoves.push(new int[]{this.player,0, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                        lastKilledEnemy = -1;

                        
                        return true;

                    }
                }else{
                // save the last position
                
                board.boardMoves.push(new int[]{this.player,0, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
            
                getPosition().setX(x);
                getPosition().setY(y);
                lastKilledEnemy = -1;
                
                        
                return true;
                
            }
        } else {

            // If the next position is unknown step, , then output
            // "Invalid move!" anyway.
            return false;
        }
    }

    // toString is a method which returns "M" and player number
    public String toString() {
        return player==1? "\u001B[31mM\u001B[0m":"\u001B[32mM\u001B[0m";
    }

    @Override
    public void addLegalMove( Animal[] a1, Animal[] a2, Cell[][] cell, ChessBoard b) {
        if(getLegalMoves.size()!=0){
            getLegalMoves.removeAll(getLegalMoves);}


        for (Position currentCandidate : CANDIDATE_MOVE_POSITIONS){

        Position currentCandidat = new Position(0, 0);

        currentCandidat.setX(currentCandidate.getX()+this.getPosition().getX());
        currentCandidat.setY(currentCandidate.getY()+this.getPosition().getY());

        if(currentCandidat.getY()>=7 || currentCandidat.getY()<0 || currentCandidat.getX()>=9 || currentCandidat.getY()<0)
            continue;
            // Check the next step position which there is self chess.
            // Otherwise, output "Invalid move!"
            boolean isAlly= false;
            for (int i = 0; i < a1.length; i++) {
                if (a1[i].getPosition().getX() == currentCandidat.getX()
                        && a1[i].getPosition().getY() == currentCandidat.getY()
                        && a1[i].getDead() == false) {
                            isAlly= true;
                            break;
                }
            }
            if(!isAlly){

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
    
                            getLegalMoves.add(new Position(currentCandidat.getX(), currentCandidat.getY()));
                            continue;
                        }
                    }

                    // If the next step position is enemy chess which bigger than
                    // self, then output "Invalid move!"
                    if (enemy instanceof Lion) {
                        continue;
                    } else if (enemy instanceof Tiger) {
                        continue;
                    } else if (enemy instanceof Leopard) {
                        continue;
                    } else if (enemy instanceof Dog) {
                        continue;
                    } else if (enemy instanceof Wolf) {
                        continue;
                    } else if (enemy instanceof Cat) {
                        continue;
                    
                    } else {

                        // If the next step position is game1.Riviere, then output
                        // "Invalid move!"
                        if (c instanceof Riviere) {
                            continue;
                        } else {

                            // If the next step position which is enemy chess and
                            // can eat it, then eat it and refresh the new position
                            
                            
                            getLegalMoves.add(new Position(currentCandidat.getX(), currentCandidat.getY()));

                        }
                    }
                } else {

                    // If the next step position is game1.Riviere, then output
                    // "Invalid move!"
                    if (c instanceof Riviere) {
                        continue;
                    } else {

                        // If the next step position is blank cell, then go to next
                        // step position and refresh the new position
                        
                        getLegalMoves.add(new Position(currentCandidat.getX(), currentCandidat.getY()));
                    }
                }
            }


        }
    }

}