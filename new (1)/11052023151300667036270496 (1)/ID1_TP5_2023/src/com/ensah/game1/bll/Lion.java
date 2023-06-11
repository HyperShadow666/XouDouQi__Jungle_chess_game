package com.ensah.game1.bll;



public class Lion extends Animal {

    
    private final static Position [] CANDIDATE_MOVE_POSITIONS = {Position.Up, Position.Down, Position.Left, Position.Right };

    /*-----Methods-----*/
    // game1.Lion is a contructor which accepts 2 parameters, player and game1.Position
    public Lion(int player, Position p) {
        super(player, p, "6 = Lion",800);
        this.player = player;
        this.p = p;
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
                        

                        board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                        enemy.setDead(true);
                        getPosition().setX(x);
                        getPosition().setY(y);
                        checkIfLastPieceEaten(board);
                        
                        return true;

                    }
                }

                

                // If the next step position is enemy chess which bigger than
                // self, then output "Invalid move!"
                if (enemy instanceof Elephant) {
                    lastKilledEnemy = -1;
                    return false;
                } else {

                    // If the next step position is river, then output
                    // "Invalid move!"
                    if (c instanceof Riviere) {
                        return false;
                    }
                
                    else {

                        // save the last position
                        

                        board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
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
                        getPosition().setX(x);
                        getPosition().setY(y);
                        board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                        if(this.player==1){
                            board.setPlayer1Win(true);
                        }
                        else{
                            board.setPlayer2Win(true);

                        }
                        // save the last position
                        

                        lastKilledEnemy = -1;

                        
                        return true;

                    }
                    
                } else {

                    // save the last position
                    

                    board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                    // If the next step position is blank cell, then go to next
                    // step position and refresh the new position
                    getPosition().setX(x);
                    getPosition().setY(y);
                    lastKilledEnemy = -1;

                        
                    return true;
                }
            }
        } else {
            // maybe he wants to jump, let's see if that's available
            for( int j= 2; j<7; j=j+4){

                if((getPosition().getY()==1 && getPosition().getX()==j) 
                || (getPosition().getY()==2 && getPosition().getX()==j)
                || (getPosition().getY()==4 && getPosition().getX()==j)
                || (getPosition().getY()==5 && getPosition().getX()==j)        
                ){
                    if((x==getPosition().getX()-4 && y==getPosition().getY()) 
                    || (x==getPosition().getX()+4 && y==getPosition().getY())
                    ){

                    // he DOES want to jump vertically 

                    // let's see if that is possible
                    return canJumpVertically(x, y , getPosition().getX(), a1, a2, cell, board);
                    }
                    
                }
            }
            for(int i = 0; i<7; i=i+3){
                for(int j = 3; j<6; j++){
                    if(getPosition().getY()== i && getPosition().getX()== j){
                        if(y==getPosition().getX() && x==getPosition().getY()+3 
                        ||(y==getPosition().getX() && x==getPosition().getY()-3)
                        ){
                            //he DOES want to jump horizentally 

                            //let us check if that's acceptable
                            return canJumphorizentally(x, y , i, a1, a2, cell, board);
                        }
                    }
                }
            }

            return false;
        }
    }

    /***special for the lion and tiger piece; checks if the piece can jump based on the existance of the mouse in the river and which animal exist after the river (the positions for jumping were already checked) */
    private boolean canJumpVertically(int x, int y, int y2, Animal[] a1, Animal[] a2, Cell[][] cell, ChessBoard board) {

        //check if the mouses exist in the middle of the animals path
        for(int verticalStep= Math.min(x,y2); verticalStep<Math.max(x, y2);verticalStep++){
            if((a1[0].getPosition().getY()== y && a1[0].getPosition().getX()==verticalStep)){
                return false;
            }
            if(a2[0].getPosition().getY()== y && a2[0].getPosition().getX()==verticalStep){
                return false;
            }
        }

        // Check the next step position which there is self chess.
        // Otherwise, output "Invalid move!"
        for (int index = 0; index < a1.length; index++) {
            if (a1[index].getPosition().getX() == x
                    && a1[index].getPosition().getY() == y
                    && a1[index].getDead() == false) {
                return false;
            }
        }

        
        Animal enemy = null;

        // Check the next step position which there is enemy chess
        for (int index = 0; index < a2.length; index++) {
            if (a2[index].getPosition().getX() == x
                    && a2[index].getPosition().getY() == y
                    && a2[index].getDead() == false) {
                enemy = a2[index];
                lastKilledEnemy = index;
                break;
            }
        }


        // If the next step position is enemy chess, it will...
        if (enemy != null) {

            

            // If the next step position is enemy chess which bigger than
            // self, then output "Invalid move!"
            if (enemy instanceof Elephant) {
                lastKilledEnemy = -1;
                return false;
            } else {

                // save the last position
                

                board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                // If the next step position which is enemy chess and
                // can eat it, then eat it and refresh the new position
                enemy.setDead(true);
                getPosition().setX(x);
                getPosition().setY(y);
                checkIfLastPieceEaten(board);
                        
                    return true;
                }
        }
        else{

            // save the last position
            

            board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
            getPosition().setX(x);
            getPosition().setY(y);
            lastKilledEnemy = -1;
                        
            return true;
        }
    }

    /***special for the lion and tiger piece; checks if the piece can jump based on the existance of the mouse in the river and which animal exist after the river (the positions for jumping were already checked) */
    private boolean canJumphorizentally(int x, int y, int i, Animal[] a1, Animal[] a2, Cell[][] cell, ChessBoard board) {


        //check if the mouses exist in the middle of the animals path
        for(int horizentalStep= Math.min(x,i); horizentalStep<Math.max(x, i);horizentalStep++){
            if((a1[0].getPosition().getY()== horizentalStep && a1[0].getPosition().getX()==x)){
                return false;
            }
            if(a2[0].getPosition().getY()== horizentalStep && a2[0].getPosition().getX()==x){
                return false;
            }
        }

        // Check the next step position which there is self chess.
        // Otherwise, output "Invalid move!"
        for (int index = 0; index < a1.length; index++) {
            if (a1[index].getPosition().getX() == x
                    && a1[index].getPosition().getY() == y
                    && a1[index].getDead() == false) {
                return false;
            }
        }

        
        Animal enemy = null;

        // Check the next step position which there is enemy chess
        for (int index = 0; index < a2.length; index++) {
            if (a2[index].getPosition().getX() == x
                    && a2[index].getPosition().getY() == y
                    && a2[index].getDead() == false) {
                enemy = a2[index];
                lastKilledEnemy = index;
                break;
            }
        }


        // If the next step position is enemy chess, it will...
        if (enemy != null) {

            // If the next step position is enemy chess which bigger than
            // self, then output "Invalid move!"
            if (enemy instanceof Elephant) {
                lastKilledEnemy = -1;
                return false;
            } else {

                // save the last position
                

                board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});
                // If the next step position which is enemy chess and
                // can eat it, then eat it and refresh the new position
                enemy.setDead(true);
                getPosition().setX(x);
                getPosition().setY(y);
                checkIfLastPieceEaten(board);
                        
                    return true;
                }
        }
        else{

            // save the last position
            board.boardMoves.push(new int[]{this.player,6, this.getPosition().getX(), this.getPosition().getY(),lastKilledEnemy});

            getPosition().setX(x);
            getPosition().setY(y);
                        
            return true;
        }

    }



    // toString is a method which returns "L" and player number
    public String toString() {
        return player==1? "\u001B[31mL\u001B[0m":"\u001B[32mL\u001B[0m";
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

                if(c instanceof Trap){

                    //check if the enemy is steping on your trap
                    if(enemy.getPlayer() != c.getPlayer()){

                        addMove= true;
                        continue;

                    }
                }

                // If the next step position is enemy chess which bigger than
                // self, then output "Invalid move!"
                if (enemy instanceof Elephant) {
                    addMove=false;
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
                    boolean deriereRiviere= false;

                    while(!deriereRiviere){

                        if(a1[0].getPosition().equals(currentCandidat) && a1[0].isDead==false){
                            addMove=false;
                            break;
                        }
                        
                        if(a2[0].getPosition().equals(currentCandidat) && a2[0].isDead==false){
                            addMove=false;
                            break;
                        }
                        
                        
                        currentCandidat.setX(currentCandidate.getX()+ currentCandidat.getX());
                        currentCandidat.setY(currentCandidate.getY()+ currentCandidat.getY());
                        
                        
                        if(!(cell[currentCandidat.getX()][currentCandidat.getY()] instanceof Riviere)){deriereRiviere=true;} 
                    }

                    //checking if an ally exist behind the river
                    for (int i = 0; i < a1.length; i++) {
                        if (a1[i].getPosition().getX() == currentCandidat.getX()
                                && a1[i].getPosition().getY() == currentCandidat.getY()
                                && a1[i].getDead() == false) {
                            addMove=false;
                            break;
                        }
                    }

                    Animal enemyRiver=null;

                    //checking which enemy exist after the river
                    for (int i = 0; i < a2.length; i++) {
                        if (a2[i].getPosition().getX() == currentCandidat.getX()
                                && a2[i].getPosition().getY() == currentCandidat.getY()
                                && a2[i].getDead() == false) {
                            enemyRiver = a2[i];
                            break;
                        }
                    }

                    if (enemyRiver != null) {

                        // If the next step position is enemy chess which bigger than
                        // self, then output "Invalid move!"
                        if (enemyRiver instanceof Elephant) {
                            addMove=false;
                        }
                    }    

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