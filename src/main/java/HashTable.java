import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

public class HashTable<K, V> implements SymbolTable<K, V> {

    private class Bucket<K, V> {
        private class Node {
            K key;
            V value;
            Node next;
            public Node(K key, V value) {
                checkNotNull(key);
                this.key = key;
                this.value = value;
            }
        }
        Node root;
        
        public V get(K key) {
            assert key != null;
            Node node = get(root, key);
            return node == null? null: node.value;
        }
        
        public boolean contains(K key) {
            checkNotNull(key);
            return get(root, key) != null;
        }
        
        private Node get(Node root, K key) {
            if (root == null) return null;
            if (root.key.equals(key)) return root;
            else return get(root.next, key);
        }

        public void put(K key, V value) {
            assert key != null;
            root = put(root, key, value);
        }

        private Node put(Node node, K key, V value) {
            if (node == null) return new Node(key, value);
            if (node.key.equals(key)) node.value = value;
            else node.next = put(node.next, key, value);
            return node;
        }

        public void remove(K key) {
            assert key != null;
            root = remove(root, key);
        }

        private Node remove(Node root, K key) {
            if (root == null) throw new AssertionError();
            if (root.key.equals(key)) return root.next;
            root.next = remove(root.next, key);
            return root;
        }
    }
    
    private static final int DEFAULT_CAPACITY = 2;
    private Bucket<K, V>[] buckets;
    private int N;
    
    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        buckets = new Bucket[capacity];
        for (int i=0; i<capacity; i++)
            buckets[i] = new Bucket<>();
    }

    @Override
    public void put(K key, V value) {
        if (N == buckets.length*10)
            resize(buckets.length<<1);
        int h = hash(key);
        buckets[h].put(key, value);
        N++;
    }

    private void resize(int capacity) {
        HashTable<K, V> tmp = new HashTable<>(capacity);
        for (K key: keys())
            tmp.put(key, get(key));
        this.buckets = tmp.buckets;
    }

    private int hash(K key) {
        checkNotNull(key);
        return (key.hashCode() & 0x7fffffff) % buckets.length;
    }

    @Override
    public V get(K key) {
        int h = hash(key);
        return buckets[h].get(key);
    }

    @Override
    public void remove(K key) {
        int h = hash(key);
        if (contains(key))
            buckets[h].remove(key);
        if (buckets.length > DEFAULT_CAPACITY 
                && N <= 2*buckets.length)
            resize(buckets.length >> 1);
    }

    @Override
    public boolean contains(K key) {
        return buckets[hash(key)].contains(key);
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
        Bag<K> keys = LinkedList.bag();
        for (Bucket<K, V> bucket : buckets)
            for (Bucket<K, V>.Node node = bucket.root; node != null; node = node.next)
                keys.add(node.key);
        return keys;
    }

    @Override
    public Iterable<V> values() {
        Bag<V> keys = LinkedList.bag();
        for (Bucket<K, V> bucket : buckets)
            for (Bucket<K, V>.Node node = bucket.root; node != null; node = node.next)
                keys.add(node.value);
        return keys;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (K key: keys()) {
            sb.append(key).append("=")
                    .append(get(key))
                    .append(" ");
        }
        return sb.toString();
    }
}
