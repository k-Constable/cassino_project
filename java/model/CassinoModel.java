package model;

import java.lang.reflect.Array;
import java.sql.PseudoColumnUsage;
import java.util.ArrayList;

import space.Location;
import space.Space;
import view.viewableCassinoModel;

public class CassinoModel implements viewableCassinoModel{
    private Deck deck;
    private Table table;
    private Hand hand1;
    private Hand hand2;
    private GameState gameState;

    public CassinoModel(){
        this.deck = new Deck();
        this.table = new Table();
        this.hand1 = new Hand();
        this.hand2 = new Hand();

        //Starting the game by shuffling our deck, laying the table cards, and dealing to the players.
        deck.shuffle();

        table.set(new Location(0, 0), deck.dealTop());
        table.set(new Location(0, 1), deck.dealTop());
        table.set(new Location(0, 2), deck.dealTop());
        table.set(new Location(0, 3), deck.dealTop());

        this.dealToPlayers();

        //Player 1 always starts.
        gameState = GameState.PLAYER1;
    }

    @Override
    public Table getTable(){
        return this.table;
    }

    @Override
    public Hand getHand1(){
        return this.hand1;
    }

    @Override
    public Hand getHand2(){
        return this.hand2;
    }

    @Override
    public GameState getGameState(){
        return this.gameState;
    }

    /**
     * If the GameState is not GAMEOVER, it deals 4 cards to each player from the 
     * top of the deck.
     */
    public void dealToPlayers(){
        if(deck.isEmpty()){
            gameState = GameState.GAMEOVER;
        }
        else{
            hand1.set(new Location(0, 0), deck.dealTop());
            hand1.set(new Location(0, 1), deck.dealTop());

            hand2.set(new Location(0, 0), deck.dealTop());
            hand2.set(new Location(0, 1), deck.dealTop());

            hand1.set(new Location(0, 2), deck.dealTop());
            hand1.set(new Location(0, 3), deck.dealTop());

            hand2.set(new Location(0, 2), deck.dealTop());
            hand2.set(new Location(0, 3), deck.dealTop());
        }
    }

    /**
     * Calls the onePlayerCollectCard method for player1 or 2 depending on the current gamestate.
     * 
     * Returns true if all conditions are met and the collection is successfull, false if not. 
     * 
     * @param selectedSpaces, an ArrayList consisting of the Space in the players hand containing the card the player wants to use 
     * to collect the card on the table, and the Space on the table containing the card the player wishes to collect.
     * @return True if the collection succeeds, false if not.
     */
    public boolean collectCard(ArrayList<Space> selectedSpaces){
        
        if(this.gameState == GameState.PLAYER1){
            if(onePlayerCollectCard(selectedSpaces, hand1)){
                //Player 2's turn
                gameState = GameState.PLAYER2;
                return true;
            }
            else{
                return false;
            }
        }

        else if(this.gameState == GameState.PLAYER2){
            if(onePlayerCollectCard(selectedSpaces, hand2)){
                //Player 1's turn
                gameState = GameState.PLAYER1;
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Takes in a Space containing the Card the player wants to add to the table, adds it to the table, and removes it from 
     * the players hand. 
     * 
     * Returns true if the card is successfully places on the table, false if not.
     * 
     * The method is run for player1 or player2 depending on the current GameState.
     * @param selectedSpaces, an ArrayList containing the Space containing the Card the player wishes to add to the table.
     * @return true if the card is placed, false if not.
     */
    public boolean placeCard(ArrayList<Space> selectedSpaces){
        Space playerSpace = selectedSpaces.get(0);
        Card playerCard = playerSpace.getCard();

        //Here a method is used to get the next available column spot for a new Space.
        Location newLocation = new Location(0, getNextTableCol());

        if(gameState == GameState.PLAYER1){
            table.set(newLocation, playerCard);
            hand1.removeSpace(playerSpace);
            gameState = GameState.PLAYER2;
            return true;
        }

        if(gameState == GameState.PLAYER2){
            table.set(newLocation, playerCard);
            hand2.removeSpace(playerSpace);
            gameState = GameState.PLAYER1;
            return true;
        }

        return false;
        
    }

    /**
     * Runs the onePlayerStackCard method for hand1 or hand2 depending on the gamestate.
     * 
     * Returns true is the method runs succesfully, false if not.
     * 
     * @param selectedSpaces, an ArrayList of the Space containing the Card that will be stacked, and the Space containing the Card that will be stacked on.
     * @return true if the method runs succesfully, false if not.
     */
    public boolean stackCard(ArrayList<Space> selectedSpaces, int buildTarget){
        if(gameState == GameState.PLAYER1){
            if(onePlayerStackCard(selectedSpaces, hand1, buildTarget)){
                //Player 2's turn
                gameState = GameState.PLAYER2;
                return true;
            }
            return false;
        }

        if(gameState == GameState.PLAYER2){
            if(onePlayerStackCard(selectedSpaces, hand2, buildTarget)){
                //Player 1's turn
                gameState = GameState.PLAYER1;
                return true;
            }
            return false;
        }
        return false;
    }



    /**
     * Does the same as the StackCard method, but the turn doesn't change after running the method,
     * as stacking tablecards doesn't end ones turn.
     * @param selectedSpaces, an ArrayList<Space> containing the space on the table wishes to stack,
     * and the space on the table the player wishes to stack on.
     * @param buildTarget, an int representing the value the player is building towards.
     * @return true if the cards are stacked successfully, false if not.
     */
    public boolean stackCardsOnTable(ArrayList<Space> selectedSpaces, int buildTarget){
        if(gameState == GameState.PLAYER1){
            if(onePlayerStackCardsOnTable(selectedSpaces, hand1, buildTarget)){
                return true;
            }
            return false;
        }
        if(gameState == GameState.PLAYER2){
            if(onePlayerStackCardsOnTable(selectedSpaces, hand2, buildTarget)){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Loops through the Spaces on the table beginning from the column given as a parameter, and decreases the 
     * column by 1.
     * @param startCol, the column to begin de-incrementing from
     */
    public void deIncrementTableColFrom(int startCol){
        ArrayList<Space> currentSpaces = new ArrayList<>();
        for(Space space : table){
            currentSpaces.add(space);
        }

        //Can't just begin with i = startCol and then increment all the following spaces, 
        //as stacked cards will appear later in the ArrayList, and should also not be incremented.
        for(int i = 0; i < table.groupSize(); i++){
            Space currentSpace = currentSpaces.get(i);
            if(currentSpace.getPlace().getCol() > startCol){
                table.removeSpace(currentSpace);
                table.set(new Location(currentSpace.getPlace().getRow(), currentSpace.getPlace().getCol()-1), currentSpace.getCard());
            }
        }
    }

    /**
     * Finds the next available column spot on the table and returns it.
     * @return The next available column spot on the table.
     */
    public int getNextTableCol(){
        int highestCol = 0;
        for(Space space : table){
            int currentCol = space.getPlace().getCol();
            if(currentCol > highestCol){
                highestCol = currentCol;
            }
        }
        //The next available column spot will be one more than the current highest filled spot.
        return highestCol + 1;
    }

    /**
     * Gets all the cards stacked on the selected card, and returns them in an ArrayList
     * @param referenceCol, an integer representing the column to get the stacked cards from.
     * @return An ArrayList containing the Spaces containing the Cards in the stack.
     */
    public ArrayList<Space> getStack(int referenceCol){
        ArrayList<Space> stack = new ArrayList<>();
        for(Space space : table){
            int currentCol = space.getPlace().getCol();
            if(currentCol == referenceCol){
                stack.add(space);
            }
        }
        return stack;
    }

    /**
     * Takes in the players card and the card the player wishes to collect, 
     * checks whether they have the same value,
     * and if they do, adds the cards to the players total cardCount, 
     * adds spades to the total spadeCount(if there are any),
     * and removes the Spaces containing the cards from the table and hand.
     * 
     * Returns true if all conditions are met and the collection is successfull, false if not.
     * 
     * @param selectedSpaces, an ArrayList consisting of the Space in the players hand containing
     * the card the player wants to use 
     * to collect the card on the table, and the Space on the table containing
     * the card the player wishes to collect.
     * @return True if the collection succeeds, false if not.
     */
    public boolean onePlayerCollectCard(ArrayList<Space> selectedSpaces, Hand hand){
        Space playerSpace = selectedSpaces.get(0);
        Card playerCard = playerSpace.getCard();

        Space tableSpace = selectedSpaces.get(1);
        ArrayList<Space> stack = getStack(tableSpace.getPlace().getCol());

        //At the end of the game, the player with the most cards collected gains an
        //additional 3 points.
        int stackSize = stack.size();

        //The sum of all the cardvalues in the stack, used for checking if the collection is legal.
        int valueSum = 0;

        //At the end of the game, the player with the most spades collected gains an 
        //additional point.
        int stackSpadeCount = 0;

        //Aces each give 1 points for collecting them.
        int aceCount = 0;

        //10 of diamonds (or "big Cassino") gives 2 points if collected.
        int bigCassino = 0;

        //2 of spades (or "little cassino") gives 1 point if collected.
        int littleCassino = 0;

        //Sums up the value of all the cards in the stack, and adds checks if 
        //the stack contains cards that give points.
        for(int i = 0; i < stackSize; i++){
            Card currentCard = stack.get(i).getCard();
            valueSum += currentCard.getValue();
            if(currentCard.getSuit() == 'S'){
                stackSpadeCount += 1;
            }
            if(currentCard.getValue() == 1){
                aceCount += 1;
            }
            if(currentCard.getValue() == 10 && currentCard.getSuit() == 'D'){
                bigCassino += 2;
            }
            if(currentCard.getValue() == 2 && currentCard.getSuit() == 'S'){
                littleCassino += 1;
            }
        }

        //Cards can be collected if the value of the stack totals the same as the card used to collect, 
        //or if the stack consists only of that card value.
        if(playerCard.getValue() == (valueSum/stackSize) || playerCard.getValue() == valueSum){
            //Adding points from the stack
            hand.addToCardCount(stackSize + 1);
            hand.addToSpadeCount(stackSpadeCount);
            hand.addToPoints(aceCount);
            hand.addToPoints(bigCassino);
            hand.addToPoints(littleCassino);

            //Player gets a point for collecting the last card on the table
            if(table.groupSize() - stackSize == 0){
                hand.addToPoints(1);
            }

            //Adding points from the players hand
            if(playerCard.getSuit() == 'S'){
                hand.addToSpadeCount(1);
                if(playerCard.getValue() == 2){
                    hand.addToPoints(1);
                }
            }
            if(playerCard.getValue() == 10 && playerCard.getSuit() == 'D'){
                hand.addToPoints(2);
            }
            if(playerCard.getValue() == 1){
                hand.addToPoints(1);
            }

            //Move all cards on the table a space back
            deIncrementTableColFrom(tableSpace.getPlace().getCol());

            //Remove collected cards from table and hand
            for(int i = 0; i < stackSize; i++){
                table.removeSpace(stack.get(i));
            }
            hand.removeSpace(playerSpace);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Takes in the Space containing a card the player wishes to stack, the Space containing the card they wish to stack on, 
     * and a given value the player is building towards.
     * The players card can either be stacked on a card of the same value if the player has another card of that value in their hand,
     * or be stacked on a card so that their combined value is the same as the value of a card in the players hand.
     * Because a stack can be more than two cards, players can build towards values further than one round ahead in time.
     * They therefore need to declare a value they are building towards, and that value needs to be in the players hand.
     * 
     * @param selectedSpaces, an ArrayList of Spaces containing the card to be stacked, and the card to be stacked on
     * @param hand, the hand of the player that is going to stack.
     * @param buildTarget, an integer representing the value the player wishes to build the stack to.
     * @return true if a card is stacked, false if not.
     */
    public boolean onePlayerStackCard(ArrayList<Space> selectedSpaces, Hand hand, int buildTarget){
        Space playerSpace = selectedSpaces.get(0);
        Space tableSpace = selectedSpaces.get(1);

        Card playerCard = playerSpace.getCard();
        Card tableCard = tableSpace.getCard();



        //Making a list of the values in the players hand, without the value they just used.
        ArrayList<Integer> handValues = new ArrayList<>();
        boolean usedValueRemoved = false;
        for(Space space : hand){
            if(!usedValueRemoved && space.getCard().getValue() != playerCard.getValue())
                handValues.add(space.getCard().getValue());
            else if(!usedValueRemoved && space.getCard().getValue() == playerCard.getValue()){
                usedValueRemoved = true;
            }
            else{
                handValues.add(space.getCard().getValue());
            }
        }

        //Counting the sum of all the values in the stack on the table, 
        //and the amount of cards in the stack.
        int valueSum = 0;
        int stackCount = 0;
        for(Space space : table){
            if(space.getPlace().getCol() == tableSpace.getPlace().getCol()){
                valueSum += space.getCard().getValue();
                stackCount += 1;
            }
        }

        //The value the stack will have after the players card is added to it. This is needed to check whether a stack is legal
        int sumAfterStack = valueSum + playerCard.getValue();

        //Cards can stack if they have the same value, and you have another card with that value in your hand.
        //They can also stack if their values add up to 10 or less, 
        //and your hand contains the given buildTarget (the value your stack is building towards).
        if(playerCard.getValue() == tableCard.getValue() && handValues.contains(tableCard.getValue())
         || sumAfterStack <= 10 && handValues.contains(buildTarget)){
            Location newLocation = new Location(stackCount, tableSpace.getPlace().getCol());
            table.set(newLocation, playerCard);
            hand.removeSpace(playerSpace);
            return true;
        }
        return false;
    }

    /**
     * Does the same as onePlayerStackCard, except for cards on the table instead of in their hand.
     * @param selectedSpaces, an ArrayList<Space> containing the spaces on the table the player wishes to build with.
     * @param hand, the hand corresponding to the player who's turn it is.
     * @param buildTarget, a integer representing the value the player wants to build towards.
     * @return true if the stack is succesfully built, false if not.
     */
    public boolean onePlayerStackCardsOnTable(ArrayList<Space> selectedSpaces, Hand hand, int buildTarget){
        Space movingTableSpace = selectedSpaces.get(0);
        Space targetTableSpace = selectedSpaces.get(1);

        Card movingTableCard = movingTableSpace.getCard();
        Card targetTableCard = targetTableSpace.getCard();

        //Making a list of the values in the players hand.
        ArrayList<Integer> handValues = new ArrayList<>();
        for(Space space : hand){
            handValues.add(space.getCard().getValue());
        }

        //Counting the sum of all the values in the target stack on the table, 
        //and the amount of cards in the stack.
        int valueSum = 0;
        int stackCount = 0;
        for(Space space : table){
            if(space.getPlace().getCol() == targetTableSpace.getPlace().getCol()){
                valueSum += space.getCard().getValue();
                stackCount += 1;
            }
        }

        //The value the stack will have after the other table card is added to it. This is needed to check whether a stack is legal
        int sumAfterStack = valueSum + movingTableCard.getValue();

        //Cards can stack if they have the same value, and you have another card with that value in your hand.
        //They can also stack if their values add up to the value of a card in your hand.
        if(movingTableCard.getValue() == targetTableCard.getValue() && handValues.contains(targetTableCard.getValue())
         || sumAfterStack <= 10 && handValues.contains(buildTarget)){
            Location newLocation = new Location(stackCount, targetTableSpace.getPlace().getCol());
            table.set(newLocation, movingTableCard);
            deIncrementTableColFrom(movingTableSpace.getPlace().getCol());
            table.removeSpace(movingTableSpace);
            return true;
        }
        return false;
    }

    /**
     * Adds up the points of each player with the additional points gained from having the most cards and
     * the most spades, and returns an ArrayList containing the two scores.
     * @return An ArrayList<Integer> of the two players final scores.
     */
    public ArrayList<Integer> calculateScore(){
        ArrayList<Integer> scores = new ArrayList<>();
        int player1Score = hand1.getPoints();
        int player2Score = hand2.getPoints();

        if(hand1.getCardCount() < hand2.getCardCount()){
            player1Score += 3;
        }
        else if(hand2.getCardCount() < hand1.getCardCount()){
            player2Score += 3;
        }

        if(hand1.getSpadeCount() < hand2.getSpadeCount()){
            player1Score += 1;
        }
        else if(hand2.getSpadeCount() < hand1.getSpadeCount()){
            player2Score += 1;
        }
        scores.add(player1Score);
        scores.add(player2Score);
        return scores;
    }
}
