
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParkLockCond
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
		private Lock lock = new ReentrantLock();
		private Condition bufferFull = lock.newCondition();
		private Condition bufferEmpty = lock.newCondition();
		
		public Park(int c) {
		   capacity = c;
		   spaces = capacity;
		   waitscale = 10;
		   inscale = 5;
		}
		 
		void arrive () {
			lock.lock();
        	try {
            	while (spaces == 0) { // Parking full
                	System.out.println("Parking is full.");
                	try {
						bufferFull.await();
					} catch (InterruptedException e) {}
            	}
				//Car arrival with radom delay
				try {
			   	Thread.sleep((int)(Math.random()*waitscale));
				} catch (InterruptedException e) { }
				System.out.println(Thread.currentThread().getName()+" arrival");
				//Car entering
				System.out.println(Thread.currentThread().getName()+"     parking");
				//Decrement capacity
				spaces--;
				System.out.println("free "+ spaces);
				bufferEmpty.signal(); // Notify a waiting car that a spot is free
        	} finally {
            	lock.unlock();
        	}
		}
			
		void depart () {
			lock.lock();
        	try {
            	while (spaces == capacity) { // Parking is empty
                System.out.println("Parking is empty.");
				try {
                	bufferEmpty.await();
				} catch (InterruptedException e) { }
            	}
				//Car departure
				System.out.println(Thread.currentThread().getName()+"         departure");
				//Increment capacity
				spaces++;
				System.out.println("free "+ spaces);
				bufferFull.signal(); // Notify a waiting car that a space is now available
			} finally {
				lock.unlock();
			}
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
