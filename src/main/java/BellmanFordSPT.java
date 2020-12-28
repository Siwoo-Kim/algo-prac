import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class BellmanFordSPT<E> implements SPT<E> {
    private SymbolTable<E, WeightedEdge<E>> edgeTo = new HashTable<>();
    private SymbolTable<E, Double> distTo = new HashTable<>();
    private final E source;
    
    public BellmanFordSPT(E source, Digraph<E> G) {
        checkNotNull(source, G);
        this.source = source;
        for (E v: G.vertices())
            distTo.put(v, Double.POSITIVE_INFINITY);
        distTo.put(source, 0.0);
        for (E v: G.vertices())
            for (Edge<E> e: G.edgeOf(v)) {
                WeightedEdge<E> we = (WeightedEdge<E>) e;
                if (distTo.get(e.to()) > distTo.get(v) + we.weight()) {
                    distTo.put(e.to(), distTo.get(v) + we.weight());
                    edgeTo.put(e.to(), we);
                }
            }
    }

    @Override
    public double distTo(E v) {
        checkNotNull(v);
        if (!distTo.contains(v))
            throw new IllegalArgumentException();
        return distTo.get(v);
    }

    @Override
    public Iterable<E> pathTo(E v) {
        if (!hasPathTo(v))
            throw new IllegalArgumentException();
        Stack<E> stack = LinkedList.stack();
        for (Edge<E> e=edgeTo.get(v); e!=null; e=edgeTo.get(e.from()))
            stack.push(e.to());
        stack.push(source);
        return stack;
    }

    @Override
    public boolean hasPathTo(E v) {
        checkNotNull(v);
        return distTo(v) != Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tinyEWDn.txt");
        int V = scanner.nextInt(),
                E = scanner.nextInt();
        Digraph<Integer> G = new Digraph<>();
        for (int i=0; i<E; i++) {
            int v = scanner.nextInt(),
                    w = scanner.nextInt();
            double weight = scanner.nextDouble();
            G.addEdge(new WeightedEdge<>(v, w, weight));
        }
        int source = 0;
        SPT<Integer> spt = new BellmanFordSPT<>(source, G);
        for (int v: G.vertices()) {
            if (spt.hasPathTo(v)) {
                System.out.printf("%d to %d (%.2f): ", source, v, spt.distTo(v));
                for (int w: spt.pathTo(v))
                    System.out.printf("->%d (%.2f)\t", w, spt.distTo(w));
                System.out.println();
            }
        }
    }
}
