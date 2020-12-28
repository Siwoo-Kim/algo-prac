import static com.google.common.base.Preconditions.checkNotNull;

public class InsertionSort<E extends Comparable<E>> implements Sort<E> {

    @Override
    public void sort(E[] elements) {
        checkNotNull(elements);
        int N = elements.length;
        for (int i=1; i<N; i++) {
            int j = i;
            while (j > 0 && less(elements[j], elements[j-1])) {
                swap(j, j-1, elements);
                j--;
            }
        }
    }
}
