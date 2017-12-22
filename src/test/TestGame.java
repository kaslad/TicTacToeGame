package test;

import logic.Game;
import logic.Player;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class TestGame {
    @Test
    public void testEmptyGameShouldNotBeFinished(){
        Game game = new Game();
        Assert.assertFalse(game.checkEnd());
    }
    @Test
    public void testLineWinningCombination() throws IOException {
        Game game = new Game();
        Player [] pl = new Player[9];
        for(int i = 0; i < 9; i++ ){
            pl[i] = null;
        }
        for(int i = 0; i < 3; i++){
            Socket socket = new Socket("localhost", 8901);
            Player player = new Player(socket,'X', game );
            pl[i] = player;

        }
        game.setGameTable(pl);
        Assert.assertFalse(game.checkEnd());
    }

    @Test
    public void testMainCrossWinningCombination() throws IOException {
        Game game = new Game();
        Player[] pl = new Player[9];
        for (int i = 0; i < 9; i++) {
            pl[i] = null;
        }

            Socket socket = new Socket("localhost", 8901);
            Player player = new Player(socket, 'X', game);
            pl[0] = player;
            player = new Player(socket, 'X', game);
            pl[4] = player;
            player = new Player(socket, 'X', game);
            pl[8] = player;

        game.setGameTable(pl);
        Assert.assertFalse(game.checkEnd());
    }

    @Test
    public void testNotMainCrossWinningCombination() throws IOException {
        Game game = new Game();
        Player[] pl = new Player[9];
        for (int i = 0; i < 9; i++) {
            pl[i] = null;
        }

        Socket socket = new Socket("localhost", 8901);
        Player player = new Player(socket, 'X', game);
        pl[2] = player;
        player = new Player(socket, 'X', game);
        pl[4] = player;
        player = new Player(socket, 'X', game);
        pl[6] = player;

        game.setGameTable(pl);
        Assert.assertFalse(game.checkEnd());
    }
    @Test
    public void testLegalMove() throws IOException {
        Game game = new Game();
        Socket socket = new Socket("localhost", 8901);
        Player player = new Player(socket, 'X', game);
        Socket socket1 = new Socket("localhost", 8901);

        Player[] pl = new Player[9];
        for (int i = 0; i < 9; i++) {
            pl[i] = null;
        }
        pl[2] = player;
        player = new Player(socket, 'X', game);
        pl[4] = player;
        player = new Player(socket, 'X', game);
        pl[6] = player;
        player.setOpponent(new Player(socket1, 'O', game));
        //player.otherPlayerMoved(5);
        player.getOpponent().setOpponent(player);
        game.setCurrentPlayer(player.getOpponent());

        Assert.assertTrue(game.legalMove(5, player.getOpponent()));
    }

}
