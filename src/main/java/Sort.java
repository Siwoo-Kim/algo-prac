import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

public interface Sort<E extends Comparable<E>> {
    
    void sort(E[] elements);
    
    default boolean less(E e1, E e2) {
        checkNotNull(e1, e2);
        return e1.compareTo(e2) < 0;
    }
    
    default boolean isSorted(E[] elements) {
        checkNotNull(elements);
        int N = elements.length;
        for (int i=1; i<N; i++)
            if (less(elements[i], elements[i-1]))
                return false;
        return true;
    }
    
    default void swap(int i, int j, E[] elements) {
        checkNotNull(elements);
        checkElementIndex(i, elements.length);
        checkElementIndex(j, elements.length);
        if (i == j) return;
        E e = elements[i];
        elements[i] = elements[j];
        elements[j] = e;
    }

    static void main(String[] args) {
        String[] elements = "S O R T E X A M P L E".split("\\s+");
        Sort<String> sort = new QuickSort<>();
        sort.sort(elements);
        assert sort.isSorted(elements);
        for (String e: elements)
            System.out.print(e + " ");
        System.out.println();
    }
    
}
