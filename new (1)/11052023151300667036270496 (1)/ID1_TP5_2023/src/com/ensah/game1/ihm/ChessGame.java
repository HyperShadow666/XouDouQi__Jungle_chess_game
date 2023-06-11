package com.ensah.game1.ihm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class ChessGame {
    public static ArrayList<Piece> pieces = new ArrayList<Piece>();

    //combien de parties gagnes
    public static int redWin = 0;
    public static int blueWin = 0;

    //score
    public static int redplayerscore=0;
    public static int blueplayerscore=0;

    //turns
    public static int redCount = 0;
    public static int blueCount = 0;
    public static Piece selectedPiece = null;
    private static int selectedX = -1;
    private static int selectedY = -1;

    public final static int width = 70;
    public final static int height = 70;

    //flag
    private static boolean tableFinished;

    //flag to check if its the first game or its a raplay
    public static boolean FirstRun=true;
    public static JFrame f;



    /**RETURNS THE PIECE BASED ON COORDINATES*/
    public static Piece findPiece(int xp, int yp) {
        for (Piece p : pieces) {
            if (p.x == xp / width && p.y == yp / height) {
                return p; //
            }
        }
        return null;//no piece was found at the specified coordinates
    }

    public static boolean getTableFinished(){
        return tableFinished;
    }

    public static void setTableFinished(boolean tableFinished) {
        ChessGame.tableFinished = tableFinished;
    }

    public static void main(String[] args) {

        if(!FirstRun){
            if(f != null){f.dispose();}
        }
        FirstRun= false;

        setTableFinished(false);
        initializePieces();

        JFrame frame = new JFrame();
        f = frame;
        frame.setBounds(400, 20, 500, 670);
        JPanel pn = new JPanel() {
            @Override
            public void paint(Graphics g) {
                drawBoard(g);
                drawPieces(g);
            }

        };

        //la souris pour selectionner la piece a deplacer (SelectedX et SelectedY)
        pn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                selectedX = mouseX / (width);
                selectedY = mouseY / (height);
                selectedPiece = findPiece(selectedX, selectedY);
                pn.repaint();
            }
        });

        //set the focus on the pn panel so that it can receive keyboard input
        pn.setFocusable(true);
        pn.requestFocus();

        //keyboard events
        pn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) throws ConcurrentModificationException {
                try{
                    int keyCode = e.getKeyCode();

                    for (Piece piece : pieces) {
                        if (piece.x == selectedX && piece.y == selectedY) {
                            if (keyCode == KeyEvent.VK_UP) {
                                if (((piece.name == "bL") || (piece.name == "L") || (piece.name == "bT") || (piece.name == "T")) && ((piece.x == 1) || (piece.x == 2) || (piece.x == 4) || (piece.x == 5)) && ((piece.y == 6))) {

                                    boolean ratExist = false;
                                    //check if rat exist in lake
                                    for(Piece p : pieces){
                                        if(p.name=="R" || p.name=="bR"){
                                            if((p.x == piece.x) && (p.y >= 3 && p.y <= 5)){
                                                ratExist=true;
                                            }
                                        }
                                    }
                                    if(!ratExist){
                                        piece.move(piece.x, piece.y - 4);
                                    }

                                }

                                piece.move(piece.x, piece.y - 1);
                            } else if (keyCode == KeyEvent.VK_DOWN){
                                if (((piece.name == "bL") || (piece.name == "L") || (piece.name == "bT") || (piece.name == "T")) && ((piece.x == 1) || (piece.x == 2) || (piece.x == 4) || (piece.x == 5)) && ((piece.y == 2))) {

                                    boolean ratExist = false;
                                    for(Piece p : pieces){
                                        if(p.name=="R" || p.name=="bR"){
                                            if((p.x == piece.x) && (p.y >= 3 && p.y <= 5)){
                                                ratExist=true;
                                            }
                                        }
                                    }

                                    if(!ratExist){
                                        piece.move(piece.x, piece.y + 4);
                                    }

                                }
                                piece.move(piece.x, piece.y + 1);
                            } else if (keyCode == KeyEvent.VK_LEFT) {
                                if (((piece.name == "bL") || (piece.name == "L") || (piece.name == "bT") || (piece.name == "T")) && ((piece.x == 6) || (piece.x == 3)) && ((piece.y >= 3 && piece.y <= 5))) {

                                    boolean ratExist = false;
                                    for(Piece p : pieces){
                                        if(p.name=="R" || p.name=="bR"){
                                            if((p.x == 1 || p.x == 2 || p.x == 4 || p.x == 5) && (p.y == piece.y)){
                                                ratExist=true;
                                            }
                                        }
                                    }

                                    if(!ratExist){
                                        piece.move(piece.x-3, piece.y );
                                    }

                                }
                                piece.move(piece.x - 1, piece.y);
                            } else if (keyCode == KeyEvent.VK_RIGHT) {
                                if (((piece.name == "bL") || (piece.name == "L") || (piece.name == "bT") || (piece.name == "T")) && ((piece.x == 0) || (piece.x == 3)) && ((piece.y >= 3 && piece.y <= 5))) {

                                    boolean ratExist = false;
                                    for(Piece p : pieces){
                                        if(p.name=="R" || p.name=="bR"){
                                            if((p.x == 1 || p.x == 2 || p.x == 4 || p.x == 5) && (p.y == piece.y)){
                                                ratExist=true;
                                            }
                                        }
                                    }
                                    if(!ratExist){
                                        piece.move(piece.x+3, piece.y );
                                    }

                                }
                                piece.move(piece.x + 1, piece.y);
                            }

                            pn.repaint();
                        }

                    }

                }catch (ConcurrentModificationException exc){}
            }
        });


        //boutton droit pour afficher "Help"
        JPopupMenu optionsMenu = new JPopupMenu();
        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle the help option here
                TextFrame textFrame = new TextFrame();
                textFrame.setVisible(true);
            }
        });
        optionsMenu.add(helpMenuItem);

        //Create a MouseListener for right-click detection
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    optionsMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        // Add the MouseListener to the panel
        pn.addMouseListener(mouseListener);

        frame.add(pn);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);

    }


  /**SETS THE BOARD*/
    private static void initializePieces() {
        // Create and add pieces to the list
        pieces.clear();
        pieces.add(new Piece(6, 8, 6, 8, "L", false,  6, 1, "Lion"));
        pieces.add(new Piece(1, 7, 1, 7, "C", false,  1, 1, "Cat"));
        pieces.add(new Piece(5, 7, 5, 7, "D", false,  3, 1, "Dog"));
        pieces.add(new Piece(0, 6, 0, 6, "E", false,  7, 1, "Elephant"));
        pieces.add(new Piece(4, 6, 4, 6, "P", false,  4, 1, "Panther"));
        pieces.add(new Piece(0, 8, 0, 8, "T", false,  5, 1, "Tiger"));
        pieces.add(new Piece(6, 6, 6, 6, "R", false,  0, 1, "Rat"));
        pieces.add(new Piece(2, 6, 2, 6, "W", false,  2, 1, "Wolf"));

        pieces.add(new Piece(0, 0, 0, 0, "bL", true,  6, 2, "Lion"));
        pieces.add(new Piece(5, 1, 5, 1, "bC", true,  1, 2, "Cat"));
        pieces.add(new Piece(1, 1, 1, 1, "bD", true,  3, 2, "Dog"));
        pieces.add(new Piece(6, 2, 6, 2, "bE", true,  7, 2, "Elephant"));
        pieces.add(new Piece(2, 2, 2, 2, "bP", true,  4, 2, "Panther"));
        pieces.add(new Piece(6, 0, 6, 0, "bT", true,  5, 2, "Tiger"));
        pieces.add(new Piece(0, 2, 0, 2, "bR", true,  0, 2, "Rat"));
        pieces.add(new Piece(4, 2, 4, 2, "bW", true,  2, 2, "Wolf"));
    }

    private static void drawBoard(Graphics g) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 7; col++) {
                // The ground
                g.setColor(Color.decode("#00DFA2"));
                g.fillRect(col * width, row * height, width, height);

                // Sanctuaries
                if (col == 3 && (row == 0 || row == 8)) {
                    g.setColor(Color.decode("#F6FA70"));
                    g.fillRect(col * width, row * height, width, height);
                }

                // Traps
                if ((col == 2 || col == 4) && (row == 0 || row == 8)) {
                    g.setColor(Color.decode("#3D5656"));
                    g.fillRect(col * width, row * height, width, height);
                } else if (col == 3 && (row == 1 || row == 7)) {
                    g.setColor(Color.decode("#3D5656"));
                    g.fillRect(col * width, row * height, width, height);
                }

                // the Lake
                if (row >= 3 && row <= 5 && (col == 1 || col == 2 || col == 4 || col == 5)) {
                    g.setColor(Color.decode("#651FFF"));
                    g.fillRect(col * width, row * height, width, height);
                }

                // The lines
                g.setColor(Color.decode("#F5F5DC"));
                g.drawRect(col * width, row * height, width, height);
            }
        }
    }
    private static void drawPieces(Graphics g) {

        for (int i = pieces.size() - 1; i >= 0; i--) {
            Piece p = pieces.get(i);
            try {
                File imgFile = new File("res/" + p.name + ".png");
                Image img = ImageIO.read(imgFile);
                int newWidth = width * 3; // New width of the image
                int newHeight = height * 3; // New height of the image
                Image resizedImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                int xOffset = (width - newWidth) / 2;
                int yOffset = (height - newHeight) / 2;

                g.drawImage(resizedImage, width * p.x + xOffset, height * p.y + yOffset, null);

                //to color the selected piece
                if (p.x == selectedX && p.y == selectedY) {
                    g.setColor(Color.YELLOW);
                    g.drawRect(width * p.x, height * p.y, width, height);
                    selectedPiece = p; // Update the selected piece

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    //afficher le gagnant
    public static void displayblueWinnerWindow() {
        blueWin++;
        System.out.println(blueWin);
        //initialiser le score
        blueplayerscore=0;
        redplayerscore=0;
        JFrame winnerFrame = new JFrame("Game Over");
        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW); // Set the background color

        // Create a JLabel to display the winner message
        JLabel winnerLabel = new JLabel("Blue Wins");
        winnerLabel.setForeground(Color.BLUE); // Set the text color
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font and size

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0); // Add some spacing
        panel.add(winnerLabel, gbc);
        // Add the winnerLabel to the panel


        // Create a JButton for restarting the game
        JButton restartButton = new JButton("Restart Game");
        ChessGame.setTableFinished(true);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                winnerFrame.dispose();
                restart();
            }
        });

        // Create a JButton for exiting the program
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the program
            }
        });
        // Add the buttons to the panel with center alignment
        gbc.gridy = 1;
        gbc.insets = new Insets(400, 400, 100, 300); // Add some spacing
        panel.add(restartButton, gbc);

        gbc.gridy = 2;
        panel.add(exitButton, gbc);


        winnerFrame.getContentPane().add(panel);


        // Set the size and visibility of the winnerFrame
        winnerFrame.setBounds(400, 150,400, 200);
        winnerFrame.setVisible(true);
    }

    //afficher le gagnant
    public static void displayredWinnerWindow() {
        redWin++;
        System.out.println(redWin);

        //initialiser le score
        redplayerscore=0;
        blueplayerscore=0;
        JFrame winnerFrame = new JFrame("Game Over");
        // Create a JPanel to hold the components
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW); // Set the background color

        // Create a JLabel to display the winner message
        JLabel winnerLabel = new JLabel("Red Wins");
        winnerLabel.setForeground(Color.RED); // Set the text color
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the font and size

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 30, 0); // Add some spacing
        panel.add(winnerLabel, gbc);
        // Add the winnerLabel to the panel



        // Create a JButton for restarting the game
        JButton restartButton = new JButton("Restart Game");
        ChessGame.setTableFinished(true);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restart();
                winnerFrame.dispose();
            }
        });

        // Create a JButton for exiting the program
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the program
            }
        });
        // Add the buttons to the panel with center alignment
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0); // Add some spacing
        panel.add(restartButton, gbc);

        gbc.gridy = 3;
        panel.add(exitButton, gbc);
        gbc.insets = new Insets(0, 0, 10, 0);
        winnerFrame.getContentPane().add(panel);



        // Set the size and visibility of the winnerFrame
        winnerFrame.setBounds(400, 150,400, 200);
        winnerFrame.setVisible(true);
    }

    //replay
    public static void restart(){

        if(getTableFinished()){
            main(null);
        }
    }

}