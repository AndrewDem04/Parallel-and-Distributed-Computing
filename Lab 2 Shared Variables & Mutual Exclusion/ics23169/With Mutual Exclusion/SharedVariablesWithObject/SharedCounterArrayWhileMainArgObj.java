import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedCounterArrayWhileMainArgObj {
   	public static void main(String[] args) {

		int numThreads = 4;
		int end = 10000;
		int counter = 0;

        CounterThread threads[] = new CounterThread[numThreads];
		
		SharedData sharedData = new SharedData(end , counter);

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
        check_array (sharedData);
    }
     
    static void check_array (SharedData sharedData)  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < sharedData.end; i++) {
			if (sharedData.array[i] != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, sharedData.array[i]);
			}         
		}
        System.out.println (errors+" errors.");
    }

	static class SharedData {

		int end;
    	int[] array;
		int counter;		
		Lock lock = new ReentrantLock();

		public SharedData (int end , int counter){
			this.end = end;
			this.array = new int[end];
			this.counter = counter;
		}

		public Lock getLock()
		{
			return lock;
		}
	}	

    static class CounterThread extends Thread {
		SharedData sharedData;
       	public CounterThread(SharedData sharedData) {
			this.sharedData =sharedData;
    	}
  	
       	public void run() {
       
            while (true) {
				int index;
                
                // Lock before accessing the shared counter
                sharedData.getLock().lock();
                try {
                    if (sharedData.counter >= sharedData.end) {
                        break;  
                    }
                    	index = sharedData.counter; 
                    	sharedData.counter++;
                	} finally {
                    	sharedData.getLock().unlock();
                	}
                	sharedData.array[index]++;		
            } 
		}            	
    }
}