package provider.strategies;

import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiMutableModel;
import provider.model.ReversiReadOnlyModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * MinimaxStrategy is a class that implements the InFallableReversiStrategy interface, providing a
 * strategy for playing the Reversi game using the Minimax algorithm. The Minimax algorithm is a
 * decision-making algorithm commonly used in two-player turn-based games for minimizing the
 * possible loss for a worst-case scenario. In the context of Reversi, this strategy aims to make
 * optimal moves by evaluating potential outcomes at a specified depth. The depth of the search and
 * the strategies for the current player and the opponent are configurable through the constructor.
 */
public class MinimaxStrategy implements InFallableReversiStrategy {

  // max depth, when the depth (starting at 0) exceeds the max depth (which must be non-negative),
  // minimax uses the given strategies to make a move instead of further calculating
  private final int maxDepth;

  // the strategy we will use after exhausting the outcomes
  private final InFallableReversiStrategy ourStrategy;

  // the strategy we think the opponent will use after exhausting their outcomes
  private final InFallableReversiStrategy opponentStrategy;

  /**
   * Constructs a MinimaxStrategy with the specified parameters.
   *
   * @param maxDepth         The maximum depth for the Minimax algorithm to search.
   * @param ourStrategy      The strategy for the current player.
   * @param opponentStrategy The strategy for the opponent player.
   * @throws IllegalArgumentException if maxDepth is negative.
   */
  public MinimaxStrategy(int maxDepth, InFallableReversiStrategy ourStrategy,
      InFallableReversiStrategy opponentStrategy) {
    if (maxDepth < 0) {
      throw new IllegalArgumentException("Depth must be non-negative");
    }
    this.maxDepth = maxDepth;
    this.ourStrategy = Objects.requireNonNull(ourStrategy);
    this.opponentStrategy = Objects.requireNonNull(opponentStrategy);
  }

  /**
   * Executes the Minimax strategy given the current state of the game and the player.
   *
   * @param model  The ReversiReadOnlyModel representing the current state of the game.
   * @param player The player for which the strategy is executed.
   * @return A list of pairs representing the chosen moves and their corresponding scores.
   */
  @Override
  public List<Pair<HexCoord, Integer>> executeStrategyGivenMoves(ReversiReadOnlyModel model,
                                                                 PlayerOwnership player) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(player);
    // called to throw exception when game not started
    model.isGameOver();
    ReversiMutableModel mutableClone = model.cloneModel();
    return this.minimax(mutableClone, player, 0).stream()
        .filter(hexCoordIntegerPair -> hexCoordIntegerPair.value1 != null).collect(
            Collectors.toList());
  }

  // returns the list of best moves for this player after traversing
  // n+1 moves deep where n is the depth
  private List<Pair<HexCoord, Integer>> minimax(ReversiMutableModel model,
      PlayerOwnership player, int depth) {

    List<HexCoord> possibleMoves = this.getPossibleMovesTurnExceptionIndependent(model, player);

    if (depth >= maxDepth) {
      if (possibleMoves.isEmpty()) {
        return evaluateGameStateWithRespectToInitialPlayer(model, player, depth);
      }
      if (depth % 2 == 0) {
        // we are at our turn and out of foresight, so we play our maximizing move
        return this.ourStrategy.executeStrategyGivenMoves(model, player);
      } else {
        // we are at opponent turn and out of foresight, so we play opponent maximizing move
        return this.opponentStrategy.executeStrategyGivenMoves(model, player);
      }
    }

    List<HexCoord> possibleMovesOtherPlayer = this.getPossibleMovesTurnExceptionIndependent(model,
        getInversePlayerOwnership(player));

    if (possibleMoves.isEmpty() && possibleMovesOtherPlayer.isEmpty()) {

      return evaluateGameStateWithRespectToInitialPlayer(model, player, depth);
    } else if (possibleMoves.isEmpty()) {
      model.pass(getInversePlayerOwnership(player));
      return this.minimax(model, getInversePlayerOwnership(player), depth + 1);
    }

    List<Pair<HexCoord, Integer>> moveScorePairs = new ArrayList<>();

    for (HexCoord hc : possibleMoves) {
      ReversiMutableModel newModel = model.cloneModel();

      newModel.placeDisk(hc, player);

      Pair<HexCoord, Integer> pairReturnedFromNextMM = this.minimax(newModel,
          getInversePlayerOwnership(player), depth + 1).get(0);

      assert pairReturnedFromNextMM != null;
      moveScorePairs.add(new Pair<>(hc, pairReturnedFromNextMM.value2));
    }

    if (depth % 2 == 0) {
      return this.extremePairs(moveScorePairs, Comparator.naturalOrder());
    } else {
      return this.extremePairs(moveScorePairs, Comparator.reverseOrder());
    }

  }

  // evaluates the game state with respect to the initial player
  private List<Pair<HexCoord, Integer>> evaluateGameStateWithRespectToInitialPlayer(
      ReversiMutableModel model,
      PlayerOwnership player, int depth) {
    if (depth % 2 == 0) {
      return List.of(new Pair<>(null, model.countClaimedTiles(player) - model.countClaimedTiles(
          getInversePlayerOwnership(player))));
    } else {
      return List.of(new Pair<>(null, model.countClaimedTiles(
          getInversePlayerOwnership(player)) - model.countClaimedTiles(player)));
    }

  }

  // gets the possible moves for this player given the current board, ignoring whose turn it is
  private List<HexCoord> getPossibleMovesTurnExceptionIndependent(ReversiReadOnlyModel model,
      PlayerOwnership player) {
    List<HexCoord> results = new ArrayList<>();

    for (HexCoord coord : model.getBoard().getMap().keySet()) {
      if (model.isMoveAllowedTurnIndependent(coord, player)) {
        results.add(coord);
      }
    }

    return results;
  }

  // returns the list of pairs that are the most extreme (such as highest or lowest),
  // determining if something is most extreme through the comparator returning a positive value
  // ties are determined when comparator returns 0
  private List<Pair<HexCoord, Integer>> extremePairs(List<Pair<HexCoord, Integer>> moveScorePairs,
      Comparator<Integer> comparator) {
    if (moveScorePairs.isEmpty()) {
      throw new RuntimeException("something went wrong, empty list for move score pairs");
    }
    List<Pair<HexCoord, Integer>> mostExtremePairsSeen = new ArrayList<>();
    mostExtremePairsSeen.add(moveScorePairs.get(0));

    for (Pair<HexCoord, Integer> pair : moveScorePairs) {
      int comparisonResult = comparator.compare(pair.value2, mostExtremePairsSeen.get(0).value2);

      if (comparisonResult > 0) {
        mostExtremePairsSeen.clear();
        mostExtremePairsSeen.add(pair);
      } else if (comparisonResult == 0) {
        mostExtremePairsSeen.add(pair);
      }
    }
    return mostExtremePairsSeen;
  }

  // given a player ownership, return the other player ownership
  private PlayerOwnership getInversePlayerOwnership(PlayerOwnership playerOwnership) {
    if (playerOwnership.equals(PlayerOwnership.UNOCCUPIED)) {
      throw new IllegalArgumentException("This is not invertible");
    }

    if (playerOwnership.equals(PlayerOwnership.PLAYER_1)) {
      return PlayerOwnership.PLAYER_2;
    } else {
      return PlayerOwnership.PLAYER_1;
    }

  }
}
