import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import model.Coordinate;
import model.Reversi;
import model.SquareBoard;
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


}
