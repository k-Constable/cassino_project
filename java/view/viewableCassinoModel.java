package view;
import java.awt.*;
import javax.swing.*;

import model.GameState;
import model.Hand;
import model.Table;

public interface viewableCassinoModel{

    /**
     * Gets the table the model is using.
     * @return the Table object beloning to the model.
     */
    public Table getTable();

    /**
     * Gets the first hand the model is using.
     * @return one of the Hand objects belonging to the model.
     */
    public Hand getHand1();

    /**
     * Gets the second hand the model is using.
     * @return one of the Hand objects belonging to the model.
     */
    public Hand getHand2();

    /**
     * Gets the current gamestate of the model.
     * @return the GameState the model currently is in.
     */
    public GameState getGameState();
}
