package provider.model;

import java.util.Objects;

/**
 * The HexDirection enum represents the directions of movement on a hexagonal grid.
 */
public enum HexDirection {

  UPLEFT(new HexCoord(0, -1, 1)),
  UPRIGHT(new HexCoord(1, -1, 0)),
  LEFT(new HexCoord(-1, 0, 1)),
  RIGHT(new HexCoord(1, 0, -1)),
  DOWNLEFT(new HexCoord(-1, 1, 0)),
  DOWNRIGHT(new HexCoord(0, 1, -1));


  private final HexCoord direction;

  /**
   * Constructs a `HexDirection` with the specified direction represented by a `HexCoord`.
   *
   * @param direction The direction as a `HexCoord`.
   * @throws NullPointerException if `other` is `null`.
   */
  HexDirection(HexCoord direction) {
    Objects.requireNonNull(direction);
    this.direction = direction;
  }

  /**
   * Adds the direction represented by this enum to a given `HexCoord`.
   *
   * @param other The `HexCoord` to which the direction should be added.
   * @return A new `HexCoord` resulting from adding the direction to the given `HexCoord`.
   * @throws NullPointerException if `other` is `null`.
   */
  public HexCoord addDirectionToCoord(HexCoord other) {
    Objects.requireNonNull(other);
    return new HexCoord(other.q + this.direction.q,
        other.r + this.direction.r, other.s + this.direction.s);
  }
}
