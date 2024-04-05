package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public enum Position {
    TOP(0){

    },
    BOTTOM(1){
    },
    LEFT(0){
    },

    RIGHT(1){
    },

    UP(2){
    },

    DOWN(2){
    };
    private final int index;
    Position(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static int HorizontalPositioning(int Column, Position LeftRight){
        if(LeftRight==RIGHT)
            return Column+1;
        else if(LeftRight==LEFT)
            return Column-1;
        else
            return Column;
    }

    public static  int VerticalPositioning(int row, Position UpDown){
        if(UpDown==TOP)
            return row-1;
        else if (UpDown==BOTTOM)
            return row+1;
        else if (UpDown==UP)
            return row-2;
        else if (UpDown==DOWN)
            return row+2;
        else
            return row;


    }


}
/*For the Card, the constructor is going to need to be implemented similar to this:
* corners[0][0] = new Corner(Position.TOP, Position.LEFT);
        corners[0][1] = new Corner(Position.TOP, Position.RIGHT);
        corners[1][0] = new Corner(Position.BOTTOM, Position.LEFT);
        corners[1][1] = new Corner(Position.BOTTOM, Position.RIGHT);*/