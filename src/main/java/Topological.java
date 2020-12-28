import static com.google.common.base.Preconditions.checkNotNull;

public class Topological<E> {
    private Stack<E> order = LinkedList.stack();
    
    public Topological(Digraph<E> G, boolean canCycle) {
        checkNotNull(G);
        if (!canCycle) {
            DirectedCycle<E> cycle = new DirectedCycle<>(G);
            if (cycle.hasCycle())
                throw new IllegalArgumentException();
        }
        Set<E> visit = new Set<>();
        for (E v: G.vertices())
            if (!visit.contains(v))
                dfs(v, visit, G);
    }

    private void dfs(E from, Set<E> visit, Digraph<E> G) {
        visit.add(from);
        for (Edge<E> e: G.edgeOf(from)) {
            E to = e.to();
            if (!visit.contains(to))
                dfs(to, visit, G);
        }
        order.push(from);
    }
    
    public Iterable<E> order() {
        return order;
    }
}
