
public interface SortedSymbolTable<K extends Comparable<K>, V> extends SymbolTable<K, V> {
    
    K min();
    
    K removeMin();
    
    K max();
    
    K removeMax();
    
    K floor(K key);
    
    K ceiling(K key);
    
    int rank(K key);
    
    K select(int k);
    
    Iterable<K> keys(K min, K max);
    
    int size(K min, K max);
    
}
