import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sol3 {

    static final int numphils = 5;
    static final int sleeptime = 1;
    static ConcurrentHashMap<Integer,Integer> tokens = new ConcurrentHashMap<Integer,Integer>();
    
        
         public static void main(String[] args) {
           Philosopher[] phil= new Philosopher[numphils];
           Fork[] fork = new Fork[numphils];
    
       for (int i =0; i<numphils; i = i + 2){
          // I intialize the hashmap in a way that gives two forks in its Philosopher that can get them 
          tokens.put(i, i);
          tokens.put(i + 1, i);
       }
          

       for (int i =0; i<numphils; ++i)
          fork[i] = new Fork(i);
       for (int i =0; i<numphils; ++i){
            phil[i] = new Philosopher(i, sleeptime,
                        fork[i], fork[(i+1)%numphils] , tokens);
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
     * I decided to make my tokens with a concurent hash map 
     * the reason is that i will need to access them alot hash map are a fast way because the have a 
     * complexity of O(1) also a concurent hash map is useful for threading.
     * The keys of the hash map represents it's fork and the values of the keys represents a Philosophers 
     * that can access the given key
     * the passToken method is created in a way that ensures it passes access to it's token in  a  
     * Philosopher that can access the given fork                                                         
     */
     static public class Philosopher extends Thread {
          private int identity;           
          private Fork left;
          private Fork right;
          private int scale , RightId , LeftId;
          ConcurrentHashMap<Integer,Integer> tokens;

          Philosopher(int id, int s, Fork l, Fork r , ConcurrentHashMap<Integer,Integer> tokens) {
                 this.identity = id; scale = s; left = l; right = r;
                 this.tokens = tokens;
                 this.RightId = r.getId();
                 this.LeftId = l.getId();
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
                    if (tokens.get(RightId) == identity && tokens.get(LeftId) == identity) {  // Check if the philosopher has the token
                       System.out.println("Philosopher " + identity + " has a token.");
                       left.get();
                       System.out.println("Philosopher " + identity + " picked up left fork " + LeftId);
                       right.get();
                       System.out.println("Philosopher " + identity + " picked up right fork " + RightId);
                       return true;
                    }
                    return false;
           }
   
           private void eat() {
               System.out.println("Philosopher " + identity + " is eating.");
               delay(scale);
           }
   
           private void passToken() {
               // I have the if because we have a cylcle and i don't want to break it 
               if(tokens.get(RightId) == numphils - 1) tokens.put(RightId, 0); 
               else tokens.put(RightId, (tokens.get(RightId) + 1));
               System.out.println("Philosopher " + identity + " is passing the token to philosopher " + tokens.get(RightId));
               if(tokens.get(LeftId) == 0) tokens.put(LeftId, numphils - 1);
               else tokens.put(LeftId, (tokens.get(LeftId) - 1));
               System.out.println("Philosopher " + identity + " is passing the token to philosopher " + tokens.get(LeftId));
               
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