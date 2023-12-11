package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ControllerFeatures;
import controller.ReversiController;
import controller.aistrat.CaptureCorners;


public class SquareBoard extends AbstractModel{

  protected final HashMap<String, Integer> compassX = new HashMap<>();
  protected final HashMap<String, Integer> compassY = new HashMap<>();


  public SquareBoard(int size){
    super(size);
    if (size <= 0 || size % 2 != 0){
      throw new IllegalArgumentException("Board size must be positive and even");
    }
    compassX.put("left", -1);
    compassX.put("right", 1);
    compassX.put("up", 0);
    compassX.put("down", 0);
    compassX.put("ne", 1);
    compassX.put("nw", -1);
    compassX.put("se", 1);
    compassX.put("sw", -1);


    compassY.put("left", 0);
    compassY.put("right", 0);
    compassY.put("up", -1);
    compassY.put("down", 1);
    compassY.put("ne", -1);
    compassY.put("nw", -1);
    compassY.put("se", 1);
    compassY.put("sw", 1);
    playGame();
  }

  private void playGame() {
    if (gameState == GameState.PRE) {
      gameState = GameState.INPROGRESS;

      for (int row = 0; row < size ; row++){
        for (int column = 0; column < size; column++){
          grid.put(new CartesianCoordinate(column, row), new Cell(Disc.EMPTY));
        }
      }


      grid.put(new CartesianCoordinate(size/2 - 1, size/2 - 1), new Cell(Disc.BLACK)); // top left
      grid.put(new CartesianCoordinate(size/2, size/2 - 1), new Cell(Disc.WHITE)); // top right
      grid.put(new CartesianCoordinate(size/2, size/2), new Cell(Disc.BLACK)); // bottom right
      grid.put(new CartesianCoordinate(size/2 - 1, size/2), new Cell(Disc.WHITE)); // bottom left
    } else {
      throw new IllegalStateException("A game has already been started");
    }

    notifyObservers();
  }

  private ArrayList<Integer> moveHelper(Position dest, String dir) {
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    CartesianCoordinate nextPiece = new CartesianCoordinate((dest.getFirstCoordinate() + compassX.get(dir)), (dest.getSecondCoordinate()
            + compassY.get(dir)));

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor()) {
      while (grid.containsKey(nextPiece) && !endFound) {
        if (grid.get(nextPiece).getContent() == this.currentColor()) {
          endFound = true;
        } else {
          captured.add(nextPiece.getFirstCoordinate());
          captured.add(nextPiece.getSecondCoordinate());
          nextPiece = new CartesianCoordinate((nextPiece.getFirstCoordinate() + compassX.get(dir)), (nextPiece.getSecondCoordinate()
                  + compassY.get(dir)));
        }
      }

      if (!endFound) {
        captured.clear();
        return captured;
      }

      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured) {
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()) {
        int x = capturedCopy.get(0);
        int y = capturedCopy.get(1);
        if (!grid.get(new CartesianCoordinate(x, y)).getContent().equals(this.oppositeColor())) {
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove) {
        captured.add(dest.getFirstCoordinate());
        captured.add(dest.getSecondCoordinate());
        return captured;
      } else {
        captured.clear();
        return captured;
      }
    } else {
      captured.clear();
      return captured;
    }
  }

  @Override
  public void makeMove(Position dest) {
    CartesianCoordinate dest1 = new CartesianCoordinate(dest.getFirstCoordinate(), dest.getSecondCoordinate());
    if (gameState != GameState.PRE) {
      if (!grid.keySet().contains(new CartesianCoordinate(dest.getFirstCoordinate(), dest.getSecondCoordinate()))) {
        throw new IllegalArgumentException("This space does not exist on the board");
      }

      System.out.println(dest1);
      if (grid.get(dest1).getContent() != Disc.EMPTY) {
        throw new IllegalArgumentException("This space is already occupied");
      }

      if (dest == null) {
        throw new IllegalArgumentException("Can't pass in a null coordinate");
      }

      boolean validMove = false;
      ArrayList<String> errors = new ArrayList<>();
      ArrayList<Integer> allcaptured = new ArrayList<>();

      for (String direction : compassX.keySet()) {
        try {
          ArrayList<Integer> caught = this.moveHelper(dest, direction);
          allcaptured.addAll(caught);
          validMove = true;
        } catch (IllegalArgumentException e) {
          errors.add(e.getMessage());
        }
      }

      if (!validMove) {
        throw new IllegalArgumentException("Invalid move. Reasons: "
                + String.join(", ", errors));
      }

      if (allcaptured.isEmpty()) {
        throw new IllegalArgumentException("Invalid move.");
      }

      while (!allcaptured.isEmpty()) {
        int q = allcaptured.remove(0);
        int r = allcaptured.remove(0);
        this.placeDisc(q, r, this.currentColor());
      }
      this.passTurn();
      this.consecPasses = 0;
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }

    notifyObservers();
    notifyTurnChange();
  }

  public void placeDisc(int q, int r, Disc disc) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      grid.get(new CartesianCoordinate(q, r)).setContent(disc);
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  public Disc getDiscAt(int q, int r) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      return grid.get(new CartesianCoordinate(q, r)).getContent();
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  public boolean isCellEmpty(int q, int r) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      return grid.get(new CartesianCoordinate(q, r)).getContent() == Disc.EMPTY;
    } else {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
  }

  public int getScoreForPlayer(ReversiReadOnly model, Position move, Disc player){
    return 0;
  }

}