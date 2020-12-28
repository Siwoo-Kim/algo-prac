import static com.google.common.base.Preconditions.checkNotNull;

public class UndirectedCycle<E> implements Cycle<E> {
    private Stack<E> cycle;
    
    public UndirectedCycle(UndirectedGraph<E> G) {
        checkNotNull(G);
        Set<E> visit = new Set<>();
        SymbolTable<E, E> pathTo = new HashTable<>();
        for (E v: G.vertices())
            if (!visit.contains(v) && !hasCycle())
                dfs(v, null, visit, pathTo, G);
    }

    private void dfs(E v, E u, Set<E> visit, SymbolTable<E, E> pathTo, UndirectedGraph<E> G) {
        visit.add(v);
        for (Edge<E> e: G.edgeOf(v)) {
            E w = e.other(v);
            if (hasCycle()) return;
            if (!visit.contains(w)) {
                pathTo.put(w, v);
                dfs(w, v, visit, pathTo, G);
            }
            else if (!w.equals(u)) {
                cycle = LinkedList.stack();
                cycle.push(w);
                while (!v.equals(w)) {
                    cycle.push(v);
                    v = pathTo.get(v);
                }
                cycle.push(w);
            }
        }
    }

    @Override
    public boolean hasCycle() {
        return cycle != null;
    }

    @Override
    public Iterable<E> cycle() {
        if (!hasCycle()) return LinkedList.bag();
        return cycle;
    }
}
