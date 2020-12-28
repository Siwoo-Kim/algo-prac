
public interface Bag<E> extends Iterable<E> {
    
    void add(E e);
    
    boolean isEmpty();
    
    int size();
    
}
