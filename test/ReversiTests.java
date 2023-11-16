import org.junit.Test;

import controller.aistrat.AvoidCorners;
import controller.aistrat.CaptureCorners;
import controller.aistrat.CaptureMost;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.GameState;
import model.Turn;
import view.BoardRenderer;

/**
 * Contains unit tests for validating the functionality of the game's textual
 * view and related operations.
 */
public class ReversiTests {

  /**
   * Houses example constructions of various objects used in our model.
   */
  private void initExamples() {
    Board board1 = new Board(3);
    Board board2 = new Board(4);
    Board board3 = new Board(10);

    Cell blackCell = new Cell(Disc.BLACK);
    Cell whiteCell = new Cell(Disc.WHITE);
    Cell emptyCell = new Cell(Disc.EMPTY);

    Coordinate c1 = new Coordinate(1, 1);
    Coordinate c2 = new Coordinate(-1, -1);
    Coordinate c3 = new Coordinate(2, -3);

    Turn whiteTurn = Turn.WHITE;
    Turn blackTurn = Turn.BLACK;

    GameState preGame = GameState.PRE;
    GameState blackWinGame = GameState.BLACKWIN;
    GameState whiteWinGame = GameState.WHITEWIN;
    GameState tieGame = GameState.TIE;

  }

  @Test
  public void testBoardIsCreatedCorrectly() {
    Board newBoard = new Board(4);

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
  public void testGameOverWithBoardOfSize3() {
    Board newBoard = new Board(3);
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
  public void testPassMoveSwitchesTurn() {
    Board newBoard = new Board(3);
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-2, 1));
    BoardRenderer br = new BoardRenderer(newBoard);
    assertEquals(
            "  _ _ _ \n" +
                    " _ X O _ \n" +
                    "_ O _ X _ \n" +
                    " O O O _ \n" +
                    "  _ _ _ \n", br.toString());

  }

  @Test
  public void testEmptyCellBetweenNoCaptureAndLegalMove() {
    Board newBoard = new Board(3);
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
    assertEquals(Disc.EMPTY, newBoard.getDiscAt(3, -2));
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertEquals(Disc.BLACK, newBoard.getDiscAt(3, -2));
  }

  @Test
  public void testGetDiscAt() {
    Board newBoard = new Board(4);
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertEquals(Disc.BLACK, newBoard.getDiscAt(3, -2));
  }

  @Test
  public void testGetDiscAtWithOutOfBoardArgumentThrowsError() {
    Board newBoard = new Board(4);
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.getDiscAt(10, 10));
  }

  @Test
  public void testGetPlaceDiscAtOutOfBoardArgumentThrowsError() {
    Board newBoard = new Board(4);
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.placeDisc(10, 10, Disc.EMPTY));
  }

  @Test
  public void testTwoConsecutivePassesEndsGame() {
    Board newBoard = new Board(4);
    newBoard.passTurn();
    newBoard.passTurn();
    assertTrue(newBoard.isGameOver());
  }

  @Test
  public void testIsCellEmpty() {
    Board newBoard = new Board(4);
    assertTrue(newBoard.isCellEmpty(3, -2));
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertFalse(newBoard.isCellEmpty(3, -2));
  }

  @Test
  public void testIsCellEmptyWithOutOfBoardArgumentThrowsError() {
    Board newBoard = new Board(4);
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.isCellEmpty(10, 10));
  }

  @Test
  public void testGetBoardSize() {
    Board newBoard = new Board(4);
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
  public void moveOnOccupiedCellThrowsError() {
    Board newBoard = new Board(4);
    newBoard.placeDisc(3, -2, Disc.BLACK);
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.makeMove(new Coordinate(3, -2)));
  }

  @Test
  public void testMakeMoveOnBoundary() {
    Board newBoard = new Board(3);
    assertThrows(IllegalArgumentException.class, () ->
            newBoard.makeMove(new Coordinate(4, -2)));
  }

  @Test
  public void testMakeMoveOutsideBoundary() {
    Board newBoard = new Board(4);
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
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.passTurn();
    assertFalse(newBoard.isGameOver());
  }

  @Test
  public void testCreateBoardWithSize5() {
    Board newBoard = new Board(5);
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
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.makeMove(new Coordinate(-1, 2));
    newBoard.makeMove(new Coordinate(1, 1));
    newBoard.makeMove(new Coordinate(-1, -1));
    newBoard.makeMove(new Coordinate(2, -1));
    assertTrue(newBoard.isGameOver());
  }

  @Test
  public void scoresAtGameInitAre3and3BecauseofStartingPieces() {
    Board newBoard = new Board(3);
    assertEquals(3, newBoard.getScore(Disc.BLACK));
    assertEquals(3, newBoard.getScore(Disc.WHITE));
  }

  @Test
  public void testTieSituation() {
    Board newBoard = new Board(2);
    newBoard.isGameOver();
    assertEquals(GameState.TIE, newBoard.getState());
  }

  @Test
  public void testWhiteWinSituation() {
    Board newBoard = new Board(3);
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.makeMove(new Coordinate(2, -1));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-1, -1));
    newBoard.passTurn();
    newBoard.passTurn();
    newBoard.isGameOver();
    assertEquals(GameState.WHITEWIN, newBoard.getState());
  }


  @Test
  public void testBlackWinSituation() {
    Board newBoard = new Board(3);
    newBoard.makeMove(new Coordinate(-1, 2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(-1, -1));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(2, -1));
    newBoard.isGameOver();
    assertEquals(GameState.BLACKWIN, newBoard.getState());

  }

  @Test
  public void testBlackWinsAfter2Passes() {
    Board newBoard = new Board(3);
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.passTurn();
    newBoard.passTurn();
    newBoard.isGameOver();
    assertEquals(GameState.BLACKWIN, newBoard.getState());
  }

  @Test
  public void testWhiteWinsAfter2Passes() {
    Board newBoard = new Board(3);
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(1, 1));
    newBoard.passTurn();
    newBoard.passTurn();
    newBoard.isGameOver();
    assertEquals(GameState.WHITEWIN, newBoard.getState());
  }

  @Test
  public void testTieSituationaftertwopasses() {
    Board newBoard = new Board(3);
    newBoard.passTurn();
    newBoard.passTurn();
    newBoard.isGameOver();
    assertEquals(GameState.TIE, newBoard.getState());
  }

  @Test
  public void testSingleMoveIsReflectedCorrectly() {
    Board newBoard = new Board(4);
    newBoard.makeMove(new Coordinate(1, -2));
    BoardRenderer br = new BoardRenderer(newBoard);
    assertEquals(
            "   _ _ _ _ \n" +
                    "  _ _ X _ _ \n" +
                    " _ _ X X _ _ \n" +
                    "_ _ O _ X _ _ \n" +
                    " _ _ X O _ _ \n" +
                    "  _ _ _ _ _ \n" +
                    "   _ _ _ _ \n", br.toString());
  }

  @Test
  public void testMultipleMoveIsReflectedCorrectly() {
    Board newBoard = new Board(4);
    newBoard.makeMove(new Coordinate(1, -2));
    newBoard.makeMove(new Coordinate(-2, 1));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(2, -3));
    BoardRenderer br = new BoardRenderer(newBoard);
    assertEquals(
            "   _ _ O _ \n" +
                    "  _ _ O _ _ \n" +
                    " _ _ O X _ _ \n" +
                    "_ _ O _ X _ _ \n" +
                    " _ O O O _ _ \n" +
                    "  _ _ _ _ _ \n" +
                    "   _ _ _ _ \n", br.toString());
  }

  @Test
  public void testAICaptureMost() {
    BoardMo
  }
}
