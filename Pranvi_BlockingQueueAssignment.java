package thread_concept;

import java.util.concurrent.*;
 
public class BlockingQueueAssignment {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(20);
        Thread producer1 = new Thread(new Producer(queue));
        Thread producer2 = new Thread(new Producer(queue));
        Thread producer3 = new Thread(new Producer(queue));
        Thread consumer1 = new Thread(new Consumer(queue));
        Thread consumer2 = new Thread(new Consumer(queue));
        Thread consumer3 = new Thread(new Consumer(queue));
        Thread consumer4 = new Thread(new Consumer(queue));
        Thread consumer5 = new Thread(new Consumer(queue));
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer5.start(); 
    }
}
class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    private String threadId;
    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
    public void run() {
        threadId = "Consumer-" + Thread.currentThread().getId();
        try {
            while (true) {
                Integer number = queue.poll(5, TimeUnit.SECONDS);
                if (number == null || number == -1) 
                    break;
                consume(number);
                Thread.sleep(1000);
            }
            System.out.println(threadId + " STOPPED.");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    private void consume(Integer number) {
        System.out.println(threadId + ": Consuming number " + number);
    }
}


class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    private String threadId;
    public Producer (BlockingQueue<Integer> queue) {
        this.queue = queue;
    }
 
    public void run() {    	
    	threadId = "Producer-" + Thread.currentThread().getId();
        try {
        	for (int i = 0; i < 10; i++) {
        		Integer number = new Integer((int) (Math.random() * 100));
                queue.put(produce(number));
                System.out.println(threadId + ": Producing number " + number);
                Thread.sleep(500);
                
            }
            queue.put(-1); 
            System.out.println(threadId + " STOPPED.");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    private Integer produce(Integer number) {
        System.out.println("Producing number " + number);
        return number;
    }
}
