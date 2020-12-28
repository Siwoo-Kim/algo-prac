import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class MST<E> {
    private final SymbolTable<E, WeightedEdge<E>> edgeTo = new HashTable<>();
    private final SymbolTable<E, Double> distTo = new HashTable<>();
    private final double cost;
    
    public MST(Graph<E> G) {
        checkNotNull(G);
        if (G.V() == 0) {
            cost = 0.0;
            return;
        }
        E source = null;
        for (E v: G.vertices()) {
            if (source == null) {
                source = v;
                distTo.put(source, 0.0);
            } else
                distTo.put(v, Double.POSITIVE_INFINITY);
        }
        edgeTo.put(source, null);
        Set<E> mst = new Set<>();
        PriorityQueue<WeightedEdge<E>> pq = new PriorityQueue<>();
        visit(source, pq, mst, G);
        double cost = 0.0;
        while (!pq.isEmpty()) {
            WeightedEdge<E> e = pq.dequeue();
            if (!mst.contains(e.to())) {
                cost += e.weight();
                edgeTo.put(e.to(), e);
                visit(e.to(), pq, mst, G);
            }
        }
        this.cost = cost;
    }

    private void visit(E v, PriorityQueue<WeightedEdge<E>> pq, Set<E> mst, Graph<E> G) {
        mst.add(v);
        for (Edge<E> e: G.edgeOf(v)) {
            WeightedEdge<E> we = (WeightedEdge<E>) e;
            E w = we.other(v);
            if (!mst.contains(w) 
                    && distTo.get(w) > we.weight()) {
                distTo.put(w, we.weight());
                pq.enqueue(we);
            }
        }
    }
    
    //no-meaning
    public Iterable<E> pathTo(E v) {
        checkNotNull(v);
        Stack<E> stack = LinkedList.stack();
        for (WeightedEdge<E> e=edgeTo.get(v); e!=null; e=edgeTo.get(e.from())) {
            stack.push(e.to());
            if (edgeTo.get(e.from()) == null)
                stack.push(e.from());
        }
        return stack;
    }
    
    //no-meaning
    public double distanceOf(E v) {
        checkNotNull(v);
        if (!distTo.contains(v))
            throw new IllegalArgumentException();
        return distTo.get(v);
    }
    
    public double cost() {
        return this.cost;
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
            G.addEdge(new WeightedEdge<>(v, w, weight));
        }
        MST<Integer> mst = new MST<>(G);
        System.out.println(mst.cost);
    }
}
