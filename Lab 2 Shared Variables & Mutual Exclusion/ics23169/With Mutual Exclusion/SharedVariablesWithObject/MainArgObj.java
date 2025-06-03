import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainArgObj {

    public static void main(String[] args) {
		int numThreads = 4;
		int end = 10000;  	
		SharedData sharedData = new SharedData(end);

		CounterThread threads[] = new CounterThread[numThreads];
		

		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(sharedData);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
		check_array (numThreads , sharedData);
    }
     
    static void check_array (int numThreads ,SharedData sharedData)  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < sharedData.end; i++) {
			if (sharedData.array[i] != numThreads*i) {
				errors++;
				System.out.printf("%d: %d should be %d\n", i, sharedData.array[i], numThreads*i);
			}         
        }
        System.out.println (errors+" errors.");
    }

	static class SharedData {

		int end;
    	int[] array;
		Lock lock = new ReentrantLock();

		public SharedData (int end){
			this.end = end;
    		this.array = new int[end];
		}

		public Lock getLock()
		{
			return lock;
		}
	}
	
    static class CounterThread extends Thread {
		
		SharedData sharedData;
       public CounterThread(SharedData sharedData) {
			this.sharedData = sharedData;
       }
  	
       public void run() {
  
            for (int i = 0; i < sharedData.end; i++) {
				sharedData.getLock().lock();  
                try {
                    for (int j = 0; j < i; j++) { 
                        sharedData.array[i]++;
                    }
                } finally {
                    sharedData.getLock().unlock();  
                }
            } 
		}            	
    }
}