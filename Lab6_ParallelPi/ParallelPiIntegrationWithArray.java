public class ParallelPiIntegrationWithArray {
    /*
     * This solution dont needs a lock 
     */
    /*
Steps: 1000000
Threads | Average Time (s)
--------|-------------------
     1  | 0.0120
     2  | 0.0120
     4  | 0.0156
     8  | 0.0210

Steps: 10000000
Threads | Average Time (s)
--------|-------------------
     1  | 0.0446
     2  | 0.0306
     4  | 0.0262
     8  | 0.0256

Steps: 100000000
Threads | Average Time (s)
--------|-------------------
     1  | 0.3954
     2  | 0.2226
     4  | 0.1324
     8  | 0.0800

Steps: 1000000000
Threads | Average Time (s)
--------|-------------------
     1  | 4.0608
     2  | 2.2078
     4  | 1.2360
     8  | 0.774
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

        double[] tsum = new double[numThreads];
        for (int i = 0; i < numThreads; i++) tsum[i] = 0.0;

        long startTime = System.currentTimeMillis();

        // Create and start threads
        VecSumThread[] threads = new VecSumThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new VecSumThread(i, numThreads, numSteps, tsum);
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < numThreads; i++) {
            try { threads[i].join(); } 
            catch (InterruptedException e) {}
        }

        // Compute final result
        double sum = 0.0;
        for (double partial : tsum) sum += partial;
        double pi = sum * (1.0 / numSteps); // Multiply by step here

        // Output results
        long endTime = System.currentTimeMillis();
		System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }

	
}

class VecSumThread extends Thread {
	private final double[] tsum; // Shared array for partial results
	private final int myId;
	private final int myStart;
	private final int myStop;
	private final int numSteps;

	public VecSumThread(int id, int numThreads, int steps, double[] tsum) {
		this.tsum = tsum;
		this.myId = id;
		this.numSteps = steps;
		int chunk = steps / numThreads;
		myStart = myId * chunk;
        if(myId == numThreads - 1) myStop = steps;
        else myStop = myStart +chunk;
	}

	public void run() {
		double localSum = 0.0;
		double step = 1.0 / numSteps;
		for (int i = myStart; i < myStop; i++) {
			double x = (i + 0.5) * step; // Midpoint of the i-th subinterval
			localSum += 4.0 / (1.0 + x * x);
		}
		tsum[myId] = localSum; // Store partial sum
	}
}