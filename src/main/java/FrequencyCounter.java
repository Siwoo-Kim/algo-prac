import java.util.Arrays;
import java.util.Scanner;

import static com.google.common.base.Preconditions.checkNotNull;

public class FrequencyCounter {

    public FrequencyCounter(String file) {
        checkNotNull(file);
        Scanner scanner = AlgData.getScanner("tale.txt");
        SymbolTable<String, Integer> st = new HashTable<>();
        int threshold = 8;
        while (scanner.hasNext()) {
            String e = scanner.next();
            if (e.length() < threshold) continue;
            if (!st.contains(e))
                st.put(e, 1);
            else
                st.put(e, st.get(e) + 1);
        }
        for (int i=0; i<5 && i<st.size(); i++) {
            String max = "";
            st.put(max, 0);
            for (String e: st.keys())
                if (st.get(e) > st.get(max))
                    max = e;
            System.out.println(max + " " + st.get(max));
            st.remove(max);
        }
        
        st = new SequentialST<>();
        st.put("a", 1);
        st.put("b", 2);
        st.put("c", 3);
        st.put("d", 4);
        st.put("e", 4);
        st.remove("c");
        assert st.size() == 4;
        for (String e: Arrays.asList("a", "b", "d", "e"))
            assert st.contains(e);
        assert !st.contains("c");
    }

    public static void main(String[] args) {
        new FrequencyCounter("tale.txt");
    }
}
