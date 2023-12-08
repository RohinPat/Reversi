Overview: The current codebase envisions a model for a 2-Player Reversi game using hexagonal tiles, that can handle various board shapes and sizes. Currently, the main/only board shape that can be created is a regular hexagon with any radius > 1. However, many other board shapes can be easily created with a new implementation of an IBoard.
We decided to close our model off to more than 2 player reversi, or differently shaped tiles, as this would be overgeneralizing. There are ways to change the rules of disk capturing/placing through new implementations of the placeDisk method on the main model mutable interface. Knowledge about cube coordinates is very helpful for understanding this codebase (see pdf files in this docs folder)

Quick start:

Create a board, currently there is only a hexagon board that can be customized with radius and optional starting tiles for each player.
Create a mutable model, no parameters are needed.
Create two players.
You can start the game by passing in a board and the two different players.

    HexagonBoard hexBoard = new HexagonBoard(4);
    ReversiMutableModel rmm = new BasicReversi();
    rmm.startGame(hexBoard);


Key components: At the highest level we start with the Mutable and ReadOnly Models. They are two parts to an overall reversi model game. A mutable model has a way to start the game, allows for a player to attempt to place a disk, and allows for a player to attempt to pass.
The read only model allows for a safe way to observe the board, guaranteeing that no mutations will be made on the given model, for example the textual view can take in a read only model since it doesn't need the ability to mutate the model. Using the read only model, you can observe the current board, check if the game is over, count the given player's claimed tiles, and check if it is this player's turn.
The textual view is another key component, that is responsible for rendering a model's current board state. The player is independent of the model, view, or controller to come. You can see our current/intended use of player in the PlayerInterfacePlans.txt file.

Key subcomponents: The board is part of every model, which is generated through a BoardGenerator. BoardGenerator allows for any type of board to be easily created in the future. The generated is passed through startGame in order to provide a model with a board. Each board is represented through a map from a cube coordinate to a hexagonal tile.
A HexCoord tuples this three parameter coordinate while a HexagonTile represents the hexagon shaped tile, storing information about its state (which player owns the tile, see PlayerOwnership).

Source organization: In the model package, we have the ReadOnly/Mutable Model interfaces and its implementation: BasicReversi. We also have an IBoard interface, with a single HexagonBoard implementation. We have a BoardGenerator interface with a single HexagonGenerator implementation.
In the player package, we have the placeholder Player interface with a dummy implementation. In the view package, we have the textual view that can render a model.

User interaction: Select a cell by clicking it, deselect a cell by clicking the currently selected cell.
Press m to attempt a move at the currently highlighted cell. Press p to attempt to pass your turn.


Changes for part 2:
- made model store player turn as an enum PlayerOwnership instead of as a direct reference to a player instance to not provide the player such direct access to the model
- added a clone method on the read only model in order to implement strategies for a player
- added a way to observe if a move is logical, ignoring the true turn of the model, which was helpful for minimax

New Interfaces:
FallableReversiStrategy - a strategy that might fail. In the case of failure, returns an empty optional.
InfalableReversiStrategy - a strategy that will never fail. Returns a list of pairs containing a coordinate and
        its corresponding score.
ReversiStrategyTieBreaker - a tiebreaker for the results of a strategy wrapper.
ReversiView - The ReversiView interface represents the view component in the MVC architecture for the Reversi game.
It describes what a concrete view must be able to do in order to properly fit into our application.
ReversiPanelView - The panel interface represents the main panel inside the frame of the view. It describes what a concrete panel view must be able to do in order to properly function with the main ReversiView.
ViewFeatures - describes the set of features that views can handle given some user interactions

New Classes:
Pair - A utility class for containing two values in one return statement
Reversi - The main class that wires all the main components up to each other
DebugFeatures - an impl. of view features that allows us to debug (through system print msgs) user moves and passes
ReversiGUIView - a basic impl. of ReversiView using the jswing library for GUI creation
ReversiPanel - a basic impl. of ReversiPanelView to accommodate the main ReversiGUIView impl.

Strategies:
AvoidTilesNextToCornersStrategy impl Fallable Strategy - returns any possible moves not next to a corner.
FallableInfallablePairStrategy  impl Infallable Strategy - pairs a fallable to an infallable as a backup
MinimaxStrategy impl Infallable Strategy - chooses the move that maximizes this player's score and minimizes
       that player's score.
MostPointsGainedStrategy impl Infallable Strategy - returns the position(s) that yield the greatest score increase.
PlayCornersStrategy imp Fallable Strategy - returns moves in corners, if any.
TryManyStrategies impl Fallable Strategy - chains together many fallable strategies, trying the next if the
        previous doesn't work.
Strategy Helpers:
TopLeftTieBreaker - Returns the position in the given list closest to the top left of the board.
StrategyWrapper - executes an infallable strategy and parses the result to only return positions.
PossibleMoveGenerator - Returns a list of all the possible legal moves for a player on a board.


Changes for part 3:

Major modifications of existing files:
- We altered the main class (Reversi) to run a configurable jar file
- We added an alert error message promise to the reversi view interfaces and implemented it in the respective view implementations
- We added an add turn listener promise to our ReversiMutableModel interface and implemented it in the respective model implementations

New interfaces:
- We added a ReversiController interface for a controller to interact with our model and views
- We added a ControllerTurnListener that acts as any object that wants to listen to a controller for updates
- We added a ModelTurnListener that acts as any object that wants to listen to a model for updates

New classes:
- BasicReversiController is our implementation of a controller for ReversiController, which also is an implementation of ModelTurnListener
- HumanPlayer, ComputerPlayer are two types of players that also are ControllerTurnListeners


