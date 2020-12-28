public class WeightedEdge<E> extends Edge<E> implements Comparable<WeightedEdge<E>> {
    private final double weight;
    
    public WeightedEdge(E from, E to, double weight) {
        super(from, to);
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    @Override
    public WeightedEdge<E> reverse() {
        return new WeightedEdge<>(to(), from(), weight);
    }

    @Override
    public int compareTo(WeightedEdge<E> that) {
        return Double.compare(weight, that.weight);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (%.2f)", weight);
    }
}
