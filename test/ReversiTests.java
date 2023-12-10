import org.junit.Assert;
import org.junit.Test;

import controller.AIPlayer;
import controller.ControllerFeatures;
import controller.ReversiControllerMock;
import controller.aistrat.AvoidCorners;
import controller.aistrat.CaptureCorners;
import controller.aistrat.CaptureMost;
import controller.aistrat.ReversiStratagy;
import controller.aistrat.TryTwo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Board;
import model.BoardMock;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.GameState;
import model.Coordinate;
import model.Reversi;
import model.SquareBoard;
import model.Turn;
import provider.model.HexCoord;
import provider.strategies.TopLeftTieBreaker;
import view.BoardPanel;
import view.BoardRenderer;
import view.IBoardPanel;
import view.ReversiFrame;

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
  public void breakTieFunctionality() {
    HexCoord h1 = new HexCoord(2, -1, -1);
    HexCoord h2 = new HexCoord(2, -3, 1);

    List<HexCoord> list = new ArrayList<HexCoord>();

    list.add(h1);
    list.add(h2);

    TopLeftTieBreaker t1 = new TopLeftTieBreaker();

    HexCoord out = t1.breakTie(list);

    assertEquals("2 -3 1", out.toString());
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
  public void testGetScoreForPlayer() {
    Board newBoard = new Board(3);
    BoardRenderer br = new BoardRenderer(newBoard);
    int white = newBoard.getScoreForPlayer(newBoard, new Coordinate(-1, -1), Disc.WHITE);
    assertEquals(white, 1);

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
    BoardMock board = new BoardMock(4);
    CaptureMost cm = new CaptureMost();

    Coordinate c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    Assert.assertEquals(board.getLog().toString(),
            "getting possible moves and found:q: -1 r: -1 s: 2\n" +
                    "q: 1 r: 1 s: -2\n" +
                    "q: -1 r: 2 s: -1\n" +
                    "q: 1 r: -2 s: 1\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: 1 r: 1 s: -2\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "q: -2 r: -1 s: 3\n" +
                    "getting possible moves and found:q: 1 r: 1 s: -2\n" +
                    "q: -1 r: -2 s: 3\n" +
                    "q: -1 r: 2 s: -1\n" +
                    "q: 1 r: -2 s: 1\n" +
                    "getting possible moves and found:q: -1 r: 2 s: -1\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: 1 r: 1 s: -2\n" +
                    "q: -1 r: -2 s: 3\n" +
                    "getting possible moves and found:q: -1 r: 2 s: -1\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: 3 r: -1 s: -2\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "getting possible moves and found:q: 3 r: -2 s: -1\n" +
                    "q: -1 r: 3 s: -2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: -1 r: -2 s: 3\n" +
                    "q: -2 r: 3 s: -1\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: -3 r: 2 s: 1\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "getting possible moves and found:q: -1 r: 3 s: -2\n" +
                    "q: -3 r: 1 s: 2\n" +
                    "q: -1 r: -2 s: 3\n" +
                    "q: -2 r: 3 s: -1\n");
  }

  @Test
  public void testNoCornersToCapture() {
    BoardMock board = new BoardMock(4);
    CaptureCorners cm = new CaptureCorners();
    Assert.assertEquals(new Coordinate(4, 4), cm.chooseMove(board, Disc.BLACK));
  }

  @Test
  public void testAvoidCorners() {
    BoardMock board = new BoardMock(4);
    ReversiStratagy cm = new TryTwo(new AvoidCorners(), new CaptureMost());

    Coordinate c = cm.chooseMove(board, Disc.BLACK);


    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = cm.chooseMove(board, Disc.BLACK);
    System.out.println(c.getFirstCoordinate() + " " + c.getSecondCoordinate());
    Assert.assertEquals(c, new Coordinate(1, -3));
    Assert.assertEquals(board.getLog().toString(),
            "getting possible moves and found:q: -1 r: -1 s: 2\n" +
                    "q: 1 r: 1 s: -2\n" + //
                    "q: -1 r: 2 s: -1\n" + //
                    "q: 1 r: -2 s: 1\n" + //
                    "q: -2 r: 1 s: 1\n" + //
                    "q: 2 r: -1 s: -1\n" + //
                    "getting possible moves and found:q: 1 r: 1 s: -2\n" + //
                    "q: -2 r: 1 s: 1\n" + //
                    "q: 2 r: -1 s: -1\n" + //
                    "q: -2 r: -1 s: 3\n" + //
                    "getting possible moves and found:q: 2 r: -1 s: -1\n" + //
                    "q: 2 r: 1 s: -3\n" + //
                    "getting possible moves and found:q: 3 r: -2 s: -1\n" + //
                    "q: 1 r: -2 s: 1\n" + //
                    "q: -2 r: 1 s: 1\n" + //
                    "getting possible moves and found:q: -1 r: 2 s: -1\n" + //
                    "q: 2 r: -3 s: 1\n" + //
                    "q: 2 r: 1 s: -3\n" + //
                    "getting possible moves and found:q: 3 r: -1 s: -2\n" + //
                    "q: -2 r: 1 s: 1\n" + //
                    "q: -2 r: -1 s: 3\n" + //
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" + //
                    "q: 1 r: 2 s: -3\n" + "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "");
  }

  @Test
  public void testCaptureCornerWorks() {
    BoardMock newBoard = new BoardMock(3);
    HashMap<Coordinate, Cell> new1 = newBoard.createCopyOfBoard();
    new1.put(new Coordinate(0, 0), new Cell(Disc.BLACK));
    BoardMock newBoard1 = new BoardMock(3, new1, Turn.BLACK);
    CaptureCorners cm = new CaptureCorners();
    System.out.println(cm.chooseMove(newBoard1, Disc.WHITE).getFirstCoordinate()
            + "" + cm.chooseMove(newBoard1, Disc.WHITE).getSecondCoordinate());
    Assert.assertEquals(new Coordinate(0, 2), cm.chooseMove(newBoard1, Disc.WHITE));
  }

  @Test
  public void tryTwoAgainstEachother() {
    ReversiStratagy cm = new CaptureMost();
    ReversiStratagy cc = new CaptureCorners();
    ReversiStratagy ac = new AvoidCorners();
    TryTwo tt1 = new TryTwo(cc, cm);
    TryTwo tt2 = new TryTwo(ac, cm);

    BoardMock board = new BoardMock(4);

    Coordinate c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    board.makeMove(c);

    c = tt2.chooseMove(board, Disc.WHITE);
    board.makeMove(c);

    c = tt1.chooseMove(board, Disc.BLACK);
    Assert.assertEquals(c, new Coordinate(4, 4));
    Assert.assertEquals(board.getLog().toString(),
            "getting possible moves and found:q: -1 r: -1 s: 2\n" +
                    "q: 1 r: 1 s: -2\n" +
                    "q: -1 r: 2 s: -1\n" +
                    "q: 1 r: -2 s: 1\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: -1 r: -1 s: 2\n" +
                    "q: 1 r: 1 s: -2\n" +
                    "q: -1 r: 2 s: -1\n" +
                    "q: 1 r: -2 s: 1\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: 1 r: 1 s: -2\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "q: -2 r: -1 s: 3\n" +
                    "getting possible moves and found:q: 2 r: -1 s: -1\n" +
                    "q: 2 r: 1 s: -3\n" +
                    "getting possible moves and found:q: 2 r: -1 s: -1\n" +
                    "q: 2 r: 1 s: -3\n" +
                    "getting possible moves and found:q: -1 r: 2 s: -1\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "q: -2 r: -1 s: 3\n" +
                    "getting possible moves and found:q: -1 r: 3 s: -2\n" +
                    "q: 1 r: -2 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: -1 r: 3 s: -2\n" +
                    "q: 1 r: -2 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: -1 r: -2 s: 3\n" +
                    "q: -2 r: 1 s: 1\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: -3 r: 2 s: 1\n" +
                    "q: -1 r: 3 s: -2\n" +
                    "q: -3 r: 1 s: 2\n" +
                    "q: -2 r: 3 s: -1\n" +
                    "getting possible moves and found:q: -3 r: 2 s: 1\n" +
                    "q: -1 r: 3 s: -2\n" +
                    "q: -3 r: 1 s: 2\n" +
                    "q: -2 r: 3 s: -1\n" +
                    "getting possible moves and found:q: -1 r: -2 s: 3\n" +
                    "q: 2 r: -1 s: -1\n" +
                    "getting possible moves and found:q: -1 r: 3 s: -2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "getting possible moves and found:q: -1 r: 3 s: -2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "q: -2 r: -1 s: 3\n" +
                    "q: -2 r: 3 s: -1\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "q: -2 r: -1 s: 3\n" +
                    "q: -2 r: 3 s: -1\n" +
                    "getting possible moves and found:q: 3 r: -2 s: -1\n" +
                    "q: -1 r: -2 s: 3\n" +
                    "getting possible moves and found:q: 3 r: -2 s: -1\n" +
                    "q: -1 r: -2 s: 3\n" +
                    "getting possible moves and found:q: -3 r: 2 s: 1\n" +
                    "q: 1 r: -3 s: 2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: -3 r: 2 s: 1\n" +
                    "q: 1 r: -3 s: 2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: -1 r: -2 s: 3\n" +
                    "q: -3 r: 3 s: 0\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: 1 r: 2 s: -3\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: -1 r: -2 s: 3\n" +
                    "q: 0 r: 3 s: -3\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: -2 r: 3 s: -1\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: -2 r: 3 s: -1\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:q: -1 r: -2 s: 3\n" +
                    "getting possible moves and found:q: -1 r: -2 s: 3\n" +
                    "getting possible moves and found:q: 1 r: -3 s: 2\n" +
                    "q: 3 r: -1 s: -2\n" +
                    "q: 0 r: -3 s: 3\n" +
                    "q: 2 r: -3 s: 1\n" +
                    "getting possible moves and found:getting possible moves and found:");
  }

  @Test
  public void TestControllerSimple() {
    Board b1 = new Board(4);
    AIPlayer p1 = new AIPlayer(Disc.BLACK, new CaptureMost());
    AIPlayer p2 = new AIPlayer(Disc.WHITE, new CaptureMost());
    IBoardPanel viewPanel1 = new BoardPanel(b1, 600, 600);
    ControllerFeatures controller = new ReversiControllerMock(b1, viewPanel1, p1);
    b1.addObserver(controller);
    IBoardPanel viewPanel2 = new BoardPanel(b1, 600, 600);
    ControllerFeatures controller2 = new ReversiControllerMock(b1, viewPanel2, p2);
    b1.addObserver(controller2);
    controller2.updateView();

    // checks for player.black moves
    Assert.assertEquals(controller.getLog().toString(), "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n" +
            "AI Move was made by BLACK\n" +
            "View was updated + \n");

    // checks for player.white moves
    Assert.assertEquals(controller2.getLog().toString(), "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n" +
            "View was updated + \n" +
            "View was updated + \n" +
            "AI Move was made by WHITE\n");
  }


}
