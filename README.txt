# README for Hexagonal Reversi Game model

## Overview

**Problem Addressed:** This codebase offers a model representation for Reversi, but with a unique twist: the board is hexagonal. This design allows users to manage game states, player turns, and board configurations in a hexagonal grid system.

**Extra Credit:**
- AI 2 and 3 are implemented in the controller folder under the astral folder.  The file name for the code that ignores spaces near corners is AvoidCorners.java and the AI that try to capture corners is CaptureCorner.java.  We also implemented the multiple strategies under TryTwo.java in the same folder.

**Assumptions:**

- The game operates on a hexagonal grid system.
- Players use discs (black or white) to play.
- Users possess a basic understanding of the Reversi game.

## Quick Start

Board gameBoard = new Board(8); // Initialize a board with 8 hexagonal rows
Cell cell = new Cell(Disc.EMPTY); // Create an empty cell
Coordinate coord = new Coordinate(4, 4); // Define a coordinate for the center of the board
gameBoard.setCell(coord, cell); // Place the initial cells on the board
BoardRenderer renderer = new BoardRenderer(gameBoard); // Create a renderer for the board
System.out.println(renderer.toString()); // Display the board

## Key Components

### model Package

- **Board:** The heart of the game, representing the hexagonal game board. It manages the hexagonal grid, keeps track of turns, and orchestrates game progression.
- **Cell:** Represents an individual hexagonal slot on the board, which can hold a disc.
- **Coordinate:** Manages hexagonal positioning on the board using a unique triple-coordinate system.
- **Disc:** Enumerated type representing the possible states of a cell: black, white, or empty.
- **Turn:** Enumerated type indicating whose turn it is, either black or white.
- **GameState:** Enumerated type indicating the state of the game.
- **BoardRenderer:** Handles the visual representation of the board, allowing users to view the current state of the game.

### view Package

- **BoardRenderer:** Renders the game board visually.
- **BoardPanel:** Handles the graphical representation of the board in the UI.
- **Hexagon:** Represents hexagonal shapes on the game board.
- **ReversiFrame:** Main frame of the game's user interface.

### Controller Package

- **HumanPlayer, AIPlayer:** Represent human and AI players.
- **Player Interface:** Supports human and AI players through makeMove() and getPlayerType() methods.
- **AI Strategies (aistrat Directory):** Implementations of various AI strategies like AvoidCorners, CaptureCorners, CaptureMost, ReversiStratagy, and TryTwo.

### Source Organization

- **model Package:** Contains logic for the game's model, including board, cells, coordinates, and game states.
- **view Package:** Manages the user interface, rendering the game board and handling user interactions.
- **Controller Package:** Implements player functionalities and AI strategies, orchestrating the game play.
