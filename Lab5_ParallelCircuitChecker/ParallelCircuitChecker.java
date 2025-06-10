import java.lang.Math;
import java.util.LinkedList;

class ParallelCircuitChecker {
	
	public static void main(String[] args) {  
        
        int size = 0;
        int numthreads = 0;
        if (args.length != 2) {
            System.out.println("Usage: java SimpleSat <vector size> <Threads>");
            System.exit(1);
        }

        try {
            size = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException nfe) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }

        try {
            numthreads = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException nfe) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }

		if (size <= 0) {
			System.out.println("size should be positive integer");
			System.exit(1);
        }

        if (numthreads <= 0) {
            System.out.println("Threads should be positive integer");
            System.exit(1);
        }

        long start = System.currentTimeMillis();

        check_circuit_thread[] threads = new check_circuit_thread[numthreads];
        saveResults save = new saveResults();

        int iterations = (int) Math.pow(2, size)/numthreads;
        LinkedList<String> output = new LinkedList<String>();


        for(int i = 0; i < numthreads; i++) {
            int startIndex = iterations*i;
            int endIndex = (i == numthreads - 1) ? (int) Math.pow(2 , size) : startIndex + iterations;

            threads[i] = new check_circuit_thread(startIndex , endIndex , size , output, save);
            threads[i].start();
        }

        for(int i = 0; i < numthreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {}
		}
                   
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        
        System.out.println(output); 
        System.out.println ("All done\n");
        System.out.println("time in ms = "+ elapsedTimeMillis);
        
    }
        
}

class saveResults {

    public saveResults() {
    }
    
    public void saveResult (boolean[] v, int size, int z, LinkedList<String> output) {
		
		String result = null;
		result = String.valueOf(z);

		for (int i=0; i< size; i++)
			if (v[i]) result += " 1";
			else result += " 0";
		
		//Just print result	for debugging
		//System.out.println(result);
		//Save result
		output.add("\n"+result);
	}
    
}


class check_circuit_thread extends Thread
{
	private int startIndex;
    private int endIndex;
    private int size;
    private LinkedList<String> output;
    private saveResults save;

	public check_circuit_thread(int startIndex , int endIndex , int size, LinkedList<String> output , saveResults save)
    {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.size = size;
        this.output = output;
        this.save = save;
    }

	public void run()
	{
		for (int i = startIndex; i < endIndex; i++)
            check_circuit (i, size, output);
	}
	
	private void check_circuit (int z, int size, LinkedList<String> output) {
        
		boolean[] v = new boolean[size];  /* Each element is a bit of z */
    
		for (int i = size-1; i >= 0; i--) 
			v[i] = (z & (1 << i)) != 0;
    
       
       boolean value = 
           (  v[0]  ||  v[1]  )
        && ( !v[1]  || !v[3]  )
        && (  v[2]  ||  v[3]  )
        && ( !v[3]  || !v[4]  )
        && (  v[4]  || !v[5]  )
        && (  v[5]  || !v[6]  )
        && (  v[5]  ||  v[6]  )
        && (  v[6]  || !v[15] )
        && (  v[7]  || !v[8]  )
        && ( !v[7]  || !v[13] )
        && (  v[8]  ||  v[9]  )
        && (  v[8]  || !v[9]  )
        && ( !v[9]  || !v[10] )
        && (  v[9]  ||  v[11] )
        && (  v[10] ||  v[11] )
        && (  v[12] ||  v[13] )
        && (  v[13] || !v[14] )
        && (  v[14] ||  v[15] )
        && (  v[14] ||  v[16] )
        && (  v[17] ||  v[1]  )
        && (  v[18] || !v[0]  )
        && (  v[19] ||  v[1]  )
        && (  v[19] || !v[18] )
        && ( !v[19] || !v[9]  )
        && (  v[0]  ||  v[17] )
        && ( !v[1]  ||  v[20] )
        && ( !v[21] ||  v[20] )
        && ( !v[22] ||  v[20] )
        && ( !v[21] || !v[20] )
        && (  v[22] || !v[20] );
        
        
        if (value) {
			save.saveResult(v, size, z, output);
		}	
    }
}
