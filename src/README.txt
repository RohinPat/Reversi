# README for Hexagonal Reversi Game Model

## Overview

**Problem Addressed:** This codebase offers a model representation for Reversi, but with a unique twist: the board is hexagonal. This design allows users to manage game states, player turns, and board configurations in a hexagonal grid system.

**Assumptions:**
- The game operates on a hexagonal grid system.
- Players use discs (black or white) to play.
- Users possess a basic understanding of the Reversi game.

## Quick Start

Board gameBoard = new Board(8); // Initialize a board with 8 hexagonal rows
Cell cell = new Cell(Disc.EMPTY); // Create an empty cell
Coordinate coord = new Coordinate(4, 4); // Define a coordinate for the center of the board
gameBoard.setCell(coord, cell); // Place the cell on the board
BoardRenderer renderer = new BoardRenderer(gameBoard); // Create a renderer for the board
System.out.println(renderer.toString()); // Display the board

## Key Components

- **Board:** The heart of the game, representing the hexagonal game board. It manages the hexagonal grid, keeps track of turns, and orchestrates game progression.
- **Cell:** Represents an individual hexagonal slot on the board, which can hold a disc.
- **Coordinate:** Manages hexagonal positioning on the board using a unique triple-coordinate system.
- **Disc:** Enumerated type representing the possible states of a cell: black, white, or empty.
- **Turn:** Enumerated type indicating whose turn it is, either black or white.
- **BoardRenderer:** Handles the visual representation of the board, allowing users to view the current state of the game.

## Key Subcomponents

- **Board:**
  - `grid`: A mapping that connects hexagonal coordinates to cells, effectively representing the game board.
  - `whoseTurn`: Specifies which player's turn it is, either black or white.
  - `compassQ`, `compassR`: These directional mappings are essential for navigating the hexagonal grid and identifying neighbors.
  
- **Cell:** 
  - `content`: The disc present in the cell, which can be one of three states: black, white, or empty.
  
- **Coordinate:** 
  - `q`, `r`, `s`: The three integers that form the custom hexagonal coordinate system. These are designed to uniquely identify positions on the hexagonal board.

- **BoardRenderer:**
  - `model`: An instance of the `Board` class, representing the game state to be rendered.
  - `ap`: An `Appendable` object used for constructing the visual representation.

## Source Organization

- **Model Package:**
  - `Board.java`: Central to the game's logic, this file describes the hexagonal board and the interactions possible on it.
  - `Cell.java`: Details the structure and behavior of individual hexagonal slots on the board.
  - `Coordinate.java`: Contains the logic for the hexagonal coordinate system, allowing for unique cell identification and navigation.
  - `Disc.java`: Defines the possible states a cell can hold: black, white, or empty.
  - `Turn.java`: Dictates player turns, ensuring the game flows smoothly between black and white players.

- **View Package:**
  - `BoardRenderer.java`: Responsible for visually rendering the game board, providing users with a view of the current game state.
