import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
                    "  _ _ _ \n", br.toString());
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
                    "  _ _ _ \n", br.toString());
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

  @Test
  public void testCreateBoardWithNegativeSizeThrowsError() {
    assertThrows(IllegalArgumentException.class, () ->
            new Board(-1));
  }

  @Test
  public void testCreateBoardWithZeroSizeThrowsError() {
    assertThrows(IllegalArgumentException.class, () ->
            new Board(0));
  }

  @Test
  public void testStateOfBoardBeforeInitialization() {
    Board newBoard = new Board(4);
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.getDiscAt(3, -2));
  }

  @Test
  public void moveOnOccupiedCellThrowsError() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.makeMove(new Coordinate(3, -2)));
  }

  @Test
  public void testMakeMoveOnBoundary() {
    Board newBoard = new Board(3);
    newBoard.playGame();
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.makeMove(new Coordinate(4, -2)));
  }

  @Test
  public void testMakeMoveOutsideBoundary() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.makeMove(new Coordinate(5, -2)));
  }

  @Test
  public void testBoardRendererWithNullBoard() {
    assertThrows(IllegalArgumentException.class, () ->
            new BoardRenderer(null));
  }

  @Test
  public void testHashCodeConsistencyWithCoordiate() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    assertEquals(c1.hashCode(), c2.hashCode());
  }

  @Test
  public void testChangingContentOfCellMultipleTimes() {
    Cell c = new Cell(Disc.BLACK);
    assertEquals(c.getContent(), Disc.BLACK);
    c.setContent(Disc.EMPTY);
    assertEquals(c.getContent(), Disc.EMPTY);
    c.setContent(Disc.WHITE);
    assertEquals(c.getContent(), Disc.WHITE);
  }

  @Test
  public void testPlayersPassWithoutEndGame() {
    Board newBoard = new Board(3);
    newBoard.playGame();
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.passTurn();
    assertFalse(newBoard.isGameOver());
  }

  @Test
  public void testCreateBoardWithSize5() {
    Board newBoard = new Board(5);
    newBoard.playGame();
    BoardRenderer br = new BoardRenderer(newBoard);
    assertEquals(
            "    _ _ _ _ _ \n" + //
                    "   _ _ _ _ _ _ \n" + //
                    "  _ _ _ _ _ _ _ \n" + //
                    " _ _ _ X O _ _ _ \n" + //
                    "_ _ _ O _ X _ _ _ \n" + //
                    " _ _ _ X O _ _ _ \n" + //
                    "  _ _ _ _ _ _ _ \n" + //
                    "   _ _ _ _ _ _ \n" + //
                    "    _ _ _ _ _ \n", br.toString());
  }

  @Test
  public void testFullGameWithMakeMove() {
    Board newBoard = new Board(3);
    newBoard.playGame();
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.makeMove(new Coordinate(-1, 2));
    newBoard.makeMove(new Coordinate(1, 1));
    newBoard.makeMove(new Coordinate(-1, -1));
    newBoard.makeMove(new Coordinate(2, -1));
    assertTrue(newBoard.isGameOver());
  }

}
