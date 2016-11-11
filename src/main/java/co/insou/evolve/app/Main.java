package co.insou.evolve.app;

import co.insou.evolve.genetics.Block;
import co.insou.evolve.genetics.Evolve;
import co.insou.evolve.genetics.Profile;

public final class Main {

    public static final int GENERATION_AMOUNT = 2000;

    public static void main(String[] args) {
        Evolve evolve = new Evolve();

        new Thread(() -> {
            int size = 0;

            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (size == evolve.getGenerations().size()) {
                    size = evolve.getGenerations().size();
                    Evolve.DEBUG = true;

                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("killed on generation " + size);
                    System.exit(0);
                }
                size = evolve.getGenerations().size();
            }
        }).start();

        for (int i = 0; i < Main.GENERATION_AMOUNT; i++) {
            System.out.println("Running generation" + (i + 1));
            evolve.runGeneration();
        }

        Profile survivor = evolve.survivor();
        System.out.println("Best performer: {lifetime=" + survivor.getLifetime() + ", matches=" + survivor.getMatches() + "}");
        System.out.println("Genomes: ");
        for (Block genome : survivor.getCreature().getGenomes()) {
            System.out.println();
            genome.print(true);
        }
        System.exit(0);

    }

}



/*
*
* new Thread(() -> {
            int size = 0;

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (size == evolve.latestGeneration().getPopulation().size()) {
                    Evolve.DEBUG = true;
                }
                size = evolve.latestGeneration().getPopulation().size();
            }
        }).start();
* */
