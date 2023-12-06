package provider.controller;

/**
 * Represents something listening for alerts from a reversi controller. This must be subscribed to
 * the controller to receive alerts from it.
 */
public interface ControllerTurnListener {

  /**
   * Alerts this object that it is its turn. It is up to the listener to do whatever it wants with
   * this information.
   */
  void alertTurn();

  /**
   * Alerts this object that it is NOT its turn, or no longer its turn. It is up to the listener to
   * do whatever it wants with this information.
   */
  void alertNotTurn();

  /**
   * Alerts this object that the game is over, so it should do whatever it wants with that
   * information.
   */
  void alertGameOver();

}
