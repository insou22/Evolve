package co.insou.evolve.genetics;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private Cell[] cells;

    private int marks = 0;

    public Board() {
        this(IntStream.range(0, Evolve.BOARD_HEIGHT * Evolve.BOARD_WIDTH).mapToObj(i -> Cell.generateEmpty()).collect(Collectors.toList()).toArray(new Cell[0]));
    }

    public Board(Cell... cells) {
        if (cells.length != Evolve.BOARD_HEIGHT * Evolve.BOARD_WIDTH) {
            throw new IllegalArgumentException("Must have " + Evolve.BOARD_HEIGHT * Evolve.BOARD_WIDTH + " Blocks in a Board [" + cells.length + "]");
        }
        this.cells = cells;
    }

    public Cell getCell(int index) {
        if (index < 0 || index >= this.cells.length) {
            throw new IllegalArgumentException("Index must be in bounds [" + index + "]");
        }
        return this.cells[index];
    }

    public Cell getCell(int x, int y) {
        if (y < 0 || x < 0 || y >= Evolve.BOARD_HEIGHT || x >= Evolve.BOARD_WIDTH) {
            throw new IllegalArgumentException("Index must be in bounds [" + x + ", " + y + "]");
        }
        return this.getCell(((Evolve.BOARD_HEIGHT - (y + 1)) * Evolve.BOARD_WIDTH) + x);
    }

    public Cell[] getCells() {
        return this.cells;
    }

    public int rowUp(int x) {
        return x - Evolve.BOARD_WIDTH;
    }

    public int rowDown(int x) {
        return x + Evolve.BOARD_WIDTH;
    }

    public Board step(Creature creature) {
        List<Integer> marks = Lists.newArrayList();

        for (int i = Evolve.BOARD_WIDTH; i < this.cells.length; i++) {
            if (i % Evolve.BOARD_WIDTH == 0 || i % Evolve.BOARD_WIDTH == (Evolve.BOARD_WIDTH - 1)) {
                continue;
            }
            if (i >= Evolve.BOARD_WIDTH * (Evolve.BOARD_HEIGHT - 1)) {
                continue;
            }

            Block block = new Block(
                    this.getCell(this.rowUp(i) - 1), this.getCell(this.rowUp(i)), this.getCell(this.rowUp(i) + 1),
                    this.getCell(i - 1), this.getCell(i), this.getCell(i + 1),
                    this.getCell(this.rowDown(i) - 1), this.getCell(this.rowDown(i)), this.getCell(this.rowDown(i) + 1)
            );

//            block.print(false);

            for (Block genome : creature.getGenomes()) {
                if (genome.countActivated() < 2) {
                    System.out.println("Genome has " + genome.countActivated() + " activations??");
                }
                if (genome.equals(block)) {
                    Evolve.debug("Matched genome to block");
                    block.print(false);

                    marks.add(i);
                }
            }

        }

        Board board = new Board();

        for (int mark : marks) {
            board.cells[mark] = new Cell(true);
        }

        board.marks = marks.size();

        return board;
    }

    public void print(boolean force) {
        if (!Evolve.DEBUG && !force) {
            return;
        }
        IntStream.range(0, Evolve.BOARD_WIDTH).forEach(x -> System.out.print(".___"));
        System.out.println(".");
        for (int i = Evolve.BOARD_HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j < Evolve.BOARD_WIDTH; j++) {
                System.out.print("| " + (this.getCell(j, i).isActive() ? "X" : " ") + " ");
            }
            System.out.println("|");
            IntStream.range(0, Evolve.BOARD_WIDTH).forEach(x -> System.out.print("|___"));
            System.out.println("|");
        }

    }

    public boolean isEmpty() {
        for (Cell cell : this.cells) {
            if (cell.isActive()) {
                return false;
            }
        }
        return true;
    }

    public int getMarks() {
        return this.marks;
    }

    public static Board generateRandom() {
        List<Cell> cells = IntStream.range(0, Evolve.BOARD_HEIGHT * Evolve.BOARD_WIDTH).mapToObj(i -> Cell.generateRandom()).collect(Collectors.toList());

        return new Board(cells.toArray(new Cell[0]));
    }

}
