import java.util.Scanner;

public class GraphTest {

    public GraphTest(String file) {
        Scanner scanner = AlgData.getScanner(file);
        int N = scanner.nextInt(),
                E = scanner.nextInt();
        Digraph<Integer> G = new Digraph<>();
        for (int i=0; i<E; i++) {
            Edge<Integer> e = new Edge<>(scanner.nextInt(), 
                    scanner.nextInt());
            G.addEdge(e);
        }
        System.out.println(G);
        
        int source = 0;
        GraphSearch<Integer> gs = new DFS<>(source, G);

        System.out.println(gs.reachable());
        for (int v: G.vertices()) {
            if (gs.hasPath(v)) {
                System.out.print(v + ": ");
                System.out.print(gs.pathsTo(v));
                System.out.println();
            }
        }
        
        Cycle<Integer> cycle = new DirectedCycle<>(G);
        System.out.println(cycle.cycle());
        
//        Topological<Integer> top = new Topological<>(G, false);
//        System.out.println(top.order());

        System.out.println("================================================");
        SCC<Integer> scc = new SCC<>(G);
        Bag<Integer>[] components = new Bag[scc.count()];
        for (int i=0; i<scc.count(); i++)
            components[i] = LinkedList.bag();
        for (int v: G.vertices())
            components[scc.id(v)].add(v);
        for (int i=0; i<scc.count(); i++) {
            System.out.println("id: " + i);
            System.out.println(components[i]);
        }
    }

    public static void main(String[] args) {
        new GraphTest("tinyDG.txt");
    }
}
