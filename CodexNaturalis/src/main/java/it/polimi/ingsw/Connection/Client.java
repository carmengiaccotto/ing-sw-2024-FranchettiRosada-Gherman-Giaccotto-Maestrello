package it.polimi.ingsw.Connection;

import it.polimi.ingsw.Connection.RMI.RMIClient;
import it.polimi.ingsw.Connection.Socket.Client.SocketClient;
import it.polimi.ingsw.Controller.Client.ClientController;
import it.polimi.ingsw.Controller.Client.ClientControllerInterface;
import it.polimi.ingsw.Exceptions.ProtocolSelectionException;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.TUI.TUI;
import it.polimi.ingsw.View.UserInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * This class represents the client in the client-server architecture.
 * It allows the client to select the type of view (CLI or GUI) and the network protocol (RMI or Socket).
 */
public class Client {
    private static String serverIp;
    //private String nickName;

    private static UserInterface view;

    private ClientControllerInterface client;
    private ClientController clientController;


//    private SocketClient socketClient;

    /**
     * The main method to start the client.
     * @param args command line arguments
     * @throws IOException if an I/O error occurs
     * @throws NotBoundException if the registry does not contain a binding for the name
     */
    public static void main(String[] args) throws IOException, NotBoundException {
        new Client().start();
    }

    /**
     * Method that allows the client to choose which type of view they want to use when playing.
     * The client can choose between Command Line Interface (CLI) and Graphic User Interface (GUI).
     * @throws RemoteException if the remote operation fails
     */
    public void selectView() throws RemoteException {
        System.out.print("Select type of view: \n 1. Command Line Interface \n 2. Graphic User Interface \n>>");
        Scanner scanner = new Scanner(System.in);
        int choice= 0;
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // discard the non-integer input
        }
        choice = scanner.nextInt();
        while (choice != 1 && choice!= 2) {
            System.out.println("Invalid choice, please try again.");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // discard the non-integer input
            }
        }

        switch (choice){
            case 1:
                client.setView(new TUI());
                break;
            case 2:
                view = new GUI();
                break;

        }
    }

    /**
     * Method that allows the client to choose which type of protocol they want to use.
     * The client can choose between RMI and Socket.
     */
    public void selectProtocol() {
        System.out.print("Select network protocol: \n 1. RMI \n 2. SOCKET \n>>");
        Scanner scanner = new Scanner(System.in);
        int protocol = 0;
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter  1 or 2.");
            scanner.next(); // discard the non-integer input
        }
        protocol = scanner.nextInt();
        while (protocol != 1 && protocol != 2) {
            System.out.println("Invalid choice, please try again.");
            if (scanner.hasNextInt()) {
                protocol = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.next(); // discard the non-integer input
            }
        }
        switch (protocol) {
            case 1:
                try {
                    this.client = new RMIClient();
                    ((RMIClient) client).setController(clientController);
                } catch (RemoteException e) {
                    throw new ProtocolSelectionException("Error during RMI client creation", e);
                }
                break;
            case 2:
                this.client = new SocketClient();
                ((SocketClient) client).setController(clientController);
                break;
        }
    }

    /**
     * This method starts the client by asking for the server IP address, selecting the protocol and view, and connecting to the server.
     * @throws IOException if an I/O error occurs
     * @throws NotBoundException if the registry does not contain a binding for the name
     */
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

