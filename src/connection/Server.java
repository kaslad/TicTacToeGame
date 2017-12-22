package connection;

import logic.Game;
import logic.Player;

import javax.swing.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static int port = 8901;
    public static volatile List<Game> gameRooms = new ArrayList<Game>(){};
    private static JFrame frame = new JFrame("Крестики нолики");
    private static String serverAdress;

    private static boolean replay() {
        int response = JOptionPane.showConfirmDialog(frame,
                "Do u wanna start a server?",
                "Крестики нолики",
                JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(port);
        System.out.println(listener.getLocalSocketAddress());
        if(!replay()){
            return;
        }

        try {
            while (true) {
                Game game = new Game();
                gameRooms.add(game);
                Player playerX = new Player(listener.accept(), 'X', game);
                Player playerO = new Player(listener.accept(), 'O', game);
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.setCurrentPlayer(playerX);
                playerX.start();
                playerO.start();
            }
        } finally {
            listener.close();
        }
    }

}


