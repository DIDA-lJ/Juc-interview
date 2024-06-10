/**
 * @author linqi
 * @version 1.0.0
 * @description 三个线程顺序打印 1-100
 */

public class AlternatePrintingThreeThreads {
    /**
     * 当前要打印的数字
     */
    private int currentNumber = 1;
    /**
     * 用于同步的锁对象
     */
    private final Object lock = new Object();
    /**
     * 控制哪个线程应该打印的标识 0:3n ,1:3n + 1,2: 3n + 2
     */
    private int turn = 0;

    public static void main(String[] args) {
        AlternatePrintingThreeThreads ap = new AlternatePrintingThreeThreads();

        // 创建并启动三个线程
        Thread t1 = new Thread(() -> ap.printNumbers(0));
        Thread t2 = new Thread(() -> ap.printNumbers(1));
        Thread t3 = new Thread(() -> ap.printNumbers(2));

        t1.start();
        t2.start();
        t3.start();

    }

    /**
     * 根据 turn 的值打印对应范围的数字
     *
     * @param offset 0:3n 1:3n+1 2:3n+2
     */
    private void printNumbers(int offset) {
        while (currentNumber <= 100) {
            synchronized (lock) {
                while ((turn % 3) != offset) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (currentNumber <= 100 && (currentNumber - 1) % 3 == offset) {
                    System.out.println("Thread " + (offset + 1) + " printed: " + currentNumber);
                    currentNumber++;
                    turn = (turn + 1) % 3;
                    lock.notifyAll();
                }
            }
        }
    }
}
