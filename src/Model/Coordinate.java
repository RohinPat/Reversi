package model;

/**
 * Represents a coordinate in a hexagonal grid system.
 * In a hexagonal coordinate system, three axes are used: q, r, and s. 
 * These axes are constrained such that q + r + s = 0.
 */
public class Coordinate {
  private int q;
  private int r;
  private int s;

  /**
   * Constructs a new Coordinate with the given q and r values.
   * The s value is automatically calculated based on the constraint q + r + s = 0.
   *
   * @param q The q-axis value.
   * @param r The r-axis value.
   */
  public Coordinate(int q, int r) {
    this.q = q;
    this.r = r;
    this.s = -q - r;
  }

  /**
   * Checks if this coordinate is equal to another object.
   *
   * @param that The object to be compared with.
   * @return True if the object is a Coordinate and has the same
   * q, r, and s values. Otherwise, false.
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || getClass() != that.getClass()) {
      return false;
    }

    Coordinate thatCord = (Coordinate) that;
    return r == thatCord.r && q == thatCord.q && s == thatCord.s;
  }

  /**
   * Retrieves the q-axis value of this coordinate.
   *
   * @return The q value.
   */
  public int getQ() {
    int q = this.q;
    return q;
  }

  /**
   * Retrieves the r-axis value of this coordinate.
   *
   * @return The r value.
   */
  public int getR() {
    int r = this.r;
    return r;
  }

  /**
   * Retrieves the s-axis value of this coordinate.
   *
   * @return The s value.
   */
  public int getS() {
    int s = this.s;
    return s;
  }

  /**
   * Generates a hash code for this coordinate based on its q, r, and s values.
   *
   * @return The generated hash code.
   */
  @Override
  public int hashCode() {
    return r * 31 + q * 261 + s * 15;
  }
}