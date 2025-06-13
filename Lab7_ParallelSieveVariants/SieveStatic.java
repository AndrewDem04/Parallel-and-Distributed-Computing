import java.util.ArrayList;
import java.util.List;

class SieveStatic
{
	public static void main(String[] args)
	{  
        
		int size = 0;
		int numThreads = 0;
		if (args.length != 2) {
			System.out.println("Usage: java SieveOfEratosthenes <size> <numThreads>");
			System.out.println("size should be positive integer");
			System.exit(1);
		}

		try {
			size = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException nfe) {
	   		System.out.println("Integer argument expected");
    		System.exit(1);
		}
		if (size <= 0) {
				System.out.println("size should be positive integer");
	    		System.exit(1);
		}

		numThreads = Integer.parseInt(args[1]);
		boolean[] prime = new boolean[size+1];


		for(int i = 2; i <= size; i++)
			prime[i] = true; 		

		long startTime = System.currentTimeMillis();
		
		// Step 1: Sieve up to sqrt(size) — single thread
        int sqrt = (int) Math.sqrt(size);
        List<Integer> smallPrimes = new ArrayList<>();
        for (int i = 2; i <= sqrt; i++) {
            if (prime[i]) {
                smallPrimes.add(i);
                for (int j = i * i; j <= sqrt; j += i) {
                    prime[j] = false;
                }
            }
        }

        // Step 2: Divide remaining range statically
        int chunk = (size - sqrt) / numThreads;
        CalculatorEx721[] threads = new CalculatorEx721[numThreads];
        for (int i = 0; i < numThreads; i++) {
			int end , start;
            start = sqrt + 1 + i * chunk;
			if(i == numThreads - 1) end = size;
			else end = start + chunk - 1;
            threads[i] = new CalculatorEx721(start, end, prime, smallPrimes);
            threads[i].start();
        }

		for(int i=0; i<numThreads; i++){
			try{
				threads[i].join();
			}
			catch(InterruptedException e){
				System.out.printf("Thread %d interrupted: %s" , i , e.getMessage());
			}
		}
		
               
		// get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-startTime;

		int count = 0;
		for(int i = 2; i <= size; i++) 
			if (prime[i] == true) {
				//System.out.println(i); 
				count++;
			}
				
		System.out.println("number of primes "+count); 
		System.out.println("time in ms = "+ elapsedTimeMillis);
	}

}

class CalculatorEx721 extends Thread {
	int start , end;
	boolean[] prime;
	List<Integer> smallPrimes;

	public CalculatorEx721(int start, int end, boolean[] prime , List<Integer> smallPrimes) {
		this.start = start;
		this.end = end;
		this.prime = prime;
		this.smallPrimes = smallPrimes;
	}

	public void run() {
        // For each small prime (from 2 up to √N)
        for (int p : smallPrimes) {
			if (p * p > end) break;
            // Find the first multiple of p in this thread's range
            int first = p * p;
            if (first < start) {
                first = ((start + p - 1) / p) * p;
            }

            // Mark all multiples of p in the range as not prime
            for (int i = first; i <= end; i += p) {
                prime[i] = false;
            }
     
		}
	}
}