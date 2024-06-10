import java.util.concurrent.CountDownLatch;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public class ThreadExecutionOrderDemo {
    private static CountDownLatch t1ToT2Latch = new CountDownLatch(1);
    private static CountDownLatch t2ToT3Latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println("T1 开始执行!");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("T1 执行时间:" + (endTime - startTime) + "ms");
            System.out.println("T1 执行完毕!");
            System.out.println("------------------------------------------------");
            t1ToT2Latch.countDown();
        }, "T1");

        Thread t2 = new Thread(() -> {
            try{
                t1ToT2Latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long startTime = System.currentTimeMillis();
            System.out.println("T2 开始执行!");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("T2 执行时间:" + (endTime - startTime) + "ms");
            System.out.println("T2 执行完毕!");
            System.out.println("------------------------------------------------");
            t2ToT3Latch.countDown();
        }, "T2");
        Thread t3 = new Thread(() -> {
            try{
                t2ToT3Latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long startTime = System.currentTimeMillis();
            System.out.println("T3 开始执行!");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("T3 执行时间:" + (endTime - startTime) + "ms");
            System.out.println("T3 执行完毕!");
        }, "T3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();


    }

}
