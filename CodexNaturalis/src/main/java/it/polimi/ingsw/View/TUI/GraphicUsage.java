package CodexNaturalis.src.main.java.it.polimi.ingsw.View.TUI;


import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Symbol;

import java.util.Map;

public class GraphicUsage {

    private Map<Symbol, String> symbolDictionary;

    public void symbolDictionary(){

        symbolDictionary.put(Symbol.PLANT, "\\uD83C\\uDF3F");
        symbolDictionary.put(Symbol.ANIMAL, "\\uD83D\\uDC3A");
        symbolDictionary.put(Symbol.FUNGI, "\\uD83C\\uDF44");
        symbolDictionary.put(Symbol.INSECT, "\\uD83E\\uDD8B");
        symbolDictionary.put(Symbol.QUILL, "\\uD83E\\uDDB6");
        symbolDictionary.put(Symbol.INKWELL, "\\uD83D\\uDD8B\\uFE0F");
        symbolDictionary.put(Symbol.MANUSCRIPT, "\\uD83D\\uDCDC");

    }

}
