package Board;

public class Cell {
  private Disc disc;

  public Cell(Disc disc) {
    this.disc = disc;
  }

  public Disc getDisc() {
    return disc;
  }

  public void setDisc(Disc disc) {
    this.disc = disc;
  }
}
