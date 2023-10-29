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

  public void playGame(){
    // this first loop sets up the first half of rows not including the middle row
    for (int i = 0; i < size - 1; i++) {
        for (int j = -i; j < size; j++) {
            grid.put(new Coordinate(j, -(size - 1 - i)), new Cell(Disc.EMPTY));
        }
    }

    // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every cell to be empty at first
    for (int i = 0; i < size; i ++){
      for (int j = -(size - 1); j < size - 1; j++){
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


  private Disc currentColor(){
    if (this.whoseTurn == Turn.BLACK){
      return Disc.BLACK;
    }
    else{
      return Disc.WHITE;
    }
  }


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
  private ArrayList<Integer> horizontalLeftMoveCheck1(Coordinate dest){
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    Coordinate nextPiece = new Coordinate(dest.getQ() - 1, dest.getR());

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor() ){
      while (grid.containsKey(nextPiece) && !endFound) {
      if (grid.get(nextPiece).getContent() == this.currentColor()){
        endFound = true;
      }
      else{
        captured.add(nextPiece.getQ());
        captured.add(nextPiece.getR());
        nextPiece = new Coordinate(nextPiece.getQ() - 1, nextPiece.getR());
      }
      }
      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured){
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()){
        int q = capturedCopy.get(0);
        int r = capturedCopy.get(1);
        if (!grid.get(new Coordinate(q, r)).getContent().equals(this.oppositeColor())){
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove){
        captured.add(dest.getQ());
        captured.add(dest.getR());
        return captured;
      }
      else{
        captured.clear();
        return captured;
      }
    }
    else{
      captured.clear();
      return captured;
    }
  }

  private ArrayList<Integer> horizontalRightMoveCheck1(Coordinate dest){
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    Coordinate nextPiece = new Coordinate(dest.getQ() + 1, dest.getR());

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor() ){
      while (grid.containsKey(nextPiece) && !endFound) {
      if (grid.get(nextPiece).getContent() == this.currentColor()){
        endFound = true;
      }
      else{
        captured.add(nextPiece.getQ());
        captured.add(nextPiece.getR());
        nextPiece = new Coordinate(nextPiece.getQ() + 1, nextPiece.getR());
      }
      }
      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured){
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()){
        int q = capturedCopy.get(0);
        int r = capturedCopy.get(1);
        if (!grid.get(new Coordinate(q, r)).getContent().equals(this.oppositeColor())){
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove){
        captured.add(dest.getQ());
        captured.add(dest.getR());
        return captured;
      }
      else{
        captured.clear();
        return captured;
      }
    }
    else{
      captured.clear();
      return captured;
    }
  }

  private ArrayList<Integer> forwardSlashUpMoveCheck1(Coordinate dest){
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    Coordinate nextPiece = new Coordinate(dest.getQ() + 1, dest.getR() - 1);

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor() ){
      while (grid.containsKey(nextPiece) && !endFound) {
      if (grid.get(nextPiece).getContent() == this.currentColor()){
        endFound = true;
      }
      else{
        captured.add(nextPiece.getQ());
        captured.add(nextPiece.getR());
        nextPiece = new Coordinate(nextPiece.getQ() + 1, nextPiece.getR() - 1);
      }
      }
      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured){
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()){
        int q = capturedCopy.get(0);
        int r = capturedCopy.get(1);
        if (!grid.get(new Coordinate(q, r)).getContent().equals(this.oppositeColor())){
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove){
        captured.add(dest.getQ());
        captured.add(dest.getR());
        return captured;
      }
      else{
        captured.clear();
        return captured;
      }
    }
    else{
      captured.clear();
      return captured;
    }
  }

  private ArrayList<Integer> forwardSlashDownMoveCheck1(Coordinate dest){
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    Coordinate nextPiece = new Coordinate(dest.getQ() - 1, dest.getR() + 1);

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor() ){
      while (grid.containsKey(nextPiece) && !endFound) {
      if (grid.get(nextPiece).getContent() == this.currentColor()){
        endFound = true;
      }
      else{
        captured.add(nextPiece.getQ());
        captured.add(nextPiece.getR());
        nextPiece = new Coordinate(nextPiece.getQ() - 1, nextPiece.getR() + 1);
      }
      }
      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured){
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()){
        int q = capturedCopy.get(0);
        int r = capturedCopy.get(1);
        if (!grid.get(new Coordinate(q, r)).getContent().equals(this.oppositeColor())){
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove){
        captured.add(dest.getQ());
        captured.add(dest.getR());
        return captured;
      }
      else{
        captured.clear();
        return captured;
      }
    }
    else{
      captured.clear();
      return captured;
    }
  }

  private ArrayList<Integer> backwardSlashUpMoveCheck1(Coordinate dest){
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    Coordinate nextPiece = new Coordinate(dest.getQ(), dest.getR() - 1);

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor() ){
      while (grid.containsKey(nextPiece) && !endFound) {
      if (grid.get(nextPiece).getContent() == this.currentColor()){
        endFound = true;
      }
      else{
        captured.add(nextPiece.getQ());
        captured.add(nextPiece.getR());
        nextPiece = new Coordinate(nextPiece.getQ(), nextPiece.getR() - 1);
      }
      }
      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured){
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()){
        int q = capturedCopy.get(0);
        int r = capturedCopy.get(1);
        if (!grid.get(new Coordinate(q, r)).getContent().equals(this.oppositeColor())){
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove){
        captured.add(dest.getQ());
        captured.add(dest.getR());
        return captured;
      }
      else{
        captured.clear();
        return captured;
      }
    }
    else{
      captured.clear();
      return captured;
    }
  }

  private ArrayList<Integer> backwardSlashDownMoveCheck1(Coordinate dest){
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    Coordinate nextPiece = new Coordinate(dest.getQ(), dest.getR() + 1);

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor() ){
      while (grid.containsKey(nextPiece) && !endFound) {
      if (grid.get(nextPiece).getContent() == this.currentColor()){
        endFound = true;
      }
      else{
        captured.add(nextPiece.getQ());
        captured.add(nextPiece.getR());
        nextPiece = new Coordinate(nextPiece.getQ(), nextPiece.getR() + 1);
      }
      }
      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured){
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()){
        int q = capturedCopy.get(0);
        int r = capturedCopy.get(1);
        if (!grid.get(new Coordinate(q, r)).getContent().equals(this.oppositeColor())){
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove){
        captured.add(dest.getQ());
        captured.add(dest.getR());
        return captured;
      }
      else{
        captured.clear();
        return captured;
      }
    }
    else{
      captured.clear();
      return captured;
    }
  }

  public void makeMove(Coordinate dest){

    boolean validMove = false;
    ArrayList<String> errors = new ArrayList<>();
    ArrayList<Integer> allcaptured = new ArrayList<>();

    try {
      ArrayList<Integer> caught = this.horizontalLeftMoveCheck1(dest);
      allcaptured.addAll(caught);
      validMove = true;
    } catch (IllegalArgumentException e) {
      errors.add(e.getMessage());
    }

    try {
      ArrayList<Integer> caught = this.horizontalRightMoveCheck1(dest);
      allcaptured.addAll(caught);
      validMove = true;
    } catch (IllegalArgumentException e) {
      errors.add(e.getMessage());
    }

    try {
      ArrayList<Integer> caught = this.forwardSlashUpMoveCheck1(dest);
      allcaptured.addAll(caught);
      validMove = true;
    } catch (IllegalArgumentException e) {
      errors.add(e.getMessage());
    }

    try {
      ArrayList<Integer> caught = this.forwardSlashDownMoveCheck1(dest);
      allcaptured.addAll(caught);
      validMove = true;
    } catch (IllegalArgumentException e) {
      errors.add(e.getMessage());
    }

    try {
      ArrayList<Integer> caught = this.backwardSlashUpMoveCheck1(dest);
      allcaptured.addAll(caught);
      validMove = true;
    } catch (IllegalArgumentException e) {
      errors.add(e.getMessage());
    }

    try {

      ArrayList<Integer> caught = this.backwardSlashDownMoveCheck1(dest);
      allcaptured.addAll(caught);
      validMove = true;
    } catch (IllegalArgumentException e) {
      errors.add(e.getMessage());
    }

    if (!validMove) {
      throw new IllegalArgumentException("Invalid move. Reasons: " + String.join(", ", errors));
    }

    if (allcaptured.isEmpty()){
      throw new IllegalArgumentException("Invalid move.");
    }

    
    while (!allcaptured.isEmpty()){
      int q = allcaptured.remove(0);
      int r = allcaptured.remove(0);
      grid.put(new Coordinate(q, r), new Cell(this.currentColor()));
    }

    this.passTurn();
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

  public Cell getGridCell(Coordinate coord) {
    return grid.get(coord);
  }


}