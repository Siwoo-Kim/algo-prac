import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

public class LinkedList<E> implements Iterable<E> {

    private class Node {
        E e;
        Node next, prev;

        public Node(E e) {
            this.e = e;
        }
    }
    
    private Node head, tail;
    private int N;
    
    public LinkedList() { }
    
    public LinkedList(E[] elements) {
        checkNotNull(elements);
        for (E e: elements)
            addFirst(e);
    }
    
    public void addFirst(E e) {
        checkNotNull(e);
        Node old = head;
        head = new Node(e);
        head.next = old;
        if (old != null)
            old.prev = head;
        else
            tail = head;
        N++;
    }

    public void addLast(E e) {
        checkNotNull(e);
        Node old = tail;
        tail = new Node(e);
        tail.prev = old;
        if (old != null)
            old.next = tail;
        else
            head = tail;
        N++;
    }

    public E removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        E e = head.e;
        head = head.next;
        if (head != null)
            head.prev = null;
        else
            tail = null;
        N--;
        return e;
    }

    public E removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        E e = tail.e;
        tail = tail.prev;
        if (tail != null)
            tail.next = null;
        else
            head = null;
        N--;
        return e;
    }

    public E peekFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return head.e;
    }

    public E peekLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        return tail.e;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(E e) {
        checkNotNull(e);
        return contains(head, e);
    }

    private boolean contains(Node node, E e) {
        if (node == null) return false;
        if (node.e.equals(e)) return true;
        else return contains(node.next, e);
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    public Iterator<E> reversedIterator() {
        return new RItr();
    }
    
    private class Itr implements Iterator<E>{
        private Node node = head;

        @Override
        public boolean hasNext() {
            return node != null;
        }
        
        @Override
        public E next() {
            E e = node.e;
            node = node.next;
            return e;
        }
    }
    
    private class RItr implements Iterator<E> {
        private Node node = tail;

        @Override
        public boolean hasNext() {
            return node != null;
        }
        
        @Override
        public E next() {
            E e = node.e;
            node = node.prev;
            return e;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node=head; node!=null; node=node.next)
            sb.append(node.e).append(" ");
        return sb.toString();
    }

    public static <E> Stack<E> stack() {
        return new Stack<E>() {
            private final LinkedList<E> ll = new LinkedList<>();
            
            @Override
            public void push(E e) {
                ll.addFirst(e);
            }

            @Override
            public E pop() {
                return ll.removeFirst();
            }

            @Override
            public E peek() {
                return ll.peekFirst();
            }

            @Override
            public boolean contains(E e) {
                return ll.contains(e);
            }

            @Override
            public boolean isEmpty() {
                return ll.isEmpty();
            }

            @Override
            public int size() {
                return ll.size();
            }

            @Override
            public Iterator<E> iterator() {
                return ll.iterator();
            }

            @Override
            public String toString() {
                return ll.toString();
            }
        };
    }
    
    public static <E> Queue<E> queue() {
        return new Queue<E>() {
            private final LinkedList<E> ll = new LinkedList<>();
            @Override
            public void enqueue(E e) {
                ll.addFirst(e);
            }

            @Override
            public E dequeue() {
                return ll.removeLast();
            }

            @Override
            public E peek() {
                return ll.peekLast();
            }

            @Override
            public boolean contains(E e) {
                return ll.contains(e);
            }

            @Override
            public boolean isEmpty() {
                return ll.isEmpty();
            }

            @Override
            public int size() {
                return ll.size();
            }

            @Override
            public Iterator<E> iterator() {
                return ll.reversedIterator();
            }

            @Override
            public String toString() {
                return ll.toString();
            }
        };
    }
    
    public static <E> Bag<E> bag() {
        return new Bag<E>() {
            private final LinkedList<E> ll = new LinkedList<>();
            
            @Override
            public void add(E e) {
                ll.addFirst(e);
            }

            @Override
            public boolean isEmpty() {
                return ll.isEmpty();
            }

            @Override
            public int size() {
                return ll.size();
            }

            @Override
            public Iterator<E> iterator() {
                return ll.reversedIterator();
            }

            @Override
            public String toString() {
                return ll.toString();
            }
        };
    }
}
