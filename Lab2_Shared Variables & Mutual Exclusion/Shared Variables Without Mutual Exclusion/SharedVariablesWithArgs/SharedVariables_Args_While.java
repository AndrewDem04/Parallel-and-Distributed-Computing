

public class SharedVariables_Args_While {
    

    public static void main(String[] args) {
		
		int end = 10000;
    	int[] array = new int[end];
    	int numThreads = 4;
        CounterThread threads[] = new CounterThread[numThreads];
		SharedCounter counter = new SharedCounter();
	
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new CounterThread(end , array, counter);
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


	/*
	 * 	We need to pass the Adresses of the counter Thats why I created this object 
	 * 	An unorthodox solution i found when i was trying to find a way to pass it as an Arg Was to make the counter an Array
	 *  int[] counter = {0};
	*/
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

       public CounterThread(int end , int[] array ,SharedCounter counter) {
			this.end = end;
			this.array = array;
			this.counter = counter;
       }
  	
       public void run() {
       
            while (true) {
				if (counter.get() >= end) 
                	break;
            	array[counter.get()]++;
				counter.inc();		
            } 
		}            	
    }
}