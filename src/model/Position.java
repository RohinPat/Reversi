package model;

public interface Position {

  boolean equals(Object that);

  int hashCode();

  int getFirstCoordinate();

  int getSecondCoordinate();

  boolean isCorner(int size);
}
