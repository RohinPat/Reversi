package provider;

/**
 * A Pair is a simple container that holds two associated generic values.
 *
 * @param <T> The type of the first value.
 * @param <U> The type of the second value.
 */
public class Pair<T, U> {

  // NOTE: by definition of a pair, it will need direct access or getters for the fields,
  // which means direct access of field is ok here
  public final T value1;
  public final U value2;

  /**
   * Constructs a new Pair with the specified values.
   *
   * @param value1 The first value.
   * @param value2 The second value.
   */
  public Pair(T value1, U value2) {
    this.value1 = value1;
    this.value2 = value2;
  }

  public String toString() {
    return "Val 1: " + value1.toString() + " Val 2: " + value2.toString();
  }
}
