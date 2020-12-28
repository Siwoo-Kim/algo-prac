import static com.google.common.base.Preconditions.checkNotNull;

public class UndirectedGraph<E> implements Graph<E> {
    private SymbolTable<E, Stack<Edge<E>>> G = new HashTable<>();
    private int V, E;
    
    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public Iterable<E> vertices() {
        return G.keys();
    }

    @Override
    public void addEdge(Edge<E> edge) {
        E v = edge.either();
        putVertex(v);
        putVertex(edge.other(v));
        G.get(v).push(edge);
        G.get(edge.other(v)).push(edge.reverse());
        E++;
    }

    private void putVertex(E v) {
        checkNotNull(v);
        if (!G.contains(v)) {
            G.put(v, LinkedList.stack());
            V++;
        }
    }

    @Override
    public Iterable<Edge<E>> edgeOf(E v) {
        checkNotNull(v);
        return G.get(v);
    }

    @Override
    public Graph<E> reverse() {
        UndirectedGraph<E> RG = new UndirectedGraph<>();
        for (E v: vertices())
            for (Edge<E> e: edgeOf(v))
                RG.addEdge(e.reverse());
        return RG;
    }

    @Override
    public String toString() {
        return formattedString();
    }
}
