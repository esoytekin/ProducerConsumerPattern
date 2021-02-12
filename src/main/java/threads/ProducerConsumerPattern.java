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

    void start() {

        ex.execute(new ProducerTask(queue));
        ex.execute(new ConsumerTask(queue));
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
                if (take == 9) {
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
