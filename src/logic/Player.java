package logic;

import helpers.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Player extends Thread {
    private Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private char letter;
    private Player opponent;
    private Game game;


    public Player(Socket socket, char letter, Game game){
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.letter = letter;
        this.opponent = opponent;
        this.game = game;
        try {
            inputStream = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(socket.getOutputStream(), true);
            outputStream.println(Command.START + Command.DIVIDER + letter);
            outputStream.println(Command.INFO + Command.DIVIDER + "Waiting for opponent");
        } catch (IOException e) {
        }
    }

    public void run() {
        try {
            outputStream.println(Command.INFO + Command.DIVIDER + "Players connected");

            if (letter == game.getFirstLetter()) {
                outputStream.println(Command.INFO + Command.DIVIDER + "Your turn");
            }
            do {
                String s = inputStream.readLine();
                String[] commandValue = Command.getCommandAndVal(s);
                if (commandValue[0].equals(Command.MOVE)) {
                    int where = Integer.parseInt(commandValue[1]);
                    if (game.legalMove(where, this)) {
                        outputStream.println(Command.CORRECT_MOVE + Command.DIVIDER);
                        outputStream.println(game.checkEnd() ? Command.WIN + Command.DIVIDER
                                : game.tableFilledUp() ? Command.DRAW + Command.DIVIDER
                                : "");
                    } else {
                        outputStream.println(Command.INFO + Command.DIVIDER + "NOT VALID");
                    }
                } else if (commandValue[0].equals(Command.FINISH)) {
                    return;
                }
            } while (true);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    public void otherPlayerMoved(int where) {
        //System.out.println(game.checkEnd());
        outputStream.println(Command.OPPONENT_MOVE + Command.DIVIDER + where);
        outputStream.println(
                game.checkEnd() ? Command.LOSS + Command.DIVIDER : game.tableFilledUp() ? Command.DRAW + Command.DIVIDER
                        : "");
    }




    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getInputStream() {
        return inputStream;
    }

    public void setInputStream(BufferedReader inputStream) {
        this.inputStream = inputStream;
    }

    public PrintWriter getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(PrintWriter outputStream) {
        this.outputStream = outputStream;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }


}
