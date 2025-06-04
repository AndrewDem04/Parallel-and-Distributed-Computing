public class TenThreadsEachClass {
    public static void main(String[] args) {


        int numThreads = 20;
        Thread[] threads = new Thread[numThreads];           
        
        for (int i = 0; i < numThreads; i = i +2) {
            System.out.println("In main: create and start threadA " + i);
            threads[i] = new TheardA();
            threads[i].start();
            System.out.println("In main: create and start threadB " + (i + 1));
            threads[i + 1] = new Thread(new TheardB());
            threads[i + 1].start();
        }
        
        for (int i = 0; i < numThreads; ++i) {
            try {
                threads[i].join();
            }
             catch (InterruptedException e) {}
        }

        System.out.println("In main: threads all done");
        
    }
}

class TheardA extends Thread {
    public void run() { 
        System.out.println("This Thread EXTENDS Thread ");
    } 
}

class TheardB implements Runnable {
    public void run() {
        System.out.println("This Thread IMPLEMENTS Runnable , then we put it in a Thread");
    } 
}
