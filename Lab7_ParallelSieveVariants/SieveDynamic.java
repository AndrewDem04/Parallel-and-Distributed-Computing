import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SieveDynamic
{

    static int size=0;
	static int numThreads=0;  
	static int tasksAssigned = -1;  
	static Lock lock = new ReentrantLock();
	static List<Integer> smallPrimes;
	public static void main(String[] args)
	{  
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
		
        int sqrt = (int) Math.sqrt(size);
        smallPrimes = new ArrayList<>();
        for (int i = 2; i <= sqrt; i++) {
            if (prime[i]) {
                smallPrimes.add(i);
                for (int j = i * i; j <= sqrt; j += i) {
                    prime[j] = false;
                }
            }
        }

        Calculator[] threads = new Calculator[numThreads];
        for (int i = 0; i < numThreads; i++) {
			threads[i] = new Calculator(prime);
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

	private static int getTask()
	{
        lock.lock();
        try {
			if (++tasksAssigned < smallPrimes.size()) 
				return tasksAssigned;
			else
				return -1;
        } finally {
        	lock.unlock() ;
        }			
	}

	private static class Calculator extends Thread {
		boolean[] prime;
	
		public Calculator(boolean[] prime) {
			this.prime = prime;
		}
	
		public void run() {
			int taskIndex;
			while ((taskIndex = getTask()) != -1) {
				int p = smallPrimes.get(taskIndex);
				int first = p * p;
				for (int i = first; i <= size; i += p) {
					prime[i] = false;
				}
			}
		}		
	}

}


