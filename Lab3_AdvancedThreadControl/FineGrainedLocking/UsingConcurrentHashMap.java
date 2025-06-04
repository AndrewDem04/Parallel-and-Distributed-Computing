import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UsingConcurrentHashMap {
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
			if (sharedData.getHM(i) != 1) {
				errors++;
				System.out.printf("%d: %d should be 1\n", i, sharedData.getHM(i));
			}         
		}
        System.out.println (errors+" errors.");
    }

	static class SharedData {

		int end;
		int counter;
		
		/*
 		* The reason we use a ConcurrentHashMap is to efficiently store and retrieve the data,
		* the counter serves as a key in The 'HashMap' provides an average O(1) time complexity
 		* for inserts and lookups, which ensures fast access to the stored data and scalability
 		*/		
		ConcurrentHashMap<Integer,Integer> conHashMap;
		Lock lock = new ReentrantLock();

		public SharedData (int end , int counter){
			this.end = end;
			this.counter = counter;

			this.conHashMap = new ConcurrentHashMap<Integer,Integer>();
			
			/*
			 * The reason a initialize the hash map 
			 * is to be able to add the value of 1 'putIfAbsent(next, conHashMap.get(next) + 1);'
			 * and not to set it 'putIfAbsent(next, 1);' 
			 * This way i can make the problems of the initial code the same as this one
			 */
			for (int i = 0; i < end; i++) {
                conHashMap.put(i, 0);
            }
		}

		/*
		 * We need to synchronized the counter because the threads may read the same value 
		 */
		public synchronized int getCounter() {     
            return counter;
		}

		public void addHM(int next){
			conHashMap.put(next, conHashMap.get(next) + 1);
		}

		public int getHM(int i){
			return conHashMap.get(i);
		}
		

		public int getNextKey() {
            lock.lock();
            try {
                return counter++; 
            } finally {
                lock.unlock(); 
            }
        }

		public int getEnd() {
			return end;
		}

	}	

    static class CounterThread extends Thread {
		SharedData sharedData;
       	public CounterThread(SharedData sharedData) {
			this.sharedData =sharedData;
    	}
  	
       	public void run() {
            while (true) {
				int next = sharedData.getNextKey();
                if (next >= sharedData.getEnd())
                    break;
                sharedData.addHM(next);	
            } 
		}            	
    }
}