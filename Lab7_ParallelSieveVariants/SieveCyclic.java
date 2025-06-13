import java.util.ArrayList;
import java.util.List;

class SieveCyclic
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
        CalculatorEx722[] threads = new CalculatorEx722[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CalculatorEx722(i , numThreads,prime, smallPrimes);
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

class CalculatorEx722 extends Thread {
	int id , numThreads;
    boolean[] prime;
	List<Integer> smallPrimes;

	public CalculatorEx722(int id , int numThreads,boolean[] prime , List<Integer> smallPrimes) {
        this.id = id; this.numThreads = numThreads;
	    this.prime = prime;
		this.smallPrimes = smallPrimes;
	}

    public void run() {
        int size = prime.length - 1; // because prime[] is size+1
        int start = (int) Math.sqrt(size) + 1;

        // Each thread will work on indexes: start + id, start + id + numThreads, ...
        for (int i = start + id; i <= size; i += numThreads) {
            for (int p : smallPrimes) {
				if (p * p > i) break;
                if (i % p == 0) {
                    prime[i] = false;
                    break; // No need to check further if i is divisible by p
                }
            }
        }
    }
}