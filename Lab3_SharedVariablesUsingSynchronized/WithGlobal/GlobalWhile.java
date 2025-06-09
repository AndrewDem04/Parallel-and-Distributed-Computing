public class GlobalWhile {
  
    static int end = 10000;
    static SharedData data = new SharedData(end);
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
			if (data.getArray(i) != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, data.getArray(i));
			}         
		}
        System.out.println (errors+" errors.");
    }

	static class SharedData {

        int counter;
        int[] array;

        /*
         * I decided to create this objects to make my program a bit faster,
         * I dint't want a thread waiting to use the counter because another thread was 
         * using the array , thats why i decided to create two objects.
         * Although MY PROGRAM is not concurrent, because i dont Syncronized 
         * the rows of the array but the whole array 
         */

        Object lockForArray;
        Object lockForCounter;


        public SharedData(int end) {
            this.counter = 0;
            this.array = new int[end];
            this.lockForArray = new Object();
            this.lockForCounter = new Object();
            
        }

        /*
		 * We need to synchronized this method to ensure
		 * that not two threads Read and later modify the same value 
		 */
        public int getAndIncCounter() { 
            synchronized(lockForCounter){
                return counter++;
            }
        }

        public int getArray(int i) {
            return array[i];
        }

        public void incArray(int i) {
            synchronized (lockForArray) { 
                array[i]++;
            }
        }
    }

    static class CounterThread extends Thread {
  	
       public CounterThread() {
       }
  	
       public void run() {
       
            while (true) {
				int index = data.getAndIncCounter(); 
                if (index >= end)
                    break;
                data.incArray(index);
            } 
		}            	
    }
}