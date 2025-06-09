class VectorAddParallel
{

  	public static void main(String args[])
  		{
    	int size = 0;
    	int numThreads = 0;
	          
		if (args.length != 2) {
    		System.out.println("Usage: java ThreadParSqrt <vector size> <number of threads>");
    		System.exit(1);
		}

		try {
			size = Integer.parseInt(args[0]);
			numThreads = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException nfe) {
   			System.out.println("Integer argument expected");
			System.exit(1);
		}
		if (numThreads == 0) numThreads = Runtime.getRuntime().availableProcessors();
    
    	double[] a = new double[size];
    	double[] b = new double[size];
    	double[] c = new double[size];
		for(int i = 0; i < size; i++) {
       	 	a[i] = 0.0;
			b[i] = 1.0;
        	c[i] = 0.5;
    	}

		long start = System.currentTimeMillis();
    
		SinxThread threads[] = new SinxThread[numThreads];
	
		int chunkSize = size / numThreads;
		for (int i = 0; i < numThreads; i++) {
   			int from = i * chunkSize;
    		int to = (i == numThreads - 1) ? size : from + chunkSize;
    		threads[i] = new SinxThread(a, b, c, from, to);
    		threads[i].start();
		}

		for(int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {}
		}
	
		long elapsedTimeMillis = System.currentTimeMillis()-start;

    	//for(int i = 0; i < size; i++) 
      		//  System.out.println(a[i]);

		System.out.println("time in ms = "+ elapsedTimeMillis);
  	}

  	static class SinxThread extends Thread {
    	private double[] a, b, c;
    	private int start, end;

    	public SinxThread(double[] a, double[] b, double[] c, int start, int end) {
        	this.a = a;
        	this.b = b;
        	this.c = c;
        	this.start = start;
        	this.end = end;
    	}

    	public void run() {
        	for (int i = start; i < end; i++) {
           	 	a[i] = b[i] + c[i];
				//I decided to add the delay too see if my code worked properly
				/*try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} */
        	}
			
			
    	}
	}

}
