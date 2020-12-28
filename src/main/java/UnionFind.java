import java.util.Scanner;

public interface UnionFind<E> {
    
    void put(E v);
    
    boolean contains(E v);
    
    void union(E v, E w);
    
    boolean connected(E v, E w);
    
    E root(E v);
    
    int count();

    static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tinyUF.txt");
        int N = scanner.nextInt();
        UnionFind<Integer> uf = new WeightedUnionFind<>();
        for (int i=0; i<N; i++) {
            int v = scanner.nextInt(),
                    w = scanner.nextInt();
            uf.put(v);
            uf.put(w);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                System.out.printf("%d - %d%n", v, w);
            }
        }
        System.out.println(uf.count() + " components.");
    }
}
