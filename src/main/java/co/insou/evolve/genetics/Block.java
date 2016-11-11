package co.insou.evolve.genetics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Block {

    private final Cell[] cells;

    public Block() {
        this((Cell[]) IntStream.range(0, 9).mapToObj(i -> Cell.generateEmpty()).toArray());
    }

    public Block(Cell... cells) {
        if (cells.length != 9) {
            throw new IllegalArgumentException("Must be 9 cells in a block " + Arrays.toString(cells));
        }
        this.cells = cells;
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public Cell getCell(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("Index must be in bounds [" + index + "]");
        }
        return this.cells[index];
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x > 2 || y > 2) {
            throw new IllegalArgumentException("Index must be in bounds [" + x + ", " + y + "]");
        }
        return this.getCell(((2 - y) * 3) + x);
    }

    public boolean isEmpty() {
        return this.countActivated() == 0;
    }

    public int countActivated() {
        int count = 0;
        for (Cell cell : this.cells) {
            if (cell.isActive()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.cells);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Block)) {
            return false;
        }
        Block other = (Block) object;

        for (int i = 0; i < 9; i++) {
            if (!this.getCell(i).equals(other.getCell(i))) {
                return false;
            }
        }

        return true;
    }

    public void print(boolean force) {
        if (!Evolve.DEBUG && !force) {
            return;
        }
        System.out.println(" ___________");
        System.out.println(String.format("| %s | %s | %s |", this.getCell(0, 2).isActive() ? "X" : " ", this.getCell(1, 2).isActive() ? "X" : " ", this.getCell(2, 2).isActive() ? "X" : " "));
        System.out.println("|___|___|___|");
        System.out.println(String.format("| %s | %s | %s |", this.getCell(0, 1).isActive() ? "X" : " ", this.getCell(1, 1).isActive() ? "X" : " ", this.getCell(2, 1).isActive() ? "X" : " "));
        System.out.println("|___|___|___|");
        System.out.println(String.format("| %s | %s | %s |", this.getCell(0, 0).isActive() ? "X" : " ", this.getCell(1, 0).isActive() ? "X" : " ", this.getCell(2, 0).isActive() ? "X" : " "));
        System.out.println("|___|___|___|");
    }

    public static Block generateRandom() {
        List<Cell> cells = IntStream.range(0, 9).mapToObj(i -> Cell.generateRandom()).collect(Collectors.toList());

        return new Block(cells.toArray(new Cell[0]));
    }

    public static Block generateEmpty() {
        List<Cell> cells = IntStream.range(0, 9).mapToObj(i -> Cell.generateEmpty()).collect(Collectors.toList());

        return new Block(cells.toArray(new Cell[0]));
    }
}
