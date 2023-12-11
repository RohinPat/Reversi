package model;

public class CartesianCoordinate implements Position{
  private final int x;
  private final int y;

  /**
   * Constructs a new Coordinate with the given q and r values.
   * The s value is automatically calculated based on the constraint q + r + s = 0.
   *
   * @param x The x-axis value.
   * @param y The y-axis value.
   */
  public CartesianCoordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null || getClass() != that.getClass()) {
      return false;
    }

    CartesianCoordinate thatCord = (CartesianCoordinate) that;
    return x == thatCord.x && y == thatCord.y;
  }

  @Override
  public int getFirstCoordinate() {
    int x = this.x;
    return x;
  }

  @Override
  public int getSecondCoordinate() {
    int y = this.y;
    return y;
  }

  @Override
  public boolean isCorner(int size) {
    return false;
  }

  @Override
  public boolean isNextToCorner(int size) {
    return false;
  }

  @Override
  public int hashCode() {
    return x * 31 + y * 26;
  }

  @Override
  public String toString(){
    return "X" + this.x + "Y" + this.y;
  }


}
