package view;

import java.awt.Color;
import java.awt.geom.Path2D;

import model.Disc;

/**
 * The {@code Square} class extends {@link Path2D.Double} and represents a square shape.
 * It is used to render a square for a grid-based board game, such as Reversi.
 */
public class Square extends Path2D.Double {
  Color color;
  double x;
  double y;
  double size;

  /**
   * A constructor to create a square shape using the Path2D class.
   *
   * @param x     x coordinate of the square.
   * @param y     y coordinate of the square.
   * @param size  the size of the square.
   * @param color the color of the disc to be placed in the square.
   */
  public Square(double x, double y, double size, Disc color) {
    if (color == Disc.BLACK) {
      this.color = Color.BLACK;
    } else if (color == Disc.WHITE) {
      this.color = Color.WHITE;
    } else if (color == Disc.EMPTY) {
      this.color = null;
    }

    this.x = x;
    this.y = y;
    this.size = size;

    moveTo(x, y);
    lineTo(x + size, y);
    lineTo(x + size, y + size);
    lineTo(x, y + size);
    closePath();
  }
}


