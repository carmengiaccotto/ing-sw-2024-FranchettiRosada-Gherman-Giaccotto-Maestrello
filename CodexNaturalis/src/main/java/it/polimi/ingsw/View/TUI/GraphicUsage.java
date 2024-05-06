package CodexNaturalis.src.main.java.it.polimi.ingsw.View.TUI;


import CodexNaturalis.src.main.java.it.polimi.ingsw.model.CardColors;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;

import java.util.HashMap;
import java.util.Map;

public class GraphicUsage {

    public static Map<Symbol, String> symbolDictionary=new HashMap<>();
    public static final Map<CardColors, int[]> ColorDictionary = new HashMap<>();

    static {
        symbolDictionary.put(Symbol.PLANT, "\uD83C\uDF3F");
        symbolDictionary.put(Symbol.ANIMAL, "\\uD83D\\uDC3A");
        symbolDictionary.put(Symbol.FUNGI, "\uD83C\uDF44");
        symbolDictionary.put(Symbol.INSECT, "\\uD83E\\uDD8B");
        symbolDictionary.put(Symbol.QUILL, "\\uD83E\\uDDB6");
        symbolDictionary.put(Symbol.INKWELL, "\\uD83D\\uDD8B\\uFE0F");
        symbolDictionary.put(Symbol.MANUSCRIPT, "\\uD83D\\uDCDC");
    }
//    public static String getEmojiFromUnicode(String unicode) {
//        return new String(Character.toChars(Integer.parseInt(unicode, 16)));
//    }




   static {
       ColorDictionary.put(CardColors.RED,new int[]{255, 93, 93});
       ColorDictionary.put(CardColors.GREEN,new int[]{97, 214, 74});
       ColorDictionary.put(CardColors.BLUE,new int[]{84, 11, 226});
       ColorDictionary.put(CardColors.PURPLE,new int[]{174, 89, 221});
   }
    public static int[] getRGBColor(CardColors color) {
        return ColorDictionary.getOrDefault(color, new int[]{0, 0, 0});     }

}
