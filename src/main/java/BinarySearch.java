import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class BinarySearch {
    
    public <E extends Comparable<E>> int binarySearch(E[] elements, E e) {
        checkNotNull(elements);
        return binarySearch(0, elements.length-1, e, elements);
    }

    private <E extends Comparable<E>> int binarySearch(int left, int right, E e, E[] elements) {
        if (left > right) return -1;
        int mid = left + (right - left) / 2;
        if (less(e, elements[mid]))
            return binarySearch(left, mid-1, e, elements);
        else if (less(elements[mid], e))
            return binarySearch(mid+1, right, e, elements);
        else
            return mid;
    }
    
    public <E extends Comparable<E>> boolean less(E e1, E e2) {
        return e1.compareTo(e2) < 0;
    }

    public static void main(String[] args) {
        Scanner scanner = AlgData.getScanner("largeW.txt");
        Queue<Integer> q = new LinkedList<>();
        while (scanner.hasNextInt())
            q.add(scanner.nextInt());
        Integer[] integers = q.toArray(new Integer[0]);
        Arrays.sort(integers);
        
        scanner = AlgData.getScanner("largeT.txt");
        BinarySearch bs = new BinarySearch();
        while (scanner.hasNextInt()) { 
            int x = scanner.nextInt();
            if (bs.binarySearch(integers, x) == -1) {
                System.out.println(x);
                assert check(integers, x);
            }
        }
    }

    private static boolean check(Integer[] integers, int x) {
        for (int i=0; i<integers.length; i++)
            if (integers[i] == x)
                return false;
        return true;
    }
}
