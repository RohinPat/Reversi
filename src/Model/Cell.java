package Model;

class Cell {
    private Disc content;      

    public Cell(Disc content, ) {
        this.content = "";
    }

    // Constructor to initialize the cell with specific content
    public Cell(String content) {
        this.isOccupied = true;
        this.content = content;
    }

    // Getter for isOccupied
    public boolean isOccupied() {
        return isOccupied;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
        if (content != null && !content.isEmpty()) {
            this.isOccupied = true;
        } else {
            this.isOccupied = false;
        }
    }

    // Clear the content of the cell
    public void clear() {
        this.content = "";
        this.isOccupied = false;
    }

    @Override
    public String toString() {
        return isOccupied ? content : "Empty";
    }
}

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
