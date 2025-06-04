/*
* We have the same Problem if we don't store the threads,
 we can't synchronize them with join(), and the main() method may terminate before they finish.
*/
public class HelloThreadArgs {

    public static void main(String[] args) {

        int numThreads = 20;

        for (int i = 0; i < numThreads; ++i) {
            System.out.println("In main: create and start thread " + i);
            new MyThread(i, numThreads).start();
            
        }
        
        // Because main can't understend when all threads all done , it prints just when the loop is done 
        System.out.println("In main: threads all done");
    }
}

class MyThread extends Thread {

    private int myID;
    private int totalThreads;

    public MyThread(int myID, int totalThreads) {
        this.myID = myID;
        this.totalThreads = totalThreads;
    }

    public void run() {
        System.out.println("Hello from thread " + myID + " out of " + totalThreads);
        System.out.println("Thread " + myID + " exits");
        
    } 

}

