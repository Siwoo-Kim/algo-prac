
public interface SPT<E> {
    
    double distTo(E v);
    
    Iterable<E> pathTo(E v);
    
    boolean hasPathTo(E v);
    
}
