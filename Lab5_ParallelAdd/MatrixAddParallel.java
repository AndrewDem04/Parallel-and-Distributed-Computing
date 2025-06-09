class MatrixAddParallel
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
    
      double[][] a = new double[size][size];
      double[][] b = new double[size][size];
      double[][] c = new double[size][size];
      for(int i = 0; i < size; i++) 
        for(int j = 0; j < size; j++) { 
          a[i][j] = 0.1;
		      b[i][j] = 0.3;
          c[i][j] = 0.5;
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

      /* for debugging 
      for(int i = 0; i < size; i++) { 
        for(int j = 0; j < size; j++) 
          System.out.print(a[i][j]+" "); 
        System.out.println(); 
      } */   

      System.out.println("time in ms = "+ elapsedTimeMillis);
  }

  static class SinxThread extends Thread {
    private double[][] a, b, c;
    private int start, end;

    public SinxThread(double[][] a, double[][] b, double[][] c, int start, int end) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.start = start;
        this.end = end;
    }

    public void run() {
      for (int i = start; i < end; i++) {
        for (int j = 0; j < a[i].length; j++) {
            a[i][j] = b[i][j] + c[i][j];
            //I decided to add the delay too see if my code worked properly
            /* 
            try {
              Thread.sleep(1);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          */
        }
      }
    }
  }
}
