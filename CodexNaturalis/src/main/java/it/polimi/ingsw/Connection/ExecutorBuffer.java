package it.polimi.ingsw.Connection;


import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class used for Async RMI task execution
 * */
public class ExecutorBuffer implements Serializable {
    private final ExecutorService executorService;

    /**
     * Creates a new ExecutorBuffer.
     * It creates a new thread pool with a cached number of threads.
     */
    public ExecutorBuffer() {
        executorService = Executors.newCachedThreadPool();
    }

    /**
     *
     * Executes a task in a separate thread.
     * @param task the task to execute
     */
    public void execute (Runnable task) {
        executorService.execute(task);
    }

    public ExecutorBuffer(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }
}
