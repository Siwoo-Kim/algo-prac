import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public final class AlgData {
    private static final Path path = Paths.get("src/main/resources/alg4-data/");
    
    public static Scanner getScanner(String file) {
        try {
            return new Scanner(new BufferedReader(new FileReader(path.resolve(file).toFile())));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
