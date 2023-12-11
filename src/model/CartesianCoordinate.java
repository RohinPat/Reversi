package model;

import java.util.ArrayList;

/**
 * Represents a Cartesian coordinate with x and y values.
 */
public class CartesianCoordinate implements Position{
  private final int x;
  private final int y;

  /**
   * Constructs a new CartesianCoordinate with the given x and y values.
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
    ArrayList<CartesianCoordinate> corners = new ArrayList<CartesianCoordinate>();
    corners.add(new CartesianCoordinate(0, 0));
    corners.add(new CartesianCoordinate(0, size - 1));
    corners.add(new CartesianCoordinate(size - 1, 0));
    corners.add(new CartesianCoordinate(size - 1, size - 1));


    return corners.contains(new CartesianCoordinate
            (this.getFirstCoordinate(), this.getSecondCoordinate()));
  }

  @Override
  public boolean isNextToCorner(int size) {
    ArrayList<CartesianCoordinate> notCorners = new ArrayList<CartesianCoordinate>();
    notCorners.add(new CartesianCoordinate(0,1));
    notCorners.add(new CartesianCoordinate(1,0));
    notCorners.add(new CartesianCoordinate(1,1));

    notCorners.add(new CartesianCoordinate(0,size - 2));
    notCorners.add(new CartesianCoordinate(1,size - 2));
    notCorners.add(new CartesianCoordinate(1,size - 1));

    notCorners.add(new CartesianCoordinate(size - 2,0));
    notCorners.add(new CartesianCoordinate(size - 2,1));
    notCorners.add(new CartesianCoordinate(size - 1,1));

    notCorners.add(new CartesianCoordinate(size - 1,size - 2));
    notCorners.add(new CartesianCoordinate(size - 2,size - 2));
    notCorners.add(new CartesianCoordinate(size - 2,size - 2));

    return notCorners.contains(new CartesianCoordinate
            (this.getFirstCoordinate(), this.getSecondCoordinate()));
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
