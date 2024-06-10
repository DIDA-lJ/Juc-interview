/**
 * @author linqi
 * @version 1.0.0
 * @description 线程交替打印 12A34B56C 使用 wait() 和 notifyAll()
 */

public class CrossPrint {
    private static final Object lock = new Object();
    private static boolean printNumber  = true;

    public static void main(String[] args) {
        Thread printNumberThread = new Thread(() ->{
           for(int i = 1; i <= 52; i=i+2){
               synchronized (lock){
                   while(!printNumber){
                       try {
                           lock.wait();
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
                   System.out.print(i);
                   System.out.print(i + 1);
                   printNumber = false;// 打印切换标志
                   lock.notifyAll();// 唤醒等待的线程
               }
           }
        });

        Thread printLetterThread = new Thread(() ->{
           for(char c = 'A'; c <= 'Z'; c++){
               synchronized (lock){
                   while(printNumber){
                       try {
                           lock.wait();
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
                   System.out.print(c);
                   printNumber = true;// 打印切换标志
                   lock.notifyAll();// 唤醒等待的线程
               }
           }
        });

        printLetterThread.start();
        printNumberThread.start();
    }
}
