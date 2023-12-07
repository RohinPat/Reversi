package controller;

import java.util.Optional;

import provider.model.HexCoord;
import provider.view.Features;

/**
 * Adapter class that acts as a bridge between the Features interface of the provider and
 * the ControllerFeatures interface of the controller. It translates provider-based feature
 * requests, like move attempts and passes, into actions understandable by the game's controller.
 */
public class FeaturesAdapter implements Features {
  private final ControllerFeatures controller;

  /**
   * Constructs a FeaturesAdapter that connects to a given ControllerFeatures instance.
   * This adapter enables communication between the provider's view components and
   * the game controller, facilitating game actions based on user interactions in the view.
   * @param controller The ControllerFeatures instance that will handle the actual game logic.
   */
  public FeaturesAdapter(ControllerFeatures controller) {
    this.controller = controller;
  }

  @Override
  public void attemptMove(Optional<HexCoord> hc) {
    if (hc.isPresent()) {
      HexCoord hexCoord = hc.get();
      controller.confirmMove(hexCoord.q, hexCoord.r);
    }
  }

  @Override
  public void attemptPass() {
    controller.passTurn();
  }
}
