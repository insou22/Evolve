package co.insou.evolve.genetics;

public class Cell {

    private final boolean active;

    public Cell(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public String toString() {
        return "Cell {active=" + this.active + "}";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cell)) {
            return false;
        }

        Cell other = (Cell) object;

        return this.active == other.active;
    }

    public static Cell generateRandom() {
        return new Cell(Evolve.RANDOM.nextBoolean());
    }

    public static Cell generateEmpty() {
        return new Cell(false);
    }

    public static Cell oppose(Cell cell) {
        return new Cell(!cell.active);
    }

}
