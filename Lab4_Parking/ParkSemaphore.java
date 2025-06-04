
import java.util.concurrent.Semaphore;

public class ParkSem
{
	public static void main(String[] args) {
	    int capacity = 4;
		Park x = new Park(capacity);
		int cars = 20;
		Car[] p = new Car[cars];

		for (int i=0; i<cars; i++) {
			p[i] = new Car(x);
			p[i].start(); 
		}
		for (int i=0; i<cars; i++) 
			try { 
				 p[i].join(); 
			} catch (InterruptedException e) { }
    }

	static public class Park{
	
		private int capacity;
		private int spaces;
		private int waitscale;
		private int inscale;
		private Semaphore mutex = new Semaphore(1);
		private Semaphore bufferFull = new Semaphore(0);
		private Semaphore bufferEmpty; 
		
		public Park(int c) {
		   capacity = c;
		   spaces = capacity;
		   waitscale = 10;
		   inscale = 5;
		   this.bufferEmpty = new Semaphore(c);
		}
		 
		void arrive () {
			//Car arrival with radom delay
			try {
				bufferEmpty.acquire();
			} catch (InterruptedException e) { }
			try {
				mutex.acquire();
			} catch (InterruptedException e) { }
			try {
			   Thread.sleep((int)(Math.random()*waitscale));
			} catch (InterruptedException e) { }
			System.out.println(Thread.currentThread().getName()+" arrival");
			//Car entering
			System.out.println(Thread.currentThread().getName()+"     parking");
			//Decrement capacity
			spaces--;
			System.out.println("free "+ spaces);
			mutex.release(); 
			bufferFull.release();
		}
			
		void depart () { 
			try {
				bufferFull.acquire();
			} catch (InterruptedException e) { }
			try {
				mutex.acquire();
			} catch (InterruptedException e) { }
			//Car departure
			System.out.println(Thread.currentThread().getName()+"         departure");
			//Increment capacity
			spaces++;
			System.out.println("free "+ spaces);
			mutex.release(); 
			bufferEmpty.release(); 
		}            
			   
		void park() {    
			try {
					Thread.sleep((int)(Math.random()*inscale));
				} catch (InterruptedException e) { }
		}
	}

	static class Car extends Thread
	{
		private Park a_park;
	
		
		public Car (Park a) {
			a_park = a;
		}
	
		public void run() {
			for (int i = 0; i < 100; i++) {
				 a_park.arrive();
				 a_park.park();
				 a_park.depart();
			}        
		}
	
	}
	
}
