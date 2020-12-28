import static com.google.common.base.Preconditions.checkNotNull;

public class Set<E> {
    private final SymbolTable<E, Object> table = new HashTable<>();
    private final Object key = new Object();
    
    public void add(E v) {
        checkNotNull(v);
        table.put(v, key);
    }
    
    public boolean contains(E v) {
        return table.contains(v);
    }

    @Override
    public String toString() {
        return table.keys().toString();
    }
}
