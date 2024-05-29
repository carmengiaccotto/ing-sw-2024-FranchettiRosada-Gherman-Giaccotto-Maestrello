package it.polimi.ingsw.View.TUI;


import it.polimi.ingsw.Model.CardColors;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class GraphicUsage {

    public static Map<Symbol, String> symbolDictionary=new HashMap<>();
    public static final Map<CardColors, int[]> ColorDictionary = new HashMap<>();
    public static final Map<PawnColor, String> pawnColorDictionary = new HashMap<>();
    public static final Map<CardColors, String> cardColorDictionary = new HashMap<>();

    static {
        symbolDictionary.put(Symbol.PLANT, "P");
        symbolDictionary.put(Symbol.ANIMAL, "A");
        symbolDictionary.put(Symbol.FUNGI, "F");
        symbolDictionary.put(Symbol.INSECT, "I");
        symbolDictionary.put(Symbol.QUILL, "Q");
        symbolDictionary.put(Symbol.INKWELL, "K");
        symbolDictionary.put(Symbol.MANUSCRIPT, "M");
    }

    static{
        cardColorDictionary.put(CardColors.RED,"F"); //F=Fungi
        cardColorDictionary.put(CardColors.GREEN,"P");//P=Plant
        cardColorDictionary.put(CardColors.BLUE,"A");//A=Animal
        cardColorDictionary.put(CardColors.PURPLE,"I");//I=Insect
    }





   static {
       ColorDictionary.put(CardColors.RED,new int[]{255, 93, 93});
       ColorDictionary.put(CardColors.GREEN,new int[]{97, 214, 74});
       ColorDictionary.put(CardColors.BLUE,new int[]{84, 11, 226});
       ColorDictionary.put(CardColors.PURPLE,new int[]{174, 89, 221});
   }
    public static int[] getRGBColor(CardColors color) {
        return ColorDictionary.getOrDefault(color, new int[]{0, 0, 0});

    }

    static {
        pawnColorDictionary.put(PawnColor.RED,"\u001B[31m");
        pawnColorDictionary.put(PawnColor.GREEN,"\u001B[32m");
        pawnColorDictionary.put(PawnColor.BLUE,"\u001B[34m");
        pawnColorDictionary.put(PawnColor.YELLOW,"\u001B[33m");
    }

}
