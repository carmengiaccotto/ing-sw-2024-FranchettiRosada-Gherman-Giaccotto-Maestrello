package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Connection.RMI.RMIClient;
import it.polimi.ingsw.View.TUI.TextualUI;
import it.polimi.ingsw.View.UserInterface;
import it.polimi.ingsw.model.Exceptions.MaxNumPlayersException;
import it.polimi.ingsw.model.Exceptions.NotReadyToRunException;
import it.polimi.ingsw.model.PlayGround.Player;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class Client {
    private static String serverIp;

    private static UserInterface view;

    private RMIClient clientRMI;

    private Player player;

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player p){
        player = p;
    }

    public static void main(String[] args) throws IOException, MaxNumPlayersException, NotBoundException, NotReadyToRunException {
        new Client().start();
    }
    public void start() throws IOException, MaxNumPlayersException, NotBoundException, NotReadyToRunException {
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
                clientRMI = new RMIClient();
                clientRMI.connect();
                view.userLogin();
                break;
            } else if (netProtocol.equals(2)) {
                //runSocket(ip);
            }
        }
    }
}

