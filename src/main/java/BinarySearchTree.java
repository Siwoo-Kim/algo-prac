import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

public class BinarySearchTree<K extends Comparable<K>, V> implements SortedSymbolTable<K, V> {
    
    private class Node {
        K key;
        V value;
        Node left, right;
        int size;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }
    
    private Node root;
    
    @Override
    public K min() {
        if (isEmpty())
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node min(Node root) {
        if (root.left == null) return root;
        return min(root.left);
    }

    @Override
    public K removeMin() {
        if (isEmpty())
            throw new NoSuchElementException();
        K min = min();
        root = removeMin(root);
        assert invariant();
        return min;
    }

    private Node removeMin(Node root) {
        if (root.left == null) return root.right;
        root = removeMin(root.left);
        root.size = size(root.left) + size(root.right) + 1;
        return root;
    }

    private int size(Node node) {
        return node == null? 0: node.size;
    }

    @Override
    public K max() {
        if (isEmpty())
            throw new NoSuchElementException();
        return max(root).key;
    }

    private Node max(Node root) {
        if (root.right == null) return root;
        return max(root.right);
    }

    @Override
    public K removeMax() {
        if (isEmpty())
            throw new NoSuchElementException();
        K max = max();
        root = removeMax(root);
        assert invariant();
        return max;
    }

    private Node removeMax(Node root) {
        if (root.right == null) return root.left;
        root.right = removeMax(root.right);
        root.size = size(root.left) + size(root.right) + 1;
        return root;
    }

    @Override
    public K floor(K key) {
        checkNotNull(key);
        Node node = floor(root, key);
        return node == null? null: node.key;
    }

    private Node floor(Node root, K key) {
        if (root == null) return null;
        if (less(key, root.key))
            return floor(root.left, key);
        else if (less(root.key, key)) {
            Node node = floor(root.right, key);
            return node == null? root: node;
        } 
        else return root;  
    }

    @Override
    public K ceiling(K key) {
        checkNotNull(key);
        Node node = ceiling(root, key);
        return node == null? null: node.key;
    }

    private Node ceiling(Node root, K key) {
        if (root == null) return null;
        if (less(key, root.key)) {
            Node node = ceiling(root.left, key);
            return node == null? root: node;
        } else if (less(root.key, key))
            return ceiling(root.right, key);
        else
            return root;
    }

    @Override
    public int rank(K key) {
        checkNotNull(key);
        return rank(root, key);
    }

    private int rank(Node root, K key) {
        if (root == null) return 0;
        int left = size(root.left);
        if (less(key, root.key))
            return rank(root.left, key);
        else if (less(root.key, key))
            return left + 1 + rank(root.right, key);
        else
            return left;
    }

    @Override
    public K select(int k) {
        checkElementIndex(k, size());
        return select(root, k);
    }

    private K select(Node root, int k) {
        if (root == null) return null;
        int left = size(root.left);
        if (left > k) return select(root.left, k);
        else if (left < k) return select(root.right, k-left-1);
        else return root.key;
    }

    public Bag<Node> inOrder() {
        return inOrder(root, LinkedList.bag());
    }

    private Bag<Node> inOrder(Node root, Bag<Node> bag) {
        if (root == null) return bag;
        inOrder(root.left, bag);
        bag.add(root);
        inOrder(root.right, bag);
        return bag;
    }

    @Override
    public Iterable<K> keys(K min, K max) {
        checkNotNull(min, max);
        Bag<K> keys = LinkedList.bag();
        for (Node node: inOrder()) {
            if (between(node.key, min, max))
                keys.add(node.key);
        }
        return keys;
    }

    private boolean between(K key, K min, K max) {
        assert key != null && min != null && max != null;
        if (key.compareTo(min) == 0 || key.compareTo(max) == 0)
            return true;
        return less(min, key) && less(key, max);
    }

    @Override
    public int size(K min, K max) {
        checkNotNull(min, max);
        int left = rank(min), right = rank(max);
        int size = right - left;
        if (contains(max))
            size++;
        return size;
    }

    @Override
    public void put(K key, V value) {
        checkNotNull(key);
        root = put(root, key, value);
        assert invariant();
    }

    private Node put(Node root, K key, V value) {
        if (root == null) return new Node(key, value, 1);
        if (less(key, root.key)) root.left = put(root.left, key, value);
        else if (less(root.key, key)) root.right = put(root.right, key, value);
        else root.value = value;
        root.size = size(root.left) + size(root.right) + 1;
        return root;
    }

    @Override
    public V get(K key) {
        checkNotNull(key);
        Node node = get(root, key);
        return node == null? null: node.value;
    }

    private Node get(Node root, K key) {
        if (root == null) return null;
        if (less(key, root.key)) return get(root.left, key);
        if (less(root.key, key)) return get(root.right, key);
        else return root;
    }

    private boolean less(K k1, K k2) {
        checkNotNull(k1, k2);
        return k1.compareTo(k2) < 0;
    }

    @Override
    public void remove(K key) {
        if (contains(key))
            root = remove(root, key);
        assert invariant();
    }

    private Node remove(Node root, K key) {
        if (root == null) return null;
        if (less(key, root.key)) root.left = remove(root.left, key);
        else if (less(root.key, key)) root.right = remove(root.right, key);
        else {
            if (root.left == null)
                return root.right;
            if (root.right == null)
                return root.left;
            Node node = max(root.left);
            root.key = node.key;
            root.value = node.value;
            root = removeMax(root.left);
        }
        root.size = size(root.left) + size(root.right) + 1;
        return root;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return size(root);
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
        for (Node node: inOrder())
            values.add(node.value);
        return values;
    }
    
    public boolean invariant() {
        assert isSorted();
        assert rangeCheck();
        return isSorted() && rangeCheck();
    }

    private boolean rangeCheck() {
        for (K key: keys()) {
            if (select(rank(key)) != key) return false;
            if (floor(key) != key) return false;
            if (ceiling(key) != key) return false;
        }
        for (int i=0; i<size(); i++)
            if (rank(select(i)) != i) return false;
        return true;
    }

    private boolean isSorted() {
        return isSorted(root, null, null);
    }

    private boolean isSorted(Node root, K min, K max) {
        if (root == null) return true;
        if (min != null && less(root.key, min)) return false;
        if (max != null && less(max, root.key)) return false;
        return isSorted(root.left, min, root.key) && isSorted(root.right, root.key, max);
    }
}
