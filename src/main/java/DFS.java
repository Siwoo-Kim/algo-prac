import static com.google.common.base.Preconditions.checkNotNull;

public class DFS<E> implements GraphSearch<E> {
    private final E source;
    private SymbolTable<E, E> pathTo = new HashTable<>();
    
    public DFS(E source, Graph<E> G) {
        checkNotNull(source, G);
        this.source = source;
        Set<E> visit = new Set<>();
        pathTo.put(source, null);
        dfs(source, visit, G);
    }

    private void dfs(E v, Set<E> visit, Graph<E> G) {
        visit.add(v);
        for (Edge<E> e: G.edgeOf(v)) {
            E w = e.other(v);
            if (!visit.contains(w)) {
                pathTo.put(w, v);
                dfs(w, visit, G);
            }
        }
    }

    @Override
    public E source() {
        return source;
    }

    @Override
    public Iterable<E> reachable() {
        return pathTo.keys();
    }

    @Override
    public boolean hasPath(E v) {
        checkNotNull(v);
        return pathTo.contains(v);
    }

    @Override
    public Iterable<E> pathsTo(E v) {
        checkNotNull(v);
        Stack<E> stack = LinkedList.stack();
        if (!hasPath(v))
            return stack;
        while (v != null) {
            stack.push(v);
            v = pathTo.get(v);
        }
        return stack;
    }

    @Override
    public int count() {
        return pathTo.size();
    }
}
