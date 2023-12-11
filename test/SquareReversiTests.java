import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import controller.aistrat.CaptureMost;
import model.BoardMock;
import model.Coordinate;
import model.Disc;
import model.Position;
import model.Reversi;
import model.SquareBoard;
import model.SquareBoardMock;
import view.SquareBoardRenderer;

public class SquareReversiTests {

  @Test
  public void testSquareBoardIsCreatedCorrectly() {
    Reversi newBoard = new SquareBoard(4);
    SquareBoardRenderer br = new SquareBoardRenderer(newBoard);

    assertEquals(
            "_ _ _ _ \n" +
                    "_ X O _ \n" +
                    "_ O X _ \n" +
                    "_ _ _ _ \n", br.toString());
  }

  @Test
  public void testSquareBoardMake2MovesPlusCheckingForTurnPassing() {
    Reversi newBoard = new SquareBoard(6);
    newBoard.makeMove(new Coordinate(4, 2));
    newBoard.makeMove(new Coordinate(4, 1));
    SquareBoardRenderer br = new SquareBoardRenderer(newBoard);

    assertEquals(
            "_ _ _ _ _ _ \n" +
                    "_ _ _ _ O _ \n" +
                    "_ _ X O X _ \n" +
                    "_ _ O X _ _ \n" +
                    "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n", br.toString());
  }

  @Test
  public void testSquareBoardMake2MovesBySamePlayerByPassing(){
    Reversi newBoard = new SquareBoard(6);
    newBoard.makeMove(new Coordinate(4, 2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(1, 4));

    SquareBoardRenderer br = new SquareBoardRenderer(newBoard);

    assertEquals(
            "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n" +
                    "_ _ X X X _ \n" +
                    "_ _ X X _ _ \n" +
                    "_ X _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n", br.toString());

  }

  @Test
  public void testSquareBoardMake2MovesBySamePlayerByPassingAndThenGameOverTrue(){
    Reversi newBoard = new SquareBoard(6);
    newBoard.makeMove(new Coordinate(4, 2));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(1, 4));
    assertTrue(newBoard.isGameOver());

  }

  @Test
  public void test2PassesMakesGameOverTrue(){
    Reversi newBoard = new SquareBoard(6);
    newBoard.passTurn();
    newBoard.passTurn();
    assertTrue(newBoard.isGameOver());
  }

  @Test
  public void test2PassesNonConsecutiveDoesntMeanGameOver(){
    Reversi newBoard = new SquareBoard(6);
    newBoard.makeMove(new Coordinate(4, 2));
    newBoard.makeMove(new Coordinate(4, 3));
    newBoard.passTurn();
    newBoard.makeMove(new Coordinate(4, 1));
    newBoard.passTurn();

    assertFalse(newBoard.isGameOver());
  }

  @Test
  public void testCaptureMostOnSquare(){
    SquareBoardMock board = new SquareBoardMock(4);
    CaptureMost cm = new CaptureMost();

    Position c = cm.chooseMove(board, Disc.BLACK);
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

    assertEquals(board.getLog().toString(),
            "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 2 0\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 1\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 1 3\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed BLACK disc\n" +
                    "Placed BLACK disc\n" +
                    "Turn was passed to White \n" +
                    "Successful move at X2Y0\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 1 0\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 0\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed WHITE disc\n" +
                    "Placed WHITE disc\n" +
                    "Turn was passed to Black \n" +
                    "Successful move at X1Y0\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 0\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 1\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 3\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed BLACK disc\n" +
                    "Placed BLACK disc\n" +
                    "Placed BLACK disc\n" +
                    "Placed BLACK disc\n" +
                    "Turn was passed to White \n" +
                    "Successful move at X0Y0\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 0\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed WHITE disc\n" +
                    "Placed WHITE disc\n" +
                    "Turn was passed to Black \n" +
                    "Successful move at X3Y0\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 1\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 2\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 1 3\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed BLACK disc\n" +
                    "Placed BLACK disc\n" +
                    "Turn was passed to White \n" +
                    "Successful move at X3Y1\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed WHITE disc\n" +
                    "Placed WHITE disc\n" +
                    "Placed WHITE disc\n" +
                    "Placed WHITE disc\n" +
                    "Turn was passed to Black \n" +
                    "Successful move at X3Y2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 3\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 1 3\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 2 3\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 3 3\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed BLACK disc\n" +
                    "Placed BLACK disc\n" +
                    "Turn was passed to White \n" +
                    "Successful move at X0Y3\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 1\n" +
                    "Deep copy of board was made \n" +
                    "Found possible move at 0 2\n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Deep copy of board was made \n" +
                    "Placed WHITE disc\n" +
                    "Placed WHITE disc\n" +
                    "Placed WHITE disc\n" +
                    "Turn was passed to Black \n" +
                    "Successful move at X0Y1\n");
  }

}
