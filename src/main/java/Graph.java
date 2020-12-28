import static com.google.common.base.Preconditions.checkNotNull;

public interface Graph<E> {
    
    int V();
    
    int E();
    
    Iterable<E> vertices();
    
    void addEdge(Edge<E> edge);
    
    Iterable<Edge<E>> edgeOf(E v);
    
    Graph<E> reverse();
    
    default int degreeOf(E v) {
        checkNotNull(v);
        int cnt = 0;
        for (Edge<E> e: edgeOf(v))
            cnt++;
        return cnt;
    }
    
    default int maxDegree() {
        int max = 0;
        for (E v: vertices())
            max = Math.max(max, degreeOf(v));
        return max;
    }
    
    default int averageDegree() {
        return E() / V();
    }
    
    default int numberOfSelfLoops() {
        int cnt = 0;
        for (E v: vertices())
            for (Edge<E> e: edgeOf(v))
                if (e.other(v).equals(v))
                    cnt++;
        return cnt;
    }
    
    default String formattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V()).append(" vertices, ")
                .append(E()).append("edges\n");
        for (E v: vertices()) {
            sb.append(v).append(": ");
            for (Edge<E> e: edgeOf(v))
                sb.append(e.other(v)).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }
}
