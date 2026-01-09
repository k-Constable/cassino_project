# Cassino
The project was to create the card Game Cassino (also spelled Casino) using the Javax Swing framework. Cassino is a card-game, in this case for 2 players, in which the goal is to gain points by collecing cards from the table.

## Rules of the card game
Here is a thorough explanation of the rules from Britannica.com (https://www.britannica.com/topic/casino-card-game):
casino, card game for two to four players, best played with two. This is the ruleset I followed when writing the logic for the game.


"
A 52-card deck is used. When two play, the dealer deals two cards facedown to the opponent, two cards faceup to the table, and two more facedown to himself and then repeats the process so that all have four cards. No further cards are dealt to the table.

The aim is to capture cards from the table, especially spades, aces, big casino (10 of diamonds), and little casino (2 of spades). A card played from the hand may capture by:

    Pairing — that is, by taking all other table cards of the same rank as itself. It is the only way face (court) cards can be taken.
    Combining — that is, by taking two or more table cards numerically equivalent to itself. For example, a 10 can take two 5s, or it can take a 6, 3, and ace (1).

Cards may also be won by building; a card is played to the table to form an announced combination that can be captured by another hand card on the next turn—provided that the opponent does not capture the build first. For example, a player holding two 3s may add one of them to a 3 on the table and announce, “Building 3s.” The build of 3s can subsequently be captured only by a 3, not by a 6. Or, holding a 3 and a 6, a player might play the 3 to a 3 on the table and announce, “Building 6,” in which case the build can be captured only with a 6. A numerical build, however, can be extended. For example, the opponent, holding a 2 and an 8, could play the 2 to the two 3s (provided it was announced as 6 and not 3s) and announce, “Building 8.” But no one may make a build without the relevant capturing card in hand.

Capturing all the cards on the table is called a sweep and earns a bonus point. The player indicates this fact by leaving the capturing card faceup in his pile of won cards. A player unable or unwilling to capture must trail—that is, play a card from hand to table and leave it there. It is not permissible to trail a card that can make a capture. Following a sweep, the next player can only trail.

Each time players run out of cards, the dealer deals four more cards to each until no cards remain in stock. When all cards have been played from hand and none remain in stock, the player who made the last capture adds to his won cards all the untaken table cards, but this does not count as a sweep unless it is one by definition.

Each player then scores what was won as follows: 1 point for each sweep, ace, and little casino, 2 points for big casino, 1 point for taking the most spades, and 3 points for taking the most cards (unless tied). Game is 11 or 21 points. Three- and four-handed casino games follow the same rules, with four playing in two partnerships.
"

The explanation was written by David Parlett and fact checked by The Editors of Encyclopaedia Britannica Article History.

In this version of the game, only 2 players can play. 

Also worth noting is that builds of the same number aren't limited to that number, as this ruleset explains.
This means that two stacked 3's, even if announced as "building 3's" can be captured by a 6. This is because I
couldn't implement this rule in time.

The remaining cards on the table will also not be counted in the last player who captured's final tally.


## The controls for the game are as follows: 
Player 1 and Player 2 switch between turns.

Left click a card in your hand, and then a card on a table to attempt to capture it.
After this action, it will be the other players turn.

Left click a card in your hand, and then anywhere on the table to place that card on the table.
After this action, it will be the other players turn.

Right click a card in your hand, and then left click a card on the table to attempt to stack the cards.
A window will pop up, asking for the value you are building towards. Write this value, and then click the 
"Submit" button. After this action, it will be the other players turn.

Right click a card on the table, and then left click another card on the table to attempt to stack the cards.
A window will pop up, asking for the value you are building towards. Write this value, and then click the 
"Submit" button. After this action, it will still be your turn.

When the deck is empty and the last player card is played, the game will end and points will be displayed.

 ## Additional information about the project:
 My plan is for one player to be able to play against an AI opponent, this is currently a work in progress.

 Because of the amount of logic in the rules, and the way the hand's and table interact, the CassinoModel class contains a large amount of the code in this project, compared to the other classes. 

 I also decided early on not to use a controller class, as the only input would be clicks on the objects in the Jframe.
 This made it so the CassinoView class also contains a large bulk of the code in this project, and if I had to start 
 over I probably would have tried to write some of it in a seperate controller class.
 
 Here is a link to a video of a playthrough: https://youtu.be/eqf6mn3kJTs


