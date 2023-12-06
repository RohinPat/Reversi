package provider.view;
import provider.model.HexCoord;
import java.awt.Rectangle;

/**
 * Converts pixel clicks to hex coords.
 */
public class PixelToHexConverter {

  /**
   * Converts a cartesian pixel coordinate to a hex coordinate, formula from redblobgames.
   *
   * @param x         the x cartesian coordinate of the click
   * @param y         the y cartesian coordinate of the click
   * @param bounds    the bounds of the screen
   * @param hexRadius the size of the hexagon
   * @return the hex coord
   */
  public static HexCoord convertPixelToHex(int x, int y, Rectangle bounds, double hexRadius) {
    int cartesianX = x - bounds.width / 2;
    int cartesianY = y - bounds.height / 2;

    double q = (Math.sqrt(3) / 3. * cartesianX - 1. / 3. * cartesianY) / hexRadius;
    double r = 2. / 3. * cartesianY / hexRadius;
    double s = -q - r;

    return roundFractionalHexCoord(q, r, s);
  }

  // rounds a fractional hex coord to the closest integer hex coord, formula from redblobgames
  private static HexCoord roundFractionalHexCoord(double q, double r, double s) {

    double roundQ = Math.round(q);
    double roundR = Math.round(r);
    double roundS = Math.round(s);

    double qDiff = Math.abs(roundQ - q);
    double rDiff = Math.abs(roundR - r);
    double sDiff = Math.abs(roundS - s);

    if (qDiff > rDiff && qDiff > sDiff) {
      roundQ = -roundR - roundS;
    } else if (rDiff > sDiff) {
      roundR = -roundQ - roundS;
    } else {
      roundS = -roundQ - roundR;
    }

    return new HexCoord((int) roundQ, (int) roundR, (int) roundS);
  }

}
