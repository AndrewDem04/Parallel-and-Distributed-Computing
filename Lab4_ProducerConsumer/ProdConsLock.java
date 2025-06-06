import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsLock
{
	public static void main(String[] args)
	{
		int noIterations = 20;
		int producerDelay = 100;
		int consumerDelay = 1;

		Buffer buff = new Buffer();

		Producer prod = new Producer(buff, noIterations, producerDelay);
		Consumer cons = new Consumer(buff, consumerDelay);		
		prod.start();
		cons.start();	
	}

	static public class Buffer
	{
		private int value;
		private boolean available = false; 
		private Lock lock = new ReentrantLock();
		private Condition bufferFull = lock.newCondition();
		private Condition bufferEmpty = lock.newCondition();
	
		
	
		// Put an item into buffer
		public void put(int data) {
	
			lock.lock();
				try {
					try {
						while (available) {  
							bufferFull.await();
						}
					}	
				catch (InterruptedException e){}
				value = data;
				System.out.println("Prod No "+ data);
				bufferEmpty.signal();
			} finally {
				lock.unlock() ;
			}
		}
	
		// Get an item from bufffer
		public int get() {
			int data = 0;
	
			lock.lock();
			try {		
				try {
					while (!available) {  
						bufferFull.await();
					}
				}	
				catch (InterruptedException e){}
				
				data = value;
				System.out.println("Cons No "+ data );
				bufferFull.signal();
			} finally {
				lock.unlock() ;
			}
			return data;
		}
	}

	static public class Producer extends Thread {
		private Buffer buff;
		private int reps;
		private int scale;
	
		// Constructor
		public Producer(Buffer b, int r, int s) {
			this.buff = b;
			this.reps = r;
			this.scale = s;
		}
	
		// Producer runs for reps times with random(scale) intervals
		public void run() {
			for(int i = 0; i < reps; i++) {
				buff.put(i);
				try {
					sleep((int)(Math.random()*scale));
				} catch (InterruptedException e) { }
			}
		}
	}

	static public class Consumer extends Thread {
		private Buffer buff;
		private int scale;
	
		// Constructor
		public Consumer(Buffer b, int s) {
			this.buff = b;
			this.scale = s;
		}
	
		// Producer runs for reps times with random(scale) intervals
		public void run() {
			int value;
			while (true) {
				try {
					sleep((int)(Math.random()*scale));
				} catch (InterruptedException e) { }
				value = buff.get();
			}
		}
	}
}
