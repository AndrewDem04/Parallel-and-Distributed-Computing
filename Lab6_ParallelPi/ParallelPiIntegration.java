public class ParallelPiIntegration {
    /*
Steps: 1000000
Threads | Average Time (s)
--------|-------------------
     1  | 0.0110
     2  | 0.0134
     4  | 0.0160
     8  | 0.0234

Steps: 10000000
Threads | Average Time (s)
--------|-------------------
     1  | 0.0496
     2  | 0.0310
     4  | 0.0264
     8  | 0.0262

Steps: 100000000
Threads | Average Time (s)
--------|-------------------
     1  | 0.3918
     2  | 0.2122
     4  | 0.1322
     8  | 0.0830

Steps: 1000000000
Threads | Average Time (s)
--------|-------------------
     1  | 3.8772
     2  | 2.0148
     4  | 1.1652
     8  | 0.6844
*/
    public static void main(String[] args) {
        int numSteps = 0;  
        int numThreads = 0;

        if (args.length != 2) {
            System.out.println("Usage: java Ex611 <number_of_steps> <number_of_threads>");
            System.exit(1);
        }
        
		try {
            numSteps = Integer.parseInt(args[0]);
            numThreads = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("Integer arguments expected");
            System.exit(1);
        }
        if (numThreads == 0) numThreads = Runtime.getRuntime().availableProcessors();

        long startTime = System.currentTimeMillis();

        // Create and start threads
        pi pi = new pi();
        VecSumThread[] threads = new VecSumThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new VecSumThread(pi ,i, numThreads, numSteps);
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < numThreads; i++) {
            try { threads[i].join(); } 
            catch (InterruptedException e) {}
        }


        // Output results
        long endTime = System.currentTimeMillis();
		System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi.getPi());
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi.getPi() - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }

	
}

class pi {
    double pi;
    public pi(){
        this.pi = 0;
    }
    
    public double getPi(){
        return pi;
    }

    public void addPi(double sum , double step){
        pi = pi + sum * step;
    }
}

class VecSumThread extends Thread {

    private pi pi;
	private int myId;
	private int myStart;
	private int myStop;
	private int numSteps;

	public VecSumThread(pi pi ,int id, int numThreads, int steps) {
        this.pi = pi;
		this.myId = id;
		this.numSteps = steps;
		int chunk = steps / numThreads;
		myStart = myId * chunk;
		if(myId == numThreads -1) myStop = steps;
        else myStop = myStart + chunk;
	}

	public void run() {
		double sum = 0.0;
		double step = 1.0 / numSteps;
		for (int i = myStart; i < myStop; i++) {
			double x = (i + 0.5) * step; // Midpoint of the i-th subinterval
			sum += 4.0 / (1.0 + x * x);
		}
        pi.addPi(sum, step);        

	}
}