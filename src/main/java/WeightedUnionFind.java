import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class WeightedUnionFind<E> implements UnionFind<E> {
    private Map<E, E> components = new HashMap<>();
    private Map<E, Integer> sizes = new HashMap<>();
    private int N;
    
    @Override
    public void put(E v) {
        if (!contains(v)) {
            components.put(v, v);
            sizes.put(v, 1);
            N++;
        }
    }

    @Override
    public boolean contains(E v) {
        checkNotNull(v);
        return components.containsKey(v);
    }

    @Override
    public void union(E v, E w) {
        if (connected(v, w)) return;
        v = root(v);
        w = root(w);
        if (sizes.get(v) < sizes.get(w)) {
            E t = v;
            v = w;
            w = t;
        }
        sizes.put(v, sizes.get(w) + sizes.get(v));
        components.put(w, v);
        N--;
    }

    @Override
    public boolean connected(E v, E w) {
        checkNotNull(v, w);
        checkArgument(contains(v) && contains(w));
        return root(v).equals(root(w));
    }

    @Override
    public E root(E v) {
        checkNotNull(v);
        if (v.equals(components.get(v)))
            return v;
        E root = root(components.get(v));
        components.put(v, root);
        return root;
    }

    @Override
    public int count() {
        return N;
    }
}
