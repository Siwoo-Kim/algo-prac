
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

public class SequentialST<K, V> implements SymbolTable<K, V> {
    private static final int DEFAULT_CAPACITY = 2;
    @SuppressWarnings("unchecked")
    private K[] keys = (K[]) new Object[DEFAULT_CAPACITY];
    @SuppressWarnings("unchecked")
    private V[] values = (V[]) new java.lang.Object[DEFAULT_CAPACITY];
    private int N;

    @Override
    public void put(K key, V value) {
        checkNotNull(key);
        for (int i=0; i<N; i++) {
            if (keys[i].equals(key)) {
                keys[i] = key;
                values[i] = value;
                return;
            }
        }
        if (N == keys.length)
            resize(N << 1);
        keys[N] = key;
        values[N++] = value;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        K[] keys = (K[]) new Object[capacity];
        V[] values = (V[]) new Object[capacity];
        System.arraycopy(this.keys, 0, keys, 0, N);
        System.arraycopy(this.values, 0, values, 0, N);
        this.keys = keys;
        this.values = values;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        for (int i=0; i<N; i++)
            if (keys[i].equals(key))
                return values[i];
        return null;
    }

    @Override
    public void remove(K key) {
        if (!contains(key))
            throw new IllegalArgumentException();
        int k = -1;
        for (int i=0; i<N; i++) {
            if (keys[i].equals(key)) {
                k = i;
                break;
            }
        }
        for (int i=k; i<N-1; i++) {
            keys[i] = keys[i+1];
            values[i] = values[i+1];
        }
        keys[--N] = null;
        values[N] = null;
        if (N > DEFAULT_CAPACITY && N == keys.length >> 2)
            resize(keys.length >> 1);
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
        return new LinkedList<>(Arrays.copyOf(keys, N));
    }

    @Override
    public Iterable<V> values() {
        return new LinkedList<>(Arrays.copyOf(values, N));
    }
}
