package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection;

import CodexNaturalis.src.main.java.it.polimi.ingsw.View.UserInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;
import CodexNaturalis.src.main.java.it.polimi.ingsw.View.TUI.TextualUI;

import java.util.Scanner;

public class Client {
    private static String serverIp;

    private static UserInterface view;

    private Player player;

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player p){
        player = p;
    }

    public static void main(String[] args){
        new Client().start();
    }
    public void start() {
        int InterfaceType = 0;
        System.out.println("Insert server ip address:");
        Scanner scan = new Scanner(System.in);
        try {
            serverIp = scan.nextLine();
        } catch (Exception e) {
            serverIp = "localhost";
        }
        while (InterfaceType != 1 && InterfaceType != 2) {
            System.out.println("Select InterfaceType: \n 1. TextUserInterface \n 2. GraphicUserInterface");
            try {
                InterfaceType = scan.nextInt();
            } catch (Exception e) {
                InterfaceType = 0;
            }
        }
        switch (InterfaceType) {
            case 1:
                view = new TextualUI();
                break;
            case 2:
                // Inizializza la GUI
                break;
        }

        System.out.print("Select network protocol: \n 1. RMI \n 2. SOCKET");

        while (true) {
            System.out.print("\n>>  ");
            Integer netProtocol = scan.nextInt();

            if (netProtocol.equals(1)) {
                //runRMI(ip);
            } else if (netProtocol.equals(2)) {
                //runSocket(ip);
            }
        }
    }
}

