package Model;

class Cell {
    private Disc content;
    private final Coordinate cord;

    public Cell(Disc content, Coordinate cord) {
        this.content = content;
        this.cord = cord;
    }

    public Disc getContent() {
        return this.content;
    }

    public Coordinate getCordinates() {
        return this.cord;
    }

    public void setContent(Disc content) {
        this.content = content;
    }

}

