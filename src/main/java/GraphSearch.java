
public interface GraphSearch<E> {
    
    E source();
    
    Iterable<E> reachable();
    
    boolean hasPath(E v);
    
    Iterable<E> pathsTo(E v);
    
    int count();
}
