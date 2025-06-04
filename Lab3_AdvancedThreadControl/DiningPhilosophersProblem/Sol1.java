import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sol1 {

    static final int numphils = 6;
    static final int sleeptime = 1;
    
    
     public static void main(String[] args) {
       Philosopher[] phil= new Philosopher[numphils];
       Fork[] fork = new Fork[numphils];

       for (int i =0; i<numphils; ++i)
            fork[i] = new Fork(i);
       for (int i =0; i<numphils; ++i){
          /*
          * Modification: Alternating the order of fork acquisition for even and odd philosophers.
          * 
          * In the original problem all philosophers try to pick up their left fork first,
          * then the right fork, this creates a circular wait leading to a deadlock
          * 
          * By reversing the order of fork acquisition for odd-numbered philosophers, we break this circular wait
          * This significantly reduces the risk of deadlock by ensuring that at least one philosopher is likely to proceed 
          * preventing the system from getting fully stuck
          * 
          * However, if we have an even number of philosophers, we greatly reduce the possibility of deadlock
          * but we cannot fully guarantee it. This is because:
          * Pairs of philosophers could end up picking up forks in the same order creating isolated deadlock cycles
          * For example, philosophers 0 and 2 could take their right forks and wait for the left, 
          * while philosophers 1 and 3 do the opposite, resulting in deadlock
          * (In my experiment with an even number of philosophers a deadlock did eventually happen 
          * but it took a significant amount of time)
          * 
          * This situation cannot happen with an odd number of philosophers because there will always be 
          * one philosopher left out of this cycle breaking the chain
          *
          */

          if(i%2 == 0){
               phil[i] = new Philosopher(i, (i+1)%numphils, sleeptime,
                        fork[i], fork[(i+1)%numphils]);
          }
          else{
               phil[i] = new Philosopher(i, (i+1)%numphils, sleeptime,
               fork[(i+1)%numphils] , fork[i]);
          }     
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
     }

     static public class Philosopher extends Thread {
          private int identity;
          private Fork left;
          private Fork right;
          private int scale;
          private int next;
        
          Philosopher(int id, int n, int s, Fork l, Fork r) {
                 identity = id; next = n; scale = s; left = l; right = r; 
          }
        
          public void run() {
             while (true) {
                //thinking
                  System.out.println(" Philosopher "+ identity + " is thinking");
                delay(scale);
                //hungry
                    System.out.println(" Philosopher "+ identity+ " is trying to get fork " + identity);
                right.get();
                //gotright chopstick
                  System.out.println(" Philosopher "+ identity+ " got fork " + identity + " and is trying to get fork " + next);
                left.get();
                System.out.println(" Philosopher "+ identity + " is eating");
                //eating
                System.out.println(" Philosopher "+ identity + " is releasing left fork " + next);
                //delay(scale);
                left.put();
                  System.out.println(" Philosopher "+ identity + " is releasing fork " + identity);
                  //delay(scale);
                right.put();
             }    
          }
        
          public void delay(int scale) {
             try {
                   sleep((int)(Math.random()*scale));
             } catch (InterruptedException e) { }
          }
     }
        

}