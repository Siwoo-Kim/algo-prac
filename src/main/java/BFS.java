import static com.google.common.base.Preconditions.checkNotNull;

public class BFS<E> implements GraphSearch<E> {
    private final E source;
    private SymbolTable<E, E> pathTo = new HashTable<>();
    
    public BFS(E source, Graph<E> G) {
        checkNotNull(source, G);
        this.source = source;
        Queue<E> q = LinkedList.queue();
        q.enqueue(source);
        pathTo.put(source, null);
        while (!q.isEmpty()) {
            E v = q.dequeue();
            for (Edge<E> e: G.edgeOf(v)) {
                E w = e.other(v);
                if (!pathTo.contains(w)) {
                    pathTo.put(w, v);
                    q.enqueue(w);
                }
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
        for (E u=v; u!=null; u=pathTo.get(u))
            stack.push(u);
        return stack;
    }

    @Override
    public int count() {
        return pathTo.size();
    }
}
