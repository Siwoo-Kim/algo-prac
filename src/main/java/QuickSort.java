import static com.google.common.base.Preconditions.checkNotNull;

public class QuickSort<E extends Comparable<E>> implements Sort<E> {

    @Override
    public void sort(E[] elements) {
        checkNotNull(elements);
        sort(0, elements.length-1, elements);
    }

    private void sort(int left, int right, E[] elements) {
        if (left >= right) return;
        int pivot = partition(left, right, elements);
        sort(left, pivot-1, elements);
        sort(pivot+1, right, elements);
    }

    private int partition(int left, int right, E[] elements) {
        E e = elements[left];
        int i = left, j = right+1;
        while (true) {
            while (less(elements[++i], e)) 
                if (i == right) break;
            while (less(e, elements[--j]))
                if (j == left) break;
            if (i >= j) break;
            swap(i, j, elements);
        }
        swap(j, left, elements);
        return j;
    }
}
