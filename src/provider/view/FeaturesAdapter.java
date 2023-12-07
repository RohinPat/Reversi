package provider.view;

import java.util.Optional;

import controller.ControllerFeatures;
import provider.model.HexCoord;

public class FeaturesAdapter implements Features{
  private final ControllerFeatures controller;

  public FeaturesAdapter(ControllerFeatures controller){
    this.controller = controller;
  }

  @Override
  public void attemptMove(Optional<HexCoord> hc) {
    //
  }

  @Override
  public void attemptPass() {
    //
  }
}
