import javax.transaction.TransactionRequiredException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;

public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {
    private static final int DEFAULT_CAPACITY = 2;
    @SuppressWarnings("unchecked")
    private E[] elements = (E[]) new Comparable[DEFAULT_CAPACITY];
    private int N;
    private final Comparator<E> comparator;

    public PriorityQueue() {
        this(null);
    }
    public PriorityQueue(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void enqueue(E e) {
        checkNotNull(e);
        if (N+1 == elements.length)
            resize(elements.length << 1);
        elements[++N] = e;
        swim(N);
    }

    private void swim(int k) {
        if (k <= 1) return;
        int p = k >> 1;
        if (less(elements[k], elements[p])) {
            swap(k, p, elements);
            swim(p);
        }
    }

    private void swap(int i, int j, E[] elements) {
        checkElementIndex(i, elements.length);
        checkElementIndex(j, elements.length);
        E e = elements[i];
        elements[i] = elements[j];
        elements[j] = e;
    }

    private boolean less(E e1, E e2) {
        assert e1 != null && e2 != null;
        if (comparator == null) {
            return e1.compareTo(e2) < 0;
        } else {
            return comparator.compare(e1, e2) < 0;
        }
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        E[] tmp = (E[]) new Comparable[capacity];
        System.arraycopy(elements, 1, tmp, 1, N);
        this.elements = tmp;
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        E e = elements[1];
        swap(1, N, elements);
        elements[N--] = null;
        sink(1);
        if (N > DEFAULT_CAPACITY 
                && elements.length >> 2 == N)
            resize(elements.length >> 1);
        return e;
    }

    private void sink(int k) {
        int c = k << 1;
        if (c > N) return;
        if (c < N && less(elements[c+1], elements[c]))
            c++;
        if (less(elements[c], elements[k])) {
            swap(c, k, elements);
            sink(c);
        }
    }

    @Override
    public E peek() {
        if (isEmpty())
            throw new NoSuchElementException();
        return elements[1];
    }

    @Override
    public boolean contains(E e) {
        checkNotNull(e);
        for (int i=1; i<=N; i++)
            if (elements[i].equals(e))
                return true;
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    private class Itr implements Iterator<E> {
        private int i = 1;
        
        @Override
        public boolean hasNext() {
            return i <= N;
        }

        @Override
        public E next() {
            return elements[i++];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tinyBatch.txt");
        Queue<Transaction> pq = new PriorityQueue<>();
        int threshold = 5;
        while (scanner.hasNextLine()) {
            Transaction transaction = new Transaction(scanner.nextLine());
            pq.enqueue(transaction);
            if (pq.size() > threshold)
                pq.dequeue();
        }
        Stack<Transaction> stack = LinkedList.stack();
        while (!pq.isEmpty())
            stack.push(pq.dequeue());
        while (!stack.isEmpty())
            System.out.println(stack.pop());
    }
}
