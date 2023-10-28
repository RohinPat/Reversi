package Model;

public class Coordinate {
    private int q;
    private int r;
    private int s;

    public Coordinate(int q, int r) {
        this.q = q;
        this.r = r;
        this.s = -q-r;
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