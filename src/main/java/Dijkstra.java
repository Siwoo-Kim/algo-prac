import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class Dijkstra<E> implements SPT<E> {
    private SymbolTable<E, Double> distTo = new HashTable<>();
    private SymbolTable<E, WeightedEdge<E>> edgeTo = new HashTable<>();
    private final E source;

    private class Path implements Comparable<Path> {
        E v;
        double cost;

        public Path(E v, double cost) {
            checkNotNull(v);
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(Path that) {
            return Double.compare(cost, that.cost);
        }
    }
    
    public Dijkstra(E source, Digraph<E> G) {
        checkNotNull(source, G);
        this.source = source;
        for (E v: G.vertices())
            distTo.put(v, Double.POSITIVE_INFINITY);
        distTo.put(source, 0.0);
        PriorityQueue<Path> pq = new PriorityQueue<>();
        pq.enqueue(new Path(source, 0.0));
        Set<E> visit = new Set<>();
        edgeTo.put(source, null);
        while (!pq.isEmpty()) {
            Path path = pq.dequeue();
            if (!visit.contains(path.v))
                relax(path.v, visit, pq, G);
        }
    }

    private void relax(E v, Set<E> visit, PriorityQueue<Path> pq, Digraph<E> G) {
        visit.add(v);
        for (Edge<E> e: G.edgeOf(v)) {
            WeightedEdge<E> we = (WeightedEdge<E>) e;
            if (!visit.contains(we.to()) 
                    && distTo.get(we.to()) > distTo.get(v) + we.weight()) {
                distTo.put(we.to(), distTo.get(v) + we.weight());
                edgeTo.put(we.to(), we);
                pq.enqueue(new Path(we.to(), distTo.get(we.to())));
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
        Stack<E> edges = LinkedList.stack();
        for (Edge<E> e=edgeTo.get(v); e!=null; e=edgeTo.get(e.from()))
            edges.push(e.to());
        edges.push(source);
        return edges;
    }

    @Override
    public boolean hasPathTo(E v) {
        return distTo(v) != Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tinyEWD.txt");
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
        SPT<Integer> spt = new Dijkstra<>(source, G);
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
