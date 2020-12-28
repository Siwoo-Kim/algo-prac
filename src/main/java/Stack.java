import java.util.Scanner;

public interface Stack<E> extends Iterable<E> {
    
    void push(E e);
    
    E pop();
    
    E peek();
    
    boolean contains(E e);
    
    boolean isEmpty();
    
    int size();

    static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("tobe.txt");
        Stack<String> stack = LinkedList.stack();
        while (scanner.hasNext()) {
            String e = scanner.next().trim();
            if ("-".equals(e))
                System.out.println(stack.pop());
            else 
                stack.push(e);
        }
        System.out.printf("(%d left on the stack)%n", stack.size());
        for (String e: stack)
            System.out.println(e);
    }
}
