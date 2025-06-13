import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapWordCount {
    public static void main(String args[]) throws FileNotFoundException, IOException {
    
        if (args.length != 2) {
			System.out.println("WordCount  <file name> <numThreads>");
			System.exit(1);
        }
        
        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));//, StandardCharsets.UTF_8);
        String[] words = fileString.split("[ \n\t\r.,;:!?(){}]");    
        int numThreads =0;
        numThreads = Integer.parseInt(args[1]);
        
        //TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
            
        int chunk = words.length / numThreads;
        int stop, start;
        int wordsLen = words.length;

        long startTime = System.currentTimeMillis();

        CountWordsEx711[] threads = new CountWordsEx711[numThreads];
        for (int thread = 0; thread < numThreads; thread++) {
            start = thread * chunk;
            if(thread == numThreads -1) stop = wordsLen;
            else stop = start + chunk;
            threads[thread] = new CountWordsEx711(map, start, stop, words);
            threads[thread].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try { threads[i].join(); } 
            catch (InterruptedException e) {}
        }

        long endTime = System.currentTimeMillis();

        for (String name: map.keySet()) {
			String key = name.toString();
			String value = map.get(name).toString();
    		System.out.println(key + "\t " + value);
        }

        System.out.println("time in seconds = "+ (double) (endTime - startTime) / 1000);

        //System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);

    }
}

class CountWordsEx711 extends Thread{
    private ConcurrentHashMap<String, Integer> map;
	private int myStart ,myStop;
    private String[] words;

    public CountWordsEx711(ConcurrentHashMap<String, Integer>  map, int myStart , int myStop , String[] words) {
        this.map = map;
		this.myStart = myStart; this.myStop = myStop;
        this.words = words;
	}


    public void run() {
        for (int wordCounter = myStart; wordCounter < myStop; wordCounter++) {
            String key = words[wordCounter].toLowerCase();
                if (key.length() > 0) {
                    synchronized(map){
                        if (map.containsKey(key)) {
                            map.put(key, map.get(key) + 1);
                        }
                        else {               
                            map.put(key, 1);
                        }
                    }          
                }       
        }
    }
    
}