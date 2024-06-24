package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Connection.RMI.RMIClient;
import it.polimi.ingsw.Connection.Socket.Client.SocketClient;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.View.TUI.TUI;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    private static String serverIp;
    //private String nickName;

    private static UserInterface view;

    private ClientControllerInterface client;
    private ClientController clientController;


//    private SocketClient socketClient;

    public static void main(String[] args) throws IOException, NotBoundException {
        new Client().start();
    }



    /**
     * Method that allows the client to choose which type of view they want to use when playing
     * */
    public void selectView() throws RemoteException {
        System.out.print("Select type of view: \n 1. Command Line Interface \n 2. Graphic User Interface \n>>");
        Scanner scanner = new Scanner(System.in);
        int choice= scanner.nextInt();
        switch (choice){
            case 1:
                client.setView(new TUI());
                break;
            case 2:
                //view = new GUI();
                break;
            default:
                System.out.println("Invalid choice, please try again");
                selectView();
        }
    }



    /**
     * Method that allows the client to choose which type of protocol they want to use
     * */
    public void selectProtocol() {
        System.out.print("Select network protocol: \n 1. RMI \n 2. SOCKET \n>>");
        Scanner scanner = new Scanner(System.in);
        int protocol = scanner.nextInt();
        switch (protocol) {
            case 1:
                try {
                    this.client = new RMIClient();
                    ((RMIClient) client).setController(clientController);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                this.client=new SocketClient();
                ((SocketClient) client).setController(clientController);
                break;
            default:
                System.out.println("Invalid choice, please try again");
                selectProtocol();
        }
    }
    public void start() throws IOException, NotBoundException {
        clientController = new ClientController();
        System.out.println("Insert server ip address:");
        Scanner scan = new Scanner(System.in);
        try {
            serverIp = scan.nextLine();
        } catch (Exception e) {
            serverIp = "localhost";
        }
        selectProtocol();
        selectView();
        this.client.connect(serverIp);

    }

}

