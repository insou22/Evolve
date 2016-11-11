package co.insou.evolve.genetics;

public class Pair<T> {

    private T x;
    private T y;

    public Pair(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return this.x;
    }

    public T getY() {
        return this.y;
    }

    public boolean isNull() {
        return this.x == null || this.y == null;
    }

}
