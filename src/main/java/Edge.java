import static com.google.common.base.Preconditions.checkNotNull;

public class Edge<E> {
    private final E from, to;

    public Edge(E from, E to) {
        checkNotNull(from, to);
        this.from = from;
        this.to = to;
    }
    
    public E either() {
        return from;
    }
    
    public E other(E v) {
        if (from.equals(v)) return to;
        if (to.equals(v)) return from;
        throw new IllegalArgumentException();
    }

    public E from() {
        return from;
    }

    public E to() {
        return to;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s", from, to);
    }

    public Edge<E> reverse() {
        return new Edge<>(to, from);
    }
}
