
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

// kruskal vs prim
// prim 은 하나의 트리을 키워감.
// kruskal 은 여러 tree 을 merging
// prim 은 priority queue & Set 으로 유효 정점 체크 & update best edge
// kruskal priority queue & union find 로 유효 정점 체크 & best edge 검사 할 필요 없음.
public class KruskalMST<E> {
    private SymbolTable<E, WeightedEdge<E>> edgeTo = new HashTable<>();
    private final double cost;
    
    KruskalMST(Graph<E> G) {
        checkNotNull(G);
        if (G.V() == 0) {
            cost = 0.0;
            return;
        }
        UnionFind<E> uf = new WeightedUnionFind<>();
        PriorityQueue<WeightedEdge<E>> pq = new PriorityQueue<>(); 
        for (E v: G.vertices()) {
            uf.put(v);
            for (Edge<E> e: G.edgeOf(v)) {
                WeightedEdge<E> we = (WeightedEdge<E>) e;
                pq.enqueue(we);
            }
        }
        double cost = 0.0;
        while (!pq.isEmpty() && edgeTo.size() != G.V()-1) {
            WeightedEdge<E> e = pq.dequeue();
            E v = e.either();
            if (!uf.connected(v, e.other(v))) {
                uf.union(v, e.other(v));
                edgeTo.put(e.other(v), e);
                cost += e.weight();
            }
        }
        this.cost = cost;
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
        KruskalMST<Integer> mst = new KruskalMST<>(G);
        System.out.println(mst.cost);
    }
}
