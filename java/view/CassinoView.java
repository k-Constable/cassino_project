package view;

import model.Table;
import space.Space;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Font;


import java.util.ArrayList;
import java.util.jar.JarEntry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.CassinoModel;
import model.GameState;
import model.Hand;
import java.lang.Exception;
import model.Card;



public class CassinoView extends JPanel{
    CassinoModel model;
    int cardWidth = 70;
    int cardHeight = 98;
    //private MouseAdapter adapter;
    JFrame frame;
    ArrayList<Space> leftSelectedSpaces;
    ArrayList<Space> rightSelectedHandSpaces;
    ArrayList<Space> rightSelectedTableSpaces;

    public CassinoView(CassinoModel model, JFrame frame){
        this.model = model;
        this.frame = frame;
        
        //Arraylists where spaces will be added and removed if they are left/right clicked in combination with other spaces.
        //Rightclicking the table or hand will grant different actions, so we need a list for each of them.
        this.leftSelectedSpaces = new ArrayList<>();
        this.rightSelectedHandSpaces = new ArrayList<>();
        this.rightSelectedTableSpaces = new ArrayList<>();

        this.setBackground(new Color(53, 101, 77));
        this.setFocusable(true);
        //Adding a mouse listener to the main panel, which is used for placing cards on the table.
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                //If the player has leftclicked a space in their hand, and then the table,
                //the method for placing a card on the table is run.
                //If it runs successfully, new cards are dealt if necessary.
                //The frame is then repainted after the model is updated.
                //All spaces are always deselected after pressing a card and then the table.
                if(leftSelectedSpaces.size() == 1){
                    if(model.placeCard(leftSelectedSpaces)){
                        deSelectSpaces();
                        if(model.getHand2().isEmpty() && model.getHand1().isEmpty()){
                            model.dealToPlayers();
                        }
                        frame.getContentPane().removeAll();
                        frame.repaint();
                    }
                    else{
                        deSelectSpaces();
                    }
                }
            }
        });
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }

    /**
     * Runs the necessary methods for drawing the game.
     * Runs the game over method if the GameState is GAMEOVER.
     * @param g2, a Graphics2D object used for drawing.
     */
    public void drawGame(Graphics2D g2){
        if(model.getGameState() == GameState.GAMEOVER){
            drawGameOver(g2);
        }
        else{
            drawTable(g2);
            drawHand1(g2);
            drawHand2(g2);
            displayTurn(g2);
        }
    }

    /**
     * Method for drawing the cards that both players have access to (the "cards on the table").
     * Loops through all the Spaces in the Table, and draws them. It also adds an MouseListener to each Space,
     * which executes specific instrucions when clicked.
     * @param g2, a Graphics2D object used for drawing.
     */
    public void drawTable(Graphics2D g2){
        for(Space space : model.getTable()){
            space.setFocusable(false);
            try{
                Image image = new ImageIcon(getClass().getResource(space.getCard().getFileLocation())).getImage();
                Image scaledImage = image.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH);
                space.setIcon(new ImageIcon(scaledImage));
            }
            catch(Exception e){
                e.printStackTrace();
            } 
            space.setLocation(20 + (cardWidth*space.getPlace().getCol()), 320-cardHeight+(20*space.getPlace().getRow()));
            space.setSize(cardWidth, cardHeight);
            

            space.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    Space space = (Space) e.getSource();

                    //To avoid both scenarios running one after the other, we can make a boolean that will act as a switch
                    //boolean oneCardIsSelected = false;

                    //If the player leftClicks the table, the methods for collecting cards or
                    //stacking cards from the player hand on the table can be run
                    if(e.getButton() == MouseEvent.BUTTON1){
                        //If the player has left clicked a space in their hand, and then a space on the table, 
                        //the method for collecting cards is run. If it runs successfully, new cards are dealt if
                        //neccessary, and the game is redrawn.
                        //
                        //Cards are always deselected after selecting the tablecard, whether the
                        //method runs successfully or not.
                        if(leftSelectedSpaces.size() == 1){
                            leftSelectSpace(space);
                            if(model.collectCard(leftSelectedSpaces)){
                                deSelectSpaces();
                                if(model.getHand2().isEmpty() && model.getHand1().isEmpty()){
                                    model.dealToPlayers();
                                }
                                frame.getContentPane().removeAll();
                                frame.repaint();
                            }
                            else{
                                deSelectSpaces();
                            }
                        }

                        /**
                         * If the player has right clicked a space in their hand, and then a space on the table,
                         * a window appears asking the player to declare a build target, the value which they are 
                         * building their stack towards. This value is passed to the stackCard method,
                         * which if run successfully, makes the necessary changes to the model.
                         * 
                         * The frame is always redrawm whether the method runs successfully or not, 
                         * to remove the window asking for the buildTarget.
                         * 
                         * Cards are always deselected after selecting the tablecard, whether the
                         * method runs successfully or not.
                         */
                        if(rightSelectedHandSpaces.size() == 1){
                            rightSelectHandSpace(space);
                            JPanel inputPanel = new JPanel();
                            inputPanel.setSize(200, 100);
                            inputPanel.setLocation(350, 450);
                            JTextField t = new JTextField(16);
                            JLabel l = new JLabel("Build target: ", JLabel.RIGHT);
                            JButton submit = new JButton("Submit");
                            submit.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e){
                                    int buildTarget = Integer.valueOf(t.getText());
                                    if(model.stackCard(rightSelectedHandSpaces, buildTarget)){
                                        deSelectSpaces();
                                        if(model.getHand2().isEmpty() && model.getHand1().isEmpty()){
                                            model.dealToPlayers();
                                        }
                                    }
                                    deSelectSpaces();
                                    frame.getContentPane().removeAll();
                                    frame.repaint();                
                                }
                            });
                            inputPanel.add(l);
                            inputPanel.add(t);
                            inputPanel.add(submit);
                            frame.add(inputPanel);
                            inputPanel.validate();
                            frame.repaint();
                            /*
                            if(model.stackCard(rightSelectedSpaces)){
                                deSelectSpaces();
                                if(model.getHand2().isEmpty() && model.getHand1().isEmpty()){
                                    model.dealToPlayers();
                                }
                                frame.getContentPane().removeAll();
                                frame.repaint();
                            }*/
                        }

                         /**
                         * If a card on the table has been selected, the player is asked for the value they are building
                         * towards, and then the stackCardsOnTable method is run using that value and the selected Spaces as
                         * parameters.
                         * 
                         * All cards are deselected whether or not the method runs successfully, 
                         * and the frame is also redrawn regardless of this, in order to remove the 
                         * window asking for the build target.
                         */

                         if(rightSelectedTableSpaces.size() == 1){
                            rightSelectTableSpace(space);
                            System.out.println("Running the stack method");
                            System.out.println(rightSelectedTableSpaces.size());
                            JPanel inputPanel = new JPanel();
                            inputPanel.setSize(200, 100);
                            inputPanel.setLocation(350, 450);
                            JTextField t = new JTextField(16);
                            JLabel l = new JLabel("Build target: ", JLabel.RIGHT);
                            JButton submit = new JButton("Submit");
                            submit.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e){
                                    int buildTarget = Integer.valueOf(t.getText());
                                    if(model.stackCardsOnTable(rightSelectedTableSpaces, buildTarget)){
                                        deSelectSpaces();
                                    }
                                    deSelectSpaces();
                                    frame.getContentPane().removeAll();
                                    frame.repaint();
                                }
                            });
                            inputPanel.add(l);
                            inputPanel.add(t);
                            inputPanel.add(submit);
                            frame.add(inputPanel);
                            inputPanel.validate();
                            frame.repaint();
                        }
                    }

                    
                    //If the rightbutton is pressed on a space the table, the space is added to the list of
                    //spaces that have been rightclicked and are tablespaces.
                    if(e.getButton() == MouseEvent.BUTTON3){
                        //If no card has been selected on the table, the player selects a card from the table
                        if(rightSelectedTableSpaces.size() == 0){
                            deSelectSpaces();
                            rightSelectTableSpace(space);
                            System.out.println("Running add to rightSelectedTableSpace");
                            System.out.println(rightSelectedTableSpaces.size());
                        }
                    }
                }
            });
            this.frame.add(space);
        }
    }

    /**
     * Loops through the all the Spaces in hand1, drawing all the cards if it's player1's turn, 
     * or all the backs of cards if it's player 2's turn.
     * 
     * For each Space it draws, it also adds a MouseListener to the Space, so that clicks on the Space will be registered.
     * The MouseListener contains instructions for what to do when clicked, in this case, each Space clicked will be added as
     * the first element in an ArrayList of selected Spaces.
     * 
     * The Space will be placed in a different ArrayList depending on which mousebutton is clicked.
     * 
     * The ArrayList is first cleared when the Space is pressed, so that each Space clicked in Hand1 will be the first element
     * of the ArrayList. This makes it possible to use indexing when accesing specific Cards in the methods that use the 
     * selectedSpaces ArrayList.
     * @param g2, a Graphics2D object used for drawing.
     */
    public void drawHand1(Graphics2D g2){
        if(model.getGameState() == GameState.PLAYER2){
            for(Space space : model.getHand1()){
                try{
                    Image image = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                    g2.drawImage(image, 20 + (cardWidth*space.getPlace().getCol()), 20, cardWidth, cardHeight, null);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(model.getGameState() == GameState.PLAYER1){
            for(Space space : model.getHand1()){
                space.setFocusable(false);
                try{
                    Image image = new ImageIcon(getClass().getResource(space.getCard().getFileLocation())).getImage();
                    Image scaledImage = image.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH);
                    space.setIcon(new ImageIcon(scaledImage));
                }
                catch(Exception e){
                    e.printStackTrace();
                } 
                space.setLocation(20 + (cardWidth*space.getPlace().getCol()), 540-cardHeight);
                space.setSize(cardWidth, cardHeight);
                
                //When clicked, a Space will be added to leftSelectedSpaces or rightSelected array
                //depending on which button is clicked. 
                //When clicking a Space in the Hand, the previous Spaces in the list are cleared,
                //so the Space you click is always the first in the list.
                space.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e){
                        Space space = (Space) e.getSource();

                        if(e.getButton() == MouseEvent.BUTTON1){
                            deSelectSpaces();
                            leftSelectSpace(space);
                        }

                        if(e.getButton() == MouseEvent.BUTTON3){
                            deSelectSpaces();
                            rightSelectHandSpace(space);
                        }
                    }
                });
                this.frame.add(space);
            }
        }
    }

    /**
     * Does the exact same as drawHand1, but for Hand2 instead.
     * @param g2, a Graphics2D object used for drawing.
     */
    public void drawHand2(Graphics2D g2){
        if(model.getGameState() == GameState.PLAYER1){
            for(Space space : model.getHand2()){
                try{
                    Image image = new ImageIcon(getClass().getResource("./cards/BACK.png")).getImage();
                    g2.drawImage(image, 20 + (cardWidth*space.getPlace().getCol()), 20, cardWidth, cardHeight, null);
                }

                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(model.getGameState() == GameState.PLAYER2){
            for(Space space : model.getHand2()){
                space.setFocusable(false);
                try{
                    Image image = new ImageIcon(getClass().getResource(space.getCard().getFileLocation())).getImage();
                    Image scaledImage = image.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH);
                    space.setIcon(new ImageIcon(scaledImage));
                }
                catch(Exception e){
                    e.printStackTrace();
                } 
                space.setLocation(20 + (cardWidth*space.getPlace().getCol()), 540-cardHeight);
                space.setSize(cardWidth, cardHeight);
                
                space.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e){
                        Space space = (Space) e.getSource();

                        if(e.getButton() == MouseEvent.BUTTON1){
                            deSelectSpaces();
                            leftSelectSpace(space);
                        }

                        if(e.getButton() == MouseEvent.BUTTON3){
                            deSelectSpaces();
                            rightSelectHandSpace(space);
                        }
                    }
                });
                this.frame.add(space);
            }
        }
    }

    /**
     * Draws a string informing which players turn it is.
     * @param g2, a Graphics2D object used for drawing.
     */
    public void displayTurn(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        if(model.getGameState() == GameState.PLAYER1){
            g2.drawString("Player 1's turn", 400, 70);;
        }
        if(model.getGameState() == GameState.PLAYER2){
            g2.drawString("Player 2's turn", 400, 70);;
        }
    }

    /**
     * A method for drawing everything that needs to be displayed when the game ends.
     * @param g2, a Graphics2D object used for drawing.
     */
    public void drawGameOver(Graphics2D g2){
        //Draw gameover screen
        Rectangle2D box = new Rectangle2D.Double(
                0,
                0,
                600,
                600);
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fill(box);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Round finished", 230, 100);

        //Draw final scores
        ArrayList<Integer> scores = model.calculateScore();
        String player1Score = String.valueOf(scores.get(0));
        String player2Score = String.valueOf(scores.get(1));
        g2.drawString("Player 1's score: " + player1Score, 200, 300);
        g2.drawString("Player 2's score: " + player2Score, 200, 400);

        if(scores.get(0) > scores.get(1)){
            g2.drawString("Player 1 wins!", 200, 500);
        }
        else if(scores.get(1) > scores.get(0)){
            g2.drawString("Player 2 wins!", 200, 500);
        }
        else{
            g2.drawString("It's a draw!", 230, 500);
        }

        this.setFocusable(false);
    }

    /**
     * Adds a given space to the list of spaces that are selected by Left clicking the mouse.
     * @param space, a Space object that has been left clicked.
     */
    public void leftSelectSpace(Space space){
        leftSelectedSpaces.add(space);
    }

    /**
     * Adds a given space to the list of spaces that are selected by right clicking the mouse.
     * @param space, a Space object that has been right clicked.
     */
    public void rightSelectHandSpace(Space space){
        rightSelectedHandSpaces.add(space);
    }

    /**
     * Adds a given space to the list of spaces that are selected by right clicking the mouse.
     * @param space, a Space object that has been right clicked.
     */
    public void rightSelectTableSpace(Space space){
        rightSelectedTableSpaces.add(space);
    }

    /**
     * Empties both lists of selected spaces. (Left and right selected.)
     */
    public void deSelectSpaces(){
        leftSelectedSpaces.clear();
        rightSelectedHandSpaces.clear();
        rightSelectedTableSpaces.clear();

    }


}
