import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public class AtomicCounterDemo {

    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // 创建100个线程来增加计数器的值
        ExecutorService executor = Executors.newFixedThreadPool(100);

        // 提交 100 个任务，每个任务执行 100 次累加
        for (int i = 0; i < 100; i++) {
            executor.submit(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        counter.incrementAndGet();
                    }
                }
            });
        }

        // 关闭线程池
        executor.shutdown();
        // 等待所有任务完成
        executor.awaitTermination(1, TimeUnit.HOURS);

        // 输出最终的值
        System.out.println("Final counter value: " + counter.get());
    }
}
