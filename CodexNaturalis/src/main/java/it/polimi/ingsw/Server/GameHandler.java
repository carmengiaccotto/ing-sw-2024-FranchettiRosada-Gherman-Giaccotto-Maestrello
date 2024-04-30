package CodexNaturalis.src.main.java.it.polimi.ingsw.Server;

import CodexNaturalis.src.main.java.it.polimi.ingsw.View.VirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private List<Socket> players = new ArrayList<>();

    public GameHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run(){

    }


    public void handleConnection(Socket clientSocket, VirtualView virtualView) throws InterruptedException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int NumPlayers = virtualView.SelectMaxPlayers();
        while (NumPlayers > 4 || NumPlayers < 2)
            NumPlayers = virtualView.SelectMaxPlayers();
        players.add(clientSocket);
        while (players.size() < NumPlayers) {
            Thread.sleep(1000);
        }
        startGame();
    }
    private void startGame() {
        //Settare lo status del game a SetUp
        //gestire questioneNickname, dove lo facciamo?
        //gestireScelta Colore
        //Lanciare il controller, con lo status ancora a Setup così viene creato tutto
    }
    }
