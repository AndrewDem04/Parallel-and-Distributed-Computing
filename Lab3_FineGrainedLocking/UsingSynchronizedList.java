import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsingSynchronizedList {

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

		/*
		 * I decided to use an array from the options Of concurentCollections becauses 
		 * i wanted to see how they work also we din't have a need for any key 
		 */
		List<Integer> array;
		Object[] locks;

		public SharedArray (int end){
			this.array = Collections.synchronizedList(new ArrayList<>(Collections.nCopies(end, 0))); 
			this.locks = new Object[end];
            for (int i = 0; i < end; i++) {
                locks[i] = new Object();
            }
		}
		
		public int get(int i){
			return array.get(i);
		}
		/* 
		 *  We use an object to Lock its row separately ,
		 * i dont want to block other threads from accesing difrend row of the array	
		*/ 
		public void inc(int i) {
			synchronized (locks[i]) {
				array.set(i, array.get(i) + 1);
		   }    
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