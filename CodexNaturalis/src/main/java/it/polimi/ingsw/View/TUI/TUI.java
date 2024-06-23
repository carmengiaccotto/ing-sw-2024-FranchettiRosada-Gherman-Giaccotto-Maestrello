package it.polimi.ingsw.View.TUI;

import it.polimi.ingsw.Model.Cards.*;
import it.polimi.ingsw.Model.Chat.Message;
import it.polimi.ingsw.Model.Chat.PrivateMessage;
import it.polimi.ingsw.Model.Enumerations.Command;
import it.polimi.ingsw.Model.Enumerations.PawnColor;
import it.polimi.ingsw.Model.Pair;
import it.polimi.ingsw.Model.PlayGround.PlayArea;
import it.polimi.ingsw.Model.PlayGround.PlayGround;
import it.polimi.ingsw.Model.PlayGround.Player;
import it.polimi.ingsw.View.TUI.TUIUtilis.DesignSupportClass;
import it.polimi.ingsw.View.TUI.TUIUtilis.GraphicUsage;
import it.polimi.ingsw.View.TUI.TUIUtilis.TUIComponents;
import it.polimi.ingsw.View.UserInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class TUI implements UserInterface, Serializable {
    private final ReentrantLock lock = new ReentrantLock();
    Scanner scanner = new Scanner(System.in);
    private static final String bold = "\033[1m";
    private static final String reset = "\033[0m";
    private static final String ANSIreset = "\u001B[0m";


    /**
     * Prompts the user to choose where to draw a card from.
     * The user can choose to draw from the gold deck, resource deck, or one of the four available cards on the playground.
     * The method will keep asking until a valid option is chosen.
     *
     * @return A string representing the chosen deck or card to draw from.
     */
    @Override
    public String chooseCardToDraw() {
        String draw;
        do {
            System.out.println("Do you want to draw a card from a deck or from the Board?\n [1]Board \n [2]Deck \n >>");
            draw = scanner.next().toUpperCase();
            if (draw.equals("1")) {
                System.out.println("Which card would you like to draw?\n [1]Resource Card1 \n [2]Resource Card2 \n [3]Gold Card1 \n [4]Gold Card2 \n >>");
                draw = scanner.next().toUpperCase();
                if (draw.equals("1"))
                    draw = "RESOURCE-CARD1";
                if (draw.equals("2"))
                    draw = "RESOURCE-CARD2";
                if (draw.equals("3"))
                    draw = "GOLD-CARD1";
                if (draw.equals("4"))
                    draw = "GOLD-CARD2";
            } else {
                System.out.println("Which deck would you like to draw from?\n [1]Resource Deck \n [2]Gold Deck \n >>");
                draw = scanner.next().toUpperCase();
                if (draw.equals("1"))
                    draw = "RESOURCE-DECK";
                if (draw.equals("2"))
                    draw = "GOLD-DECK";
            }
            if (!draw.equals("GOLD-DECK") && !draw.equals("RESOURCE-DECK") && !draw.equals("RESOURCE-CARD1") && !draw.equals("RESOURCE-CARD2") && !draw.equals("GOLD-CARD1") && !draw.equals("GOLD-CARD2")) {
                System.out.println("Invalid option! Please choose a valid option!");
            }
        } while (!draw.equals("GOLD-DECK") && !draw.equals("RESOURCE-DECK") && !draw.equals("RESOURCE-CARD1") && !draw.equals("RESOURCE-CARD2") && !draw.equals("GOLD-CARD1") && !draw.equals("GOLD-CARD2"));
        return draw;
    }

    @Override
    public int chooseCardToPlay(ArrayList<PlayCard> cardInHand) {
        System.out.println("Which card do you want to play? [1/2/3]");
        int c = Integer.parseInt(scanner.next());
        while ((c != 1) && (c != 2) && (c != 3)) {
            System.out.println("Choose a valid card! [1/2/3]");
            c = Integer.parseInt(scanner.next());
        }
        return c;
    }

    public void showInitialCard(InitialCard card) {// todo modify using string builder
        System.out.println("This is your initial card: ");
        String[][] frontBack = new String[11][70];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 70; j++) {
                frontBack[i][j] = " ";
            }
        }
        frontBack[0][10] = "FRONT";
        frontBack[0][40] = "BACK";
        DesignSupportClass.printCard(frontBack, card.getFront(), 0, 0, 10, 34);
        DesignSupportClass.printCard(frontBack, card.getBack(), 0, 36, 10, 34);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 70; j++) {
                System.out.print(frontBack[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public String chooseSide() {
        System.out.println("Which side of the card do you want to play? [FRONT/BACK]");
        String side = scanner.next().toUpperCase();
        while (!side.equals("FRONT") && !side.equals("BACK")) {
            System.out.println("Please insert a valid side: [FRONT/BACK]");
            side = scanner.next().toUpperCase();
        }
        return side;
    }

    @Override
    public Pair<Integer, Integer> choosePositionCardOnArea(PlayArea playArea) {
        System.out.println("Choose the row and column in which you want to place the card: [row] [column]");
        int row = scanner.nextInt();
        int column = scanner.nextInt();
        Pair<Integer, Integer> position = new Pair<>(row, column);
        return position;
    }


    /**
     * Prompts the user to choose a nickname.
     *
     * @return nickname               A string representing the chosen nickname.
     */
    @Override
    public String selectNickName() {
        System.out.println("Insert your Nickname: ");
        return scanner.next();
    }

    /**
     * Prompts the user to either create a new game or join an existing one.
     * The user can choose to create a new game by entering 0 or join an existing game by entering 1.
     *
     * @return An integer representing the user's choice. 0 for creating a new game, 1 for joining an existing game.
     */
    @Override
    public int createOrJoin() {
        System.out.println("Do you want to create a new game or join an existing one? ");
        System.out.println(" 1. Create new Game ");
        System.out.println(" 2. Join existing one ");
        System.out.println(">>");
        return scanner.nextInt();

    }

    /**
     * Prompts the user to select the maximum number of players for the match.
     * The user can choose a number between 2 and 4.
     *
     * @return An integer representing the maximum number of players chosen by the user.
     */
    @Override
    public int MaxNumPlayers() {
        System.out.println("How Many Players do you want to have for this match? Please select a Number between 2 and 4!");
        return scanner.nextInt();
    }

    /**
     * Displays the available games that the user can join.
     * For each game, it shows the game number, the number of players needed to start the match, and the nicknames of the players that are already in.
     * It also provides an option for the user to create a new game instead of joining an existing one.
     *
     * @param games An ArrayList of GameController objects representing the available games.
     * @return An integer representing the user's choice. The index of the chosen game in the list, or 0 to create a new game.
     * @throws RemoteException If a communication-related exception occurs during the execution of a remote method call.
     */
    @Override
    public int displayavailableGames(Map<Integer, ArrayList<String>> games, ArrayList<Pair<Integer, Integer>> numPlayers) throws RemoteException {
        System.out.println("Please choose one of the following games");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < games.size(); i++) {
            s.append("   ").append("\n" + bold + bold + "  LOBBY " + reset).append(bold).append(i + 1).append(reset).append("\n");
            s.append("─────────────────").append("\n");
            s.append("needed: ").append(numPlayers.get(i).getSecond());
            if (games.get(i) != null) {
                for (String name : games.get(i)) {
                    s.append("\n").append("      ").append(bold).append("• ").append(name).append(reset);
                }
            } else
                s.append("\nNo players in this lobby");
            s.append("\n\n");

        }
        System.out.println(s);
        System.out.println("If you don't want to join any of the available games and you want to create a new one, please insert 0 (zero)");
        return (scanner.nextInt());

    }


    /**
     * Prompts the user to choose a color from the available colors.
     * The user can choose a color by entering the corresponding number.
     *
     * @param availableColors An ArrayList of PawnColor objects representing the available colors.
     * @return An integer representing the index of the chosen color in the list.
     */
    @Override
    public int displayAvailableColors(List<PawnColor> availableColors) {
        System.out.println("Choose one of the following colors: ");
        for (int i = 0; i < availableColors.size(); i++) {
            System.out.println(GraphicUsage.pawnColorDictionary.get(availableColors.get(i)) + " [" + (i + 1) + "]" + availableColors.get(i).toString() + ANSIreset);
        }
        return (scanner.nextInt());
    }

    /**
     * Notifies the user that the system is waiting for other players to join the game.
     */
    @Override
    public void waitingForPlayers() {
        System.out.println("Waiting for other players to Join...");
    }


    /**
     * Prints a given message to the console.
     *
     * @param message The string message to be printed.
     */
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }


    /**
     * Prompts the user to select a command from the available options.
     * The user can choose either the "MOVE" or "CHAT" command.
     * The method will keep asking until a valid command is chosen.
     *
     * @return A Command enum representing the chosen command by the user.
     */

    @Override
    public Command receiveCommand(Boolean IsMyTurn) {
        if(IsMyTurn){
            System.out.println("Choose a command: [MOVE/CHAT]");
            while (!scanner.hasNext()) {} // Wait for the next input
            String command = scanner.next().toUpperCase();
            while (!command.equals("MOVE") && !command.equals("CHAT")) {
                System.out.println("Invalid command! Please choose a valid command: [MOVE/CHAT]");
                while (!scanner.hasNext()) {} // Wait for the next input
                command = scanner.next().toUpperCase();
            }
            if (command.equals("MOVE")) {
                return Command.MOVE;
            } else {
                return Command.CHAT;
            }

        }
        else{
            System.out.println("Please write CHAT if you want to send a message");
            while (!scanner.hasNext()) {} // Wait for the next input
            String command = scanner.next().toUpperCase();
            while (!command.equals("CHAT")) {
                System.out.println("Invalid command! Please choose a valid command: [CHAT]");
                while (!scanner.hasNext()) {} // Wait for the next input
                command = scanner.next().toUpperCase();
            }
            return Command.CHAT;
        }
    }



    @Override
    public void printBoard(PlayGround model, ArrayList<Player> opponents, Player me, ArrayList<Message> myChat) {
        String ANSI_CYAN = "\u001B[36m";
        StringBuilder opponentsInfo = new StringBuilder();
        for(Player p: opponents){
            if(p!=me)
                opponentsInfo.append(TUIComponents.showOpponent(p)).append("\n");
        }
        String opp= TUIComponents.createBoxWithSpace( opponentsInfo.toString(), "\033[30m", 0);
        ArrayList<String> chatMessages = viewChat(myChat, me);
        String Chat= TUIComponents.formatStringList( chatMessages);
        String chatBox= TUIComponents.createBoxWithSpace(Chat, ANSI_CYAN, 0);
        String finalOutput = TUIComponents.concatString(opp, chatBox, 30);
        System.out.println(finalOutput);
        System.out.println();
        System.out.println(TUIComponents.showCommonCards(model));
        System.out.println();
        System.out.println(TUIComponents.showMySelf(me));
    }




    /**
     * Prompts the user to choose a personal Objective Card from a list of available objective cards.
     * The user can choose an objective card by entering the corresponding number.
     *
     * @param               objectives An ArrayList of ObjectiveCard objects representing the available objective cards.
     * @return              An integer representing the index of the chosen objective card in the list.
     */
    @Override
    public int choosePersonaObjectiveCard(ArrayList<ObjectiveCard> objectives) {
        String s="";
        String[][] matrix= new String[10][70];
        System.out.println("Please, choose your personal Objective Card!");
            for (int i = 0; i < objectives.size(); i++) {
                s=TUIComponents.concatString(s,TUIComponents.printObjectives(objectives.get(i)), 4);
            }
            System.out.println(TUIComponents.concatString("[1]","[2]", 40));
            System.out.println(s);

        int choice;
        do {
            choice = scanner.nextInt();
            if (choice != 1 && choice != 2) {
                System.out.println("Invalid option! Please choose 1 or 2.");
            }
        } while (choice != 1 && choice != 2);
        return choice - 1;
    }




    /**
     *
     * Method that returns an array of strings representing the messages the player can see in the chatBox.
     *
     * @param player            that is visualizing the chat.
     * @param myChat            messages in this player's chat
     *
     * @return messages         String representation of the chat messages
     *
     * */
    @Override
    public ArrayList<String> viewChat(ArrayList<Message> myChat, Player player) {
        String bold = "\033[1m";
        String reset = "\033[0m";
        ArrayList<String> messages = new ArrayList<>();// creating a new arraylist of strings
        for (Message m : myChat) { // for each message in the chat
            String sender; //initialize sender
            String receiver = "ALL";//initialize receiver. If the message is not a private message, the receiver is everyone
            if (m.getSender().getNickname().equals(player.getNickname())) { //if i'm the sender
                sender = "YOU";
            } else {
                sender = m.getSender().getNickname(); //else the sender is the nickname of the sender
            }
            if (m instanceof PrivateMessage) { //if the message is a private message
                receiver = ((PrivateMessage) m).getReceiver(); //retrieve the receiver
                if (receiver.equals(player.getNickname())) { //if player is the receiver
                    receiver = "YOU"; //receiver is you
                }
            }
            String message = bold + "[" + sender + "] to [" + receiver + "]:" + reset + m.getText();
            messages.add(message);
        }
        Collections.reverse(messages); //we want to print the messages in order from the most recent ones to the oldest ones,
                                        // so we reverse the order of the arraylist

        // Limit the size of the list to 20
        if (messages.size() > 20) {
            return new ArrayList<>(messages.subList(0, 20));
        } else {
            return messages;
        }
    }

    @Override
    public void showString(String s){
        if(s == "WIN"){
            printMessage(GraphicUsage.you_win);
        }else if(s == "GAME_OVER"){
            printMessage(GraphicUsage.game_over);
        }else if(s == "CODEX_NATURALIS"){
            printMessage(GraphicUsage.codex_naturalis_string);
        }
    }


    @Override
    public Pair<String, String> sendChatMessage(ArrayList<Player> opponents) {
            ArrayList<String> players = new ArrayList<>();
            for (Player p : opponents) {
                players.add(p.getNickname());
            }
            String receiver="";
            String text="";
            System.out.println("Do you want to send a message to everyone? [Y/N]");
            String choice = scanner.next().toUpperCase();
            scanner.nextLine(); // Aggiungi questa linea per consumare il resto della riga
            if(choice.equals("Y")){
                receiver="everyone";
            }else{
                do{
                    String message="Insert the nickname of the receiver: ";
                    for (String p : players) {
                        message+="["+p+"]";
                    }
                    System.out.println(message);
                    receiver=scanner.nextLine();
                    if(!players.contains(receiver)){
                    System.out.println("There is no player with such nickname in the game! Please insert a valid nickname.");
                }
                    }while (!players.contains(receiver));
            }
            System.out.println("Insert the text of the message: ");
            text=scanner.nextLine();
            Pair<String, String> message = new Pair<>(receiver, text);
            return message;
    }
}







