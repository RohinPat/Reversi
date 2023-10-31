
//import org.junit.Assert;
//import org.junit.Before;
import org.junit.Test;

//import java.util.ArrayList;
//import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import view.BoardRenderer;

/**
 * Contains unit tests for validating the functionality of the game's textual 
 * view and related operations.
 */
public class TextualViewTests {
  @Test
  public void testBoardIsCreatedCorrectly() {
    Board newBoard = new Board(4);
    newBoard.playGame();

    BoardRenderer br = new BoardRenderer(newBoard);
    assertEquals(
                "   _ _ _ _ \n" + //
                "  _ _ _ _ _ \n" + //
                " _ _ X O _ _ \n" + //
                "_ _ O _ X _ _ \n" + //
                " _ _ X O _ _ \n" + //
                "  _ _ _ _ _ \n" + //
                "   _ _ _ _ \n", br.toString());
  }

  @Test 
  public void testGameOverWithBoardOfSize2() {
    Board newBoard = new Board(2);
    newBoard.playGame();
    BoardRenderer br = new BoardRenderer(newBoard);
    System.out.println(br.toString());
    assertTrue(newBoard.isGameOver());
  }

  @Test 
  public void testGameOverWithBoardOfSize3() {
    Board newBoard = new Board(3);
    newBoard.playGame();
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.makeMove(new Coordinate(2, -1));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-1, -1));
    newBoard.passTurn();
    BoardRenderer br = new BoardRenderer(newBoard);
    assertEquals(
            "  _ X _ \n" + //
            " O O O O \n" + //
            "_ X _ O _ \n" + //
            " X X O _ \n" + //
            "  _ _ _ \n" + //
            "", br.toString());
  }

  @Test
  public void testEmptyCellBetweenNoCaptureAndLegalMove() {
    Board newBoard = new Board(3);
    newBoard.playGame();
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.makeMove(new Coordinate(2, -1));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-1, -1));
    newBoard.passTurn();
    newBoard.placeDisc(-2, 1, Disc.WHITE);
    newBoard.placeDisc(-1, 0, Disc.EMPTY);
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-1, 0));
    BoardRenderer br = new BoardRenderer(newBoard);
    assertEquals(
        "  _ X _ \n" + //
        " O X O O \n" + //
        "_ X _ O _ \n" + //
        " O X O _ \n" + //
        "  _ _ _ \n" + //
        "", br.toString());
  }

  @Test
  public void testPlaceDiscAt() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertEquals(Disc.EMPTY, newBoard.getDiscAt(3, -2));
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertEquals(Disc.BLACK, newBoard.getDiscAt(3, -2));
  }

  @Test
  public void testGetDiscAt() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertEquals(Disc.BLACK, newBoard.getDiscAt(3, -2));
  }

  @Test
  public void testGetDiscAtWithOutOfBoardArgumentThrowsError() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.getDiscAt(10, 10));
  }

  @Test
  public void testGetPlaceDiscAtOutOfBoardArgumentThrowsError() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.placeDisc(10, 10, Disc.EMPTY));
  }

  @Test
  public void testTwoConsecutivePassesEndsGame() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    newBoard.passTurn();
    newBoard.passTurn();
    assertTrue(newBoard.isGameOver());
  }

  @Test
  public void testValidMoveResetsTheConsecutivePassCounter() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertFalse(newBoard.isGameOver());
  }


  //This is co pilot cooking 
  @Test
  public void testIsCellEmpty() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertTrue(newBoard.isCellEmpty(3, -2));
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertFalse(newBoard.isCellEmpty(3, -2));
  }

  @Test
  public void testIsCellEmptyWithOutOfBoardArgumentThrowsError() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.isCellEmpty(10, 10));
  }

  @Test
  public void testGetBoardSize() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertEquals(4, newBoard.getSize());
  }

  @Test
  public void testSetContentChangesCellContent() {
    Cell c = new Cell(Disc.BLACK);
    assertEquals(c.getContent(), Disc.BLACK);
    c.setContent(Disc.EMPTY);
    assertEquals(c.getContent(), Disc.EMPTY);
  }
  
  /*
   * Board Initialization:

Test creating a board with negative or zero size. It should probably throw an exception.
Test the state of the board right after initialization without calling playGame.
Move Validations:

Test making a move to an already occupied cell.
Test making a move that doesn't result in capturing any opponent discs.
Test making moves on the boundaries of the board.
Test making a move outside the boundaries of the board.
Game Progression:

Simulate a complete game with alternating valid moves between players and verify the final state.
Test the scenario where a player has no valid moves and is forced to pass.
Renderer:

Test BoardRenderer with a null board or appendable. It should handle or throw appropriate exceptions.
If you plan on adding more rendering features (e.g., highlighting possible moves, showing scores), ensure you have tests for those.
Coordinate:

Test the equals method with non-Coordinate objects.
Test the hashCode method's consistency. Given the same coordinate values, the hash code should be the same.
Cell:

Test changing the content of a cell multiple times.
Test getting the content of a cell after various changes.
Consecutive Passes:

Test scenarios where players pass turns consecutively, but not enough to end the game.
Capture Mechanics:

Test the capturing mechanics in all possible directions (not just a straight line).
Edge Cases:

For methods that accept arguments, consider edge cases (e.g., null values, extreme values).
Error Handling:

Ensure that your methods throw the expected exceptions under erroneous conditions.
Test how the classes handle unexpected states or inputs.
Performance:

While this isn't a unit test per se, consider performance tests especially if the board size can be large. This will help you ensure the game remains performable under larger scenarios.
Integration Tests:

While the provided tests are more unit-test in nature (testing individual components), consider writing tests that cover the interaction of multiple components. This can help catch issues that might not surface when components are tested in isolation.
   */

}
