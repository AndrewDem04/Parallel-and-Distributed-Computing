import java.util.concurrent.Semaphore;

public class ProdConSemMux
{	

	public static void main(String[] args)
	{
		int noIterations = 20;
		int producerDelay = 1;
		int consumerDelay = 100;

		Buffer buff = new Buffer();

		Producer prod = new Producer(buff, noIterations, producerDelay);
		prod.start();

		Consumer cons = new Consumer(buff, consumerDelay);
		cons.start();
		
		
	}

	static public class Buffer {
		private int value;  
		private Semaphore bufferFull = new Semaphore(0);  
		private Semaphore bufferEmpty = new Semaphore(1);  
	
		public void put(int data) {
			try {
				bufferEmpty.acquire();  
			} catch (InterruptedException e) { }
			
			value = data;
			System.out.println("Prod: " + data);
	
			bufferFull.release();  
		}
	
		public int get() {
			try {
				bufferFull.acquire();  
			} catch (InterruptedException e) { }
			
			int data = value;
			System.out.println("  Cons: " + data);
	
			bufferEmpty.release();  
			return data;
		}
	}
	
	static public class Consumer extends Thread {
		private Buffer buff;
		private int scale;
		
		public Consumer(Buffer b, int s) {
			this.buff = b;
			this.scale = s;
		}

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
	
	static public class Producer extends Thread {
		private Buffer buff;
		private int reps;
		private int scale;
	
		public Producer(Buffer b, int r, int s) {
			this.buff = b;
			this.reps = r;
			this.scale = s;
		}
	
		public void run() {
			for(int i = 0; i < reps; i++) {
				buff.put(i);
				try {
					sleep((int)(Math.random()*scale));
				} catch (InterruptedException e) { }
			}
		}
	}
}
