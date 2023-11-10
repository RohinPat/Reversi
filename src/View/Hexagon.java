package view;

import java.awt.*;
import java.awt.geom.Path2D;

public class Hexagon extends Path2D.Double {
  public Hexagon(double x, double y, double size) {
    // Start with an angle that points upwards
    double startAngle = Math.PI / 6;
    moveTo(x + size * Math.cos(startAngle), y + size * Math.sin(startAngle));
    for (int i = 1; i < 6; i++) {
      // Increment the angle by 60 degrees in radians to get the next point
      double angle = startAngle + i * Math.PI / 3;
      lineTo(x + size * Math.cos(angle), y + size * Math.sin(angle));
    }
    closePath();
  }
}


