import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class MST<E> {
    private SymbolTable<E, WeightedEdge<E>> pathTo = new HashTable<>();
    private final double distance;
    
    public MST(Graph<E> G) {
        checkNotNull(G);
        if (G.V() == 0) {
            this.distance = 0.0;
            return;
        }
        E source = G.vertices().iterator().next();
        Set<E> mst = new Set<>();
        PriorityQueue<WeightedEdge<E>> pq = new PriorityQueue<>();
        visit(source, mst, pq, G);
        double distance = 0.0;
        while (!pq.isEmpty()) {
            WeightedEdge<E> e = pq.dequeue();
            if (mst.contains(e.to())) continue;
            pathTo.put(e.to(), e);
            distance += e.weight();
            visit(e.to(), mst, pq, G);
        }
        this.distance = distance;
    }

    private void visit(E v, Set<E> mst, PriorityQueue<WeightedEdge<E>> pq, Graph<E> G) {
        mst.add(v);
        for (Edge<E> e: G.edgeOf(v)) {
            WeightedEdge<E> we = (WeightedEdge<E>) e;
            E w = we.other(v);
            if (!mst.contains(w))
                pq.enqueue(we);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tinyEWG.txt");
        int V = scanner.nextInt(),
                E = scanner.nextInt();
        Graph<Integer> G = new UndirectedGraph<>();
        for (int i=0; i<E; i++) {
            int v = scanner.nextInt(),
                    w = scanner.nextInt();
            double weight = scanner.nextDouble();
            WeightedEdge<Integer> edge = new WeightedEdge<>(v, w, weight);
            G.addEdge(edge);
        }
        MST<Integer> mst = new MST<>(G);
        System.out.println(mst.distance);
        
        for (int v: G.vertices()) {
            System.out.print(v + ": ");
            System.out.println(mst.pathTo.get(v));
        }
    }
}
