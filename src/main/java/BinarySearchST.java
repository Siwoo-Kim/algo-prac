import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

public class BinarySearchST<K extends Comparable<K>, V> implements SortedSymbolTable<K, V> {
    
    private class Node<K extends Comparable<K>, V> 
            implements Comparable<Node<K, V>> {
        K key;
        V value;

        public Node(K key, V value) {
            checkNotNull(key);
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Node<K, V> that) {
            return key.compareTo(that.key);
        }

        @Override
        public String toString() {
            return String.format("%s: %s", key, value);
        }
    }
    
    private static final int DEFAULT_CAPACITY = 2;
    @SuppressWarnings("unchecked")
    private Node<K, V>[] elements = new Node[DEFAULT_CAPACITY];
    private int N;
    
    @Override
    public K min() {
        if (isEmpty())
            throw new IllegalArgumentException();
        return elements[0].key;
    }

    @Override
    public K removeMin() {
        K min = min();
        remove(min);
        return min;
    }

    @Override
    public K max() {
        if (isEmpty())
            throw new IllegalArgumentException();
        return elements[N-1].key;
    }

    @Override
    public K removeMax() {
        K max = max();
        remove(max);
        return max;
    }

    @Override
    public K floor(K key) {
        int k = rank(key);
        if (k == N) 
            return elements[N-1].key;
        if (k == 0 && contains(key)) 
            return elements[0].key;
        if (contains(key))
            return elements[k].key;
        else
            return elements[k-1].key;
    }

    @Override
    public K ceiling(K key) {
        int k = rank(key);
        if (k == N) return null;
        return elements[k].key;
    }

    @Override
    public int rank(K key) {
        checkNotNull(key);
        return rank(0, N-1, key);
    }

    private int rank(int left, int right, K key) {
        if (left > right) return left;
        int mid = left + (right - left) / 2;
        if (less(key, elements[mid].key))
            return rank(left, mid-1, key);
        else if (less(elements[mid].key, key))
            return rank(mid+1, right, key);
        else
            return mid;
    }

    private boolean less(K k1, K k2) {
        assert k1 != null && k2 != null;
        return k1.compareTo(k2) < 0;
    }

    @Override
    public K select(int k) {
        checkElementIndex(k, N);
        return elements[k].key;
    }

    @Override
    public Iterable<K> keys(K min, K max) {
        checkNotNull(min, max);
        if (less(max, min))
            throw new IllegalArgumentException();
        int left = rank(min), right = rank(max);
        Bag<K> keys = LinkedList.bag();
        for (int i=left; i<right; i++)
            keys.add(elements[i].key);
        if (contains(max))
            keys.add(elements[right].key);
        return keys;
    }

    @Override
    public int size(K min, K max) {
        checkNotNull(min, max);
        if (less(max, min))
            throw new IllegalArgumentException();
        int left = rank(min), right = rank(max);
        int size = right - left;
        if (contains(max))
            size++;
        return size;
    }

    @Override
    public void put(K key, V value) {
        checkNotNull(key);
        int k = rank(key);
        if (contains(key))
            elements[k].value = value;
        else {
            if (N == elements.length)
                resize(elements.length << 1);
            for (int i=N; i>k; i--)
                elements[i] = elements[i-1];
            elements[k] = new Node<>(key, value); 
            N++;
        }
        assert invariant();
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Node<K, V>[] tmp = new Node[capacity];
        System.arraycopy(elements, 0, tmp, 0, N);
        this.elements = tmp;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        int k = rank(key);
        if (k < N && elements[k].key.equals(key))
            return elements[k].value;
        return null;
    }

    @Override
    public void remove(K key) {
        if (!contains(key))
            throw new IllegalArgumentException();
        int k = rank(key);
        for (int i=k; i<N-1; i++)
            elements[i] = elements[i+1];
        elements[--N] = null;
        assert invariant();
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterable<K> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<V> values() {
        Bag<V> values = LinkedList.bag();
        for (int i=0; i<N; i++)
            values.add(this.elements[i].value);
        return values;
    }
    
    public boolean invariant() {
        assert isSorted();
        assert rankCheck();
        return isSorted() && rankCheck();
    }

    private boolean rankCheck() {
        for (K key: keys()) {
            if (!select(rank(key)).equals(key)) return false;
            if (!floor(key).equals(key)) return false;
            if (!ceiling(key).equals(key)) return false;
        }
        for (int i=0; i<N; i++)
            if (rank(select(i)) != i) return false;
        return true;
    }

    private boolean isSorted() {
        for (int i=1; i<N; i++)
            if (less(elements[i].key, elements[i-1].key))
                return false;
        return true;
    }
}
