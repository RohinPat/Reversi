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
import model.CartesianCoordinate;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.GameState;
import model.Position;
import model.Reversi;
import model.SquareBoard;
import model.Turn;
import provider.model.HexCoord;
import provider.strategies.TopLeftTieBreaker;
import view.BoardPanel;
import view.BoardRenderer;
import view.ReversiFrame;
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
    newBoard.makeMove(new CartesianCoordinate(4, 2));
    newBoard.makeMove(new CartesianCoordinate(4, 1));
    SquareBoardRenderer br = new SquareBoardRenderer(newBoard);

    assertEquals(
            "_ _ _ _ _ _ \n" +
                    "_ _ _ _ O _ \n" +
                    "_ _ X O X _ \n" +
                    "_ _ O X _ _ \n" +
                    "_ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ \n", br.toString());
  }
}
