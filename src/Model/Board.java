package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Board {
  private Map<Coordinate, Cell> grid;
  private final int size;
  private Turn whoseTurn;


  public Board(int size) {
    this.size = size;
    this.grid = new HashMap<>();
    this.whoseTurn = Turn.BLACK;
  }

  public void playGame(Board board){
    // this first loop sets up the first half of rows not including the middle row
    for (int i = 0; i < board.size - 1; i++) {
        for (int j = -i; j < board.size; j++) {
            grid.put(new Coordinate(j, -(board.size - 1 - i)), new Cell(Disc.EMPTY));
        }
    }

    // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every cell to be empty at first
    for (int i = 0; i < board.size; i ++){
      for (int j = -(board.size - 1); j < board.size - 1; j++){
        grid.put(new Coordinate(j, i), new Cell(Disc.EMPTY));
      }
    }

    //i'm thinking of adding the starting pieces in the constructor as well but this should be easily moveable
    grid.put(new Coordinate(1, 0), new Cell(Disc.BLACK));
    grid.put(new Coordinate(1, -1), new Cell(Disc.WHITE));
    grid.put(new Coordinate(0, -1), new Cell(Disc.BLACK));
    grid.put(new Coordinate(-1, 0), new Cell(Disc.WHITE));
    grid.put(new Coordinate(-1, 1), new Cell(Disc.BLACK));
    grid.put(new Coordinate(0, 1), new Cell(Disc.WHITE));
  }

  // make a playGame method - in that add the board setup kinda like startGame

  // maybe create a list of players that is initialized at the start, and create a switch helper that keeps track of whose move it is and it should update after each person's move - this could just be the pass method because we need a method to just pass to the other player

  // make a move method - should do all the checks to make sure a certain move is legal + take in target place to move + who's move it is?

  // make a game over method


  private Disc oppositeColor(){
    if (this.whoseTurn == Turn.BLACK){
      return Disc.WHITE;
    }
    else{
      return Disc.BLACK;
    }
  }
  

  // used to swap turns either when a player passes their turn or at the end of their move
  public void passTurn(){
    if (this.whoseTurn == Turn.BLACK){
      this.whoseTurn = Turn.WHITE;
    }
    else{
      this.whoseTurn = Turn.BLACK;
    }
  }

  private void horizontalLeftMoveCheck(Coordinate dest){
    // first check left - check if the first one is opposite, and if so keep going until you find a piece of the same color
    // then check right - check if the first one is opposite, and if so keep going until you find a piece of the same color
    // add each visited and valid one to an array list

    ArrayList<Coordinate> captured = new ArrayList<>();

    if (grid.get(new Coordinate(dest.getQ() - 1, dest.getR())).getContent() == this.oppositeColor()){
      captured.add(new Coordinate(dest.getQ() - 1, dest.getR()));
    }
  }

  public void makeMove(Coordinate dest){
    // check if its legal in both horizontal directions
    // check if its legal in both sides on /
    // check if its legal in both sides on \

    // if even one of these is valid, then go into that one - maybe use helpers for checking each direction
  }


  public void placeDisc(int q, int r, Disc disc) {
    grid.get(new Coordinate(q, r)).setContent(disc);
  }

  public Disc getDiscAt(int q, int r) {
    return grid.get(new Coordinate(q, r)).getContent();
  }


  public boolean isCellEmpty(int q, int r) {
    return grid.get(new Coordinate(q, r)).getContent() == Disc.EMPTY;
  }

  
  public int getSize() {
    return size;
  }



}
