import static com.google.common.base.Preconditions.checkNotNull;

public class MergeSort<E extends Comparable<E>> implements Sort<E> {
    private E[] aux;
    
    @SuppressWarnings("unchecked")
    @Override
    public void sort(E[] elements) {
        checkNotNull(elements);
        aux = (E[]) new Comparable[elements.length];
        sort(0, elements.length-1, elements);
    }

    private void sort(int left, int right, E[] elements) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        sort(left, mid, elements);
        sort(mid+1, right, elements);
        merge(left, mid, right, elements);
    }

    private void merge(int left, int mid, int right, E[] elements) {
        if (less(elements[mid], elements[mid+1])) return;
        for (int i=left; i<=right; i++)
            aux[i] = elements[i];
        int l = left, r = mid+1;
        for (int i=left; i<=right; i++) {
            if (l > mid) 
                elements[i] = aux[r++];
            else if (r > right) 
                elements[i] = aux[l++];
            else if (less(aux[r], aux[l]))
                elements[i] = aux[r++];
            else
                elements[i] = aux[l++];
        }
    }
}
