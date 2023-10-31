
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.Turn;
import view.BoardRenderer;

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
}
