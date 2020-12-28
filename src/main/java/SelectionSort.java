import static com.google.common.base.Preconditions.checkNotNull;

public class SelectionSort<E extends Comparable<E>> implements Sort<E> {
    
    @Override
    public void sort(E[] elements) {
        checkNotNull(elements);
        int N = elements.length;
        for (int i=0; i<N; i++) {
            int min = i;
            for (int j=i+1; j<N; j++)
                if (less(elements[j], elements[min]))
                    min = j;
            swap(i, min, elements);
        }
    }
}
