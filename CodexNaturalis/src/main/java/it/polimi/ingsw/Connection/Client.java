package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Connection.RMI.RMIClient;
import it.polimi.ingsw.Connection.RMI.RMIServer;
import it.polimi.ingsw.View.UserInterface;
import it.polimi.ingsw.Model.PlayGround.Player;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class Client {
    private static String serverIp;

    private static UserInterface view;

    private RMIClient clientRMI;

//    private SocketClient socketClient;

    private Player player;

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player p){
        player = p;
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        new Client().start();
    }
    public void start() throws IOException, NotBoundException {
        int InterfaceType = 0;
        System.out.println("Insert server ip address:");
        Scanner scan = new Scanner(System.in);
        try {
            serverIp = scan.nextLine();
        } catch (Exception e) {
            serverIp = "localhost";
        }

        System.out.print("Select network protocol: \n 1. RMI \n 2. SOCKET");

        while (true) {
            System.out.print("\n>>  ");
            Integer netProtocol = scan.nextInt();

            if (netProtocol.equals(1)) {
                clientRMI = new RMIClient(new RMIServer());
                clientRMI.connect();


                break;
            } else if (netProtocol.equals(2)) {
//                socketClient=new SocketClient();

            }
        }
    }
}

