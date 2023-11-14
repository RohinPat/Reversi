package model.AIStrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Turn;

public class CaptureCorners implements ReversiStratagy {

    @Override
    public Coordinate chooseMove(Board model, Turn turn) {
        Board copy = model.createCopyOfBoard();
        ArrayList<Coordinate> moves = copy.getPossibleMoves();
        int size = model.getSize();
        for(Coordinate move : moves) {
          if (this.getCorners(size).contains(move)) {
            return move;
          }
        }
        
        ReversiStratagy ac = new AvoidCorners();
        return ac.chooseMove(model, turn);
        
    }
    
    private ArrayList<Coordinate> getCorners(int size) {
        ArrayList<Coordinate> corners = new ArrayList<Coordinate>();
        corners.add(new Coordinate(0, 0 - size));
        corners.add(new Coordinate(0 - size, 0));
        corners.add(new Coordinate(0 - size, size));
        corners.add(new Coordinate(size, 0 - size));
        corners.add(new Coordinate(size, 0));
        corners.add(new Coordinate(0, size));
        return corners;
    }

    @Override
    public Coordinate chooseMove(Board model, Turn turn, ArrayList<Coordinate> possibleMoves) {
        CaptureMost cm = new CaptureMost();
        return cm.chooseMove(model, turn, possibleMoves);
    }
    
}
