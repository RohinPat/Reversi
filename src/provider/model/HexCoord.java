package provider.model;

import java.util.Objects;

/**
 * Represents a coordinate in a hexagonal grid. HexCoord uses cube coordinates, a common system for
 * hexagonal grids. In this system, three axial coordinates q, r, and s are used to specify a
 * hexagon's position.
 *
 * <p>The axial coordinate system:
 * <ul>
 *   <li><b>q:</b> Axis from the top left to the bottom right.</li>
 *   <li><b>r:</b> Axis across the board.</li>
 *   <li><b>s:</b> Axis from the top right to the bottom left.</li>
 * </ul>
 * </p>
 *
 * <p>INVARIANT: the sum of the three coordinates is equal to 0</p>
 *
 * <p>Each HexCoord instance is immutable, meaning its values cannot be changed after creation. The
 * primary purpose of this class is to represent and compare coordinates in a hexagonal grid.</p>
 */
public class HexCoord {

  public final int q;
  public final int r;
  public final int s;

  /**
   * Constructs a new HexCoord with the specified axial coordinates.
   *
   * @param q The q-coordinate.
   * @param r The r-coordinate.
   * @param s The s-coordinate.
   * @throws IllegalStateException if the sum of q, r, and s does not equal 0, violating the
   *                               invariant.
   */
  public HexCoord(int q, int r, int s) {
    if (q + r + s != 0) {
      throw new IllegalStateException("Invariant is that q, r, and s should sum to 0");
    }
    this.q = q;
    this.r = r;
    this.s = s;
  }

  /**
   * Compares this HexCoord to the given object for equality. Two HexCoord objects are considered
   * equal if their q, r, and s coordinates are identical.
   *
   * @param o The object to compare to this HexCoord.
   * @return True if the two objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof HexCoord)) {
      return false;
    }

    HexCoord hc = (HexCoord) o;
    return (hc.q == this.q) && (hc.r == this.r) && (hc.s == this.s);
  }

  /**
   * Computes the hash code for this HexCoord based on its q, r, and s coordinates. This method
   * ensures that equal HexCoord objects have the same hash code.
   *
   * @return The hash code for this HexCoord.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.s, this.q, this.s);
  }

  /**
   * Get the X-pixel coordinate of this HexCoord in a hexagonal grid.
   *
   * @return The X-pixel coordinate of this HexCoord.
   */
  public double getXPixelCoord() {
    return (this.q * Math.sqrt(3)) + ((Math.sqrt(3) / 2) * (double) this.r);
  }

  /**
   * Get the Y-pixel coordinate of this HexCoord in a hexagonal grid.
   *
   * @return The Y-pixel coordinate of this HexCoord.
   */
  public double getYPixelCoord() {
    return (3. / 2. * (double) this.r);
  }

  /**
   * ToString for this hexcoord. Returns the q,r, and s values.
   *
   * @return q, r, and s values of this hexcoord.
   */
  @Override
  public String toString() {
    return q + " " + r + " " + s;
  }
}
