import static com.google.common.base.Preconditions.checkNotNull;

//Kosaraju-sharir
public class SCC<E> {
    private SymbolTable<E, Integer> components = new HashTable<>();
    private int id;
    
    public SCC(Digraph<E> G) {
        checkNotNull(G);
        Digraph<E> RG = G.reverse();
        Topological<E> top = new Topological<>(RG, true);
        for (E e: top.order())
            if (!components.contains(e))
                dfs(id++, e, G);
    }

    private void dfs(int id, E v, Graph<E> G) {
        components.put(v, id);
        for (Edge<E> e: G.edgeOf(v))
            if (!components.contains(e.to()))
                dfs(id, e.to(), G);
    }
    
    public int id(E v) {
        checkNotNull(v);
        if (!components.contains(v))
            throw new IllegalArgumentException();
        return components.get(v);
    }

    public int count() {
        return id;
    }
}
