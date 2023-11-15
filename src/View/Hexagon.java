package view;

import java.awt.Color;
import java.awt.geom.Path2D;

import model.Disc;

public class Hexagon extends Path2D.Double {
  Color color;
  double x;
  double y;
  int q;
  int r;
  double size;

  public Hexagon(double x, double y, double size, Disc color, int q, int r) {
    // Start with an angle that points upwards
    if (color == Disc.BLACK) {
      this.color = Color.BLACK;
    } else if (color == Disc.WHITE) {
      this.color = Color.WHITE;
    } else if (color == Disc.EMPTY) {
      this.color = null;
    }
    this.q = q;
    this.r = r;
    this.x = x;
    this.y = y;
    this.size = size;
    double startAngle = Math.PI / 6;
    moveTo(x + size * Math.cos(startAngle), y + size * Math.sin(startAngle));
    for (int i = 0; i < 6; i++) {
      // Increment the angle by 60 degrees in radians to get the next point
      double angle = startAngle + i * Math.PI / 3;
      lineTo(x + size * Math.cos(angle), y + size * Math.sin(angle));
    }
    closePath();
  }


}


