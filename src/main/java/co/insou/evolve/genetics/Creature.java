package co.insou.evolve.genetics;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Creature {

    private final Block[] genomes;

    public Creature() {
        this(IntStream.range(0, Evolve.CREATURE_GENOMES).mapToObj(i -> Block.generateEmpty()).collect(Collectors.toList()).toArray(new Block[0]));
    }

    public Creature(Creature mother, Creature father) {
        Block[] genomes = new Block[Evolve.CREATURE_GENOMES];

        for (int i = 0; i < Evolve.CREATURE_GENOMES; i++) {
            do {
                genomes[i] = Evolve.RANDOM.nextBoolean() ? mother.getGenomes()[i] : father.getGenomes()[i];
                if (Creature.shouldMutateGenome()) {
                    genomes[i] = Creature.mutateGenome(genomes[i]);
                }
            } while (genomes[i].countActivated() < 2);
        }

//        System.out.println("Genomes: ");
//        for (Block block : genomes) {
//            block.print(true);
//        }

        for (Block genome : genomes) {
            if (genome.countActivated() < 2) {
                System.out.println("Genome has " + genome.countActivated() + " on creation??");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        this.genomes = genomes;
    }

    public Creature(Block... genomes) {
        this.genomes = genomes;
    }

    public Block[] getGenomes() {
        return this.genomes;
    }

    private static boolean shouldMutateGenome() {
        return Evolve.RANDOM.nextDouble() * 100 < Evolve.CREATURE_MUTATION_CHANCE;
    }

    private static boolean shouldMutateCell() {
        return Evolve.RANDOM.nextDouble() * 100 < Evolve.GENOME_MUTATION_CHANGE;
    }

    private static Block mutateGenome(Block genome) {
        Cell[] original = Arrays.copyOf(genome.getCells(), genome.getCells().length);
        Cell[] cells = Arrays.copyOf(genome.getCells(), genome.getCells().length);

        for (int i = 0; i < cells.length; i++) {
            if (Creature.shouldMutateCell()) {
                cells[i] = Cell.oppose(cells[i]);
            }
        }

        Block block = new Block(cells);

        if (block.countActivated() < 2) {
            return Creature.mutateGenome(new Block(original));
        }

        return new Block(cells);
    }

    public static Creature generateRandom() {
        Block[] blocks = new Block[Evolve.CREATURE_GENOMES];

        for (int i = 0; i < Evolve.CREATURE_GENOMES; i++) {
            do {
                blocks[i] = Block.generateRandom();
            } while (blocks[i].countActivated() < 2);
        }

        return new Creature(blocks);
    }

}
