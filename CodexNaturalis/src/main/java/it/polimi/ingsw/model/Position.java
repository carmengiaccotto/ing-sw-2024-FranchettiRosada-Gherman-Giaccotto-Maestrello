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
