package co.insou.evolve.genetics;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class Evolve {

    public static boolean DEBUG = false;

    public static final int BOARD_HEIGHT =              25;
    public static final int BOARD_WIDTH =               25;

    public static final int CREATURE_GENOMES =          6;
    public static final int CREATURE_MUTATION_CHANCE =  30;

    public static final int GENOME_MUTATION_CHANGE =    25;

    public static final int GENERATION_SIZE =           20;

    public static final Random RANDOM = ThreadLocalRandom.current();

    private final List<Board> boards = Lists.newArrayList();
    private final List<Generation> generations = Lists.newArrayList();

    private Pair<Profile> parents = new Pair<>(null, null);

    public Evolve() {

    }

    public void runGeneration() {
        Evolve.debug("Generating random board");
        this.boards.add(Board.generateRandom());
        Evolve.debug("Generating generation " + (this.generations.size() + 1));
        if (this.parents.isNull()) {
            this.generations.add(new Generation());
        } else {
            this.generations.add(Generation.populate(this.parents.getX().getCreature(), this.parents.getY().getCreature()));
        }

        for (Creature creature : this.latestGeneration().getPopulation()) {
            Evolve.debug("Profiling creature, genomes: ");
            Stream.of(creature.getGenomes()).forEach(genome -> genome.print(false));
            Evolve.debug("Board: ");
            this.latestBoard().print(false);
            Profile profile = new Profile(creature);
            Board nextBoard = null;

            do {
                if (nextBoard == null) {
                    nextBoard = this.latestBoard().step(creature);
                } else {
                    nextBoard = nextBoard.step(creature);
                }
                Evolve.debug("Board step, state: ");
                nextBoard.print(false);

                profile.matched(nextBoard.getMarks());
                Evolve.debug("Creature @ lifetime " + profile.getLifetime() + ", " + profile.getMatches() + " matches");
            } while (!nextBoard.isEmpty());

            if (profile.beats(this.parents.getY())) {
                if (profile.beats(this.parents.getX())) {
                    Evolve.debug("Creature is new parent X");
                    this.parents = new Pair<>(profile, this.parents.getX());
                } else {
                    Evolve.debug("Creature is new parent Y");
                    this.parents = new Pair<>(this.parents.getX(), profile);
                }
            }
        }

        Evolve.debug("Generation " + this.generations.size() + " completed!");
    }

    public Profile survivor() {
        return this.parents.getX();
    }

    public Board latestBoard() {
        return this.boards.get(this.boards.size() - 1);
    }

    public Generation latestGeneration() {
        return this.generations.get(this.generations.size() - 1);
    }

    public List<Generation> getGenerations() {
        return generations;
    }

    public static void debug(String message) {
        if (Evolve.DEBUG) {
            System.out.println(": " + message);
        }
    }

}
