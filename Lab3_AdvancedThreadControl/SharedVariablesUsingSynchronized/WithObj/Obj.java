public class Obj {

    public static void main(String[] args) {
		int numThreads = 4;

		SharedData sharedData = new SharedData(10000);

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

        for (i = 0; i < sharedData.getend(); i++) {
			if (sharedData.get(i) != numThreads*i) {
				errors++;
				System.out.printf("%d: %d should be %d\n", i, sharedData.get(i), numThreads*i);
			}         
        }
        System.out.println (errors+" errors.");
    }

	
	static class SharedData {

		int end;
		int[] array;

		public SharedData (int end){
			this.end = end;
			this.array = new int[end];
		}

		public int getend(){
			return end;
		}
		
		public int get(int i){
			return array[i];
		}

		/* 
		 * This gives me greater control than locking the whole method with public synchronized void inc(int i)
		 * because i can lock the critical section of the code in this method though its not required 	
		*/
		public void inc(int i) {
			synchronized (this) {
				array[i]++;
		   }    
		}
			
	}
    static class CounterThread extends Thread {
		
		SharedData sharedData;
       public CounterThread(SharedData sharedData) {
			this.sharedData = sharedData;
       }
  	
       public void run() {
  
            for (int i = 0; i < sharedData.getend(); i++) {
				for (int j = 0; j < i; j++)
					sharedData.inc(i);		
            } 
		}            	
    }
}

