package threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

class ProducerConsumerPattern {

    /**
     * executor for the task.
     */
    private final ExecutorService ex = Executors.newCachedThreadPool();
    /**
     * queue contains tasks.
     */
    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    /**
     * number of consumers.
     */
    private static final int N_CONSUMERS = 100;

    /**
     * Poison pill to stop threads.
     */
    static final int POISON_PILL = -1;

    void start() {

        ex.execute(new ProducerTask(queue));

        for (int i = 0; i < N_CONSUMERS; i++) {
            ex.execute(new ConsumerTask(queue));
        }
        ex.shutdown();
    }

    public static void main(final String[] args) {
        new ProducerConsumerPattern().start();
    }
}

class ProducerTask implements Runnable {

    /**
     * will push the task to queue.
     */
    private final BlockingQueue<Integer> queue;

    ProducerTask(final BlockingQueue<Integer> blockingQueue) {
        this.queue = blockingQueue;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println("Puts-" + Thread.currentThread().getName()
                    + ": " + i);
            queue.add(i);
        }

        // add poison pill to queue to notify consumer tasks
        queue.add(ProducerConsumerPattern.POISON_PILL);
    }
}

class ConsumerTask implements Runnable {

    /**
     * will process tasks in queue.
     */
    private final BlockingQueue<Integer> queue;

    ConsumerTask(final BlockingQueue<Integer> blockingQueue) {
        this.queue = blockingQueue;
    }

    @Override
    public void run() {

        try {

            while (true) {

                int take = queue.take();
                if (take == ProducerConsumerPattern.POISON_PILL) {
                    // put poison back for other consumers and exit
                    queue.put(take);
                    break;
                }
                Thread.sleep (1000);
                System.out.println(Thread.currentThread().getName()
                        + " consuming: " + take);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
