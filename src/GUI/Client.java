package GUI;

import helpers.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private JFrame frame = new JFrame("");
    private JLabel messageLabel = new JLabel("");
    private Color messageColor = Color.lightGray;
    private Color boardColor = Color.green;
    private ImageIcon icon;
    private ImageIcon opponentIcon;

    private Cell[] board = new Cell[9];
    private Cell currentCell;


    private Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    public Client(String serverAddress,int  port) throws Exception {
        socket = new Socket(serverAddress, port);
        inputStream = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        outputStream = new PrintWriter(socket.getOutputStream(), true);

        messageLabel.setBackground(messageColor);
        frame.getContentPane().add(messageLabel, "South");

        // Design
        JPanel boardPanel = new JPanel();

        boardPanel.setBackground(boardColor);
        boardPanel.setLayout(new GridLayout(3,3, 9, 9));
        for (int i = 0; i < board.length; i++) {
            int j = i;
            board[i] = new Cell();
            board[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    currentCell = board[j];
                    outputStream.println(Command.MOVE + Command.DIVIDER + j);
                }
            });
            boardPanel.add(board[i]);
        }
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {


                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.exit(0);
            }
        });

        frame.getContentPane().add(boardPanel, "Center");
    }

    private boolean replay() {
        int response = JOptionPane.showConfirmDialog(frame,
                "Do u wanna start again?",
                "Крестики нолики",
                JOptionPane.YES_NO_OPTION);
        frame.dispose();
        return response == JOptionPane.YES_OPTION;
    }

    public void play() throws Exception {
        String resp;
        try {
            resp = inputStream.readLine();
            String[] commandValue = Command.getCommandAndVal(resp);
            System.out.println("here");
            System.out.println(resp);
            System.out.println(commandValue[0]);
            System.out.println(commandValue[1]);
            if (commandValue[0].equals(Command.START)) {
                char letter = commandValue[1].charAt(0);
                System.out.println(letter);
                if(letter == 'X'){
                    icon = createImageIcon("icons/X.png", "X image");
                    opponentIcon = createImageIcon("icons/o.png", "O image");
                }
                else{
                    icon = createImageIcon("icons/o.png", "O image");
                    opponentIcon = createImageIcon("icons/X.png", "X image");
                }
                frame.setTitle("logic.Player " + letter);
            }
            boolean check = true;


            while (true) {
                resp = inputStream.readLine();
                System.out.println(resp);

                commandValue = Command.getCommandAndVal(resp);
                switch (commandValue[0]) {
                    case Command.CORRECT_MOVE: {
                        messageLabel.setText("Nice, wait for your turn");
                        currentCell.setIcon(icon);
                        currentCell.repaint();
                        break;

                    }
                    case Command.OPPONENT_MOVE: {
                        int where = Integer.parseInt(commandValue[1]);
                        board[where].setIcon(opponentIcon);
                        board[where].repaint();
                        messageLabel.setText("Opponent moved, your turn");
                        break;
                    }

                    case Command.WIN: {
                        messageLabel.setText("You win");
                        check = false;
                        break;

                    }

                    case Command.DRAW: {
                        messageLabel.setText("Friendship won");
                        check = false;
                        break;
                    }

                    case Command.LOSS: {
                        messageLabel.setText("You lost");
                        check = false;
                        break;
                    }

                    case Command.INFO: {
                        messageLabel.setText(commandValue[1]);
                        break;
                    }
                }

                if (!check) {
                    break;
                }
            }

            outputStream.println(Command.FINISH + Command.DIVIDER);
        }
         finally {
            socket.close();
        }

    }

    private ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
        System.out.println(imgURL);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            return null;
        }
    }

    public static void initFirstClientSettings(Client client){

        client.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.frame.setSize(480, 320);
        client.frame.setResizable(false);

    }


    public static void main(String[] args) throws Exception {
        String serverAddress = "localhost";
        int port = 8901;
        int response = JOptionPane.showConfirmDialog(new Frame(),
                "Do u wanna start a client?",
                "Крестики нолики",
                JOptionPane.YES_NO_OPTION);
        if(response != JOptionPane.YES_OPTION){
            System.exit(0);
        }
        while (true) {
            Client client = new Client(serverAddress, port);
            initFirstClientSettings(client);

            client.play();
            if(!client.replay()){
                client.socket.close();
                System.exit(0);
            }
        }
    }


}