package co.insou.evolve.genetics;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Generation {

    private final List<Creature> population = Lists.newArrayList();

    public Generation() {
        IntStream.range(0, Evolve.GENERATION_SIZE).forEach(i -> this.population.add(Creature.generateRandom()));
    }

    public Generation(Creature... creatures) {
        Stream.of(creatures).forEach(this.population::add);
    }

    public List<Creature> getPopulation() {
        return this.population;
    }

    public static Generation populate(Creature mother, Creature father) {
        List<Creature> creatures = IntStream.range(0, Evolve.GENERATION_SIZE).mapToObj(i -> new Creature(mother, father)).collect(Collectors.toList());

        return new Generation(creatures.toArray(new Creature[0]));
    }

    public static Generation generateEmpty() {
        List<Creature> creatures = IntStream.range(0, Evolve.GENERATION_SIZE).mapToObj(i -> new Creature()).collect(Collectors.toList());

        return new Generation(creatures.toArray(new Creature[0]));
    }

}
