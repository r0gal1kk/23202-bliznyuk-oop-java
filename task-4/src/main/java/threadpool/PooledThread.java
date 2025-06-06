package threadpool;

import factory.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;

class PooledThread extends Thread {
    private final ArrayDeque<Task> taskQueue;
    private boolean isRunning;
    private Logger logger = LoggerFactory.getLogger(Factory.class.getName());

    public PooledThread(String name, ArrayDeque<Task> taskQueue, boolean isRunning) {
        super(name);
        this.isRunning = isRunning;
        this.taskQueue = taskQueue;
    }

    void finish() {
        isRunning = false;
        taskQueue.clear();
    }

    public Task getTask() {
        return taskQueue.peek();
    }

    @Override
    public void run() {
        Task task = null;
        while (true) {
            synchronized (taskQueue) {
                if (taskQueue.isEmpty() && isRunning) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Thread was interrupted: " + getName());
                    }
                    continue;
                } else {
                    task = taskQueue.remove();
                }
            }
            try {
                task.execute();
            } catch (InterruptedException e){
                logger.info("Thread has been interrupted {}", task.getTaskName());
            }
            logger.info("{} got the task {}", getName(), task.getTaskName());
        }
        //logger.info("{} is shutting down", getName());
    }
}