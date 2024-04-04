package CodexNaturalis.src.main.java.it.polimi.ingsw.model;

public enum Position {
    TOP{
        public int VerticalPositioning(int row){
            return row-1;
        }

    },
    BOTTOM{
        public int VerticalPositioning(int row){
            return row+1;
        }
    },
    LEFT{
        public int HorizontalPositioning(int Column){
            return Column-1;
        }
    },

    RIGHT{
        public int HorizontalPositioning(int Column){
            return Column+1;
        }
    },

    UP{
        public int VerticalPositioning(int row){
            return row-2;
        }
    },

    DOWN{
        public int VerticalPositioning(int row){
            return row+2;
        }
    }

}
/*For the Card, the constructor is going to need to be implemented similar to this:
* corners[0][0] = new Corner(Position.TOP, Position.LEFT);
        corners[0][1] = new Corner(Position.TOP, Position.RIGHT);
        corners[1][0] = new Corner(Position.BOTTOM, Position.LEFT);
        corners[1][1] = new Corner(Position.BOTTOM, Position.RIGHT);*/