package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection;

import CodexNaturalis.src.main.java.it.polimi.ingsw.model.PlayGround.Player;

import java.util.Scanner;

public class Client {
    private static String serverIp;

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
    public void start(){
        int InterfaceType=0;
        System.out.println("Insert server ip address:");
        Scanner scan = new Scanner(System.in);
        try {
            serverIp = scan.nextLine();
        }
        catch(Exception e){
            serverIp="localhost";
        }
        while(InterfaceType!=1 && InterfaceType!=2){
            System.out.println("Select InterfaceType: \n 1. TextUserInterface \n 2. GraphicUserInterface");
            try {
                InterfaceType = scan.nextInt();
            }
            catch(Exception e){
                InterfaceType=0;
            }
        }
        switch (InterfaceType) {
            case 1:
                // Inizializza la GUI
                break;
            case 2:
                // Inizializza la GUI
                break;
        }

    }

    public ClientConnectionHandler StartConnection (int type){ // tipo della connessione. Viene restituita dalla View
        ClientConnectionHandler clientConnection=null;

        switch(type)
        {
            case 1:
                //avvio RMIServer
                //creazione nuovo RMIClient

                break;
            case 2:
                //Avvio nuovo Socket Server
                //creazione nuovo SocketClient



        }


        // aggiunta gestione thread(?)
        return clientConnection;


    }







}
