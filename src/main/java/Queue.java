import java.util.Scanner;

public interface Queue<E> extends Iterable<E> {
    
    void enqueue(E e);
    
    E dequeue();
    
    E peek();
    
    boolean contains(E e);
    
    boolean isEmpty();
    
    int size();

    static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tobe.txt");
        Queue<String> stack = LinkedList.queue();
        while (scanner.hasNext()) {
            String e = scanner.next().trim();
            if ("-".equals(e))
                System.out.println(stack.dequeue());
            else
                stack.enqueue(e);
        }
        System.out.printf("(%d left on the stack)%n", stack.size());
        for (String e: stack)
            System.out.println(e);
    }
}
