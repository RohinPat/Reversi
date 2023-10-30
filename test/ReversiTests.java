
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

import Model.Board;
import Model.Coordinate;
import Model.Cell;
import Model.Disc;
import Model.Reversi;
import Model.Turn;

public class ReversiTests {
  @Test
  public void testMoveWorks() {
    Board newBoard = new Board(4);
    newBoard.playGame();
    newBoard.makeMove(new Coordinate(1, -2));
    assertEquals(Disc.BLACK, newBoard.getDiscAt(1, -1));
    assertEquals(Disc.BLACK, newBoard.getDiscAt(1, -2));
    newBoard.makeMove(new Coordinate(2, -3));
    assertEquals(Disc.WHITE, newBoard.getDiscAt(0, -1));
    assertEquals(Disc.WHITE, newBoard.getDiscAt(1, -2));
    assertEquals(Disc.WHITE, newBoard.getDiscAt(2, -3));


  }

}
