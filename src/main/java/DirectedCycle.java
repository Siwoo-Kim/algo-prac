import static com.google.common.base.Preconditions.checkNotNull;

public class DirectedCycle<E> implements Cycle<E> {
    private Stack<E> cycle;
    
    public DirectedCycle(Digraph<E> G) {
        checkNotNull(G);
        Stack<E> stack = LinkedList.stack();
        Set<E> visit = new Set<>();
        for (E v: G.vertices())
            if (!visit.contains(v) && !hasCycle())
                dfs(v, stack, visit, G);
    }

    private void dfs(E from, Stack<E> stack, Set<E> visit, Digraph<E> G) {
        visit.add(from);
        stack.push(from);
        for (Edge<E> e: G.edgeOf(from)) {
            if (hasCycle()) return;
            E to = e.to();
            if (!visit.contains(to))
                dfs(to, stack, visit, G);
            else if (stack.contains(to)) { //from -> to && to -> from (stack)
                cycle = LinkedList.stack();
                cycle.push(to);
                while (!stack.isEmpty()) {
                    cycle.push(stack.pop());
                    if (cycle.peek().equals(to))
                        break;
                }
                return;
            }
        }
        stack.pop();
    }

    @Override
    public boolean hasCycle() {
        return cycle != null;
    }

    @Override
    public Iterable<E> cycle() {
        return cycle;
    }
}
