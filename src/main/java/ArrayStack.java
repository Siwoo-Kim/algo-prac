import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

public class ArrayStack<E> implements Stack<E> {
    private static final int DEFAULT_CAPACITY = 2;
    @SuppressWarnings("unchecked")
    private E[] elements = (E[]) new Object[DEFAULT_CAPACITY];
    private int N;
    
    @Override
    public void push(E e) {
        checkNotNull(e);
        if (N == elements.length)
            resize(N << 1);
        elements[N++] = e;
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        E[] tmp = (E[]) new Object[capacity];
        System.arraycopy(elements, 0, tmp, 0, N);
        this.elements = tmp;
    }

    @Override
    public E pop() {
        if (isEmpty())
            throw new NoSuchElementException();
        E e = elements[N-1];
        elements[--N] = null;
        if (N > DEFAULT_CAPACITY && N == (elements.length >> 2))
            resize(elements.length >> 1);
        return e;
    }

    @Override
    public E peek() {
        if (isEmpty())
            throw new NoSuchElementException();
        return elements[N-1];
    }

    @Override
    public boolean contains(E e) {
        checkNotNull(e);
        for (int i=0; i<N; i++)
            if (e.equals(elements[i]))
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
        int i = N-1;

        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public E next() {
            return elements[i--];
        }
    }

}
