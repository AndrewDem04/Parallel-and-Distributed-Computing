/*
 * I decided to have EXTEND and Implement Methods in my program SO ThreadA extends Thread(Klironomikotita) 
 * And ThreadB implements Runnable(Polymorfismos)
*/
public class TwoDifferentThreadClasses {
    public static void main(String[] args) {


        int numThreads = 2;
        Thread[] threads = new Thread[numThreads];           
        
        System.out.println("In main: create and start threadA ");
        threads[0] = new TheardA();
        threads[0].start();
        System.out.println("In main: create and start threadB ");
        threads[1] = new Thread(new TheardB());
        threads[1].start();

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

