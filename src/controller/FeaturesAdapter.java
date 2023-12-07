package controller;

import java.util.Optional;

import controller.ControllerFeatures;
import model.Coordinate;
import provider.model.HexCoord;
import provider.view.Features;

public class FeaturesAdapter implements Features {
  private final ControllerFeatures controller;

  public FeaturesAdapter(ControllerFeatures controller){
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
