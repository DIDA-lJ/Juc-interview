/**
 * @author linqi
 * @version 1.0.0
 * @description 两个线程轮流打印 1~100
 */

public class AlternatePrinting {
    private int currentNumber = 1;
    private final Object lock = new Object();

    public static void main(String[] args) {
        AlternatePrinting ap = new AlternatePrinting();

        // 创建奇数打印线程
        Thread oddPrinter = new Thread(() -> {
            ap.printNumber(true);
        });
        oddPrinter.start();

        // 创建偶数打印线程
        Thread evenPrinter = new Thread(() -> {
            ap.printNumber(false);
        });
        evenPrinter.start();
    }

    /**
     * 根据 isOdd 标志打印奇数或者偶数
     *
     * @param flag true：奇数 false：偶数
     */
    private void printNumber(boolean flag) {
        while (currentNumber <= 100) {
            synchronized (lock) {
                while ((flag && currentNumber % 2 == 0) || (!flag && currentNumber % 2 == 1)) {
                    try {
                        // 如果当前线程不应该打印,等待
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (currentNumber <= 100) {
                    System.out.println("Thread " + (flag ? "Odd " : "Even") + ": " + currentNumber);
                    currentNumber++;
                    lock.notifyAll();
                }
            }
        }
    }


}
