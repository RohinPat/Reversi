package controller;

import java.util.List;

import model.Reversi;
import controller.aistrat.ReversiStratagy;
import model.Coordinate;
import model.Disc;
import model.ReversiReadOnly;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.strategies.InFallableReversiStrategy;
import provider.strategies.StrategyWrapper;
import provider.strategies.TopLeftTieBreaker;

public class StratagyAdapter implements ReversiStratagy {

    private final InFallableReversiStrategy stratagy;
    private TopLeftTieBreaker tieBreaker;

    public StratagyAdapter(InFallableReversiStrategy stratagy) {
        this.stratagy = stratagy;
        this.tieBreaker = new TopLeftTieBreaker();
    }

    @Override
    public Coordinate chooseMove(ReversiReadOnly model, Disc turn) {
      PlayerOwnership ownership = discToOwnership(turn);
      ReversiModelAdapter modelAdapter = new ReversiModelAdapter((Reversi) model);
      StrategyWrapper wrapper = new StrategyWrapper(stratagy);

      // Debugging: Check if the strategy is executed correctly
      List<HexCoord> possibleMoves = wrapper.executeStrategy(modelAdapter, ownership);

      if (possibleMoves == null || possibleMoves.isEmpty()) {
        System.out.println("Strategy returned no moves. Passing Turn");
        return new Coordinate(model.getSize(), model.getSize()); // or handle this case as appropriate
      }

      // Debugging: Check if tiebreaker is called
      System.out.println("Executing tiebreaker...");
      HexCoord output = tieBreaker.breakTie(possibleMoves);

      return new Coordinate(output.q, output.r);
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
