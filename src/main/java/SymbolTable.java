
public interface SymbolTable<K, V> {

    void put(K key, V value);
    
    V get(K key);
    
    void remove(K key);
    
    boolean contains(K key);
    
    int size();
    
    boolean isEmpty();
    
    Iterable<K> keys();
    
    Iterable<V> values();
    
}
