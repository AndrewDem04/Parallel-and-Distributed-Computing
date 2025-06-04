public class Args {
  

    public static void main(String[] args) {
		int numThreads = 4;
		int end = 1000;
		SharedArray array = new SharedArray(end);
		
		CounterThread threads[] = new CounterThread[numThreads];
		
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(end , array);
			threads[i].start();
		}
	
		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			}
			catch (InterruptedException e) {}
		} 
		check_array (numThreads , end , array);
    }
     
    static void check_array (int numThreads , int end , SharedArray array)  {
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

		public SharedArray (int end){
			this.array = new int[end];
		}
		
		public int get(int i){
			return array[i];
		}
		
		/*
		 * We can synchronized the whole method because the method it only includes the critical point. 
		 * If our method had non critical sections, it may would be better to use a method that 
		 * synchronize only the block of the critical section and not the whole method 
		 */
		public synchronized void inc(int i) {
			array[i]++;
		}
			
	}

	
    static class CounterThread extends Thread {
		int end;	
		SharedArray array;
       public CounterThread(int end  , SharedArray array) {
			this.end = end;   
			this.array = array;
       }
  	
       public void run() {
  
            for (int i = 0; i < end; i++) {
				for (int j = 0; j < i; j++)
					array.inc(i);	
            } 
		}            	
    }
}