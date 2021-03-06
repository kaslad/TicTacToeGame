package logic;

import logic.Player;

public class Game {
    private Player[] gameTable = new Player[9];

    public Player[] getGameTable() {
        return gameTable;
    }

    public void setGameTable(Player[] gameTable) {
        this.gameTable = gameTable;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    private char firstLetter = 'X';
    public Game() {
        for(int i = 0; i < gameTable.length; i++){
            gameTable[i] = null;
        }
    }
    private Player currentPlayer;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean tableFilledUp() {
        for (Player player : gameTable) {
            if (player == null) {
                return false;
            }
        }
        return true;

    }

    public boolean checkEnd() {
        return
                ((gameTable[0] != null && gameTable[0] == gameTable[4] && gameTable[0] == gameTable[8])
                        || (gameTable[2] != null && gameTable[2] == gameTable[4] && gameTable[2] == gameTable[6])
                        || gameTable[0] != null && gameTable[0] == gameTable[1] && gameTable[0] == gameTable[2])
                        || (gameTable[3] != null && gameTable[3] == gameTable[4] && gameTable[3] == gameTable[5])
                        || (gameTable[6] != null && gameTable[6] == gameTable[7] && gameTable[6] == gameTable[8])
                        || (gameTable[0] != null && gameTable[0] == gameTable[3] && gameTable[0] == gameTable[6])
                        || (gameTable[1] != null && gameTable[1] == gameTable[4] && gameTable[1] == gameTable[7])
                        || (gameTable[2] != null && gameTable[2] == gameTable[5] && gameTable[2] == gameTable[8])
                ;
    }

    public synchronized boolean legalMove(int where, Player player) {
        if (player == currentPlayer && gameTable[where] == null) {
            gameTable[where] = currentPlayer;
            currentPlayer = currentPlayer.getOpponent();
            currentPlayer.otherPlayerMoved(where);
            return true;
        }
        return false;
    }

}
