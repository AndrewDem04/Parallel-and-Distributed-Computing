import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProdConsBlockingQueue
{
	public static void main(String[] args)
	{
		int bufferSize = 5;
		int noIterations = 20;
		int producerDelay = 1;
		int consumerDelay = 100;
		int noProds = 3;
		int noCons = 2;
		Producer prod[] = new Producer[noProds];
		Consumer cons[] = new Consumer[noCons];		

		BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(bufferSize);
		
		for (int i=0; i<noProds; i++) {
			prod[i] = new Producer(buffer, noIterations, producerDelay);
			prod[i].start();
		}

		for (int j=0; j<noCons; j++) {
	        cons[j] = new Consumer(buffer, consumerDelay);
			cons[j].start();
		}
	}

	static public class Producer extends Thread {
		private final BlockingQueue<Integer> buff;
		private int reps;
		private int scale;
	
		// Constructor
		public Producer(BlockingQueue<Integer> b, int r, int s) {
			this.buff = b;
			this.reps = r;
			this.scale = s;
		}
	
		public void run() {
			for (int i = 0; i < reps; i++) {
				try {
					buff.put(i);
					System.out.println("Produced: " + i);
					sleep((int)(Math.random() * scale));
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
			try {
				buff.put(-1);
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
	}

	static public class Consumer extends Thread {
		private final BlockingQueue<Integer> buff;
		private int scale;
	
		public Consumer(BlockingQueue<Integer> b, int s) {
			this.buff = b;
			this.scale = s;
		}
	
		public void run() {
			int value;
			while (true) {
				try {
					value = buff.take(); 
        			if (value == -1) {
            			System.out.println("Consumer stopping...");
            			break; 
        			}
        			System.out.println("Consumed: " + value);
					sleep((int)(Math.random()*scale));
				} catch (InterruptedException e) { }
				
			}
		}
	}
}
	