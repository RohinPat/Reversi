
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
import View.BoardRenderer;

public class TextualViewTests {
  @Test
  public void test(){
    Board newBoard = new Board(4);
    newBoard.playGame();
    BoardRenderer br = new BoardRenderer(newBoard);
    System.out.println(br.toString());
    System.out.println("hi");

  }

}
