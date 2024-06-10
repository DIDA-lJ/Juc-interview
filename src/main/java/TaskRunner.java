import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public class TaskRunner {

    public static void main(String[] args) {
        // 假设创建了 100 个任务
        List<Runnable> tasks = TaskRunner.createTasks();

        // 第一批和第二批执行的任务索引
        List<Integer> batch1 = new ArrayList<Integer>(Arrays.asList(1, 3, 5, 7));
        List<Integer> batch2 = new ArrayList<Integer>(Arrays.asList(11, 13, 15, 17));

        List<List<Integer>> batchs = new ArrayList<>();
        batchs.add(batch1);
        batchs.add(batch2);

        try {
            runTasksInBatches(tasks, batchs);
            System.out.println("====== 全部任务执行完成！======");

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void runTasksInBatches(List<Runnable> tasks, List<List<Integer>> batchs) throws ExecutionException, InterruptedException {
        // 使用固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 执行第一批任务
        List<Future<Void>> futures = new ArrayList<Future<Void>>();
        Set<Integer> allBatchTaskIndexs = new HashSet<Integer>();
        for (int i = 0; i < batchs.size(); i++) {
            System.out.println("====== 第 " + (i + 1) + " 批任务开始执行！======");
            for (int index : batchs.get(i)) {
                Future<Void> future = (Future<Void>) executor.submit(tasks.get(index));
                futures.add(future);
                allBatchTaskIndexs.add(index);
            }
            // 等待一批任务完成
            for (Future<Void> f : futures) {
                f.get();
            }
            futures.clear();
            System.out.println("====== 第 " + (i + 1) + " 批任务执行完成！======");
        }

        // 执行剩下的任务
        System.out.println("====== 最后 1 批任务开始执行！======");
        for (int i = 0; i < tasks.size(); i++) {
            if (!allBatchTaskIndexs.contains(i)) {
                executor.submit(tasks.get(i));
            }
        }


        // 关闭线程池，并且等待所有任务执行
        executor.shutdown();
        while (!executor.isTerminated()) {
            // 等待所有任务执行完毕
        }
        System.out.println("====== 最后 1 批任务执行完成！======");
    }

    private static List<Runnable> createTasks() {
        List<Runnable> tasks = new ArrayList<>();
        // 创建任务的逻辑
        for (int i = 0; i < 30; i++) {
            final int taskId = i;
            tasks.add(() -> {
                try {
                    long startTime = System.currentTimeMillis();
                    Thread.sleep((long) (Math.random() * 1000));
                    long endTime = System.currentTimeMillis();
                    System.out.println("执行任务，任务 ID :" + taskId + ",耗时:" + (endTime - startTime) + "ms");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return tasks;
    }
}
