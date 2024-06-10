import java.util.Random;

/**
 * @author linqi
 * @version 1.0.0
 * @description T2 在 T1 之后执行，T3 在 T2 之后执行
 */

public class ThreadJoinDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Task("T1"), "T1");
        Thread t2 = new Thread(new Task("T2"), "T2");
        Thread t3 = new Thread(new Task("T3"), "T3");


        // 启动 t1
        t1.start();
        try {
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private static class Task implements Runnable {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            System.out.println(name + " 开始执行！");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " 执行完毕！");
            long endTime = System.currentTimeMillis();
            System.out.println(name + " 执行时间:" + (endTime - startTime) + "ms");
            System.out.println("--------------------------------");
        }
    }
}
