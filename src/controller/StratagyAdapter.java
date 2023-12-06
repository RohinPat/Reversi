package controller;

import controller.aistrat.ReversiStratagy;
import model.Coordinate;
import model.Disc;
import model.ReversiReadOnly;
import provider.model.PlayerOwnership;
import provider.strategies.InFallableReversiStrategy;
import provider.strategies.StrategyWrapper;
import provider.strategies.TopLeftTieBreaker;

public class StratagyAdapter implements ReversiStratagy {

    private final InFallableReversiStrategy stratagy;
    private final TopLeftTieBreaker tieBreaker;

    public StratagyAdapter(InFallableReversiStrategy stratagy, TopLeftTieBreaker tieBreaker) {
        this.stratagy = stratagy;
        this.tieBreaker = new TopLeftTieBreaker();
    }

    @Override
    public Coordinate chooseMove(ReversiReadOnly model, Disc turn) {
      PlayerOwnership ownership = discToOwnership(turn);
      StrategyWrapper wrapper = new StrategyWrapper(model, ownership);
      tieBreaker.breakTie(wrapper.executeStrategy(model, ownership));
      // Convert their coordinate system to ours and then return the new coordinate in our system.
    }
 
    private PlayerOwnership discToOwnership(Disc disc) {
        if (disc == Disc.BLACK) {
            return PlayerOwnership.PLAYER_1;
        } else if (disc == Disc.WHITE) {
            return PlayerOwnership.PLAYER_2;
        } else if (disc == Disc.EMPTY) {
            return PlayerOwnership.UNOCCUPIED;
        } else {
            throw new IllegalArgumentException("Invalid disc");
        }
    }
}
