public class SharedCounter_Global {
  

    public static void main(String[] args) {
		int numThreads = 4;
		int end = 1000;
		int[] array = new int[end];

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
     
	
	/*
	 * We don't have a problem with variable "end" and "numThreads" because we only need to read them 
	 * If we needed to write on them we should pass the address 
	 * In the "array" we pass the Andress and we dont copy it
	 */
    static void check_array (int numThreads , int end , int[] array)  {
		int i, errors = 0;
		System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
			if (array[i] != numThreads*i) {
				errors++;
				System.out.printf("%d: %d should be %d\n", i, array[i], numThreads*i);
			}         
        }
        System.out.println (errors+" errors.");
    }


    static class CounterThread extends Thread {
		// We don't have a problem with variable "end" because we only need to read them 
		int end;	
		int[] array;
       public CounterThread(int end  , int[] array) {
			this.end = end;   
			this.array = array;
       }
  	
       public void run() {
  
            for (int i = 0; i < end; i++) {
				for (int j = 0; j < i; j++)
					array[i]++;		
            } 
		}            	
    }
}