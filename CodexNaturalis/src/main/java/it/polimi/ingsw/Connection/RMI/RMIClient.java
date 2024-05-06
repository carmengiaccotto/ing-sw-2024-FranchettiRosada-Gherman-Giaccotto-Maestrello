package CodexNaturalis.src.main.java.it.polimi.ingsw.Connection.RMI;

import CodexNaturalis.src.main.java.it.polimi.ingsw.controller.GameControllerInterface;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Chat.Message;

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

    public RMIClient() throws RemoteException {
    }

    @Override
    public void connect() throws RemoteException {
        boolean connected = false;
        int attempt = 1;

        while (!connected && attempt <= 4) {
            try {
                Thread connectionThread = new Thread(() -> {
                    try {
                        registry = LocateRegistry.getRegistry("127.0.0.1", 6321);
                        server = (GameInterface) registry.lookup("CodexNaturalis");
                        System.out.println("Client ready");
                    } catch (Exception e) {
                        System.out.println("[ERROR] Connecting to server: \n\tClient exception: " + e + "\n");
                    }
                });
                connectionThread.start();
                connectionThread.join(1000);

                if (server != null) {
                    connected = true;
                } else {
                    System.out.println("[#" + attempt + "]Waiting to reconnect to Server on port: '" + 6321 + "' with name: '" + "CodexNaturalis" + "'");
                    attempt++;
                    if (attempt <= 4) {
                        System.out.println("Retrying...");
                        Thread.sleep(1000);
                    } else {
                        System.out.println("Give up!");
                        System.exit(-1);
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void sendMessage(Message m) throws RemoteException {
        gameController.sentMessage(m);
    }

    @Override
    public void leave(String nickname) throws RemoteException {
        registry = LocateRegistry.getRegistry("127.0.0.1", 6321);
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
        registry = LocateRegistry.getRegistry("127.0.0.1", 6321);
        try {
            server = (GameInterface) registry.lookup("CodexNaturalis");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        server.disconnectPlayer(nickname);
    }
}
