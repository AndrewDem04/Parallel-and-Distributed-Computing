import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ParallelPatternCounter {
 
    public static void main(String args[]) throws IOException {
    
        if (args.length != 3) {
			System.out.println("BruteForceStringMatch  <file name> <pattern> <threads>");
			System.exit(1);
        }

        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));//, StandardCharsets.UTF_8);
        char[] text = new char[fileString.length()]; 
        int lenFile = fileString.length();
        for (int i = 0; i < lenFile; i++) { 
            text[i] = fileString.charAt(i); 
        } 
 
        String patternString = new String(args[1]);                                                
        char[] pattern = new char[patternString.length()]; 
        int lenPattern = patternString.length(); 
        for (int i = 0; i < lenPattern; i++) { 
            pattern[i] = patternString.charAt(i); 
        }

        int numThreads = Integer.parseInt(args[2]);

        int matchLength = lenFile - lenPattern;
        char[] match = new char[matchLength+1]; 
        for (int i = 0; i <= matchLength; i++) { 
            match[i] = '0'; 
        }
        
        int[] matchCount = new int[numThreads];
        // get current time
		long start = System.currentTimeMillis();
		
        VecSumThread[] threads = new VecSumThread[numThreads];
        int totalPositions = lenFile - lenPattern + 1;
        int chunk = (totalPositions + numThreads - 1) / numThreads; 
        int tStop , tStart;
        for (int i = 0; i < numThreads; i++) {
		    tStart = i * chunk;
		    if (i == numThreads - 1) tStop = totalPositions;
            else tStop = tStart + chunk + lenPattern;
            threads[i] = new VecSumThread(pattern,text,match,lenPattern, tStart , tStop,i , matchCount);
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < numThreads; i++) {
            try { threads[i].join(); } 
            catch (InterruptedException e) {}
        }
		
        // get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		System.out.println("time in ms = "+ elapsedTimeMillis);
		
        /*for (int i = 0; i < matchLength; i++) { 
            if (match[i] == '1') System.out.print(i+" ");
        }
        int totalMatches = 0;
        for (int count : matchCount) totalMatches += count;
        System.out.println("Total matches: " + totalMatches);*/
       
   }
 

}

class VecSumThread extends Thread {

    private char[] pattern , text ,match;
    private int m,LocalStart,LocalStop , id;
    private int[] matchCount;

    public VecSumThread(char[] pattern, char[] text, char[] match,int m, int start , int stop, int id , int[] matchCount) {
        this.pattern = pattern;
        this.text = text;
        this.match = match;
        this.m = m;
        this.LocalStart = start;
        this.LocalStop = stop;
        this.id = id;
        this.matchCount = matchCount;
    }

    public void run() {
        for (int j = LocalStart; j < LocalStop && j <= text.length - m; j++) {
            boolean found = true;
            for (int i = 0; i < m; i++) {
                if (text[j + i] != pattern[i]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                match[j] = '1';
                matchCount[id]++; 
            }
        }       
    }
}