package CodexNaturalis.src.main.java.it.polimi.ingsw.controller;

import CodexNaturalis.src.main.java.it.polimi.ingsw.ClientPack.PlayerTurnStatus;
import CodexNaturalis.src.main.java.it.polimi.ingsw.model.Enumerations.GameStatus;

import java.net.Socket;
import java.util.ArrayList;

public class Game {
    public ArrayList<Socket> players;
    public GameStatus status;

    public Game(ArrayList<Socket> players){
        this.players=players;
        this.status=GameStatus.SET_UP;
    }

    public void ClientGameActions(GameStatus status){
        GameController gameController=new GameController();
        switch(status){
            case SET_UP:
                //Valutare possibilità di chiamare una classe Lobby apposta per questa gestione
                //Inserisce il nickname che vuole
                //Richiede il Nickname fintanto che CheckUniqueNickname restituisce false
                //Sceglie il pawnColor tra quelli available
                break;
            case RUNNING:
                //chiama PlayRound con altri suoi messaggi
                //Incrementa il numero di round del Player
                //Gestione turnistica
                //Aggiorna la PlayGround e la mostra a tutti i giocatori
                break;
            case LAST_TURN:
                //fa giocare un ultimo turno ai giocatori
                //Chiama il conteggio  ounti objectiveCard finale
                break;
            case ENDED:
                //dichiara il giocatore vincente
                //schermata finale

        }

    }

    public void PlayerRound(PlayerTurnStatus action){
        switch (action){
            case CHOOSE_CARD_TO_PLAY:
                //invoca metodo per scegliere la carta
                break;

            case CHOOSE_SIDE_CARD:
                //Invoca metodo per scelta SideOfCard
                break;
            case CHOOSE_POSITION_ON_PLAYAREA:
                //Invoca ISValidMove() nel controller
                //Se ritorna true, invoca add card On Area, altrimenti la richiede
                break;
            case DRAW_NEW_CARD:
                //Metodo che chiede Deck o Playground?
                //if Deck-> chiedi quale deck-> DrawFromDeck
                //if PlayGround-> chiedi quale carta->DrawNewCard->sostituisci carta
                //manda un messaggio RoundFinished, che fa partire la gestione della turnistica
        }
    }



}
