package com.ensah.game1.bll;

public class Position {
    private int x;
    private int y;

    public final static Position Up= new Position (0,1);
    public final static Position Down= new Position (0,-1);
    public final static Position Left= new Position (-1,0);
    public final static Position Right= new Position (1,0);

    public Position(int x,int y){
        this.x=x;
        this.y=y;

    }
    public void setX(int x){
            this.x=x;
            

    }
    public void setY(int y){
    
            this.y=y;
            
        

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    
    public int getX(){return x;}
    public int getY(){return y;}
    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }

    
}

