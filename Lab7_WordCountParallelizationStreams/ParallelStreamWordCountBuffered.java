package Lab7_WordCountParallelizationStreams;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ParallelStreamWordCountBuffered {
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Ex713 <file name>");
            System.exit(1);
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));

        long startTime = System.currentTimeMillis();

        Map<String, Long> wordCounts = in.lines()
            .parallel()
            .filter(line -> line.length() > 10)
            .flatMap(line -> Arrays.stream(line.split(" ")))
            .map(String::toLowerCase)
            .filter(word -> !word.isBlank())
            .collect(Collectors.groupingByConcurrent(
                word -> word,
                Collectors.counting()
            ));

        in.close();

        wordCounts.forEach((word, count) -> System.out.println(word + ": " + count));

        long endTime = System.currentTimeMillis();
        System.out.println("time in seconds = "+ (double) (endTime - startTime) / 1000);
        //System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

