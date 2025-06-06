public class ProdConsWaitNotify
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

	static class Buffer {
        private int value;
        private boolean bufferEmpty = true;

        // Put an item into the buffer
        public synchronized void put(int data) {
            while (!bufferEmpty) { 
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            value = data;
            bufferEmpty = false; 
            System.out.println("Produced: " + data);
            notify(); // Notify consumer
        }

        // Get an item from the buffer
        public synchronized int get() {
            while (bufferEmpty) { // Wait if buffer is empty
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            int data = value;
            bufferEmpty = true; 
            System.out.println("Consumed: " + data);
            notify();// Notify producer
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
