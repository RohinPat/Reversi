package provider.view;

import java.util.Optional;

import controller.ControllerFeatures;
import model.Coordinate;
import provider.model.HexCoord;

public class FeaturesAdapter implements Features{
  private final ControllerFeatures controller;

  public FeaturesAdapter(ControllerFeatures controller){
    this.controller = controller;
  }

  @Override
  public void attemptMove(Optional<HexCoord> hc) {
    if (hc.isPresent()) {
      // If HexCoord is present, convert it to a Coordinate and make a move
      HexCoord hexCoord = hc.get();
      controller.confirmMove(hexCoord.q, hexCoord.r);
    }
  }

  @Override
  public void attemptPass() {
    //
  }
}
