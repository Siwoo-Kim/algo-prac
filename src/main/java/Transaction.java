import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Transaction implements Comparable<Transaction> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final String client;
    private final LocalDate date;
    private final double amount;

    public Transaction(String line) {
        checkNotNull(line);
        String[] data = line.split("\\s+");
        checkArgument(data.length == 3);
        this.client = data[0];
        this.date = LocalDate.parse(data[1], FORMATTER);
        this.amount = Double.parseDouble(data[2]);
    }

    @Override
    public int compareTo(Transaction that) {
        checkNotNull(that);
        return Double.compare(amount, that.amount);
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", client, FORMATTER.format(date), amount);
    }
}
