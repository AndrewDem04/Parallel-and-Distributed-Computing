package Lab7_WordCountParallelizationStreams;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.*;

public class ParallelStreamWordCountList {
    // Avg Time (s)
    // -------------------------
    // 0.7747
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Ex713 <file name>");
            System.exit(1);
        }

        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        long startTime = System.currentTimeMillis();

        Map<String, Long> wordCount = lines.parallelStream()
            .filter(line -> line.length() > 10)
            .flatMap(line -> Arrays.stream(line.split(" ")))
            .map(String::toLowerCase)
            .filter(word -> !word.isBlank())
            .collect(Collectors.groupingByConcurrent(word -> word, Collectors.counting()));

        wordCount.forEach((word, count) -> System.out.println(word + ": " + count));

        long endTime = System.currentTimeMillis();
        System.out.println("time in seconds = "+ (double) (endTime - startTime) / 1000);

        //System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}
