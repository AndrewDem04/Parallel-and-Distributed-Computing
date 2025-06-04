import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sol2 {

    static final int numphils = 5;
    static final int sleeptime = 1;
    
    
     public static void main(String[] args) {
       Philosopher[] phil= new Philosopher[numphils];
       Fork[] fork = new Fork[numphils];

       for (int i =0; i<numphils; ++i)
            fork[i] = new Fork(i);
       for (int i =0; i<numphils; ++i){
            phil[i] = new Philosopher(i, sleeptime,
                        fork[i], fork[(i+1)%numphils]);
            phil[i].start();
       }
    }

     static public class Fork {

          private int identity;
          private Lock lock = new ReentrantLock();

          Fork(int id)
               {identity = id;}

          void get() {
  	          lock.lock();
          }

          void put() {
               lock.unlock();
          }

          public int getId() {
               return identity;
          }
     }
     /* 
     * I wanted to test a token based solution 
     * How it works; We have a token When a Philosophers eats we pass the token to the next one 
     * Although its a simple solution it can becomme slow if we add meny Philosophers 
     * because it unnecessary locks some philosofers out 
     * In Sol 3 i will make a token based solution but I will
     * only lock the philosofers that use the same forks
     */
     static public class Philosopher extends Thread {
          private int identity;
          private Fork left;
          private Fork right;
          private int scale;
          private static int tokenIndex = 0;

          Philosopher(int id, int s, Fork l, Fork r) {
                 this.identity = id; scale = s; left = l; right = r;
          }
        
          public void run() {
               while (true) {
                   think();
                   if (canPickUpForks()) {
                       eat();
                       passToken();
                   }
               }
           }
   
           private void think() {
               /*
                * I decided to remove the print because it overwelmed 
                * the output screen with bloatware
                */
               //System.out.println("Philosopher " + identity + " is thinking.");
               delay(scale);
           }
   
           private boolean canPickUpForks() {
                    if (tokenIndex == identity) {  // Check if the philosopher has the token
                       System.out.println("Philosopher " + identity + " has a token.");
                       left.get();
                       System.out.println("Philosopher " + identity + " picked up left fork " + left.getId());
                       right.get();
                       System.out.println("Philosopher " + identity + " picked up right fork " + right.getId());
                       return true;
                    }
                    return false;
           }
   
           private void eat() {
               System.out.println("Philosopher " + identity + " is eating.");
               delay(scale);
           }
   
           private void passToken() {
               // Pass the token to the next philosopher
               tokenIndex = (tokenIndex + 1) % numphils;
               System.out.println("Philosopher " + identity + " is passing the token to philosopher " + tokenIndex);
               left.put();
               right.put();
               
           }
        
          public void delay(int scale) {
             try {
                   sleep((int)(Math.random()*scale));
             } catch (InterruptedException e) { }
          }
        }
        

}