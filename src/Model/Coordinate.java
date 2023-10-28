package Model;

public class Coordinate {
    private int r;
    private int q;
    private int s;

    public Coordinate(int r, int q, int s) {
        this.r = r;
        this.q = q;
        this.s = s;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || getClass() != that.getClass()){
            return false;
        } 

        Coordinate thatCord = (Coordinate) that;
        return r == thatCord.r && q == thatCord.q && s == thatCord.s;
    }

    @Override
    public int hashCode() {
        return r * 31 + q * 261 + s * 15;
    }
}