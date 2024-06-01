package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Connection.RMI.RMIClient;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.View.TUI.TUI;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    private static String serverIp;

    private static UserInterface view;

    private ClientControllerInterface client;


//    private SocketClient socketClient;

    public static void main(String[] args) throws IOException, NotBoundException {
        new Client().start();
    }


    public void selectView() throws RemoteException {
        System.out.print("Select type of view: \n [1] TUI \n [2] GUI \n>>");
        Scanner scanner = new Scanner(System.in);
        int choice= scanner.nextInt();
        switch (choice){
            case 0:
                client.setView(new TUI());
                break;
            case 1:
                //view = new GUI();
                break;
            default:
                System.out.println("Invalid choice, please try again");
                selectView();
        }
    }

    public void selectProtocol() {
        System.out.print("Select network protocol: \n [1] RMI \n [2] SOCKET \n>>");
        Scanner scanner = new Scanner(System.in);
        int protocol = scanner.nextInt();
        switch (protocol) {
            case 1:
                try {
                    this.client = new RMIClient();

                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                //socketClient=new SocketClient();
                break;
            default:
                System.out.println("Invalid choice, please try again");
                selectProtocol();
        }
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

        selectProtocol();
        selectView();
        client.connect();
    }

}

