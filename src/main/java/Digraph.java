import static com.google.common.base.Preconditions.checkNotNull;

public class Digraph<E> implements Graph<E> {
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
        checkNotNull(edge);
        putVertex(edge.from());
        putVertex(edge.to());
        G.get(edge.from()).push(edge);
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
    public Digraph<E> reverse() {
        Digraph<E> RG = new Digraph<>();
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
