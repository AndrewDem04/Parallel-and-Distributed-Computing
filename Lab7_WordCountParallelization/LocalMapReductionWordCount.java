import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;

public class LocalMapReductionWordCount {
    public static void main(String args[]) throws FileNotFoundException, IOException {
    
        if (args.length != 2) {
            System.out.println("WordCount <file name> <numThreads>");
            System.exit(1);
        }
        
        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));
        String[] words = fileString.split("[ \n\t\r.,;:!?(){}]");
        
        int numThreads =0;
        numThreads = Integer.parseInt(args[1]);
        
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        int chunk = words.length / numThreads;
        int stop, start;
        int wordsLen = words.length;


        long startTime = System.currentTimeMillis();

        CountWordsEx712[] threads = new CountWordsEx712[numThreads];
        for (int thread = 0; thread < numThreads; thread++) {
            start = thread * chunk;
            if(thread == numThreads - 1) stop = wordsLen;
            else stop = start + chunk;

            threads[thread] = new CountWordsEx712(start, stop, words);
            threads[thread].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        for (int i = 0; i < numThreads; i++) {
            HashMap<String, Integer> localMap = threads[i].getLocalMap();

            for (Map.Entry<String, Integer> entry : localMap.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();

                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + value);
                } else {
                    map.put(key, value);
                }
            }
        }

        long endTime = System.currentTimeMillis();

        for (String name: map.keySet()) {
            String key = name;
            String value = map.get(name).toString();
            System.out.println(key + "\t" + value);
        }

        System.out.println("time in seconds = "+ (double) (endTime - startTime) / 1000);

        //System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }
}

class CountWordsEx712 extends Thread {
    private int myStart, myStop;
    private String[] words;
    private HashMap<String, Integer> localMap;

    public CountWordsEx712(int myStart, int myStop, String[] words) {
        this.localMap = new HashMap<>();
        this.myStart = myStart; this.myStop = myStop; 
        this.words = words;
    }

    public void run() {
        for (int wordCounter = myStart; wordCounter < myStop; wordCounter++) {
            String key = words[wordCounter].toLowerCase();
            if (key.length() > 0) {
                if (localMap.containsKey(key)) {
                    localMap.put(key, localMap.get(key) + 1);
                } else {
                    localMap.put(key, 1);
                }
            }
        }
    }

    public HashMap<String, Integer> getLocalMap() {
        return localMap;
    }
}

