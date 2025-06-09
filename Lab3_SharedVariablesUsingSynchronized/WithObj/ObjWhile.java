
public class ObjWhile {
   	public static void main(String[] args) {

		int numThreads = 4;

        CounterThread threads[] = new CounterThread[numThreads];
		
		SharedData sharedData = new SharedData(10000);

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

        for (i = 0; i < sharedData.getend(); i++) {
			if (sharedData.array[i] != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, sharedData.array[i]);
			}         
		}
        System.out.println (errors+" errors.");
    }

	
 	
	static class SharedData {

		/*
		 * This is the worst way to synchronized the critical sections because 
		 * We lock the counter and the array in the same time 
		 */
		int end;
        int counter;
        int[] array;
        

        public SharedData(int end) {
	        this.end = end;
            this.counter = 0;
            this.array = new int[end];
        }

        public int getend() { 
            return end;
        }

		/*
		 * We need to synchronized this method to ensure
		 * that not two threads Read and later modify the same value 
		 */
        public  int getAndIncCounter() { 
			synchronized(this)
			{
				//We return the counter and then we Increase him
				return counter++;
			}
        }

        public int getArray(int i) {
            return array[i];
        }

        public void incArray(int i) {
            synchronized (this) { 
                array[i]++;
            }
        }
    }

    static class CounterThread extends Thread {
		SharedData sharedData;
       	public CounterThread(SharedData sharedData) {
			this.sharedData =sharedData;
    	}
  	
       	public void run() {
       
            while (true) {
				int index = sharedData.getAndIncCounter(); 
                if (index >= sharedData.getend())
                    break;
				sharedData.incArray(index);		
            } 
		}            	
    }
}