

public class ArgsWhile {
  
    

    public static void main(String[] args) {
		
		int end = 10000;
    	int numThreads = 4;
        CounterThread threads[] = new CounterThread[numThreads];
		SharedData data = new SharedData(end);
	
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(end , data);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
        check_array (end , data);
    }
     
    static void check_array (int end , SharedData data)  {
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

        /*
         * By synchronizing this two methods we ensure that the critical sections 
         * are safe , our program becomes a bit faster because we have different synchronizion for the counter
         * and for the array.
         */
        int counter;
        int[] array;

        public SharedData(int end) {
            this.counter = 0;
            this.array = new int[end];
        }

        /*
		 * We need to synchronized this method to ensure
		 * that not two threads Read and later modify the same value 
		 */
        public synchronized int getAndIncCounter() { 
            return counter++;
        }

        public int getArray(int i) {
            return array[i];
        }

        public synchronized void incArray(int i) {
             array[i]++; 
        }
    }

    static class CounterThread extends Thread {
		int end;
		int[] array;
		SharedData data;

       public CounterThread(int end ,SharedData data) {
			this.end = end;
			this.data = data;
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