public class Global {
	
    static int end = 1000;
    static SharedArray array = new SharedArray(end);
    static int numThreads = 4;
	
    public static void main(String[] args) {

		CounterThread threads[] = new CounterThread[numThreads];
		
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread();
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
		check_array ();
    }
     
    static void check_array ()  {
		int i, errors = 0;

		System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
			if (array.get(i) != numThreads*i) {
				errors++;
				System.out.printf("%d: %d should be %d\n", i, array.get(i), numThreads*i);
			}         
        }
        System.out.println (errors+" errors.");
    }

	static class SharedArray {

		int[] array;
		Object lock;

		public SharedArray (int end){
			this.array = new int[end];
			this.lock = new Object();
        	
		}
		
		public int get(int i){
			return array[i];
		}

		/* 
		 *  I use an object to synchronize the array this gives me greater control 
		 * If i wanted i could make multiple objects for difrent objectives(p.x concurrency by
		 * synchronizing its row of the array)
		*/
		public void inc(int i) {
			synchronized (lock) {
				array[i]++;
		   }    
		}
			
	}

	static class CounterThread extends Thread {
  	
       public CounterThread() {
       }
  	
       public void run() {
  
            for (int i = 0; i < end; i++) {
				for (int j = 0; j < i; j++)
					array.inc(i);
            } 
		}            	
    }
}	