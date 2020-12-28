import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Directed acyclic graph shortest path tree
 * 
 * 위상 정렬에 따라 v 가 이완되고 나면 distTo[w] <= distTo(v) + e.weight 가 만족된다.
 * 왜냐하면 v 은 한번 이완된 이후에 절대 다시 이완되지 않기 때문이다.
 * 그리고 distTo[w] 은 감소되기만 하고 w 의 차례가 되면 ?-w 간선은 모두 이완된 상태임을 만족한다.
 * 
 */
public class DagSPT<E> implements SPT<E> {
    private SymbolTable<E, WeightedEdge<E>> edgeTo = new HashTable<>();
    private SymbolTable<E, Double> distTo = new HashTable<>();
    private final E source;
    
    public DagSPT(E source, Digraph<E> G) {
        checkNotNull(source, G);
        if (new DirectedCycle<>(G).hasCycle())
            throw new IllegalArgumentException();
        this.source = source;
        for (E v: G.vertices())
            distTo.put(v, Double.POSITIVE_INFINITY);
        distTo.put(source, 0.0);
        Topological<E> top = new Topological<>(G, false);
        Set<E> visit = new Set<>();
        for (E v: top.order())
            visit(v, visit, G);
    }

    private void visit(E v, Set<E> visit, Digraph<E> G) {
        visit.add(v);
        for (Edge<E> e: G.edgeOf(v)) {
            WeightedEdge<E> we = (WeightedEdge<E>) e;
            if (distTo.get(we.to()) > distTo.get(v) + we.weight()) {
                distTo.put(we.to(), distTo.get(v) + we.weight());
                edgeTo.put(we.to(), we);
            }
        }
    }

    @Override
    public double distTo(E v) {
        checkNotNull(v);
        if (!distTo.contains(v))    // v is not in graph
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
        if (!distTo.contains(v))    // v is not in graph
            throw new IllegalArgumentException();
        return distTo.get(v) != Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tinyEWDAG.txt");
        int V = scanner.nextInt(),
                E = scanner.nextInt();
        Digraph<Integer> G = new Digraph<>();
        for (int i=0; i<E; i++) {
            int v = scanner.nextInt(),
                    w = scanner.nextInt();
            double weight = scanner.nextDouble();
            G.addEdge(new WeightedEdge<>(v, w, weight));
        }
        int source = 5;
        SPT<Integer> spt = new DagSPT<>(source, G);
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
