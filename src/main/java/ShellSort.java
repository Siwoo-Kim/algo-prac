import static com.google.common.base.Preconditions.checkNotNull;

public class ShellSort<E extends Comparable<E>> implements Sort<E> {

    @Override
    public void sort(E[] elements) {
        checkNotNull(elements);
        int k = 1, N = elements.length;
        while (N/3 > k)
            k = 3 * k + 1;
        while (k > 0) {
            for (int i=0; i<N; i+=k) {
                int j = i;
                while (j >= k && less(elements[j], elements[j-k])) {
                    swap(j, j-k, elements);
                    j-=k;
                }
            }
            k /= 3;
        }
    }
}
