/* 
* if we isolate the output of one thread:
* the output is clear,
* the calculations follow a sequential order.
*
* if all threads print simultaneously:
* The output gets mixed because threads run in parallel.
* It's harder to see which thread prints what.
*/
public class MultiplesThreadedPrinter {

    public static void main(String[] args) {

        int numThreads = 20;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; ++i) {
            threads[i] = new Thread(new MultiplicationTask(i));
            threads[i].start();
        }

        for (int i = 0; i < numThreads; ++i) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {
                System.err.println("this should not happen");
            }
        }

        System.out.println("In main: threads all done");
    }
}

class MultiplicationTask implements Runnable {

    private int number;

    public MultiplicationTask(int number) {
        this.number = number;
    }

    public void run() {
        for (int i = 1; i <= 20; i++) {
            System.out.println(i + " * " + number + " = " + (i * number));
        }
    }

}


