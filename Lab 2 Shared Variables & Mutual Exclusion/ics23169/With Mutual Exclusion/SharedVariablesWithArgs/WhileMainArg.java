import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WhileMainArg {
  
    

    public static void main(String[] args) {
		
		int end = 10000;
    	int[] array = new int[end];
    	int numThreads = 4;
        CounterThread threads[] = new CounterThread[numThreads];
		SharedCounter counter = new SharedCounter();
		Lock lock = new ReentrantLock();


		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(end , array, counter , lock);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
        check_array (end , array);
    }
     
    static void check_array (int end , int[] array)  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
			if (array[i] != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, array[i]);
			}         
		}
        System.out.println (errors+" errors.");
    }

	// 	We could also make the counter as an Array int[] counter = {0};
	static class SharedCounter {

		int n;
		
		public SharedCounter (){
			this.n = 0;
		}
		
		public int get() {
			return n;
		}    
		
		public void inc() {
			n = n + 1;
		} 
		  
	}


    static class CounterThread extends Thread {
		int end;
		int[] array;
		SharedCounter counter;
		Lock lock;

       public CounterThread(int end , int[] array ,SharedCounter counter , Lock lock) {
			this.end = end;
			this.array = array;
			this.counter = counter;
			this.lock = lock;
       }
  	
       public void run() {
			int index;

			while (true) {
                lock.lock();
                try {
                    if (counter.get() >= end) {
                        break;  
                    }
                    	index = counter.get(); 
                    	counter.inc();
                	} finally {
                    	lock.unlock();
                	}
                	array[index]++;
					
			}
            
		}            	
    }
}