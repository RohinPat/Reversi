package view;

import java.awt.Color;
import java.awt.geom.Path2D;

import model.Disc;

/**
 * The {@code Hexagon} class extends {@link Path2D.Double} and represents a hexagonal shape.
 * It is used to render a hexagon for a grid-based board game, such as Reversi.
 */
public class Hexagon extends Path2D.Double {
  Color color;
  double x;
  double y;
  int q;
  int r;
  double size;

  /**
   * A constructor to create a hexagon shape using the Path2D class.
   *
   * @param x     x coordinate of the hexagon.
   * @param y     y coordinate of the hexagon.
   * @param size  the size of the hexagon.
   * @param color the color of the disc to be placed in the hexagon.
   */
  public Hexagon(double x, double y, double size, Disc color) {
    // Start with an angle that points upwards
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
    double startAngle = Math.PI / 6;
    moveTo(x + size * Math.cos(startAngle), y + size * Math.sin(startAngle));
    for (int side = 0; side < 6; side++) {
      double angle = startAngle + side * Math.PI / 3;
      lineTo(x + size * Math.cos(angle), y + size * Math.sin(angle));
    }
    closePath();
  }
}


