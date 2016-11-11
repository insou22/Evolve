package co.insou.evolve.genetics;

public class Profile {

    private final Creature creature;
    private int matches = 0;
    private int lifetime = 0;

    public Profile(Creature creature) {
        this.creature = creature;
    }

    public void matched(int amount) {
        this.matches += amount;
        this.lifetime++;
    }

    public boolean beats(Profile other) {
        return other == null || this.lifetime > other.lifetime;
    }

    public Creature getCreature() {
        return this.creature;
    }

    public int getMatches() {
        return this.matches;
    }

    public int getLifetime() {
        return this.lifetime;
    }

}
