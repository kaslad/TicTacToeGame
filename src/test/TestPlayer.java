package test;

import helpers.Command;
import logic.Game;
import logic.Player;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class TestPlayer {
    @Test
    public void TestOtherPlayerMoved() throws IOException {
        Game game = new Game();
        Socket socket = new Socket("localhost", 8901);
        Player currentPlayer = new Player(socket, 'X', game);
        Socket socket1 = new Socket("localhost", 8901);
        int where  = 5;
        Player[] pl = new Player[9];
        for (int i = 0; i < 9; i++) {
            pl[i] = null;
        }
        game.setCurrentPlayer(currentPlayer);
        pl[2] = currentPlayer;
        currentPlayer = new Player(socket, 'X', game);
        pl[4] = currentPlayer;
        currentPlayer = new Player(socket, 'X', game);
        pl[6] = currentPlayer;
        Player opponentPlayer = new Player(socket1, 'O', game);
        opponentPlayer.setOpponent(currentPlayer);
        currentPlayer.setOpponent(opponentPlayer);
        game.getGameTable()[where] = currentPlayer;
        //System.out.println(currentPlayer.getOpponent().getLetter());
        opponentPlayer.otherPlayerMoved(where);
    }
    @Test
    public void TestgetCommantStartFromOpponent() throws IOException {
        Game game = new Game();
        Socket socket = new Socket("localhost", 8901);
        Player currentPlayer = new Player(socket, 'X', game);
        Socket socket1 = new Socket("localhost", 8901);
        int where = 5;
        Player[] pl = new Player[9];
        for (int i = 0; i < 9; i++) {
            pl[i] = null;
        }
        game.setCurrentPlayer(currentPlayer);
        pl[2] = currentPlayer;
        currentPlayer = new Player(socket, 'X', game);
        pl[4] = currentPlayer;
        currentPlayer = new Player(socket, 'X', game);
        pl[6] = currentPlayer;
        Player opponentPlayer = new Player(socket1, 'O', game);
        opponentPlayer.setOpponent(currentPlayer);
        currentPlayer.setOpponent(opponentPlayer);
        game.getGameTable()[where] = currentPlayer;
        //System.out.println(currentPlayer.getOpponent().getLetter());
        Assert.assertEquals(currentPlayer.getInputStream().readLine(), Command.START + Command.DIVIDER + 'O');
        //System.out.println(currentPlayer.getInputStream().readLine());
    }

}
