package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements ClientMoves {

    // Add draw method, place card

    GameInterface server;

    GameControllerInterface gameController = null;

    private Registry registry;

    private String nickname;

    public RMIClient(GameInterface server, Registry registry) throws RemoteException {
        this.server = server;
        this.registry = registry;
    }

    @Override
    public void connect() throws RemoteException {
            boolean retry = false;
            int attempt = 1;
            int i;

            do {
                try {
                    registry = LocateRegistry.getRegistry("127.0.0.1", 4321);
                    gameController = (GameControllerInterface) registry.lookup("CodexNaturalis");

                    System.out.println("Client RMI ready");
                    retry = false;

                } catch (Exception e) {
                    if (!retry) {
                        System.out.println("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                    }
                    System.out.println("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + 4321 + "' with name: '" + "CodexNaturalis" + "'");

                    i = 0;
                    while (i < 5) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.out.println(".");
                        i++;
                    }
                    System.out.println("\n");

                    if (attempt >= 5) {
                        System.out.println("Give up!");
                        try {
                            System.in.read();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        System.exit(-1);
                    }
                    retry = true;
                    attempt++;
                }
            } while (retry);
    }

    @Override
    public void sendMessage(Message m) throws RemoteException {
        gameController.sentMessage(m);
    }

    @Override
    public void leave(String nickname) throws RemoteException {
        registry = LocateRegistry.getRegistry("127.0.0.1", 4321);
        try {
            server = (GameInterface) registry.lookup("CodexNaturalis");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        server.disconnectPlayer(nickname);

        gameController = null;
        nickname = null;
    }

    @Override
    public void disconnect(String nickname) throws RemoteException {
        registry = LocateRegistry.getRegistry("127.0.0.1", 4321);
        try {
            server = (GameInterface) registry.lookup("CodexNaturalis");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        server.disconnectPlayer(nickname);
    }
}
