package it.polimi.ingsw.View.TUI.TUIUtilis;


import it.polimi.ingsw.Model.Enumerations.CardColors;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Enumerations.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides utility methods and constants for graphical usage in the game.
 * It includes dictionaries for mapping game symbols and colors to their string and RGB representations,
 * as well as ANSI color codes for different pawn colors.
 * It also includes string representations for game over and win messages, and the game title.
 */
public class GraphicUsage {

    /**
     * A dictionary mapping game symbols to their string representations.
     */
    public static Map<Symbol, String> symbolDictionary=new HashMap<>();

    /**
     * A dictionary mapping card colors to their RGB representations.
     */
    public static final Map<CardColors, int[]> ColorDictionary = new HashMap<>();

    /**
     * A dictionary mapping pawn colors to their ANSI color codes.
     */
    public static final Map<PawnColor, String> pawnColorDictionary = new HashMap<>();

    /**
     * A dictionary mapping card colors to their string representations.
     */
    public static final Map<CardColors, String> cardColorDictionary = new HashMap<>();

    // ANSI color codes
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String legenda="   I=INSECT             P=PLANT              M=MANUSCRIPT                A=ANIMAL              K=INKWELL              F=FUNGI               Q=QUILL\n" +
            "____\\\\.//____           . /\\                  -------@)                //\\\\___//\\\\              ./)                   _____                   /\n" +
            "\\\\___\\|/___//          / / |                /      //                 (   O   O  )             ///                   /*****\\              .__/__.  \n" +
            " //___|___\\\\           \\/_/                /      //                   <  (o)   >             \\/-                   *-------*             |______|\n" +
            "                       /                 (@--------                        ┴                 .                         |_|\n";


    // Game over and win messages, and game title
    public static final String game_over = ANSI_RED +
            "╔══════════════════════════════════════════════╗\n" +
            "║  _______      ___      .___  ___.  _______   ║\n" +
            "║ /  _____|    /   \\     |   \\/   | |   ____|  ║\n" +
            "║|  |  __     /  ^  \\    |  \\  /  | |  |__     ║\n" +
            "║|  | |_ |   /  /_\\  \\   |  |\\/|  | |   __|    ║\n" +
            "║|  |__| |  /  _____  \\  |  |  |  | |  |____   ║\n" +
            "║ \\______| /__/     \\__\\ |__|  |__| |_______|  ║\n" +
            "║                                              ║\n" +
            "║  ______   ____    ____  _______ .______      ║\n" +
            "║ /  __  \\  \\   \\  /   / |   ____||   _  \\     ║\n" +
            "║|  |  |  |  \\   \\/   /  |  |__   |  |_)  |    ║\n" +
            "║|  |  |  |   \\      /   |   __|  |      /     ║\n" +
            "║|  `--'  |    \\    /    |  |____ |  |\\  \\----.║\n" +
            "║ \\______/      \\__/     |_______|| _| `._____|║\n" +
            "╚══════════════════════════════════════════════╝" + ANSI_RESET;
    public static final String you_win = ANSI_YELLOW +
            "╔═══════════════════════════════════════════════════════════════════════════╗\n" +
            "║____    ____  ______    __    __     ____    __    ____  __  .__   __.  __ ║\n" +
            "║\\   \\  /   / /  __  \\  |  |  |  |    \\   \\  /  \\  /   / |  | |  \\ |  | |  |║\n" +
            "║ \\   \\/   / |  |  |  | |  |  |  |     \\   \\/    \\/   /  |  | |   \\|  | |  |║\n" +
            "║  \\_    _/  |  |  |  | |  |  |  |      \\            /   |  | |  . `  | |  |║\n" +
            "║    |  |    |  `--'  | |  `--'  |       \\    /\\    /    |  | |  |\\   | |__|║\n" +
            "║    |__|     \\______/   \\______/         \\__/  \\__/     |__| |__| \\__| (__)║\n" +
            "╚═══════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET;

    public static final String codex_naturalis_string = ANSI_PURPLE +
            "╔══════════════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                    "║  ______   ______    _______   __________   ___                                                   ║\n" +
                    "║ /      | /  __  \\  |       \\ |   ____\\  \\ /  /                                                   ║\n" +
                    "║|  ,----'|  |  |  | |  .--.  ||  |__   \\  V  /                                                    ║\n" +
                    "║|  |     |  |  |  | |  |  |  ||   __|   >   <                                                     ║\n" +
                    "║|  `----.|  `--'  | |  '--'  ||  |____ /  .  \\                                                    ║\n" +
                    "║ \\______| \\______/  |_______/ |_______/__/ \\__\\                                                   ║\n" +
                    "║                                                                                                  ║\n" +
                    "║.__   __.      ___   .___________. __    __  .______          ___       __       __       _______.║\n" +
                    "║|  \\ |  |     /   \\  |           ||  |  |  | |   _  \\        /   \\     |  |     |  |     /       |║\n" +
                    "║|   \\|  |    /  ^  \\ `---|  |----`|  |  |  | |  |_)  |      /  ^  \\    |  |     |  |    |   (----`║\n" +
                    "║|  . `  |   /  /_\\  \\    |  |     |  |  |  | |      /      /  /_\\  \\   |  |     |  |     \\   \\    ║\n" +
                    "║|  |\\   |  /  _____  \\   |  |     |  `--'  | |  |\\  \\----./  _____  \\  |  `----.|  | .----)   |   ║\n" +
                    "║|__| \\__| /__/     \\__\\  |__|      \\______/  | _| `._____/__/     \\__\\ |_______||__| |_______/    ║\n" +
                    "╚══════════════════════════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET;

    static {
        // Initialize symbolDictionary
        symbolDictionary.put(Symbol.PLANT, "P");
        symbolDictionary.put(Symbol.ANIMAL, "A");
        symbolDictionary.put(Symbol.FUNGI, "F");
        symbolDictionary.put(Symbol.INSECT, "I");
        symbolDictionary.put(Symbol.QUILL, "Q");
        symbolDictionary.put(Symbol.INKWELL, "K");
        symbolDictionary.put(Symbol.MANUSCRIPT, "M");
    }

    static{
        // Initialize cardColorDictionary
        cardColorDictionary.put(CardColors.RED,"F"); //F=Fungi
        cardColorDictionary.put(CardColors.GREEN,"P");//P=Plant
        cardColorDictionary.put(CardColors.BLUE,"A");//A=Animal
        cardColorDictionary.put(CardColors.PURPLE,"I");//I=Insect
    }

   static {
       // Initialize ColorDictionary
       ColorDictionary.put(CardColors.RED,new int[]{255, 93, 93});
       ColorDictionary.put(CardColors.GREEN,new int[]{97, 214, 74});
       ColorDictionary.put(CardColors.BLUE,new int[]{84, 11, 226});
       ColorDictionary.put(CardColors.PURPLE,new int[]{174, 89, 221});
   }

    /**
     * Returns the RGB color representation for a given card color.
     * If the color is not found in the dictionary, it returns black (0, 0, 0).
     *
     * @param color The card color to get the RGB representation for.
     * @return An array of three integers representing the RGB color.
     */
    public static int[] getRGBColor(CardColors color) {
        return ColorDictionary.getOrDefault(color, new int[]{0, 0, 0});

    }

    static {
        // Initialize pawnColorDictionary
        pawnColorDictionary.put(PawnColor.RED,"\u001B[31m");
        pawnColorDictionary.put(PawnColor.GREEN,"\u001B[32m");
        pawnColorDictionary.put(PawnColor.BLUE,"\u001B[34m");
        pawnColorDictionary.put(PawnColor.YELLOW,"\u001B[33m");
    }

}
