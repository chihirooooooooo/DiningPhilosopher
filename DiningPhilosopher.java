import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;
public class DiningPhilosopher {
  private final static Random generator = new Random();
  private final Semaphore running = new Semaphore(1,true);
  private static Semaphore [] chopstick = new Semaphore [5];
  static {
    for(int i = 0; i < 5; i++) {
        chopstick[i] = new Semaphore(1,true);
    }  
  }
  public void StartTest(int k) {
    for (int i = 1; i < k+1; i++) {
        Philosopher person = new Philosopher(i);
        person.start();
    }
  } 
  public class Philosopher extends Thread{
    int philosopher;
    int first_chopstick;
    int second_chopstick;
    Philosopher(int i){
      philosopher = i;
      if(i!=5){
        first_chopstick = i-1;
      }else{
        first_chopstick = i-2;
      }
      if(i == 1){
        second_chopstick = 4;
      }else if(i == 5){
        second_chopstick = 4;
      }else{
        second_chopstick = i-2;
      }
    }
    @Override
    public void run() {
      try{
        System.out.println("Philosopher " + philosopher + " thinking");
        Thread.sleep(generator.nextInt(1000)); 
        chopstick[first_chopstick].acquire();
        running.acquire();
        if(philosopher != 5){
          System.out.println("Philosopher " + philosopher + " Picked up left chopstick");
        }else{
          System.out.println("Philosopher " + philosopher + " Picked up right chopstick");
        }
        running.release();
        chopstick[second_chopstick].acquire();
        running.acquire();
        if(philosopher != 5){
          System.out.println("Philosopher " + philosopher + " Picked up right chopstick");
        }else{
          System.out.println("Philosopher " + philosopher + " Picked up left chopstick");
        }
        running.release();
        Thread.sleep(generator.nextInt(2000));
        running.acquire();
        if(philosopher != 5){
          System.out.println("Philosopher " + philosopher + " Put down left chopstick");
        }else{
          System.out.println("Philosopher " + philosopher + " Put down right chopstick");
        }
        chopstick[first_chopstick].release();
        running.release();
        running.acquire();
        if(philosopher != 5){
          System.out.println("Philosopher " + philosopher + " Put down right chopstick");
        }else{
          System.out.println("Philosopher " + philosopher + " Put down left chopstick");
        }
        chopstick[second_chopstick].release();
        running.release(); 
        run();
      }catch(InterruptedException e){
        System.out.println("error");
      } 
    }
  }
  public static void main(String[] args) {
    int running_time = Integer.parseInt(args[0]);
    long start= System.currentTimeMillis(); 
    long end= start + running_time * 1000; 
    DiningPhilosopher test = new DiningPhilosopher();
    try{
          test.StartTest(5);
    } catch (Exception e) {
      System.out.println(e);
    }
    while (System.currentTimeMillis() < end) {
      // Some expensive operation on the item.
    }
    System.exit(0);
    

  }
}
